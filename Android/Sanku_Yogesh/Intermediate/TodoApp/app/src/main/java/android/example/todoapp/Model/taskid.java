package android.example.todoapp.Model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class taskid {
    @Exclude
    public String taskid;

    public < T extends taskid> T withID(@NonNull final String id){
        this.taskid = id;
        return (T) this;
    }
}
