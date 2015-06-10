package com.iot11.exdatabaseapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by iot11 on 15. 5. 27.
 */
public class MyDatabase extends ContentProvider{

    public static final String TAG = "MyDatabase";

    public static final String DATABASE_NAME = "person";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "iot";
    public static final String DROP_TABLE = "drop table if exists "+ TABLE_NAME;

    public static final String CREATE_SQL = "create table " + TABLE_NAME
            +"("
            +TablePerson._ID + " integer PRIMARY KEY autoincrement,"
            +TablePerson.NAME + " text,"
            +TablePerson.AGE + " integer);";

    private MyDataBaseHelper myDataBaseHelper;
    private SQLiteDatabase myDatabase;
    private Context context;

    public interface TablePerson extends BaseColumns{
        public static final String NAME = "name";
        public static final String AGE = "age";
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        String name = contentValues.getAsString(TablePerson.NAME);
        String age = contentValues.getAsString(TablePerson.AGE);

        if( myDatabase == null)
            LogMsg("insertDate : failed");
        else {
            String query = "insert into "+ TABLE_NAME
                    +"(name, age) values('"+name+"', "+age+");";
            myDatabase.execSQL(query);
            LogMsg("insertData : " + query);
        }

        return null;

    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }



    public void LogMsg(String msg) {
        Log.i(TAG, msg);
    }

    public MyDatabase(Context context) {
        this.context = context;
    }

    public boolean openDatabase() {
        myDataBaseHelper = new MyDataBaseHelper(context, DATABASE_NAME, null, 1);
        myDatabase = myDataBaseHelper.getWritableDatabase();

        return true;
    }

//    public boolean insertData(String name, int age ) {
//
//        values.getAsString();
//        String query = "insert into "+ TABLE_NAME +"(name, age) values('"+name+"', "+age+");";
//
//        myDatabase.execSQL(query);
//        LogMsg("insertData : " + query);
//        return true;
//    }

    public Cursor selectData() {
        Cursor data;
        String selectSql = "select name, age from "+ TABLE_NAME+";";
        data = myDatabase.rawQuery(selectSql, null);
        int count = data.getCount();
        LogMsg("selectData : "+((Integer)count).toString());

        return data;
    }

    public boolean close() {
        myDatabase.close();
        myDatabase = null;

        return true;
    }

    private class MyDataBaseHelper extends SQLiteOpenHelper {

        //private SQLiteOpenHelper dbHelper;

        public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            // create db
            db.execSQL(DROP_TABLE);
            LogMsg("MyDataBaseHelper : " + DROP_TABLE);

            // create table
            db.execSQL(CREATE_SQL);
            LogMsg("MyDataBaseHelper : " + CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if(newVersion > 1) {
                db.execSQL(DROP_TABLE);
            }

        }
    }
}
