package android.example.libusage;

import android.app.Application;
import android.example.libusage.dag.DaggerRetroComponent;
import android.example.libusage.dag.RetroComponent;
import android.example.libusage.dag.RetroModule;

import com.facebook.stetho.Stetho;

public class MyApplication extends Application {

    private RetroComponent retroComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if(BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

        retroComponent = DaggerRetroComponent.builder()
                .retroModule(new RetroModule())
                .build();
    }

    public RetroComponent getRetroComponent(){
        return retroComponent;
    }
}
