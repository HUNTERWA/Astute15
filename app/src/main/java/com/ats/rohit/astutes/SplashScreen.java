package com.ats.rohit.astutes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//import com.ats.rohit.astute.R;

public class SplashScreen extends AppCompatActivity
{
    //ImageView imageView;
    TextView textView,tagLine;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        textView=findViewById(R.id.textView);
        tagLine=findViewById(R.id.tagLine);
        tagLine.animate().translationX(1000).setDuration(0);

        //printKeyHash();  //sha key for facebook login

        //Code for Main Text view
        Thread thread=new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(500);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    //imageView.animate().alpha(1f).setDuration(2500);
                    textView.animate().alpha(1f).setDuration(2000);
                    tagLine.animate().translationX(0).setDuration(1000);
                }
            }
        };
        thread.start();

        //Code for Splash screen
        Handler handler=new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                sharedPreferences=getSharedPreferences("USER_INFO",MODE_PRIVATE);
                String string=sharedPreferences.getString("TOKEN","");

                sharedPreferences=getSharedPreferences("SignIn",MODE_PRIVATE);
                String str=sharedPreferences.getString("fSignIn","");

                if (string==""&&str=="")
                {
                    Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent=new Intent(SplashScreen.this,VideoList.class);
                    startActivity(intent);
                    finish();
                }

            }
        },3000);

    }

    /*private void printKeyHash()
    {
        try
        {
            PackageInfo packageInfo=getPackageManager().getPackageInfo("com.ats.rohit.astutes", PackageManager.GET_SIGNATURES);
            for(Signature signature:packageInfo.signatures)
            {
                MessageDigest messageDigest=MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(messageDigest.digest(),Base64.DEFAULT));
            }
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
    }*/

}
