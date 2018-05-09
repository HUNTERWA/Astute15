package com.ats.rohit.astutes;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//import com.ats.rohit.astute.R;

public class VeryGood extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_very_good);

        Button button=findViewById(R.id.one);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(VeryGood.this,LogIn.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
