package com.nabeeltech.retrofitcodinginflow.SingltonVolley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton
{
    private static MySingleton mInstance;
    private RequestQueue mRequestQueue;

    private MySingleton(Context Context){
        mRequestQueue = Volley.newRequestQueue(Context.getApplicationContext());
    }

    public static synchronized MySingleton getInstance(Context context){
        if(mInstance == null){
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
}
