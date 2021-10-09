package com.nabeeltech.retrofitcodinginflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nabeeltech.retrofitcodinginflow.Constants.Constants;
import com.nabeeltech.retrofitcodinginflow.SingltonVolley.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button getposts, commentByPost_Id ,postByUserId,
            getPostByUserIdQueryMap, getByUrl, postBody, postBodyUrl_one,
            postBodyUrl_two, btn_put, btn_patch, btn_delete , json_object, jsonarray ,volleyPost;

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mQueue = MySingleton.getInstance(this).getRequestQueue();

        getposts = findViewById(R.id.get_posts);
        volleyPost = findViewById(R.id.volley_post);
        commentByPost_Id = findViewById(R.id.post_comment_by_id);
        postByUserId = findViewById(R.id.query_parameter);
        getPostByUserIdQueryMap = findViewById(R.id.query_map);
        getByUrl = findViewById(R.id.full_url);
        postBody = findViewById(R.id.post_body);
        postBodyUrl_one = findViewById(R.id.form_url_encoded);
        btn_put = findViewById(R.id.put);
        btn_patch = findViewById(R.id.patch);
        btn_delete = findViewById(R.id.delete);
        json_object = findViewById(R.id.btn_test);
        jsonarray = findViewById(R.id.json_array);
        //when get all posts is clicked
        getposts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetPosts.class);
                startActivity(intent);
            }
        });

        //when commentsby post id is clicked
        commentByPost_Id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, getCommentsByPostId.class);
                startActivity(intent);

            }
        });

        //get post by user id
        postByUserId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, postByUserId.class);
                startActivity(intent);
            }
        });

        //get post by user id through query map
        getPostByUserIdQueryMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, postByUserIdQueryMap.class);
                startActivity(intent);
            }
        });

        getByUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, getFullUrlMethod.class);
                startActivity(intent);
            }
        });

        postBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostByBody.class);
                startActivity(intent);
            }
        });

        postBodyUrl_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostByFormUrlEncoded.class);
                startActivity(intent);
            }
        });

        btn_put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PutMethod.class);
                startActivity(intent);
            }
        });

        btn_patch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PatchMethod.class);
                startActivity(intent);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeleteMethod.class);
                startActivity(intent);
            }
        });

        json_object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volleyGet();
            }
        });

        jsonarray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJsonArrayData();
            }
        });

        volleyPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyPostData();
            }
        });

    }

    private void VolleyPostData()
    {
        //patchRequest();
        deleteRequest();

//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.POST_DATA_VOLLEY,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response)
//                    {
//
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//
//                            Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error)
//                    {
//                        Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//        ){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("text", "this is a body");
//                params.put("title", "Gone By Wind");
//                params.put("userId", "23");
//                return params;
//            }
//        };
//
//         mQueue.add(stringRequest);
    }

    private void deleteRequest(){
        //https://www.itsalif.info/content/android-volley-tutorial-http-get-post-put

        StringRequest dr = new StringRequest(Request.Method.DELETE, Constants.DELETE_DATA_VOLLEY,

                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        // response
                        Toast.makeText(MainActivity.this, "success deleted "+response.toString(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );


        mQueue.add(dr);
    }

    private void patchRequest()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, Constants.PATCH_DATA_VOLLEY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("body", "the body has changed");
                params.put("userId", "3");
                return params;
            }
        };

        mQueue.add(stringRequest);
    }

    //volley patch request
    private void putRequest()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.PATCH, Constants.PUT_DATA_VOLLEY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", "the title is changed");
                params.put("body", "the body has changed");
                params.put("userId", "3");
                params.put("id", "2");
                return params;
            }
        };

        mQueue.add(stringRequest);
    }

    private void getJsonArrayData()
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Constants.GET_ARRAY, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {

                        JSONObject responseObj = response.getJSONObject(i);
                        String name = responseObj.getString("name");
                        Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(jsonArrayRequest);

    }


    private void volleyGet()
    {

        final List<String> jsonResponses = new ArrayList<>();



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.GET_JSON_OBJECT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
//                    Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = response.getJSONArray("employee");

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String email = jsonObject.getString("name");
                        Toast.makeText(MainActivity.this, email, Toast.LENGTH_SHORT).show();
                        //jsonResponses.add(email);
                    }
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        mQueue.add(jsonObjectRequest);

    }
}
