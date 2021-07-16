package android.example.todoapp.Model;

public class taskobject extends taskid {
    public String title;
    public String details;
    public String date;
    public String time;
    public String id;
    public taskobject(String title, String details, String date, String time,String id) {
        this.title = title;
        this.details = details;
        this.date = date;
        this.time = time;
        this.id = id;
    }
}
