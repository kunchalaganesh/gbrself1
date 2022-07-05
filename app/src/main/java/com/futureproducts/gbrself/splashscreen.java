package com.futureproducts.gbrself;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.futureproducts.gbrself.appsupport.SessionManage;

public class splashscreen extends AppCompatActivity {
    SessionManage sessionManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        sessionManage = SessionManage.getInstance(this);

        LottieAnimationView animationView
                = findViewById(R.id.animationView);
        animationView
                .addAnimatorUpdateListener(
                        (animation) -> {
                            // Do something.
//                            Toast.makeText(this, "done1", Toast.LENGTH_SHORT).show();
                        });
        animationView
                .playAnimation();

        if (animationView.isAnimating()) {
            // Do something.
//            Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String s = sessionManage.loginn();
                Log.d("tag", "checkstate1" + s);
                if(s.matches("logedin")){
                    Intent go =new Intent(splashscreen.this, MainActivity.class);
                    startActivity(go);
                    finish();

                }else {

                    Intent go = new Intent(splashscreen.this, loginactivity.class);
                    startActivity(go);
                    finish();
                }
            }
        }, 2200);


    }
}