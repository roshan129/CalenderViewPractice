package com.roshanadke.calenderviewpractice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="practice.db";
    private static final String TABLE_NAME="wallet";
    private static final String USER_TABLE="user_data";
    //private static final String TEST="cutomer";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

        // SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " +TABLE_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT)");
     /*   sqLiteDatabase.execSQL("CREATE TABLE " +USER_TABLE+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,DOB TEXT,GENDER TEXT,MOBILE TEXT,EMAIL TEXT,PASSWORD TEXT)");*/
        //sqLiteDatabase.execSQL("CREATE TABLE " +TEST+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,MOBILE_NUMBER INTEGER,EMAIL TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //===============Insert Data=======================
    public  boolean insertData(String date)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("DATE",date);

        Long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

        if (result==-1)

            return  false;
        else
            return true;
    }

    //==============Select data============================
    public Cursor getData(String date)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        String query ="SELECT * FROM " +TABLE_NAME+ " WHERE DATE LIKE '" + date + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        return cursor;
    }


    public Cursor showData()
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        //  String query =("SELECT * FROM " +TABLE_NAME+ );
        String query="SELECT * FROM " +TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        return cursor;
    }
    public Cursor getData2(String date2)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        String query ="SELECT * FROM " +TABLE_NAME+ " WHERE DATE LIKE '" + date2 + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        return cursor;
    }

    public  boolean insertUserDetails(String name, String dob, String gender, String mobile, String email, String password)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",name);
        contentValues.put("DOB",dob);
        contentValues.put("GENDER",gender);
        contentValues.put("MOBILE",mobile);
        contentValues.put("EMAIL",email);
        contentValues.put("PASSWORD",password);
        Log.d("SSSS",name);

        long result = sqLiteDatabase.insert(USER_TABLE,null,contentValues);

        if (result==-1)
            return  false;
        else
            return true;

    }

    public boolean userLogin(String mobile, String password)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        String query ="SELECT * FROM " +USER_TABLE+ " WHERE MOBILE = '" + mobile + "' AND "+ "PASSWORD = '" + password + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        if(cursor.getCount() != 0){
            return true;
        }else{
            return false;
        }
    }

}
