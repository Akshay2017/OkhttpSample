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

public class Main3Activity extends AppCompatActivity {

    private static final String TAG = "Main3Activity";
    private TextView t;
    private Button b;
    private OkHttpClient clients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        t= (TextView) findViewById(R.id.textView);
        b= (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata();
            }
        });

        new AsyncTask<Void,Void,String>(){

            @Override
            protected String doInBackground(Void... params) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://api.github.com/users/ashokslsk")
                        .build();

                try {
                    final Response response = client.newCall(request).execute();

                    Log.d(TAG, "doInBackground() called with: " + "params = [" + response.body().string() + "]");

                    return response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                t.setText(s);
            }
        }.execute();
        clients = new OkHttpClient();

    }

    private void getdata() {


        final Request request = new Request.Builder()
                .url("http://www.json-generator.com/api/json/get/cfuzQvcryq?indent=2")
                .build();

        clients.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        t.setText("Failure");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response){
                Main3Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final String data;
                        try {
                            data = response.body().string();

                            JSONArray jsonArray=new JSONArray(data);

                            for (int i=0;i<jsonArray.length();i++){

                                JSONObject jsonObject=jsonArray.getJSONObject(i);

                                final int age=jsonObject.getInt("age");
                                final String name=jsonObject.getString("name");
                                final String email=jsonObject.getString("email");

                                t.setText(age+"    "+name+"    "+email);


                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });


    }
}