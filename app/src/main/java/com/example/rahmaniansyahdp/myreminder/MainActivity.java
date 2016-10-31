package com.example.rahmaniansyahdp.myreminder;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<DbJadwal.TJadwal> items = new ArrayList<DbJadwal.TJadwal>();
    AlarmAdapter adapter ;
    DbJadwal db ;
    ListView listView ;

    static final int ACT2_REQUEST = 99 ; //request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //panggil database
        db = new DbJadwal(getApplication()) ;

       listView = (ListView) findViewById(R.id.listJadwal);
       adapter = new AlarmAdapter(this, items) ;
       listView.setAdapter(adapter);
       tampilData();

       //untuk klik lama pada lsit view
       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showInsertUpdateDialog(position);
                return true;
            }
        });

    }

    private void tampilData() {
        //menampilkan dari database
        db.open();
        DbJadwal.TJadwal jadwal[] = db.getAllJadwal() ;
        if(jadwal.length>0){
            adapter.clear();
            //jika ada data maka akan ditampilkan di list
            for(int i=0;i<jadwal.length;i++){
                adapter.add(jadwal[i]);
            }
        }
        db.close();
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
        if (id == R.id.action_tambah) {
            Intent intent = new Intent(this, Main2Activity.class);
            startActivityForResult(intent,ACT2_REQUEST);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //untuk mengetahui bahwa MainActivity 2 telah diclose
    //lalu data akan diupdate
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //cek request code
        if(requestCode == ACT2_REQUEST){
            Log.d("Main","panggil");
            tampilData();
        }
    }

    //menampilkan dialog insert update
    //layout untuk dialog ada di layout/layout_dialog_insertdelete
    private void showInsertUpdateDialog(final int position) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_dialog_insetdelete,null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();

        //button
        Button deleteButton = (Button)dialogView.findViewById(R.id.deleteButton);
        Button updatekButton = (Button)dialogView.findViewById(R.id.updateButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Delete",Toast.LENGTH_SHORT).show();
                //delete data pada database
                db.open();
                db.deleteJadwal(adapter.getItem(position).id);
                db.close();
                //menghilangkan alert dialog
                alertDialog.dismiss();
                //menampilkan data yang baru
                tampilData();
            }
        });

        updatekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Update",Toast.LENGTH_SHORT).show();

                //pergi ke activity 2
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("id",adapter.getItem(position).id);
                intent.putExtra("waktu",adapter.getItem(position).waktu);
                intent.putExtra("kegiatan",adapter.getItem(position).kegiatan);
                startActivityForResult(intent,ACT2_REQUEST);

                alertDialog.dismiss();
            }
        });


        alertDialog.show();
    }
}
