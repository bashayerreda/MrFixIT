package com.example.fixawy.Share.SelectionPage;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.fixawy.Client.HomePageClient.HomePageClientActivity;
import com.example.fixawy.R;
import com.example.fixawy.Share.Homes.OwnerHome;
import com.example.fixawy.Share.Homes.WorkerHome;
import com.example.fixawy.Share.LoginPage.LoginActivity;
import com.example.fixawy.Share.RegisterPage.RegisterActivity;
import com.example.fixawy.Share.Session.SharedPreferencesConfig;
import com.example.fixawy.Share.SessionManager;
import com.example.fixawy.ShopOwner.AllShopsAvailable.AllShopsAvailableActivity;
import com.example.fixawy.Worker.SelectJobPage.SelectJobActivity;

import java.util.Locale;


public class SelectMembershipType extends AppCompatActivity {

    Button btnOwner,btnWorker,btnShopOwner;
    SharedPreferencesConfig preferencesConfig;
  ImageView langChange;

  SharedPreferences sp;
  SharedPreferences.Editor editor;

  SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_membership_type);
          loadLocal();
        btnOwner=findViewById(R.id.client_btn);
        btnWorker=findViewById(R.id.worker_btn);
        btnShopOwner=findViewById(R.id.shop_owner_btn);
        langChange = findViewById(R.id.select_lang);


        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();
        editor.apply();

        sessionManager = new SessionManager(this);
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle(getResources().getString(R.string.app_name));
      /*  preferencesConfig = new SharedPreferencesConfig(getApplicationContext());
        if (preferencesConfig.readUserLoginStatus()) {
            Intent intent = new Intent(SelectMembershipType.this, HomePageClientActivity.class);
            startActivity(intent);
            finish();
        }*/
     langChange.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             showChangeLanguageDialog();
         }
     });

        btnOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  startActivity(new Intent(getApplicationContext(), RegisterActivity.class)
                          .putExtra("type", "Owner"));

                //  preferencesConfig.writeUserLoginStatus(true);

            }
        });

        btnWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SelectJobActivity.class)
                        .putExtra("type", "Worker"));
                //preferencesConfig.writeWorkerLoginStatus(true);
            }
        });

        btnShopOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AllShopsAvailableActivity.class)
                        .putExtra("type","ShopOwner"));
            }
        });


    }
    public void showChangeLanguageDialog(){
         String items[] = {"English","Arabic"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SelectMembershipType.this);
        builder.setTitle("Choose language");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    setLocalization("en");
                    editor.putString("lang","en");
                    editor.apply();
                    sessionManager.setLanguage("en");
                    recreate();
                }
                else if (which ==1){
                    setLocalization("ar");
                    editor.putString("lang","ar");
                    editor.apply();
                    sessionManager.setLanguage("ar");
                    recreate();
                }
                dialog.dismiss();
            }
        });
        AlertDialog mDialog = builder.create();
        mDialog.show();

    }

    private void setLocalization(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("my_lang",lang);
        editor.apply();


    }
    public void loadLocal(){
        SharedPreferences preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String lang = preferences.getString("my_lang","");
        setLocalization(lang);
    }

    //backButton
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);

        return;
    }
}