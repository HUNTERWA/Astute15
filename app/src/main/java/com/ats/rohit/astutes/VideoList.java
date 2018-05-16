package com.ats.rohit.astutes;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    JSONArray jsonArray;
    JSONObject jsonObject;
    public String[] urL;//=new String[jsonArray.length()];
    public String[] namE;//=new String[jsonArray.length()];
    public String[] sD;//=new String[jsonArray.length()];
    public String[] lD;//=new String[jsonArray.length()];
    public String[] iP;//=new String[jsonArray.length()];
    public String[] isPaid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        fetchData();

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
                    //CustomList customList=new CustomList();
                    //listView.setAdapter(customList);
                    //RecyclerAdapter recyclerAdapter=new RecyclerAdapter();
                    recyclerView=findViewById(R.id.recyclerView);
                    layoutManager=new LinearLayoutManager(VideoList.this);
                    recyclerView.setLayoutManager(layoutManager);

                    adapter=new RecyclerAdapter();
                    recyclerView.setAdapter(adapter);
                    Log.d("k=>",""+k);

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
            }
        });

        requestQueue.add(jsonObjectRequest);
        //CustomList customList=new CustomList();
        //listView.setAdapter(customList);
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
    {
        View views;

        class ViewHolder extends RecyclerView.ViewHolder
        {
            ImageView imageView;
            TextView title;
            TextView description;

            public ViewHolder(View itemView)
            {
                super(itemView);
                imageView=itemView.findViewById(R.id.imageView);
                title=itemView.findViewById(R.id.ttl);
                description=itemView.findViewById(R.id.description);

                itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        int position=getAdapterPosition();
                        if (isPaid[position].equalsIgnoreCase("F"))
                        {
                            Intent intent=new Intent(VideoList.this,VideoPlayer.class);
                            intent.putExtra("position",position);
                            startActivity(intent);
                        }

                        if (isPaid[position].equalsIgnoreCase("P"))
                        {
                             Intent intent=new Intent(VideoList.this,ForPaidVideo.class);
                             intent.putExtra("position",position);
                             startActivity(intent);
                        }
                    }
                });
            }
        }

        @NonNull
        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
        {
            views= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customl,viewGroup,false);
            ViewHolder viewHolder=new ViewHolder(views);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i)
        {
            viewHolder.title.setText(namE[i]);
            viewHolder.description.setText(sD[i]);
            //ImageView imageView = views.findViewById(R.id.imageView);
            Picasso.get().load(iP[i]).into(viewHolder.imageView);
        }

        @Override
        public int getItemCount()
        {
            return jsonArray.length();
        }
    }
}
