package com.example.irfan.squarecamera;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "picture_db";
    private static final String TABLE_SQUARE = "picture_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_WAKTUMULAI = "waktu_mulai";
    private static final String COLUMN_WAKTUSELESAI = "waktu_selesai";
    private static final String COLUMN_WAKTU = "waktu_upload";

    public Database(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TODO_TABLE =  "CREATE    TABLE " +
                TABLE_SQUARE +
                "(" + COLUMN_ID +
                " STRING PRIMARY KEY," +
                COLUMN_WAKTUMULAI +
                " DOUBLE," +
                COLUMN_WAKTUSELESAI +
                " DOUBLE," +
                COLUMN_WAKTU +
                " DOUBLE" +")";
        sqLiteDatabase.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SQUARE);
        onCreate(sqLiteDatabase);
    }

    public void addItem(Picture picture){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, picture.getId());
        values.put(COLUMN_WAKTUMULAI,picture.getWaktuMulai());
        values.put(COLUMN_WAKTUSELESAI,picture.getWaktuSelesai());
        values.put(COLUMN_WAKTU,picture.getWaktu());
        SQLiteDatabase db =this.getWritableDatabase();
        db.insert(TABLE_SQUARE,null,values);
    }

    public List<Picture> getAll(){
        String sqlQuery = "SELECT * FROM " + TABLE_SQUARE;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Picture> items = new ArrayList<>();
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor.moveToFirst()){
            do{
                String id=cursor.getString(0);
                String waktuMulai=cursor.getString(1);
                String waktuSelesai=cursor.getString(2);
                String waktu = cursor.getString(3);
                items.add(new Picture(id, waktuMulai,waktuSelesai,waktu));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    public void deletePictures(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SQUARE,COLUMN_ID +"  = ?", new String[]{id});
    }
}
