package com.example.kaisersentinel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.example.kaisersentinel.DataModal.Values;

import java.util.ArrayList;
import java.util.List;

//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.provider.Telephony;
//
//import java.sql.Blob;
//
public class SentinalDatabase extends SQLiteOpenHelper {

    private static final String dbname = "kaiser";
    private static final String table_name = "homesentinal";
    private static final String col1 = "id_no";
    private static final String col2 = "unixtime";
    private static final String col3 = "timestamp";
    private static final String col4 = "hub_mac";
    private static final String col5 = "user_mac_1";
    private static final String col6 = "user_mac_2";
    private static final String col7 = "user_mac_3";
    private static final String col8 = "user_mac_4";
    private static final String col9 = "user_mac_5";
    private static final String col10 = "channel";
    private static final String col11 = "alert_type";
    private static final String col12 = "marked";
    private static final String col13 = "description";
    private static final String col14 = "image";

    static final int version = 1;

  ImageView imageView;


    public SentinalDatabase(Context context) {

        super(context, dbname, null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table sentinal(id_no INTEGER PRIMARY KEY AUTOINCREMENT,unixtime varchar(25),timestamp varchar(25),hub_mac varchar(25),user_mac varchar(25),channel varchar(10),alert_type varchar(20),marked varchar(20),description varchar(25),image BLOB,processed varchar(20))");
        db.execSQL("create table homesentinal(id_no INTEGER PRIMARY KEY AUTOINCREMENT,unixtime varchar(25),timestamp varchar(25),hub_mac varchar(25),user_mac_1 varchar(25),user_mac_2 varchar(25),user_mac_3 varchar(25),user_mac_4 varchar(25),user_mac_5 varchar(25),channel varchar(10),alert_type varchar(20),marked varchar(20),description varchar(25),image BLOB,processed varchar(20))");
        db.execSQL("create table input(id_no INTEGER PRIMARY KEY AUTOINCREMENT,event varchar(25),image BLOB,time varchar(20))");
        db.execSQL("create table login(mac_id INTEGER PRIMARY KEY AUTOINCREMENT,username varchar(25),password varchar(20))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists " + table_name);
        db.execSQL("drop table if exists " + "input");
        db.execSQL("drop table if exists " + "login");


        onCreate(db);
    }

//    public boolean insertData() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(col1, "15");
//        contentValues.put(col2, "19299");
//        contentValues.put(col3, "287719");
//        contentValues.put(col4, "192.168.10.101");
//        contentValues.put(col5, "192.161.1.200");
//        contentValues.put(col6, "192.161.1.201");
//        contentValues.put(col7, "192.161.1.202");
//        contentValues.put(col8, "192.161.1.203");
//        contentValues.put(col9, "192.161.1.204");
//        contentValues.put(col10, "C4");
//        contentValues.put(col11, "PERSON");
//        contentValues.put(col12, "NO");
//        contentValues.put(col13, "PERSON WALKING");
//        contentValues.put(col14, "100100010");
//        long result = db.insert(table_name, null, contentValues);
//        if (result == -1)
//            return false;
//        else
//            return true;
//
//    }




    //
//
    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from " + table_name, null);
        return res;

    }
    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + table_name;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    public List<Values> getAllvalues() {
        List<Values> languageList = new ArrayList<Values>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + table_name;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Values lang = new Values();
                lang.setId_no(Integer.parseInt(cursor.getString(0)));
                lang.setUnixtime(cursor.getString(1));
                lang.setTimestamp(cursor.getString(2));
                lang.setHub_mac(cursor.getString(3));
                lang.setUser_mac(cursor.getString(4));
                lang.setChannel(cursor.getString(5));
                lang.setAlert_type(cursor.getString(6));
                lang.setMarked(cursor.getString(7));
                lang.setDescription(cursor.getString(8));
                lang.setImage(cursor.getBlob(13));

                languageList.add(lang);
            } while (cursor.moveToNext());
        }

        return languageList;
    }


//    public List<Values> getAllvalues() {
//        List<Values> languageList = new ArrayList<Values>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM  image";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Values lang = new Values();
//                lang.setId_no(Integer.parseInt(cursor.getString(1)));
//
//
//                languageList.add(lang);
//            } while (cursor.moveToNext());
//        }
//
//        return languageList;
//    }

//    public  void save(View view) throws IOException {
//        SQLiteDatabase db = this.getWritableDatabase();
//        FileInputStream fis = new FileInputStream("/Internal%20shared%20storage/Pictures/download.jpeg");
//        byte[] image= new byte[fis.available()];
//        fis.read(image);
//        ContentValues values = new ContentValues();
//        values.put("a",image);
//        db.insert("image", null,values);
//        fis.close();
//
//    }
//    public void getdata(View view) {
//
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery("select * from image", null);
//        if(c.moveToNext())
//        {
//            byte[] image = c.getBlob(0);
//            Bitmap bmp= BitmapFactory.decodeByteArray(image, 0 , image.length);
//            imageView.setImageBitmap(bmp);
//
//        }
//    }

    public Bitmap getImage(Integer id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Bitmap bt=null;
        Cursor c = db.rawQuery("select * from image where id_no=?",new String[]{String.valueOf(id)});
        if(c.moveToNext())
        {
            byte[] image=c.getBlob(1);
            bt=BitmapFactory.decodeByteArray(image,0,image.length);
        }
        return bt;
    }

}

//}
