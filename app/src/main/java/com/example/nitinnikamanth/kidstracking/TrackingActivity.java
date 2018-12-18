package com.example.nitinnikamanth.kidstracking;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import io.reactivex.internal.schedulers.TrampolineScheduler;

public class TrackingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    List<String> kids;
    DataBaseHelper myDb;
    private Spinner spin;
//    private TextView details;
    int kidmajor,kidminor;
    UUID kiduuid;
    ImageButton ranging;
    String username , password , selected_kid_name , selected_kid_age , selected_kid_uuid , selected_kid_major , selected_kid_minor;
    Button addbutton,adv;
//    TextView kidnamedisp;

    SharedPreferences s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
//        details = (TextView)findViewById(R.id.details);
        s = getApplicationContext().getSharedPreferences("kidsage", 0);

        spin = (Spinner)findViewById(R.id.kids);
        addbutton = (Button)findViewById(R.id.addbutton);
        adv = (Button)findViewById(R.id.adv);
        //view = (Button)findViewById(R.id.view);
        ranging = (ImageButton) findViewById(R.id.ranging);
        myDb = new DataBaseHelper(this);
        spin.setOnItemSelectedListener(this);
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
       // kidnamedisp = (TextView)findViewById(R.id.kidnamedisp);

//        Toast.makeText(this, "Tracking Activity Started", Toast.LENGTH_SHORT).show();
        kids = new ArrayList<String>();

        /*while (res.moveToNext()) {
            if(username.equals(res.getString(1))){
                kids.add(res.getString(2));
            }
        }

*/
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(TrackingActivity.this , KidAddActivity.class);
                i.putExtra("username",username);
                i.putExtra("password",password);
                startActivity(i);
            }
        });

        ranging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selected_kid_name.equals("select a kid")){
                    Toast.makeText(TrackingActivity.this, "select from list", Toast.LENGTH_SHORT).show();
                }
                else{
//                    kidnamedisp.setText(selected_kid_name);
                    Intent i = new Intent(TrackingActivity.this, ResultActivity.class);
                    i.putExtra("username",username);
                    kidmajor = Integer.parseInt(selected_kid_major);
                    kidminor = Integer.parseInt(selected_kid_minor);
                    kiduuid = UUID.fromString(selected_kid_uuid);
                    i.putExtra("uuid",selected_kid_uuid);
                    i.putExtra("major",selected_kid_major);
                    i.putExtra("minor",selected_kid_minor);
                    i.putExtra("kidname",selected_kid_name);
                    startActivity(i);
                }

            }
        });

        adv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TrackingActivity.this , AdvancedSettingsActivity.class);
                i.putExtra("username",username);
                i.putExtra("password",password);
                startActivity(i);
            }
        });


    }



    @Override
    public void onItemSelected(AdapterView<?> kid, View view, int position, long id) {
        selected_kid_name = kid.getItemAtPosition(position).toString();
        Cursor cur = myDb.getAllData();
        StringBuffer buffer = new StringBuffer();
        cur.moveToFirst();
        if (cur.getCount()==0){
           // Toast.makeText(this, "Add a kid", Toast.LENGTH_SHORT).show();
        }
        else
        {
            do
            {
                if(selected_kid_name.equals(cur.getString(cur.getColumnIndex(DataBaseHelper.COL_3)))){
                    selected_kid_uuid = cur.getString(3);
                    selected_kid_major = cur.getString(4);
                    selected_kid_minor = cur.getString(5);
                    buffer.append(selected_kid_uuid+"\n");
                    buffer.append(selected_kid_major+"\n");
                    buffer.append(selected_kid_minor+"\n");
                }
            }while (cur.moveToNext());
           // Toast.makeText(this, buffer.toString() , Toast.LENGTH_SHORT).show();
           // Toast.makeText(this, "kid selected: " + selected_kid_name, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onResume() {
        super.onResume();
       Cursor res = myDb.getAllData();
        kids.clear();
        if(res.getCount() == 0) {
            Toast.makeText(this, "add a kid", Toast.LENGTH_SHORT).show();
            kids.add("No Kid");
        }
        else {
            while (res.moveToNext()) {
                if(username.equals(res.getString(1)))
                {
                    if(!kids.contains("select a kid"))
                    {
                        kids.add("select a kid");
                    }
                    kids.add(res.getString(2));
                }
            }
        }

        //res.close();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kids);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(dataAdapter);
    }


   /* boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to log out", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }*/
}
