package com.example.taskmanagerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "TaskManagerDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_TASKS = "tasks";

    private static final String COL_ID = "id";
    private static final String COL_TITLE = "title";
    private static final String COL_DESC = "description";
    private static final String COL_DATE = "due_date";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db_for) {
        String query_for_create = "CREATE TABLE " + TABLE_TASKS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITLE + " TEXT, " +
                COL_DESC + " TEXT, " +
                COL_DATE + " TEXT)";
        db_for.execSQL(query_for_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db_for, int oldVersion_for, int newVersion_for) {
        db_for.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db_for);
    }

    // Add a new task
    public void insertTask(String title_for, String desc_for, String date_for) {
        SQLiteDatabase db_for = this.getWritableDatabase();
        ContentValues val_for_insert = new ContentValues();
        val_for_insert.put(COL_TITLE, title_for);
        val_for_insert.put(COL_DESC, desc_for);
        val_for_insert.put(COL_DATE, date_for);
        db_for.insert(TABLE_TASKS, null, val_for_insert);
        db_for.close();
    }

    // Get all tasks sorted by due date
    public List<TaskDataModel> getAllTasks() {
        List<TaskDataModel> task_list_for = new ArrayList<>();
        SQLiteDatabase db_for = this.getReadableDatabase();
        Cursor cur_for = db_for.rawQuery("SELECT * FROM " + TABLE_TASKS + " ORDER BY " + COL_DATE, null);

        if (cur_for.moveToFirst()) {
            do {
                TaskDataModel task_for = new TaskDataModel(
                        cur_for.getString(1),
                        cur_for.getString(2),
                        cur_for.getString(3)
                );
                task_for.setVal_id(cur_for.getInt(0));

                task_list_for.add(task_for);
            } while (cur_for.moveToNext());
        }

        cur_for.close();
        db_for.close();
        return task_list_for;
    }

    // Update a task
    public void updateTask(int id_for, String title_for, String desc_for, String date_for) {
        SQLiteDatabase db_for = this.getWritableDatabase();
        ContentValues val_for_update = new ContentValues();
        val_for_update.put(COL_TITLE, title_for);
        val_for_update.put(COL_DESC, desc_for);
        val_for_update.put(COL_DATE, date_for);
        db_for.update(TABLE_TASKS, val_for_update, COL_ID + "=?", new String[]{String.valueOf(id_for)});
        db_for.close();
    }

    // Delete a task
    public void deleteTask(int id_for) {
        SQLiteDatabase db_for = this.getWritableDatabase();
        db_for.delete(TABLE_TASKS, COL_ID + "=?", new String[]{String.valueOf(id_for)});
        db_for.close();
    }
}
