package com.example.akshay.okhttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView results;
    private Button gets;
    OkHttpClient client;
    private StringBuilder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        results= (TextView) findViewById(R.id.result);
        gets= (Button) findViewById(R.id.get);
        builder=new StringBuilder();

        gets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });
        client = new OkHttpClient();

    }
    private void load(){

        final Request request = new Request.Builder()
                .url("https://api.imgur.com/3/gallery/OHDYZ")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        results.setText("Failure");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                final String data=response.body().string();


                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getJsonFromString(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        results.setText(builder.toString());

                    }
                });


            }
        });


    }

    private void getJsonFromString(String data) throws JSONException {

        JSONObject jsonObject=new JSONObject(data);
        JSONArray items = jsonObject.getJSONArray("data");
        int i=items.length();
        JSONObject item = items.getJSONObject(i);

//        int id=jsonObject.getInt("id");
//        String login=jsonObject.getString("login");
        String it=item.getString("description");

        results.setText(it.toString());
//        builder.append(
//                "Link :"+it+""
//
//        );
    }
}
