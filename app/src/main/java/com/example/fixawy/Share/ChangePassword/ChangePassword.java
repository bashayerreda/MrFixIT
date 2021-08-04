package com.example.fixawy.Share.ChangePassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fixawy.R;
import com.example.fixawy.Share.LoginPage.LoginActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePassword extends AppCompatActivity {

    TextInputLayout editTextNewPassword,editTextConfirmPassword;
    Button buttonUpdatePassword;
    String OTP,phoneNum,verification_code,newPassword,confirmPassword,type,jobTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        editTextNewPassword=findViewById(R.id.edit_password);
        editTextConfirmPassword=findViewById(R.id.edit_confirmPassword);
        buttonUpdatePassword=findViewById(R.id.btnUpdatePassword);


        OTP = getIntent().getStringExtra("auth");
        phoneNum=getIntent().getStringExtra("phone");
        verification_code=getIntent().getStringExtra("verification_code");
        type=getIntent().getExtras().getString("type");
        jobTitle=getIntent().getExtras().getString("jobTitle");

        buttonUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword=editTextNewPassword.getEditText().getText().toString().trim();
                confirmPassword=editTextConfirmPassword.getEditText().getText().toString().trim();
                phoneNum=getIntent().getStringExtra("phone");

                if (newPassword.isEmpty()){
                    editTextNewPassword.setError("Password is required");
                    editTextNewPassword.requestFocus();
                    return;
                }

                if (confirmPassword.isEmpty()){
                    editTextConfirmPassword.setError("ConfirmPassword is required");
                    editTextConfirmPassword.requestFocus();
                    return;
                }

                if (newPassword.length() < 6){
                    editTextNewPassword.setError("password is required 6 character");
                    editTextNewPassword.requestFocus();
                    return;
                }

                if (!(confirmPassword.equals(newPassword))) {
                    editTextConfirmPassword.setError("Password isn't matched");
                    editTextConfirmPassword.requestFocus();
                    return;
                }

                if(type.equals("Owner")) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Client").child("Data");
                    reference.child(phoneNum).child("password").setValue(newPassword);

                    Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("jobTitle", jobTitle);
                    startActivity(intent);
                    finish();
                }

                else if (type.equals("Worker")){
                    if (jobTitle.equals("Electricity")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Worker").child(jobTitle).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("jobTitle", jobTitle);
                        startActivity(intent);
                        finish();
                    }
                    else if (jobTitle.equals("Plumber")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Worker").child(jobTitle).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("jobTitle", jobTitle);
                        startActivity(intent);
                        finish();
                    }
                    else if (jobTitle.equals("Carpenter")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Worker").child(jobTitle).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("jobTitle", jobTitle);
                        startActivity(intent);
                        finish();
                    }
                    else if (jobTitle.equals("Painter")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Worker").child(jobTitle).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("jobTitle", jobTitle);
                        startActivity(intent);
                        finish();
                    }
                    else if (jobTitle.equals("Tiles Handyman")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Worker").child(jobTitle).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("jobTitle", jobTitle);
                        startActivity(intent);
                        finish();
                    }
                    else if (jobTitle.equals("Mason")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Worker").child(jobTitle).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("jobTitle", jobTitle);
                        startActivity(intent);
                        finish();
                    }
                    else if (jobTitle.equals("Smith")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Worker").child(jobTitle).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("jobTitle", jobTitle);
                        startActivity(intent);
                        finish();
                    }
                    else if (jobTitle.equals("Parquet")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Worker").child(jobTitle).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("jobTitle", jobTitle);
                        startActivity(intent);
                        finish();
                    }
                    else if (jobTitle.equals("Gypsum")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Worker").child(jobTitle).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("jobTitle", jobTitle);
                        startActivity(intent);
                        finish();
                    }
                    else if (jobTitle.equals("Marble")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Worker").child(jobTitle).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("jobTitle", jobTitle);
                        startActivity(intent);
                        finish();
                    }
                    else if (jobTitle.equals("Alumetal")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Worker").child(jobTitle).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("jobTitle", jobTitle);
                        startActivity(intent);
                        finish();
                    }
                    else if (jobTitle.equals("Glass")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Worker").child(jobTitle).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("jobTitle", jobTitle);
                        startActivity(intent);
                        finish();
                    }
                    else if (jobTitle.equals("Wood")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Worker").child(jobTitle).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("jobTitle", jobTitle);
                        startActivity(intent);
                        finish();
                    }
                    else if (jobTitle.equals("Curtains")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Worker").child(jobTitle).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("jobTitle", jobTitle);
                        startActivity(intent);
                        finish();
                    }
                    else if (jobTitle.equals("Satellite")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Worker").child(jobTitle).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("jobTitle", jobTitle);
                        startActivity(intent);
                        finish();
                    }
                    else if (jobTitle.equals("Appliances Maintenance")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Worker").child(jobTitle).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("jobTitle", jobTitle);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

    }
}