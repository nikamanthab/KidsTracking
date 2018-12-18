package com.example.nitinnikamanth.kidstracking;

import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ResultActivity extends AppCompatActivity {

    BeaconManager beaconManager;
    BeaconRegion beaconRegion;
    int kidmajor,kidminor;
    String agestring;
    String lastScannedTime = "";
    private CountDownTimer countDownTimer = null;
    int age ;
    double x;
    Button stop;
    TextView uuid , major , minor , kidname , kidage ,inout ,time;
    TextView dist;
    double avg , d1,d2,d3,d4,d5;
    String selected_kid_uuid,selected_kid_name;//selected_kid_major,selected_kid_minor,
    String distance_log = "";

    SharedPreferences s;
    SharedPreferences.Editor ed;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        s = getApplicationContext().getSharedPreferences("kidsage",0);
        ed = s.edit();
        username = getIntent().getStringExtra("username");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        inout = (TextView)findViewById(R.id.inout);
        uuid = (TextView)findViewById(R.id.uuid);
        major = (TextView)findViewById(R.id.major);
        minor = (TextView)findViewById(R.id.minor);
        kidname = (TextView)findViewById(R.id.kidname);
        kidage = (TextView)findViewById(R.id.age);
        stop = (Button)findViewById(R.id.stopbutton);
        time = (TextView)findViewById(R.id.time);
        dist = (TextView)findViewById(R.id.dist);
        selected_kid_uuid = getIntent().getStringExtra("uuid");

        selected_kid_name = getIntent().getStringExtra("kidname");

        kidmajor = Integer.parseInt(getIntent().getStringExtra("major"));
        kidminor = Integer.parseInt(getIntent().getStringExtra("minor"));

        uuid.setText(selected_kid_uuid);
        major.setText(""+kidmajor);
        minor.setText(""+kidminor);
        kidname.setText(selected_kid_name);
       // Toast.makeText(this,String.valueOf(s.getInt(username + selected_kid_name+"age",0)) , Toast.LENGTH_SHORT).show();
        agestring = String.valueOf(s.getInt(username + selected_kid_name+"age" , 0));
        age = s.getInt(username + selected_kid_name+"age" , 0);
        kidage.setText(String.valueOf(age));

        beaconManager = new BeaconManager(ResultActivity.this);

        //lastScannedTime = currentTime();

        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> beacons) {
                Log.e("upside down" , String.valueOf(beacons.size()));
                if(beacons.size() == 0){
                    inout.setText("outside range");
                    inout.setTextColor(getResources().getColor(R.color.red));
                    sound();
                }
                else {
                    Beacon beacon = beacons.get(0);
                    Log.e("TAg" , "beacon rssi : "+beacon.getRssi() + "\tTxpower : "+beacon.getMeasuredPower()
                            +"\tDistance : "+getRange(beacon.getRssi() , beacon.getMeasuredPower()));
                    distance_log += "beacon rssi : "+beacon.getRssi() + "\tTxpower : "+beacon.getMeasuredPower()
                            +"\tDistance :    "+String.format("%.4f",calculateDistance(beacon.getRssi() , beacon.getMeasuredPower()))+"m\n" ;
                    lastScannedTime = currentTime();
                    dist.setText(distance_log);
                    x = calculateDistance(beacon.getRssi() , beacon.getMeasuredPower());
                    Log.e("x" , String.valueOf(x));
//                    Toast.makeText(ResultActivity.this, s.getString(username + "range1" , null), Toast.LENGTH_SHORT).show();
                    if(age <= 2){
                        Log.e("tag",s.getString(username + "range1" , null));
                        if (x < Double.parseDouble(s.getString(username + "range1" , null))){
                            ed.putInt(username+selected_kid_name+"timekey" , 1);
                            ed.commit();
                            inout.setText("inside range");
                            inout.setTextColor(getResources().getColor(R.color.white));
                            //Toast.makeText(ResultActivity.this, "inside range", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(s.getInt(username + selected_kid_name + "timekey" , -1) == 1 )
                            {
                                ed.putString(username+selected_kid_name+"time" , currentTime());
                                ed.putInt(username+selected_kid_name+"timekey" , 0);
                                ed.commit();
                                time.setText(s.getString(username+selected_kid_name+"time" , null));

                            }
                            inout.setText("outside range");
                            inout.setTextColor(getResources().getColor(R.color.red));
                            sound();
                           // Toast.makeText(ResultActivity.this, "outside range", Toast.LENGTH_SHORT).show();

                        }
                    }

                    else if(age <= 4){
                        if (x < Double.parseDouble(s.getString(username + "range2" , null))){
                            ed.putInt(username+selected_kid_name+"timekey" , 1);
                            ed.commit();
                            inout.setText("inside range");
                            inout.setTextColor(getResources().getColor(R.color.white));
                            //Toast.makeText(ResultActivity.this, "inside range", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(s.getInt(username + selected_kid_name + "timekey" , -1) == 1 )
                            {
                                ed.putString(username+selected_kid_name+"time" , currentTime());
                                ed.putInt(username+selected_kid_name+"timekey" , 0);
                                ed.commit();
                                time.setText(s.getString(username+selected_kid_name+"time" , null));

                            }
                            inout.setText("outside range");
                            inout.setTextColor(getResources().getColor(R.color.red));
                            sound();
                           // Toast.makeText(ResultActivity.this, "outside range", Toast.LENGTH_SHORT).show();

                        }
                    }

                    else if(age <= 6){
                        if (x < Double.parseDouble(s.getString(username + "range3" , null))){
                            ed.putInt(username+selected_kid_name+"timekey" , 1);
                            ed.commit();
                            inout.setText("inside range");
                            inout.setTextColor(getResources().getColor(R.color.white));
                            //Toast.makeText(ResultActivity.this, "inside range", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(s.getInt(username + selected_kid_name + "timekey" , -1) == 1 )
                            {
                                ed.putString(username+selected_kid_name+"time" , currentTime());
                                ed.putInt(username+selected_kid_name+"timekey" , 0);
                                ed.commit();
                                time.setText(s.getString(username+selected_kid_name+"time" , null));

                            }
                            inout.setText("outside range");
                            inout.setTextColor(getResources().getColor(R.color.red));
                            sound();
                           // Toast.makeText(ResultActivity.this, "outside range", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else if(age <= 8){
                        if (x < Double.parseDouble(s.getString(username + "range4" , null))){
                            ed.putInt(username+selected_kid_name+"timekey" , 1);
                            ed.commit();
                            inout.setText("inside range");
                            inout.setTextColor(getResources().getColor(R.color.white));
                            //Toast.makeText(ResultActivity.this, "inside range", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(s.getInt(username + selected_kid_name + "timekey" , -1) == 1 )
                            {
                                ed.putString(username+selected_kid_name+"time" , currentTime());
                                ed.putInt(username+selected_kid_name+"timekey" , 0);
                                ed.commit();
                                time.setText(s.getString(username+selected_kid_name+"time" , null));

                            }
                            inout.setText("outside range");
                            inout.setTextColor(getResources().getColor(R.color.red));
                            sound();
                          //  Toast.makeText(ResultActivity.this, "outside range", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else if(age <= 10){
                        if (x < Double.parseDouble(s.getString(username + "range5" , null))){
                            ed.putInt(username+selected_kid_name+"timekey" , 1);
                            ed.commit();
                            inout.setText("inside range");
                            inout.setTextColor(getResources().getColor(R.color.white));
                            //Toast.makeText(ResultActivity.this, "inside range", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(s.getInt(username + selected_kid_name + "timekey" , -1) == 1 )
                            {
                                ed.putString(username+selected_kid_name+"time" , currentTime());
                                ed.putInt(username+selected_kid_name+"timekey" , 0);
                                ed.commit();
                                time.setText(s.getString(username+selected_kid_name+"time" , null));

                            }
                            inout.setText("outside range");
                            inout.setTextColor(getResources().getColor(R.color.red));
                            sound();
                           // Toast.makeText(ResultActivity.this, "outside range", Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.e("tag","stop clicked");
                finish();
            }
        });

        startTimer();

    }

    private void sound(){
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String currentTime(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        Calendar c = Calendar.getInstance();
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    private void startTimer(){
         countDownTimer = new CountDownTimer(5000 , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("tag" , "timerrunning "+millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.e("tag" , "timerfinished");
                if(!(lastScannedTime.equals("") )){
                    if(getTimeDifferance(lastScannedTime , currentTime()) > 30){
                        if(s.getInt(username + selected_kid_name + "timekey" , -1) == 1 )
                        {
                            ed.putString(username+selected_kid_name+"time" , currentTime());
                            ed.putInt(username+selected_kid_name+"timekey" , 0);
                            ed.commit();
                            time.setText(s.getString(username+selected_kid_name+"time" , null));

                        }
                        inout.setText("outside range");
                        inout.setTextColor(getResources().getColor(R.color.red));
                        sound();
                    }
                }
                else {
                    inout.setText("outside range");
                    inout.setTextColor(getResources().getColor(R.color.red));
                    sound();
                }
                countDownTimer.start();

            }
        };
        countDownTimer.start();
    }

    private long getTimeDifferance(String startDate , String endDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        long diffInSec ;
        try{
            Date date1 = simpleDateFormat.parse(startDate);
            Date date2 = simpleDateFormat.parse(endDate);
            long  difference = date2.getTime() - date1.getTime();
             diffInSec = TimeUnit.MILLISECONDS.toSeconds(difference);

        }
        catch (ParseException e){
            diffInSec = 0;
            e.printStackTrace();

        }
        Log.e("diff" , ""+diffInSec+ "  " + startDate +" " +endDate);
        return diffInSec;
    }

    private double getRange(int rssi, int txCalibratedPower) {
        double ratio_db = txCalibratedPower - rssi;
        double ratio_linear = Math.pow(10, ratio_db / 10);

        double r = Math.sqrt(ratio_linear);
        return r;
    }

    private double calculateDistance(int rssi , int txpower) {

        if (rssi == 0) {
            return -1.0;
        }

        double ratio = rssi*1.0/txpower;
        if (ratio < 1.0) {
            return Math.pow(ratio,10);
        }
        else {
            double distance =  (0.89976)*Math.pow(ratio,7.7095) + 0.111;
            return distance;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                // Toast.makeText(getApplicationContext(), "monitoring starts", Toast.LENGTH_SHORT).show();
                beaconRegion = new BeaconRegion("BeaconRegion" ,UUID.fromString(selected_kid_uuid) , kidmajor , kidminor);
                ed.putInt(username + selected_kid_name + "tracking" , 1);
                int x=s.getInt(username + selected_kid_name + "tracking" , -1);
                Log.e("tracking" ,String.valueOf(x));
                time.setText(s.getString(username+selected_kid_name+"time" , null));
                beaconManager.startRanging(beaconRegion);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       beaconManager = null;
        countDownTimer.cancel();
        countDownTimer = null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        beaconManager.stopRanging(beaconRegion);
    }
}
