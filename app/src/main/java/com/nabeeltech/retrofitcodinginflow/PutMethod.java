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
import android.widget.Button;
import android.widget.TextView;

import com.nabeeltech.retrofitcodinginflow.Model.Post;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

public class PutMethod extends AppCompatActivity {

    MaterialEditText use_id, title, detail, id;
    Button btn_put;
    String txt_user_id, txt_title, txt_detail, txt_id;
    private TextView textViewResult;
    private ProgressDialog loadingBar;
    jsonPlaceHolderApi jsonPlaceHolderApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_method);

        use_id = findViewById(R.id.userid);
        title = findViewById(R.id.title);
        detail = findViewById(R.id.detail);
        btn_put = findViewById(R.id.btn_send);
        id = findViewById(R.id.id);

        textViewResult = findViewById(R.id.text_view_result);
        loadingBar = new ProgressDialog(this);

        okHttpClient okHttpClient = new okHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.getOkHttpClient())
                .build();

        jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);

        btn_put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeRetrofit();
            }
        });


    }

    //initialize retrofit
    private void initializeRetrofit()
    {
        txt_user_id = use_id.getText().toString();
        txt_id = id.getText().toString();
        txt_title = title.getText().toString();
        txt_detail = detail.getText().toString();

        if(txt_user_id.equals(""))
        {
            use_id.setError("user id cannot be blank");
            use_id.requestFocus();
            return;
        }


        if(txt_title.equals(""))
        {
            title.setError("title cannot be blank");
            title.requestFocus();
            return;
        }


        if(txt_detail.equals(""))
        {
            detail.setError("details cannot be blank");
            detail.requestFocus();
            return;
        }


        if(txt_id.equals(""))
        {
            id.setError("post id cannot be blank");
            id.requestFocus();
            return;
        }

        loadingBar.setTitle("Uploading Data");
        loadingBar.setMessage("Please wait, while we are uploading data ...");
        loadingBar.show();
        loadingBar.setCanceledOnTouchOutside(true);

        Post post = new Post(Integer.parseInt(txt_user_id), txt_title, txt_detail);


        Call<Post> call = jsonPlaceHolderApi.putPost("123456", Integer.parseInt(txt_id), post);

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
