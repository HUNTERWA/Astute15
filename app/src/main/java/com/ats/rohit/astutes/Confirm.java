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
    Button button;
    EditText newPass,confirmPassword;
    String cp,p,ot;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        pinview=findViewById(R.id.otp);

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
        ot=pinview.getValue();
        Log.d("otpIs",ot);

        if (p.equals(cp)&&p.length()!=0&&cp.length()!=0&&ot.length()==4)
        {
            Intent intent = new Intent(Confirm.this, LogIn.class);
            startActivity(intent);
        }
        else if(!p.equals(cp))
        {
            Toast.makeText(getApplicationContext(),"Password does not match",Toast.LENGTH_SHORT).show();
        }
        else if (ot.length()!=4&&p.length()==0&&cp.length()==0)
        {
            Toast.makeText(getApplicationContext(),"All fields are mandatory",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"OPT not entered",Toast.LENGTH_SHORT).show();
        }
    }
}
