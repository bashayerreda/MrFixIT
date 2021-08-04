package com.example.fixawy.Share.Homes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fixawy.R;
import com.example.fixawy.Share.SelectionPage.SelectMembershipType;
//import com.example.fixawy.Share.Session.SharedPreferencesConfig;
import com.google.firebase.auth.FirebaseAuth;

public class WorkerHome extends AppCompatActivity {

    Button buttonLogout;
    //SharedPreferencesConfig preferencesConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_home);


        buttonLogout=findViewById(R.id.logout);
       // preferencesConfig = new SharedPreferencesConfig(getApplicationContext());

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(WorkerHome.this, SelectMembershipType.class);
            //    preferencesConfig.writeWorkerLoginStatus(false);
                startActivity(intent);
                finish();
            }
        });
    }
}