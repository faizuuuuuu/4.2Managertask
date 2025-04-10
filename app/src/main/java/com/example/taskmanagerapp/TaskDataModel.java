package com.example.taskmanagerapp;

public class TaskDataModel {
    private int val_id;
    private String val_title;
    private String val_desc;
    private String val_due;

    public TaskDataModel(String val_title, String val_desc, String val_due) {
        this.val_title = val_title;
        this.val_desc = val_desc;
        this.val_due = val_due;
    }

    public int getVal_id() {
        return val_id;
    }

    public void setVal_id(int val_id) {
        this.val_id = val_id;
    }

    public String getVal_title() {
        return val_title;
    }

    public void setVal_title(String val_title) {
        this.val_title = val_title;
    }

    public String getVal_desc() {
        return val_desc;
    }

    public void setVal_desc(String val_desc) {
        this.val_desc = val_desc;
    }

    public String getVal_due() {
        return val_due;
    }

    public void setVal_due(String val_due) {
        this.val_due = val_due;
    }
}
