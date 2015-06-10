package com.iot11.exdatabaseapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private MyDatabase myDB;

    private String dbName = "dbHelper";
    private String tableName = "TTable";

    EditText edTableName;
    TextView tvLogmsg;
    public static final String TAG = "MainActivity";

    private void LogMsg(String msg){
        Log.i(TAG, msg);
        //tvLogmsg.append(msg+"\n");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLogmsg = (TextView)findViewById(R.id.textView);

        myDB = new MyDatabase(this);
        myDB.openDatabase();
    }

    public void onButtonInsertClicked(View v) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDatabase.TablePerson.NAME, "수지");
        contentValues.put(MyDatabase.TablePerson.AGE, 22);

        myDB.insert(Uri.parse("content://"),contentValues );
//        myDB.insertData("김재훈", 28);
//        myDB.insertData("한경석", 26);
//        myDB.insertData("이창욱", 26);
    }

    public void onButtonSelectClicked(View v) {
        Cursor data = myDB.selectData();
        for(int i=0; i != data.getCount(); ++i) {
            data.moveToNext();
            String name = data.getString(0);
            int age = data.getInt(1);
            tvLogmsg.append("\nselect : "+i+name+age);
        }
        data.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDB.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
