package com.example.rahmaniansyahdp.myreminder;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rahmaniansyah DP on 29/10/2016.
 */

public class OpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dbMyreminder.db" ;
    public static final  String TABLE_CREATE =
            "CREATE TABLE JADWAL (ID INTEGER PRIMARY KEY AUTOINCREMENT, WAKTU TEXT, KEGIATAN TEXT)" ;

    public OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS JADWAL");
    }
}
