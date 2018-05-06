package com.ats.rohit.astute;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity //implements View.OnClickListener
{
    Button veryGood,good,notSoGood;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        veryGood=findViewById(R.id.veryGood);
        good=findViewById(R.id.good);
        notSoGood=findViewById(R.id.notSoGood);

        veryGood.animate().translationX(1000).setDuration(0);
        good.animate().translationX(-1000).setDuration(0);
        notSoGood.animate().translationX(1000).setDuration(0);

        animationPart();

    }

    private void animationPart()
    {
        Thread thread=new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(500);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    veryGood.animate().translationX(0).setDuration(500);
                    good.animate().translationX(0).setDuration(500);
                    notSoGood.animate().translationX(0).setDuration(500);
                }
            }
        };
        thread.start();
    }

    public void onBackPressed()
    {
        AlertDialog.Builder aDb=new AlertDialog.Builder(this);
        aDb.setMessage("Do you really want to exit ?");
        aDb.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                finish();
            }
        });
        aDb.setNegativeButton("No",null);
        aDb.show();
    }

    public void vgMethod(View view)
    {
        go();
        Intent intent=new Intent(MainActivity.this,VeryGood.class);
        startActivity(intent);
        finish();
    }

    public void gMethod(View view)
    {
        go();
        Intent intent=new Intent(MainActivity.this,Good.class);
        startActivity(intent);
        finish();
    }

    public void nsgMethod(View view)
    {
        //intent.putExtra("PassValue",view.getId());
        go();
        Intent intent=new Intent(MainActivity.this,NotSoGood.class);
        startActivity(intent);
        finish();
    }

    public void go()
    {
        veryGood.animate().translationX(1000).setDuration(500);
        good.animate().translationX(-1000).setDuration(500);
        notSoGood.animate().translationX(1000).setDuration(500);

    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        animationPart();
    }

    /*@Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.veryGood:
                commonIntent();
                break;

            case R.id.good:
                commonIntent();
                break;

            case R.id.notSoGood:
                commonIntent();
                break;
        }
    }

    private void commonIntent()
    {
        Intent intent=new Intent(MainActivity.this,Mood.class);
        startActivity(intent);
    }*/


}
