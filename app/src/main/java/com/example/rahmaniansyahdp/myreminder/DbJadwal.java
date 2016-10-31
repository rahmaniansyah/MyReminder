package com.example.rahmaniansyahdp.myreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Rahmaniansyah DP on 29/10/2016.
 */

public class DbJadwal {

    public static class TJadwal{
        String waktu ;
        String kegiatan ;
        int id;
    }


    private SQLiteDatabase db ;
    private final OpenHelper dbHelper ;

    public DbJadwal(Context c) {
        dbHelper = new OpenHelper(c);
    }

    public void open(){
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    //delete data
    public long deleteJadwal(int id){
        return db.delete("JADWAL","ID='"+ id +"'",null);
    }

    //Masukan data
    public long insertJadwal(String waktu, String kegiatan){
        ContentValues newValues = new ContentValues();
        newValues.put("WAKTU", waktu);
        newValues.put("KEGIATAN", kegiatan);
        return db.insert("JADWAL",null,newValues);
    }

    //Masukan data
    public long updateJadwal(String waktu, String kegiatan,int id){
        ContentValues newValues = new ContentValues();
        newValues.put("WAKTU", waktu);
        newValues.put("KEGIATAN", kegiatan);
        return db.update("JADWAL",newValues,"ID='"+ id +"'",null);
    }

    //ambil data
    public TJadwal[] getAllJadwal(){
        Cursor cur = null ;

        //kolom yang diambil
        String[] cols = new String[]{"ID","WAKTU","KEGIATAN"};
        String[] param = {} ;

        cur = db.query("JADWAL",cols,null,null,null,null,null) ;
        TJadwal[] jadwals = new TJadwal[cur.getCount()] ;

        if(cur.getCount()>0){
            cur.moveToFirst() ;
            int i = 0 ;
            int batas = cur.getCount() ;
                while(i<batas){
                    TJadwal jadwal = new TJadwal();
                    jadwal.id = cur.getInt(0);
                    jadwal.waktu = cur.getString(1);
                    jadwal.kegiatan = cur.getString(2);
                    jadwals[i] = jadwal;
                    i = i + 1 ;
                    cur.moveToNext() ;
                }
        }
        return jadwals ;
    }
}
