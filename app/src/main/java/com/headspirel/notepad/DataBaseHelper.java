package com.headspirel.notepad;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.ArrayList;

/**
 *DataBaseHelper class contains all the operations of the database.*/
public class DataBaseHelper extends SQLiteOpenHelper {

    //name of the data base
    private static final String DB_NAME="notepad_DB.db";
    //name of the table
    private static final String TABLE_NAME="notepad";
    //primary auto increamented field
    private static final String COL_ID="id";
    private static final String COL_1="data";
    private static final String COL_2="date";

 public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "
                +TABLE_NAME+" ("+COL_ID+" integer primary key autoincrement" +
                ",data text,date text)");
    }

    /**
     * any change in the database table in future is written in this function.
     * @param db instance of the SQLiteDatabase
     * @param oldVersion an integer value
     * @param newVersion an integer value representing change in database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * making any change in the data stored in the database.
     * @param tableData instance of the model class.
     * @param id tables primary key ID value.
     */
    public void updateData(TableData tableData,int id)
    {
        SQLiteDatabase database=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,tableData.getNote());
        contentValues.put(COL_2,tableData.getDate());
        database.update(TABLE_NAME,contentValues,"id = "+id,null);
    }

    /**
     *
     * @param tableData instance of the model class.
     * @return boolean value that represent success in operation.
     */
    public boolean insertData(TableData tableData)
    {
        SQLiteDatabase database=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,tableData.getNote());
        contentValues.put(COL_2,tableData.getDate());
        long result=database.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    /**
     *
     * @return ArrayList of type model class, containing all the data stored in database.
     */
    public ArrayList<TableData> getAllData()
    {
        ArrayList<TableData> arrayList=new ArrayList<>();
        SQLiteDatabase database=getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=database.rawQuery("select * from "+TABLE_NAME,null);
        if(cursor.moveToFirst())
        {
            do {
                int id=cursor.getInt(0);
                String note = cursor.getString(1);
                String date=cursor.getString(2);
                TableData tableData = new TableData();
                tableData.setId(id);
                tableData.setNote(note);
                tableData.setDate(date);
                arrayList.add(tableData);
            }
            while (cursor.moveToNext());
        }
        return arrayList;
    }

    /**
     *
     * @param data String value of Data oject
     * @return boolean value that represents the success of operation.
     */
    public boolean delete(String data)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,"data LIKE '"+data+"'",null);
        return true;
    }
}
