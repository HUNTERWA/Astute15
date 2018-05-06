package com.ats.rohit.astute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Questions extends AppCompatActivity
{
    TextView textView;
    JSONArray jsonArray;
    JSONObject jsonObject;
    Button yes,no,cantSay;
    int i,j=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        textView=findViewById(R.id.question);
        yes=findViewById(R.id.yes);
        no=findViewById(R.id.no);
        cantSay=findViewById(R.id.cantSay);

        apiData();
    }

    private void apiData()
    {
        //final String apiAddr="http://45.126.170.217:9000/linkQuesAns/FindAll\n";
        final String apiAddr="http://188.166.50.216:9000/linkQuesAns/FindAll\n";

        JsonObjectRequest jsonObjectRequest;
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, apiAddr, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                Log.d("Response:=>",""+response);
                try
                {
                    jsonArray=response.getJSONArray("doc");
                    for(i=0;i<jsonArray.length();i++)
                    {
                        jsonObject=jsonArray.getJSONObject(i);
                        String _id=jsonObject.getString("_id");
                        String answer=jsonObject.getString("answer");
                        Log.d("_id",_id);
                        Log.d("answer",answer);
                        if(i==0)
                        {
                            textView.append(_id+"?");
                        }
                    }

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
                Log.d("Error:=>",""+error);
                Toast.makeText(getApplicationContext(),"network error occured",Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void yes(View view)
    {
        callQueOnebyOne();
    }

    public void no(View view)
    {
        callQueOnebyOne();
    }

    public void cantSay(View view)
    {
        callQueOnebyOne();
    }

    public void callQueOnebyOne()
    {
        ++j;
        if (j<jsonArray.length()+1)
        {
            try
            {
                jsonObject = jsonArray.getJSONObject(j);
                String que = jsonObject.getString("_id");
                textView.setText(que + "?");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        if (j==jsonArray.length())
        {
            Log.d("arrayLength",""+j);

            Intent intent=new Intent(Questions.this,VideoList.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();

        Log.d("Restart","method");

        yes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                forRestart();
            }
        });

        no.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                forRestart();
            }
        });

        cantSay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                forRestart();
            }
        });
    }

    public void forRestart()
    {
        Intent intent=new Intent(Questions.this,VideoList.class);
        startActivity(intent);
    }
}
