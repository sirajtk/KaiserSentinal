package com.example.kaisersentinel;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;

public class ConfigureActivity extends AppCompatActivity {


    EditText t11,t12,t13,t14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);
       t11=findViewById(R.id.editText7);
        t12=findViewById(R.id.editText5);
        t13=findViewById(R.id.editText9);
        t14=findViewById(R.id.editText8);

        SentinalDatabase db=new SentinalDatabase(this);
        SQLiteDatabase my =db.getReadableDatabase();
        String projection[]={"t11,t12,t13,t14"};
        Cursor c=my.query("configure",projection,null,null,null,null,null);
        c.moveToFirst();
        String t11=c.getString(2).toString();
        String t12=c.getString(3).toString();
        String t13=c.getString(12).toString();
        String t14=c.getString(13).toString();

        



    }
}
