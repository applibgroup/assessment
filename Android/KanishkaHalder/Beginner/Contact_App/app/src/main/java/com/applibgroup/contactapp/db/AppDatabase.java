package com.applibgroup.contactapp.db;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.applibgroup.contactapp.AllFragment;
import com.applibgroup.contactapp.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

@Database(entities = {User.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    private static AppDatabase INSTANCE;

    private static final Object LOCK = new Object();

    public static AppDatabase getINSTANCE(final Context context) {
        if(INSTANCE == null){
            synchronized (LOCK) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "DB")
                        .addCallback(new Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                super.onCreate(db);
                                final List<User> users = new ArrayList<User>();
                                AsyncTask.execute(new Runnable() {
                                    @Override
                                    public void run() {

                                        //Pre-populating the Database
                                        try {
                                            InputStreamReader is = new InputStreamReader(context.getAssets().open("dummyDetails.csv"));
                                            BufferedReader reader = new BufferedReader(is);
                                            reader.readLine();
                                            String line;
                                            StringTokenizer st = null;

                                            ArrayList<Integer> imageNameList = new ArrayList<>();
                                            imageNameList.add(R.drawable.avatar2);
                                            imageNameList.add(R.drawable.avatar_female);
                                            imageNameList.add(R.drawable.img_avatar2);

                                            ArrayList<byte[]> imagesList = new ArrayList<>();

                                            for (int i = 0; i < imageNameList.size(); i++) {
                                                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageNameList.get(i));
                                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // what 90 does ??
                                                byte[] image = stream.toByteArray();
                                                imagesList.add(image);
                                            }
                                            int i = 0;
                                            while ((line = reader.readLine()) != null) {
                                                Log.d("CSV OUTPUT", line);
                                                st = new StringTokenizer(line, ",");
                                                User user = new User();
                                                user.setFirstName(st.nextToken());
                                                user.setLastName(st.nextToken());
                                                user.setEmail(st.nextToken());
                                                user.setPhoneNumber(st.nextToken());
                                                user.setImage(imagesList.get(i));
                                                users.add(user);
                                                i++;
                                                if (i == 3)
                                                    i = 0;
                                            }

                                            getINSTANCE(context).userDao().insertUsers(users);

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                //AllFragment.updateUserList(users);
                            }
                        })
                        .allowMainThreadQueries()
                        .build();
                return INSTANCE;
            }
        } else {
            return INSTANCE;
        }
    }

}
