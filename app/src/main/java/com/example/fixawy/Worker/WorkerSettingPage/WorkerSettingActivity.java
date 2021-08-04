package com.example.fixawy.Worker.WorkerSettingPage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fixawy.Client.ClientSettingPage.ClientSettingsActivity;
import com.example.fixawy.Helper.LocaleHelper;
import com.example.fixawy.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WorkerSettingActivity extends AppCompatActivity {
    TextInputEditText textInputEditTextName,textInputEditTextEmail,textInputEditTextPhone,
            textInputEditTextPassword,textInputEditTextRepassword;
    TextView textViewAddress;
    Button buttonCancel,buttonApply;
    ImageButton imageButtonLanguages;
    DatabaseReference ref;
    DatabaseReference updateRef;
    Context context;
    Resources resources;



    String phone;
    String job;
    String oldPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_setting);

        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        job = intent.getStringExtra("job");


        /* ----- Components Assigning ----- */
        textInputEditTextName = findViewById(R.id.editText_name_settings);
        textInputEditTextEmail = findViewById(R.id.editText_email_settings);
        //textInputEditTextPhone = findViewById(R.id.editText_phone_settings);
        textViewAddress = findViewById(R.id.editText_address_settings);
        textInputEditTextPassword = findViewById(R.id.editText_newpassword_settings);
        textInputEditTextRepassword = findViewById(R.id.editText_repassword_settings);
        buttonApply = findViewById(R.id.btn_apply_settings);
        imageButtonLanguages = findViewById(R.id.imageBtn_languages_settings);

        imageButtonLanguages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });



        ref = FirebaseDatabase.getInstance().getReference().child("Worker").child(job).child("Data").child(phone);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("userName").getValue().toString();
                String email = snapshot.child("email").getValue().toString();
                String address = snapshot.child("address").getValue().toString();
                oldPass = snapshot.child("password").getValue().toString();

                textInputEditTextName.setText(name);
                textInputEditTextEmail.setText(email);
                textViewAddress.setText(address);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Detect Location
        Places.initialize(getApplicationContext(),getString(R.string.api_key));
        //setEditText non focusable
        textViewAddress.setFocusable(false);
        textViewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize place field list
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                //create intent
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(WorkerSettingActivity.this);
                //start Activity result
                startActivityForResult(intent,1);

            }
        });


        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

    }


    private void updateData() {
        String new_name = textInputEditTextName.getText().toString();
        String new_email = textInputEditTextEmail.getText().toString();
        String new_address = textViewAddress.getText().toString();

        String new_password = textInputEditTextPassword.getText().toString();
        String rep_password = textInputEditTextRepassword.getText().toString();




        if (new_name.isEmpty()) {
            textInputEditTextName.setError("UserName is required");
            textInputEditTextName.requestFocus();
            return;
        }

        else if (new_email.isEmpty()) {
            textInputEditTextEmail.setError("Email is required");
            textInputEditTextEmail.requestFocus();
            return;
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(new_email).matches()) {
            textInputEditTextEmail.setError("Please Enter Valid Email");
            textInputEditTextEmail.requestFocus();
            return;
        }

//        if (phoneNum.isEmpty()) {
//            editTextPhone.setError("Phone is required");
//            editTextPhone.requestFocus();
//            return;
//        }
//
//        if (!Patterns.PHONE.matcher(phoneNum).matches()) {
//            editTextPhone.setError("Please Enter Valid Phone");
//            editTextPhone.requestFocus();
//            return;
//        }

        else if (new_address.isEmpty()) {
            textViewAddress.setError("Address is required");
            textViewAddress.requestFocus();
            return;
        }

        else if (new_password.isEmpty()) {
            textInputEditTextPassword.setError("Password is required");
            textInputEditTextPassword.requestFocus();
            return;
        }

        else if (rep_password.isEmpty()) {
            textInputEditTextRepassword.setError("ConfirmPassword is required");
            textInputEditTextRepassword.requestFocus();
            return;
        }

        else if (new_password.length() < 6) {
            textInputEditTextPassword.setError("password is required 6 character");
            textInputEditTextPassword.requestFocus();
            return;
        }

        // 3-Checking if the password == repeat password
        else if (!(new_password.equals(rep_password))) {
            Toast.makeText(WorkerSettingActivity.this, "Mismatched passwords", Toast.LENGTH_SHORT).show();
        }else{
            HashMap hashMap = new HashMap();
            hashMap.put("userName", new_name);
            hashMap.put("email", new_email);
            hashMap.put("address", new_address);
            hashMap.put("password", new_password);

            updateRef = FirebaseDatabase.getInstance().getReference().child("Worker").child(job).child("Data");
            updateRef.child(phone).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Toast.makeText(WorkerSettingActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();
                }
            });
        }





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            //when success Initialize place
            Place place = Autocomplete.getPlaceFromIntent(data);
            //set address on editText
            textViewAddress.setText(place.getAddress());

        }

    }




    public void showChangeLanguageDialog(){
        String items[] = {"English","Arabic"};
        AlertDialog.Builder builder = new AlertDialog.Builder(WorkerSettingActivity.this);
        builder.setTitle("Choose language");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    setLocalization("en");
                    recreate();
                }
                else if (which ==1){
                    setLocalization("ar");
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
}