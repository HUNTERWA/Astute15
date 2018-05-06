package com.ats.rohit.astute;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VideoPlayer extends YouTubeBaseActivity
{
    TextView reTitle,reLong;

    JSONArray jsonArray;
    JsonObjectRequest jsonObjectRequest;
    JSONObject jsonObject;
    RequestQueue requestQueue;
    //String ur="http://45.126.170.217:9000/upload/FindAll\n";
    String ur="http://188.166.50.216:9000/upload/FindAll\n";

    String[] urlArray,titleArray,lArray;

    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        youTubePlayerView=findViewById(R.id.youTubePlayerView);


        reTitle=findViewById(R.id.reTitle);
        reLong=findViewById(R.id.reLong);


        requestQueue= Volley.newRequestQueue(this);

        reFetch();
    }

    private void reFetch()
    {
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, ur, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    jsonArray=response.getJSONArray("doc");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        urlArray=new String[jsonArray.length()];
                        titleArray=new String[jsonArray.length()];
                        lArray=new String[jsonArray.length()];

                        for (int j=0;j<jsonArray.length();j++)
                        {
                            jsonObject=jsonArray.getJSONObject(j);
                            urlArray[j]=jsonObject.getString("code");
                            titleArray[j]=jsonObject.getString("name");
                            lArray[j]=jsonObject.getString("longDescription");
                            Log.d("videoPlayer",urlArray[j]);
                            Log.d("videoPlayer",titleArray[j]);
                            Log.d("videoPlayer",lArray[j]);

                        }
                    }

                    int f=getIntent().getIntExtra("position",0);
                    Log.d("videoPlayer",""+f);


                    onInitializedListener=new YouTubePlayer.OnInitializedListener()
                    {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer,                               boolean b)
                        {
                            int f=getIntent().getIntExtra("position",0);
                            Log.d("new",""+f);
                            youTubePlayer.loadVideo(urlArray[f]);
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult                                    youTubeInitializationResult)
                        {
                            Log.d("errorIs",""+youTubeInitializationResult);
                        }
                    };
                    youTubePlayerView.initialize("AIzaSyDheKHlDb3CYdrkzsVT_F8wsdayraIOtZs",onInitializedListener);
                    //method for youtube ends at here

                    reTitle.setText(titleArray[f]);
                    reLong.setText(lArray[f]);


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
                Log.d("ErrorIsInVideoPlayer",""+error);
                Toast.makeText(getApplicationContext(),""+error,Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


}
