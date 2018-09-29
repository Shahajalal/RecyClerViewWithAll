package com.example.shahajalal.recyclerviewwithall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Showbills extends AppCompatActivity {

    String name,father;
    String URL = "http://10.0.2.2/habibnet/api2.php";
    private RecyclerView recyclerView;
    RecyclerAdapterForUserbill adapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showbills);
        name=getIntent().getStringExtra("name");
        father=getIntent().getStringExtra("father");
        recyclerView=findViewById(R.id.recyclerviewid1);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        getIngormation();

    }

    private void getIngormation(){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();
                Log.d("Response", response);

                List<ContactForBill>list= Arrays.asList(gson.fromJson(response,ContactForBill[].class));

                adapter=new RecyclerAdapterForUserbill(list);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Showbills.this,error.toString(),Toast.LENGTH_LONG).show();
                Log.d("volley", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String > map=new HashMap<>();
                map.put("name",name);
                map.put("father",father);
                return map;
            }
        };
        MySingleTon.getInstance(this).addToRequestQue(stringRequest);
    }
}
