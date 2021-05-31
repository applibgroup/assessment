package com.example.todolist.Model;

public class ToDoListModel extends TaskId {
    private String taskName, taskDesc, taskDate, taskTime;
    public ToDoListModel()
    {}
    public ToDoListModel(String taskName, String taskDesc, String taskDate, String taskTime, String id)
    {
        this.taskName=taskName;
        this.taskDesc=taskDesc;
        this.taskDate=taskDate;
        this.taskTime=taskTime;
        this.withID(id);
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public String getTaskTime() {
        return taskTime;
    }
}
