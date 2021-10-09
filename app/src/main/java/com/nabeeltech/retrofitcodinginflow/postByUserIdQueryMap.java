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

import com.nabeeltech.retrofitcodinginflow.Model.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class postByUserIdQueryMap extends AppCompatActivity {

    String userid1, userid2, sort_by, order_by = "";
    Spinner spin_1, spin_2, spin_3, spin_4;
    Button search;
    private jsonPlaceHolderApi jsonPlaceHolderApi;
    private TextView textViewResult;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_by_user_id_query_map);

        search = findViewById(R.id.find);
        spin_1 = findViewById(R.id.spinner);
        spin_2 = findViewById(R.id.spinner2);
        spin_3 = findViewById(R.id.spinner3);
        spin_4 = findViewById(R.id.spinner4);
        textViewResult = findViewById(R.id.text_view_result);
        loadingBar = new ProgressDialog(this);

        okHttpClient okHttpClient = new okHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.getOkHttpClient())
                .build();

        jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi.class);

        //initialize all apinners
        initializeSpinner1();
        initializeSpinner2();
        initializeSpinner3();
        initializeSpinner4();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewResult.setText("");
                loadingBar.setTitle("Fetching Data");
                loadingBar.setMessage("Please wait, while we are fetching data ...");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(true);

                initializeRetrofit();
            }
        });

    }


    private void initializeSpinner1()
    {
        List age = new ArrayList<Integer>();
        for (int i = 1; i <= 100; i++) {
            age.add(Integer.toString(i));

        }
        ArrayAdapter<Integer> spinnerArrayAdapter1 = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, age);
        spinnerArrayAdapter1.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spin_1.setAdapter(spinnerArrayAdapter1);

        spin_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userid1 = parent.getItemAtPosition(position).toString();
                Toast.makeText(postByUserIdQueryMap.this, "id1  :"+userid1, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initializeSpinner2()
    {

        List age = new ArrayList<Integer>();
        for (int i = 1; i <= 100; i++) {
            age.add(Integer.toString(i));

        }
        ArrayAdapter<Integer> spinnerArrayAdapter2 = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, age);
        spinnerArrayAdapter2.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spin_2.setAdapter(spinnerArrayAdapter2);

        spin_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userid2 = parent.getItemAtPosition(position).toString();
                Toast.makeText(postByUserIdQueryMap.this, "id2  :"+userid2, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initializeSpinner3()
    {
        final List<String> sortby = new ArrayList<>();
        sortby.add("id");
        sortby.add("userId");

        ArrayAdapter<String> dataAdapter_one;
        dataAdapter_one = new ArrayAdapter(postByUserIdQueryMap.this, android.R.layout.simple_spinner_item, sortby);

        //drop down layout style
        dataAdapter_one.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //attaching data adapter to spinner
        spin_3.setAdapter(dataAdapter_one);

        spin_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sort_by = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initializeSpinner4()
    {

        final List<String> order = new ArrayList<>();
        order.add("aesc");
        order.add("desc");

        ArrayAdapter<String> dataAdapter_two;
        dataAdapter_two = new ArrayAdapter(postByUserIdQueryMap.this, android.R.layout.simple_spinner_item, order);

        //drop down layout style
        dataAdapter_two.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //attaching data adapter to spinner
        spin_4.setAdapter(dataAdapter_two);

        spin_4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                order_by = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //initialize retrofit
    private void initializeRetrofit()
    {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", userid1);
        parameters.put("userId", userid2); //this is a downside of hashmap it takes only one key cant be duplicated
        parameters.put("_sort", sort_by);
        parameters.put("_order", order_by);

        Call<List<Post>> call = jsonPlaceHolderApi.getPostsByQueryMap(parameters);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()) {
                    loadingBar.dismiss();
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

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

}
