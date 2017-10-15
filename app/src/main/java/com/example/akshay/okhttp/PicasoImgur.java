package com.example.akshay.okhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicasoImgur extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picaso_imgur);
        imageView = (ImageView) findViewById(R.id.imageView);

        Picasso.with(getBaseContext()).load("http://i.imgur.com/DvpvklR.png").into(imageView);
    }
}
