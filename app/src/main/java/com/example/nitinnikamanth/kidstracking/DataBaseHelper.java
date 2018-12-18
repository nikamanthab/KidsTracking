package com.example.nitinnikamanth.kidstracking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "Kiddetails.db";
    public static final String TABLE_NAME = "kiddetails";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "PARENT_NAME";
    public static final String COL_3 = "KID_NAME";
    public static final String COL_4 = "UUID";
    public static final String COL_5 = "MAJOR";
    public static final String COL_6 = "MINOR";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,PARENT_NAME TEXT,KID_NAME TEXT,UUID INTEGER,MAJOR INTEGER,MINOR INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String parentname,String kidname,String uuid,String major,String minor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,parentname);
        contentValues.put(COL_3,kidname);
        contentValues.put(COL_4,uuid);
        contentValues.put(COL_5,major);
        contentValues.put(COL_6,minor);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String id,String parentname,String kidname,String uuid,String major,String minor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,parentname);
        contentValues.put(COL_3,kidname);
        contentValues.put(COL_4,uuid);
        contentValues.put(COL_5,major);
        contentValues.put(COL_6,minor);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteData (String kidname ) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, " KID_NAME= ?",new String[] {kidname});
    }


}
