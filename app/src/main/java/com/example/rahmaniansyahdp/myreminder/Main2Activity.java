package com.example.rahmaniansyahdp.myreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {

    //----------------------------------
    //Timepicker
    TimePicker myTimePicker ;
    Button ambiljam ;
    TimePickerDialog timePickerDialog ;
    TextView textAlarmPrompt ;
    Calendar targetCal ;

    final static int RQS_1 = 1 ;
    //----------------------------------

    //EditText fieldWaktuJam ;
    //EditText fieldWaktuMenit ;
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

        //fieldWaktuJam = (EditText) findViewById(R.id.editJam) ;
        //fieldWaktuMenit = (EditText) findViewById(R.id.editMenit) ;
        fieldKegiatan = (EditText) findViewById(R.id.editKegiatan);
        textAlarmPrompt = (TextView) findViewById(R.id.alarmprompt) ;

        intent = getIntent();
        id = intent.getIntExtra("id",0);
        waktu = intent.getStringExtra("waktu");
        kegiatan = intent.getStringExtra("kegiatan");

        if(id!=0){
            textAlarmPrompt.setText(waktu);
            fieldKegiatan.setText(kegiatan);
        }
        db = new DbJadwal(getApplication()) ;

        //----------------------------------
        ambiljam = (Button) findViewById(R.id.ambilJam);
        ambiljam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textAlarmPrompt.setText("");
                openTimePickerDialog(false);
            }
        });
        //----------------------------------
    }

    //----------------------------------
    private void openTimePickerDialog(boolean is24r){
        Calendar calendar = Calendar.getInstance() ;

        timePickerDialog = new TimePickerDialog(Main2Activity.this,
                onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),true);
        timePickerDialog.setTitle("Atur waktu");
        timePickerDialog.show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if (calSet.compareTo(calNow) <= 0) {
                // Today Set time passed, count to tomorrow
                calSet.add(Calendar.DATE, 1);
                Log.i("hasil", " =<0");
            } else if (calSet.compareTo(calNow) > 0) {
                Log.i("hasil", " > 0");
            } else {
                Log.i("hasil", " else ");
            }

            targetCal = calSet ;
            setAlarm();

        }
    };


    private void setAlarm() {

        textAlarmPrompt.setText("***\n" + "Alarm set on " + targetCal.getTime()
                + "\n***");

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                pendingIntent);

    }

    //----------------------------------

    public void simpanData(View view){
        //get jam dan menit dari edit text
        //String waktuJam = fieldWaktuJam.getText().toString();
        //String waktuMenit = fieldWaktuMenit.getText().toString();
        String waktu = "" + targetCal.getTime() ;
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
