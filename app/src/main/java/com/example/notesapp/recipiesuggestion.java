package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

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

public class recipiesuggestion extends AppCompatActivity {
   String recipie;
    private JSONArray testArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipiesuggestion);
        recipie="pasta";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final String URL = "https://api.spoonacular.com/recipes/findByIngredients?ingredients="+recipie+"&number=30&instructionsRequired=true&apiKey=c957b6816ba048139fbc25a67d2cff33";

                    JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                testArr=response;
                                for (int i = 0; i < testArr.length(); i++) {
                                    JSONObject jsonObject1;
                                    jsonObject1 = testArr.getJSONObject(i);
                                    Log.i("title",jsonObject1.getString("title").toString());
                                }

                               // RecyclerViewAdapterSearchResult myAdapter = new RecyclerViewAdapterSearchResult(getApplicationContext(), lstRecipe);
                               // myrv.setAdapter(myAdapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("the res is error:", error.toString());
                        }
                    }

                    );



            }
}

