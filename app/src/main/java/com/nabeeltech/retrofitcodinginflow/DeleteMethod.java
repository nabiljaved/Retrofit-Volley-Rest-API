package com.nabeeltech.retrofitcodinginflow;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nabeeltech.retrofitcodinginflow.Model.Post;

public class DeleteMethod extends AppCompatActivity {

    private TextView textViewResult;
    private ProgressDialog loadingBar;
    jsonPlaceHolderApi jsonPlaceHolderApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_method);

        textViewResult = findViewById(R.id.text_view_result);
        loadingBar = new ProgressDialog(this);

        //if you want to allow null in patch request pass gson to .create(gson)
        Gson gson = new GsonBuilder().serializeNulls().create();

        okHttpClient okHttpClient = new okHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.getOkHttpClient())
                .build();

        jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);

        loadingBar.setTitle("Fetching Data");
        loadingBar.setMessage("Please wait, while we are fetching data ...");
        loadingBar.show();
        loadingBar.setCanceledOnTouchOutside(true);

        initializeRetrofit();

    }


    private void initializeRetrofit()
    {
        Call<Void> call = jsonPlaceHolderApi.deletePost(5);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                loadingBar.dismiss();
                textViewResult.setText("Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                loadingBar.dismiss();
                textViewResult.setText(t.getMessage());
            }
        });
    }

}
