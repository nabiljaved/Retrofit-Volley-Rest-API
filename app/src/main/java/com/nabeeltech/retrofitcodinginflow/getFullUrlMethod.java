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

import com.nabeeltech.retrofitcodinginflow.Model.Comment;
import com.nabeeltech.retrofitcodinginflow.Model.PostObject;

import java.util.List;

public class getFullUrlMethod extends AppCompatActivity {

    private TextView textViewResult;
    private ProgressDialog loadingBar;
    jsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_full_url_method);

        textViewResult = findViewById(R.id.text_view_result);
        loadingBar = new ProgressDialog(this);



        okHttpClient okHttpClient = new okHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.getOkHttpClient())
                .build();

        loadingBar.setTitle("Fetching Data");
        loadingBar.setMessage("Please wait, while we are fetching data ...");
        loadingBar.show();
        loadingBar.setCanceledOnTouchOutside(true);

        jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);


        Call<List<Comment>> call = jsonPlaceHolderApi
                .getCommentsByUrl("https://jsonplaceholder.typicode.com/posts/1/comments");

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if (!response.isSuccessful()) {
                    loadingBar.dismiss();
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Comment> comments = response.body();
                loadingBar.dismiss();

                for (Comment comment : comments) {
                    String content = "";
                    content += "ID: " + comment.getId() + "\n";
                    content += "Post ID: " + comment.getPostId() + "\n";
                    content += "Name: " + comment.getName() + "\n";
                    content += "Email: " + comment.getEmail() + "\n";
                    content += "Text: " + comment.getText() + "\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                loadingBar.dismiss();
                textViewResult.setText(t.getMessage());
            }
        });



    }
}
