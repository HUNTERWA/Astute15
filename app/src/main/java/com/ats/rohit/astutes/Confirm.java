package com.ats.rohit.astutes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;

public class Confirm extends AppCompatActivity
{
    Pinview pinview;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        pinview=findViewById(R.id.otp);


    }


    public void pinView(View view)
    {
        Intent intent=new Intent(Confirm.this,AfterOtp.class);
        startActivity(intent);
    }
}
