package com.nabeeltech.retrofitcodinginflow;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.nabeeltech.retrofitcodinginflow.Model.Comment;
import com.nabeeltech.retrofitcodinginflow.Model.Post;

import java.util.ArrayList;
import java.util.List;

public class postByUserId extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner dropDownSpinner;
    Button findPost;
    String text="";


    private TextView textViewResult;
    private jsonPlaceHolderApi jsonPlaceHolderApi;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_by_user_id);

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

        //Call<List<Post>> call = jsonPlaceHolderApi.getPostsByQuery(Integer.parseInt(text));
        //it can be single and multiple ?userId=1&userId=2&_sort=id etc.....
        //Call<List<Post>> call = jsonPlaceHolderApi.getPostsByQueryDesc(Integer.parseInt(text),2, "id", "desc");
        //id and order can both be null
        Call<List<Post>> call = jsonPlaceHolderApi.getPostsByQueryDesc(new Integer[]{Integer.valueOf(text), 2, 6}, "id", "desc");
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    loadingBar.dismiss();
                    return;
                }

                Log.d("statusCode   :", String.valueOf(response.code()));

                List<Post> posts = response.body();
                loadingBar.dismiss();

                for (Post post : posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
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




