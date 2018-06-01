package com.ats.rohit.astutes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AfterOtp extends AppCompatActivity
{
    Button button;
    EditText newPass,confirmPassword;
    String cp,p;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_otp);

        newPass=findViewById(R.id.newPass);

        confirmPassword=findViewById(R.id.confirmPassword);

        button=findViewById(R.id.bu);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clickEvent();
            }
        });
    }

    private void clickEvent()
    {
        p=newPass.getText().toString();
        cp=confirmPassword.getText().toString();

        if (p.equals(cp)&&p.length()!=0&&cp.length()!=0)
        {
            Intent intent = new Intent(AfterOtp.this, LogIn.class);
            startActivity(intent);
        }
        else if(!p.equals(cp))
        {
            Toast.makeText(getApplicationContext(),"Password does not match",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"All fields are mandatory",Toast.LENGTH_SHORT).show();
        }

    }
}
