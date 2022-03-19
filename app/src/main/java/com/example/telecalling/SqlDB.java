package com.example.telecalling;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqlDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Telecalling.db";
    public static final String TABLE_NAME = "userlist";
    public static final String COL_1 = "name";
    public static final String COL_2 = "mobile";
    public static final String COL_3 = "Status";
    public static final String COL_4 = "comments";

    public SqlDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (name TEXT,mobile TEXT,Status TEXT,comments TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    /*public boolean insertData(String name, String number, String status, String comments ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,name);
        contentValues.put(COL_2,number);
        //contentValues.put(COL_3,status);
        //contentValues.put(COL_4,comments);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }*/



    public boolean insertData(String name, String number, String status, String comments )  {
    SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,name);
        contentValues.put(COL_2,number);
        contentValues.put(COL_3,status);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }


    public int getUpdate(String number, Integer state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_3,state);
        int res=db.update(TABLE_NAME, cv, "mobile="+(number), null);
        return res;

    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME,null);
        return res;
    }
}
