package com.example.nitinnikamanth.kidstracking;

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

public class SignupActivity extends AppCompatActivity {

    EditText parentid,passkey,passkey2;
    Button signupbutton;
    SharedPreferences s;
    SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        parentid = (EditText)findViewById(R.id.parentid);
        passkey = (EditText)findViewById(R.id.passkey);
        passkey2 = (EditText)findViewById(R.id.passkey2);
        signupbutton = (Button)findViewById(R.id.signupbutton);
        s= getSharedPreferences("kidstracker",0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Button","signup pressed");
                if(s.contains(parentid.getText().toString()))
                {
                    Toast.makeText(SignupActivity.this, "username Already exist", Toast.LENGTH_SHORT).show();
                }
                else if(parentid.getText().toString().equals(""))
                {
                    Toast.makeText(SignupActivity.this, "enter userid", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    String p1=passkey.getText().toString();
                    String p2=passkey2.getText().toString();

                    if(p1.equals(""))
                    {
                        Toast.makeText(SignupActivity.this, "enter a password", Toast.LENGTH_SHORT).show();
                    }
                    else if(p1.equals(p2))
                    {
                        SharedPreferences.Editor ed = s.edit();
                        Log.e("Button","signup if clause.....");
                        ed.putString(parentid.getText().toString(),parentid.getText().toString());
                        Log.e("Button","After 1st statement.......");
                        ed.putString(parentid.getText().toString()+"password",passkey.getText().toString());
                        ed.apply();
                        if(s.contains(parentid.getText().toString()))
                        {
                            Toast.makeText(SignupActivity.this, "New Account Created!!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignupActivity.this , AdvancedSettingsActivity.class);
                            i.putExtra("username" , parentid.getText().toString());
                            ed.putInt(parentid.getText().toString()+"count" , 0);
                            ed.commit();
                            startActivity(i);
                            SignupActivity.this.finish();

                        }


                    }
                    else
                    {
                        Toast.makeText(SignupActivity.this, "password doesn't match", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}
