package com.example.nitinnikamanth.kidstracking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdvancedSettingsActivity extends AppCompatActivity {

    EditText range1 , range2 , range3 , range4 , range5 ;
    SharedPreferences s;
    SharedPreferences.Editor ed;
    String username ,password;
    Button save;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_settings);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        save =(Button)findViewById(R.id.save);
        range1 = (EditText)findViewById(R.id.range1);
        range2 = (EditText)findViewById(R.id.range2);
        range3 = (EditText)findViewById(R.id.range3);
        range4 = (EditText)findViewById(R.id.range4);
        range5 = (EditText)findViewById(R.id.range5);
        s = getApplicationContext().getSharedPreferences("kidsage", 0);
        ed = s.edit();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        range1.setText(s.getString(username + "range1", null));
        range2.setText(s.getString(username + "range2", null));
        range3.setText(s.getString(username + "range3", null));
        range4.setText(s.getString(username + "range4", null));
        range5.setText(s.getString(username + "range5", null));


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (range1.getText().toString().equals("") ||range2.getText().toString().equals("") ||
                        range3.getText().toString().equals("") ||range4.getText().toString().equals("") ||
                        range5.getText().toString().equals(""))
                {
                    Toast.makeText(AdvancedSettingsActivity.this, "enter range for all ages", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    ed.putString(username + "range1",range1.getText().toString());
                    ed.putString(username + "range2",range2.getText().toString());
                    ed.putString(username + "range3",range3.getText().toString());
                    ed.putString(username + "range4",range4.getText().toString());
                    ed.putString(username + "range5",range5.getText().toString());
                    ed.commit();
                    Toast.makeText(AdvancedSettingsActivity.this, "saved", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AdvancedSettingsActivity.this , TrackingActivity.class);
                    i.putExtra("username",username);
                    i.putExtra("password",password);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (range1.getText().toString().equals("") ||range2.getText().toString().equals("") ||
                range3.getText().toString().equals("") ||range4.getText().toString().equals("") ||
                range5.getText().toString().equals(""))
        {
            Toast.makeText(this, "enter range for all ages", Toast.LENGTH_SHORT).show();
            /*Intent i =new Intent(AdvancedSettingsActivity.this , AdvancedSettingsActivity.class);
            AdvancedSettingsActivity.this.startActivity(i);*/
        }
        else
        {
            super.onBackPressed();
            ed.putString(username + "range1",range1.getText().toString());
            ed.putString(username + "range2",range2.getText().toString());
            ed.putString(username + "range3",range3.getText().toString());
            ed.putString(username + "range4",range4.getText().toString());
            ed.putString(username + "range5",range5.getText().toString());
            ed.commit();
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        }


    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        range1.setText("5");
//        range5.setText("150");
//        range2.setText("25");
//        range4.setText("100");
//        range3.setText("50");
//
//    }
}
