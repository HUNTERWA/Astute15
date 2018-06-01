package com.ats.rohit.astutes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.ats.rohit.astute.R;

import org.apache.commons.validator.routines.EmailValidator;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity
{
    SharedPreferences sharedPreferences;
    EditText name,passowrd,email,numb;
    Button button;
    RadioGroup radioGroup;
    RadioButton radioButtonMale;
    RadioButton radioButtonFemale;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name=findViewById(R.id.name);
        passowrd=findViewById(R.id.password);
        email=findViewById(R.id.email);
        numb=findViewById(R.id.numb);
        radioGroup=findViewById(R.id.radioGroup);
        button=findViewById(R.id.signUp);
        radioButtonMale=findViewById(R.id.male);
        radioButtonFemale=findViewById(R.id.female);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //int check=radioGroup.getCheckedRadioButtonId();
                //radioButton=findViewById(check);
                //Log.d("Selected radio button:=",""+radioButton.getText());


                apiCalling();
            }
        });

    }

    private void apiCalling()
    {
        //final String gender = null;

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest;

        //final String apiAdd="http://45.126.170.217:9000/RegisterUser/insert\n";
        final String apiAdd="http://188.166.50.216:9000/RegisterUser/insert";

        final String authKey="";
        String gen="";

        final String userName=name.getText().toString().trim();
        final String pass=passowrd.getText().toString().trim();
        final String emailId=email.getText().toString().trim();
        final String num=numb.getText().toString().trim();
        if(radioButtonMale.isChecked())
        {
           gen= (String) radioButtonMale.getText();
            Log.d("Gender",gen);
        }
        else if (radioButtonFemale.isChecked())
        {
            gen= (String) radioButtonFemale.getText();
            Log.d("Gender",gen);
        }
        final String gender=gen;
        Log.d("genderIs",gender);
        final String type="N";

        if (userName.length()!=0&&pass.length()!=0&& emailId.length()!=0&&gender.length()!=0&&type.length()!=0&&num.length()==10)
            {
            stringRequest = new StringRequest(Request.Method.POST, apiAdd, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    Toast.makeText(getApplicationContext(), "Sign up successfull", Toast.LENGTH_SHORT).show();
                    Log.d("Responses:=>", response);

                    Intent intent = new Intent(SignUp.this, LogIn.class);
                    startActivity(intent);
                    finish();
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    if (num.length()!=10)
                    {
                        Toast.makeText(getApplicationContext(), "Number must be of 10 digit", Toast.LENGTH_SHORT).show();
                    }
                    else if (EmailValidator.getInstance().isValid(emailId)&&num.length()==10)
                    {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        Log.d("Error is:=>", "" + error);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Enter right email Id", Toast.LENGTH_SHORT).show();
                    }
                }
            })
            {
                @Override
                protected Map<String, String> getParams()
                {
                    if (EmailValidator.getInstance().isValid(emailId))
                    {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("name", userName);
                        map.put("emailId", emailId);
                        map.put("gender", gender);
                        map.put("password", pass);
                        map.put("type", type);

                        return map;
                    }
                    return null;

                }
            };
            requestQueue.add(stringRequest);
        }

            else
            {
            Toast.makeText(getApplicationContext(),"All fields are mandatory",Toast.LENGTH_SHORT).show();
            }
    }
}