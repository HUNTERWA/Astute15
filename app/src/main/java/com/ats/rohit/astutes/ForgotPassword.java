package com.ats.rohit.astutes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity
{
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editText=findViewById(R.id.number);
        button=findViewById(R.id.reset);

    }

    public void reset(View view)
    {
        /*if (editText.length()!=10)
        {
            Toast.makeText(getApplicationContext(),"Mobile number must be of 10 digit",Toast.LENGTH_SHORT).show();
        }
        else
        {*/
            Intent intent=new Intent(ForgotPassword.this,Confirm.class);
            startActivity(intent);
        //}
    }
}
