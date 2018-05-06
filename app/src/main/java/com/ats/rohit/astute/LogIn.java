package com.ats.rohit.astute;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogIn extends Activity implements GoogleApiClient.OnConnectionFailedListener
{
    JSONObject jsonObject;
    GoogleSignInResult result;
    EditText emId,password;
    TextView newUser;
    Button login;
    String key;
    SharedPreferences sharedPreferences;

    SignInButton signInButton;
    GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 007;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emId=findViewById(R.id.emID);
        password=findViewById(R.id.password);
        newUser=findViewById(R.id.newUser);
        login=findViewById(R.id.logIn);

        signInButton=findViewById(R.id.signInButton);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                signIn();
            }
        });


        newUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(LogIn.this,SignUp.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.e("User name is",""+emId.getText());
                Log.e("Password is",""+password.getText());
                /*if(emId.length()==0||password.length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Enter valid credentials",Toast.LENGTH_LONG).show();
                }*/

                apiForLogIn();
            }
        });

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Log.d("ConnectionResult",""+connectionResult);
    }
    private void signIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result)
    {
        Log.d("result is",""+result);
        //Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        Log.d("handleSignInResult",""+result.isSuccess());
        Log.d("statusCode",""+result.getStatus());

        if (result.isSuccess())
        {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            //Log.e(TAG, "display name: " + acct.getDisplayName());
            Log.d("display name",""+acct.getDisplayName());


            String personName = acct.getDisplayName();
            String email = acct.getEmail();

            Log.d("person name",personName);
            Log.d("email",email);

            sharedPreferences=getSharedPreferences("SignIn",MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("NAME",personName);
            editor.apply();

            Intent intent=new Intent(LogIn.this,Start.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please try another login method.",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN)
        {
            result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    public void apiForLogIn()
    {
        //final String apiAdd="http://45.126.170.217:9000/UserLogin\n";
        final String apiAdd="http://188.166.50.216:9000/UserLogin\n";


        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest;

        final String mailId=emId.getText().toString().trim();
        final String pass=password.getText().toString().trim();

        stringRequest=new StringRequest(Request.Method.POST, apiAdd, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("Response:=>",response);

                //Log.d("token:=>",response.)
                Boolean code=response.contains("200");
                if(code==true)
                {
                    Toast.makeText(getApplicationContext(),"Sign In successfull",Toast.LENGTH_SHORT).show();
                    Log.d("Response:=>","true");
                    Intent intent=new Intent(LogIn.this,Start.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Wrong credential",Toast.LENGTH_SHORT).show();
                }

                try
                {
                    jsonObject=new JSONObject(response);
                    key=jsonObject.getString("token");
                    Log.d("TokenKeyIs",key);
                    sharedPreferences=getSharedPreferences("USER_INFO",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("TOKEN",key);
                    editor.apply();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getApplicationContext(),"user does not exist",Toast.LENGTH_SHORT).show();
                Log.d("Error:=>",""+error);
                Log.d("mailId\n pass",mailId+"\n"+pass);
            }
        })
        {
            @Override
            protected Map<String,String>getParams()
            {
                Map<String,String> map=new HashMap<>();
                map.put("emailId",mailId);
                map.put("password",pass);
                return map;
            }

            /*@Override
            protected Response<String> parseNetworkResponse(NetworkResponse response)
            {
                String responseString="";
                responseString=String.valueOf(response.statusCode);
                Log.d("Token:=>",responseString);
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }*/
        };

        requestQueue.add(stringRequest);
    }


}
