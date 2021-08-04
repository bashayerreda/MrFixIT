package com.example.fixawy.Share.VerifyCode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fixawy.R;
import com.example.fixawy.Share.ChangePassword.ChangePassword;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class ReceiveCode extends AppCompatActivity {

    private Button mVerifyCodeBtn;
    private TextInputLayout otpEdit;
    private String OTP,verification_code;
    private FirebaseAuth mAuth;
    String phoneNum,type,jobTitle;
    int numOfJob,rating,like,disLike;
    PhoneAuthCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_code);
        otpEdit = findViewById(R.id.edit_code);
        mVerifyCodeBtn = findViewById(R.id.btnNext);


        mAuth = FirebaseAuth.getInstance();


        OTP = getIntent().getStringExtra("auth");
        phoneNum=getIntent().getStringExtra("phone");
        type=getIntent().getExtras().getString("type");
        jobTitle=getIntent().getExtras().getString("jobTitle");

        //another worker data
//        numOfJob = getIntent().getIntExtra("numOfJob",0);
//        like = getIntent().getIntExtra("numOfLike",0);
//        disLike = getIntent().getIntExtra("numOfDisLike",0);
//        rating = getIntent().getIntExtra("rating",0);



        mVerifyCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verification_code = otpEdit.getEditText().getText().toString();
                if(verification_code.isEmpty()){
                    Toast.makeText(ReceiveCode.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (OTP != null){
                    credential = PhoneAuthProvider.getCredential(OTP , verification_code);
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                signIn(credential);
                            }
                            else {
                                Toast.makeText(ReceiveCode.this, "OTP is invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });
    }
    private void signIn(PhoneAuthCredential credential){
        sendToMain();
    }

    private void sendToMain(){
        Intent intentChangePassword=new Intent(ReceiveCode.this, ChangePassword.class);
        intentChangePassword.putExtra("auth" , OTP);
        intentChangePassword.putExtra("verification_code",verification_code);
        intentChangePassword.putExtra("phone",phoneNum);
        intentChangePassword.putExtra("type",type);
        intentChangePassword.putExtra("jobTitle",jobTitle);
        intentChangePassword.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentChangePassword);
        finish();
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