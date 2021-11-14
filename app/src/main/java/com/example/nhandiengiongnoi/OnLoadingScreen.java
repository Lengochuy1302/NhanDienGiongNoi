package com.example.nhandiengiongnoi;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class OnLoadingScreen extends AppCompatActivity {
    Intent service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_loading_screen);
        getSupportActionBar().hide();

        LottieAnimationView loadinggoogle = findViewById(R.id.loading);
        loadinggoogle.setSpeed((float) 0.5);
        Animation avttext2 = AnimationUtils.loadAnimation(this,R.anim.splash_anim_img);
        TextView texttesst2 = findViewById(R.id.text2);
        texttesst2.setAnimation(avttext2);
        avttext2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        avttext2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                texttesst2.setVisibility(View.VISIBLE);
                Intent intent = new Intent(OnLoadingScreen.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

}