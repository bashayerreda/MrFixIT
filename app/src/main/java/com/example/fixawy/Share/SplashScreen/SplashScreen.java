package com.example.fixawy.Share.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.fixawy.Activity_Tips;
import com.example.fixawy.Client.HomePageClient.HomePageClientActivity;
import com.example.fixawy.R;
import com.example.fixawy.Share.Homes.OwnerHome;
import com.example.fixawy.Share.LoginPage.LoginActivity;
import com.example.fixawy.Share.SelectionPage.SelectMembershipType;
import com.example.fixawy.Share.SessionManager;
import com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {
    Animation top_down , down_top;
    ImageView imageView;

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sessionManager = new SessionManager(getApplicationContext());

        top_down = AnimationUtils.loadAnimation(this,R.anim.top_down);
        imageView = findViewById(R.id.splash_image);
        imageView.setAnimation(top_down);
        final int secondsDelayed = 5000;
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            new Handler().postDelayed(() -> {
//                startActivity(new Intent(SplashScreen.this,Activity_Tips.class));
//                finish();

                if(sessionManager.getLogin()==false){
                    startActivity(new Intent(SplashScreen.this,Activity_Tips.class));
                    finish();
                }else if(sessionManager.getLogin()==true && sessionManager.getOwnerPhone() =="") {
                    startActivity(new Intent(SplashScreen.this, RequestedHomePageActivity.class));
                    finish();
                }else if(sessionManager.getLogin()==true && sessionManager.getWorkerPhone() ==""){
                    startActivity(new Intent(SplashScreen.this, HomePageClientActivity.class));
                    finish();
                }

            },2000);
        }else {
            new Handler().postDelayed(() -> {
                if(sessionManager.getLogin()==false){
                    startActivity(new Intent(SplashScreen.this, SelectMembershipType.class));
                    finish();
                }else if(sessionManager.getLogin()==true && sessionManager.getOwnerPhone() =="") {
                    startActivity(new Intent(SplashScreen.this, RequestedHomePageActivity.class));
                    finish();
                }else if(sessionManager.getLogin()==true && sessionManager.getWorkerPhone() ==""){
                    startActivity(new Intent(SplashScreen.this, HomePageClientActivity.class));
                    finish();
                }


            },500);
        }
    }
}