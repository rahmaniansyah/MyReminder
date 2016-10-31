package com.example.rahmaniansyahdp.myreminder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rahmaniansyah DP on 29/10/2016.
 */

public class AlarmAdapter extends ArrayAdapter<DbJadwal.TJadwal>{

    public AlarmAdapter(Context context, int resource){
        super(context, resource);
    }

    public AlarmAdapter(Context context, List<DbJadwal.TJadwal> jadwals){
        super(context, R.layout.item_listview, jadwals);
    }

    public static class ViewHolder{
        TextView textJam ;
        TextView textKegiatan ;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //mengambil data berasarrkan posisi
        DbJadwal.TJadwal jadwal = getItem(position) ;
        //check tampilan apabila digunakan oleh yang lain
        ViewHolder viewHolder ;
        if(convertView == null){
            //jika belum ada view untuk ditampilkan
            //create new view
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listview,null) ;
            viewHolder = new ViewHolder();
            viewHolder.textJam = (TextView) convertView.findViewById(R.id.textJam) ;
            viewHolder.textKegiatan = (TextView) convertView.findViewById(R.id.textKegiatan) ;
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag() ;
        }

        viewHolder.textJam.setText(jadwal.waktu);
        viewHolder.textKegiatan.setText(jadwal.kegiatan);
        return convertView ;
    }
}
