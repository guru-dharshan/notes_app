package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class recipiesuggestion extends AppCompatActivity {

    RecyclerView recipierecycle;

    String title,id,image,source;
    ArrayList<recipiedata> recipielist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipiesuggestion);
        recipierecycle=findViewById(R.id.recipierecycle);
        Intent intent = getIntent();
        String recipe=intent.getStringExtra("recipe");
        String URL = "https://api.spoonacular.com/recipes/findByIngredients?ingredients="+recipe+"&number=10&instructionsRequired=true&apiKey=91f7cc0a2fdd4faa856d38429dda7000";
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        title=jsonObject1.getString("title");
                        id=jsonObject1.getString("id");
                        image=jsonObject1.getString("image");
                        Log.i("title",title);

                        String url="https://api.spoonacular.com/recipes/"+id+"/information?apiKey=91f7cc0a2fdd4faa856d38429dda7000";
                        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    //Toast.makeText(recipiesuggestion.this, jsonObject.getString("sourceUrl"), Toast.LENGTH_SHORT).show();
                                    source=jsonObject.getString("sourceUrl");
                                    Log.i("title",source);
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        },new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(recipiesuggestion.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        request.setRetryPolicy(new DefaultRetryPolicy(
                                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        RequestQueue requestQueue = Volley.newRequestQueue(recipiesuggestion.this);
                        requestQueue.add(request);

                        recipiedata recipiedata = new recipiedata(title,image,source);
                        recipielist.add(recipiedata);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(recipiesuggestion.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

        recipieAdapter recipieAdapter= new recipieAdapter(recipiesuggestion.this,recipielist);
        recipierecycle.setAdapter(recipieAdapter);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(recipiesuggestion.this, LinearLayoutManager.VERTICAL, false);
        recipierecycle.setLayoutManager(horizontalLayoutManagaer);

    }
}

