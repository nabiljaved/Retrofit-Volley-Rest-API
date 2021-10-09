package com.nabeeltech.retrofitcodinginflow;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nabeeltech.retrofitcodinginflow.Model.Comment;
import com.nabeeltech.retrofitcodinginflow.Model.Post;

import java.util.ArrayList;
import java.util.List;

public class getCommentsByPostId extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner dropDownSpinner;
    Button findPost;
    String text="";


    private TextView textViewResult;
    private jsonPlaceHolderApi jsonPlaceHolderApi;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_comments_by_post_id);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        findPost = findViewById(R.id.find);
        dropDownSpinner = findViewById(R.id.spinner);
        textViewResult = findViewById(R.id.text_view_result);
        loadingBar = new ProgressDialog(this);


        initializeSpinner();
        findPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textViewResult.setText("");
                loadingBar.setTitle("Fetching Data");
                loadingBar.setMessage("Please wait, while we are fetching data ...");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(true);
                initializeRetrofitService();

            }
        });




    }

    //initialize retrofit service
    private void initializeRetrofitService()
    {
        okHttpClient okHttpClient = new okHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.getOkHttpClient())
                .build();

        jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);
        //getPosts();
        getComments();
    }

    //get comments methods
    private void getComments()
    {
        //Call<List<Comment>> call = jsonPlaceHolderApi.getCommentsBySelectedId();
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments(Integer.parseInt(text));
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    loadingBar.dismiss();
                    return;
                }

                Log.d("statuscode   :", String.valueOf(response.code()));

                List<Comment> commentByPostID = response.body();
                loadingBar.dismiss();

                for(Comment comment : commentByPostID)
                {
                    String content = "";
                    content += "ID: " + comment.getId() + "\n";
                    content += "POST ID: " + comment.getPostId() + "\n";
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

    //initialize spinner
    private void initializeSpinner()
    {
        List age = new ArrayList<Integer>();
        for (int i = 1; i <= 100; i++) {
            age.add(Integer.toString(i));

        }
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, age);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        dropDownSpinner.setAdapter(spinnerArrayAdapter);
        dropDownSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        text = parent.getItemAtPosition(position).toString();
        //Toast.makeText(this, "Selected id   :"+text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
