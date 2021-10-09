package com.nabeeltech.retrofitcodinginflow;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nabeeltech.retrofitcodinginflow.Model.Post;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatchMethod extends AppCompatActivity {

    private TextView textViewResult;
    private ProgressDialog loadingBar;
    jsonPlaceHolderApi jsonPlaceHolderApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patch_method);

        textViewResult = findViewById(R.id.text_view_result);
        loadingBar = new ProgressDialog(this);

        //if you want to allow null in patch request pass gson to .create(gson)
        Gson gson = new GsonBuilder().serializeNulls().create();

        okHttpClient okHttpClient = new okHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
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
        Map<String, String> headers = new HashMap<>();
        headers.put("Map-Header1", "def");
        headers.put("Map-Header2", "ghi");

        Post post = new Post(12, null, "New Text");

        Call<Post> call = jsonPlaceHolderApi.patchPost(headers,5, post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    loadingBar.dismiss();
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();
                loadingBar.dismiss();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                loadingBar.dismiss();
                textViewResult.setText(t.getMessage());
            }
        });
    }

}
