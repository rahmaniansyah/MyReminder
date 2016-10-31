package com.example.rahmaniansyahdp.myreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

    EditText fieldWaktuJam ;
    EditText fieldWaktuMenit ;
    EditText fieldKegiatan ;
    DbJadwal db ;

    Intent intent;
    int id;
    String waktu;
    String kegiatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        fieldWaktuJam = (EditText) findViewById(R.id.editJam) ;
        fieldWaktuMenit = (EditText) findViewById(R.id.editMenit) ;
        fieldKegiatan = (EditText) findViewById(R.id.editKegiatan);

        intent = getIntent();
        id = intent.getIntExtra("id",0);
        waktu = intent.getStringExtra("waktu");
        kegiatan = intent.getStringExtra("kegiatan");

        if(id!=0){
            fieldWaktuJam.setText(waktu);
            fieldKegiatan.setText(kegiatan);
        }
        db = new DbJadwal(getApplication()) ;
    }

    public void simpanData(View view){
        //get jam dan menit dari edit text
        String waktuJam = fieldWaktuJam.getText().toString();
        String waktuMenit = fieldWaktuMenit.getText().toString();
        String waktu = waktuJam + ":" + waktuMenit;
        String namaKegiatan = fieldKegiatan.getText().toString();

        db.open();
        if(id!=0){
            db.updateJadwal(waktu,namaKegiatan,id);
        }else{
            db.insertJadwal(waktu,namaKegiatan);
        }
        db.close();


        setResult(RESULT_OK,intent);
        //close activity 2
        finish();

    }
}
