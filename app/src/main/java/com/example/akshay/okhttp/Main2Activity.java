package com.example.akshay.okhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Main2Activity extends AppCompatActivity {
    private TextView textView;
    private StringBuilder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView= (TextView) findViewById(R.id.textView);
        builder=new StringBuilder();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://www.json-generator.com/api/json/get/cfuzQvcryq?indent=2")
                .build();

        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Main2Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("Failure");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String data=response.body().string();

                try {
                    getJsonFromString(data);

                    Main2Activity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(builder.toString());
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                Main2Activity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        textView.setText(data);
//                    }
//                });
            }
        });


    }

    private void getJsonFromString(String data) throws JSONException {
        JSONArray jsonArray=new JSONArray(data);

        for (int i=0;i<jsonArray.length();i++){

            JSONObject jsonObject=jsonArray.getJSONObject(i);

            displyJsonObject(jsonObject);


        }
    }

    private void displyJsonObject(JSONObject jsonObject) throws JSONException {

        final int age=jsonObject.getInt("age");
        final String name=jsonObject.getString("name");
        final String email=jsonObject.getString("email");

        Main2Activity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                builder.append(
                        "Age :"+age+"" +
                                "\n Name :"+name+
                                "\n Email :"+email

                );
                // Toast.makeText(Main2Activity.this, age, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
