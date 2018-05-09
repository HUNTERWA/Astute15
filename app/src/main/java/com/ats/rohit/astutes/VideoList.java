package com.ats.rohit.astutes;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
//import com.ats.rohit.astute.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VideoList extends AppCompatActivity
{
    public int k=0,l=0;
    ListView listView;
    JSONArray jsonArray;
    JSONObject jsonObject;
    public String[] urL;//=new String[jsonArray.length()];
    public String[] namE;//=new String[jsonArray.length()];
    public String[] sD;//=new String[jsonArray.length()];
    public String[] lD;//=new String[jsonArray.length()];
    public String[] iP;//=new String[jsonArray.length()];
    public String[] isPaid;

    //ImageView imageView1;//,imageView2;//,imageView3,imageView4,imageView5,imageView6;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        listView=findViewById(R.id.listView);

        fetchData();

        //CustomList customList=new CustomList();
        //listView.setAdapter(customList);

    }

    private void fetchData()
    {
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        //final String dataUrl="http://45.126.170.217:9000/upload/FindAll\n";
        final String dataUrl="http://188.166.50.216:9000/upload/FindAll\n";

        JsonObjectRequest jsonObjectRequest;

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, dataUrl, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    jsonArray=response.getJSONArray("doc");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        //JSONObject jsonObject=jsonArray.getJSONObject(i);


                        urL=new String[jsonArray.length()];
                        namE=new String[jsonArray.length()];
                        sD=new String[jsonArray.length()];
                        lD=new String[jsonArray.length()];
                        iP=new String[jsonArray.length()];
                        isPaid=new String[jsonArray.length()];

                        for(int j=0;j<jsonArray.length();j++)
                        {
                            jsonObject=jsonArray.getJSONObject(j);
                            urL[j]=jsonObject.getString("url");
                            namE[j]=jsonObject.getString("name");
                            sD[j]=jsonObject.getString("shortDescription");
                            lD[j]=jsonObject.getString("longDescription");
                            iP[j]=jsonObject.getString("imagePath");
                            isPaid[j]=jsonObject.getString("status");
                            Log.d("Url Data "+j,urL[j]);
                            Log.d("Name Data "+j,namE[j]);
                            Log.d("Short D "+j,sD[j]);
                            Log.d("Long D "+j,lD[j]);
                            Log.d("Image path "+j,iP[j]);
                            Log.d("isPaid "+j,isPaid[j]);

                        }
                    }
                    k=iP.length;
                    Log.d("k:=>",""+k);
                    CustomList customList=new CustomList();
                    listView.setAdapter(customList);
                    Log.d("k=>",""+k);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        for (l=0;l<jsonArray.length();l++)
                        {
                            if (position==l&&isPaid[l].equalsIgnoreCase("F"))
                            {
                                Intent intent=new Intent(VideoList.this,VideoPlayer.class);
                                intent.putExtra("position",l);
                                startActivity(intent);
                            }

                            if (position==l&&isPaid[l].equalsIgnoreCase("P"))
                            {
                                Intent intent=new Intent(VideoList.this,ForPaidVideo.class);
                                intent.putExtra("position",l);
                                startActivity(intent);
                            }

                        }
                    }
                });
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("Error:=>",""+error);
            }
        });

        requestQueue.add(jsonObjectRequest);
        //CustomList customList=new CustomList();
        //listView.setAdapter(customList);
    }

    class CustomList extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            Log.d("I am in Custom list",""+k);
            return k;
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent)
        {
            convertView=getLayoutInflater().inflate(R.layout.customl,null);

            ImageView imageView = convertView.findViewById(R.id.imageView);
            Picasso.get().load(iP[i]).into(imageView);
            Log.d("ImageAddress",iP[i]);

            TextView title = convertView.findViewById(R.id.ttl);
            title.setText(namE[i]);

            TextView description = convertView.findViewById(R.id.description);
            description.setText(sD[i]);


            return convertView;
        }
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();  this was causing instant closing.
        AlertDialog.Builder adB=new AlertDialog.Builder(this);
        adB.setMessage("Do you really want to exit?");
        adB.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                finish();
            }
        });
        adB.setNegativeButton("No",null);
        adB.show();
    }
}
