package com.example.nitinnikamanth.kidstracking;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.ArrayList;
import java.util.List;

public class KidAddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DataBaseHelper myDb;
    String username,password,item ="0:0:0";
    private BeaconManager beaconManager;
    private BeaconRegion beaconRegion;
    Button searchbutton,stopsearch,addkid,view,delete;
    Spinner spinner;
    EditText kidname,kidage;
    //TextView beaconselected;
    SharedPreferences s;
    SharedPreferences.Editor ed;
    int t=0,x=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kid_add);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        s= getSharedPreferences("kidsage",0);

        ed = s.edit();
        myDb = new DataBaseHelper(this);
        addkid = (Button)findViewById(R.id.addkid);

        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        spinner = (Spinner) findViewById(R.id.beaconspinner);
        beaconManager = new BeaconManager(KidAddActivity.this);
        beaconRegion = new BeaconRegion("Kidzone",null,null,null);
        searchbutton = (Button)findViewById(R.id.searchbutton);
        stopsearch = (Button)findViewById(R.id.stopsearch);
        delete = (Button)findViewById(R.id.delete);
        kidname = (EditText)findViewById(R.id.kidname);
        kidage = (EditText)findViewById(R.id.kidage);
        view = (Button)findViewById(R.id.view);
        //beaconselected = (TextView)findViewById(R.id.selectedbeacon);

        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        AddData();
        viewAll();
        DeleteData();
        spinner.setOnItemSelectedListener(this);
        final List<String> beaconlist = new ArrayList<String>();
        beaconlist.add("select a beacon");

        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> beacons) {
                for(int i=0;i< beacons.size();i++){
                    Beacon beacon = beacons.get(i);
                    //beaconlist.clear();
                    beaconlist.add(beacon.getProximityUUID()+":"+beacon.getMajor()+":"+beacon.getMinor());
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(KidAddActivity.this, android.R.layout.simple_spinner_item, beaconlist);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(dataAdapter);
                }

            }
        });




        stopsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(KidAddActivity.this, "search stopped", Toast.LENGTH_SHORT).show();
                beaconManager.stopRanging(beaconRegion);
                t--;
            }
        });
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t==0){
                    t++;
                    Toast.makeText(KidAddActivity.this, "Searching...", Toast.LENGTH_SHORT).show();
                    beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
                        @Override
                        public void onServiceReady() {
                            Log.e("Button" , "search button clicked");
                            beaconManager.startRanging(beaconRegion);
                        }
                    });
                }
                else{
                    Toast.makeText(KidAddActivity.this, "wait for results...", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void DeleteData() {

        delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ed.remove(username + kidname+"age");
                        Integer deletedRows = myDb.deleteData(kidname.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(KidAddActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(KidAddActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );

    }


    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    private void viewAll() {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if(res.getCount() == 0) {
                    // show message
                    showMessage("Error","Nothing found");
                    return;
                }
                Toast.makeText(KidAddActivity.this, "viewing...", Toast.LENGTH_SHORT).show();
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id :"+ res.getString(0)+"\n");
                    buffer.append("Parentname :"+ res.getString(1)+"\n");
                    buffer.append("Kidname :"+ res.getString(2)+"\n");
                    buffer.append("Kidage :"+ s.getInt(username+res.getString(2)+"age" , 0)+"\n");
                    buffer.append("UUID :"+ res.getString(3)+"\n");
                    buffer.append("Major :"+ res.getString(4)+"\n");
                    buffer.append("Minor :"+ res.getString(5)+"\n\n");
                }
                // Show all data/**/
                showMessage("Data",buffer.toString());
            }
        });
    }

    private void AddData() {
        addkid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(kidname.getText().toString().equals("")) {
                    Toast.makeText(KidAddActivity.this, "enter valid username", Toast.LENGTH_SHORT).show();

                }
                else if(kidage.getText().toString().equals("") || Integer.parseInt(kidage.getText().toString())>10)
                {
                    Toast.makeText(KidAddActivity.this, "enter valid age", Toast.LENGTH_SHORT).show();
                }
                else if(item.equals("0:0:0") || item.equals("select a beacon"))
                {
                    Toast.makeText(KidAddActivity.this, "select a sensor", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String uuid,major,minor;
                    String str = item;

                    String[] parts = str.split(":", 3);
                    uuid = parts[0];
                    major = parts[1];
                    minor = parts[2];
                    // Toast.makeText(KidAddActivity.this, uuid+","+major+","+minor, Toast.LENGTH_SHORT).show();
                    ed.putInt(username + kidname.getText().toString() + "tracking" , 0);
                    ed.putInt(username + kidname.getText().toString()+"age" , Integer.parseInt(kidage.getText().toString()));
                    ed.commit();

                    boolean isAdd = myDb.insertData(username,kidname.getText().toString(),uuid,major,minor);
                    if(isAdd == true){
                        Toast.makeText(KidAddActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(KidAddActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(KidAddActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager = null;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        item = parent.getItemAtPosition(position).toString();
        //beaconselected.setText(item);

        // Showing selected spinner item
        if(!item.equals("select a beacon")){
            //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            x++;
            beaconManager.stopRanging(beaconRegion);
            t=0;
            //Toast.makeText(this, "Search stopped", Toast.LENGTH_SHORT).show();
        }



    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
