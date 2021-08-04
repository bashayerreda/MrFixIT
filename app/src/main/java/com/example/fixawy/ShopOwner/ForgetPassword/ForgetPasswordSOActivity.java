package com.example.fixawy.ShopOwner.ForgetPassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fixawy.R;
import com.example.fixawy.Share.ForgetPassword.ForgetPassword;
import com.example.fixawy.Share.VerifyCode.ReceiveCode;
import com.example.fixawy.ShopOwner.VerifiyCode.ReceiveCodeSOActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ForgetPasswordSOActivity extends AppCompatActivity {

    TextInputLayout editEnterPhone;
    Button buttonSendCode;
    private TextView processText;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    String phoneNum,phoneNumber,type,shopType;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_s_o);

        editEnterPhone=findViewById(R.id.edit_phone);
        buttonSendCode=findViewById(R.id.btnSendCode);
        processText=findViewById(R.id.progress);
        mAuth=FirebaseAuth.getInstance();
        type=getIntent().getExtras().getString("type");
        shopType=getIntent().getExtras().getString("shopType");


        buttonSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { sendCode(); }});

        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                processText.setText(e.getMessage());
                processText.setTextColor(Color.RED);
                processText.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                processText.setText("OTP has been Sent");
                processText.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {//put recive code for shopOwner when forgetPassword
                        Intent otpIntent = new Intent(ForgetPasswordSOActivity.this , ReceiveCodeSOActivity.class);
                        otpIntent.putExtra("auth" , s);
                        otpIntent.putExtra("phone",phoneNum);
                        otpIntent.putExtra("type",type);
                        otpIntent.putExtra("shopType",shopType);
                        startActivity(otpIntent);
                    }
                }, 10000);

            }
        };
    }


    private void sendToMain(){
        Intent otpIntent = new Intent(ForgetPasswordSOActivity.this , ReceiveCodeSOActivity.class);
        otpIntent.putExtra("phone",phoneNum);
        otpIntent.putExtra("type",type);
        otpIntent.putExtra("shopType",shopType);
        startActivity(otpIntent);
    }
    private void signIn(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    sendToMain();
                }else{
                    processText.setText(task.getException().getMessage());
                    processText.setTextColor(Color.RED);
                    processText.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    public void sendCode(){
        String country_code = "20";
        phoneNum = editEnterPhone.getEditText().getText().toString().trim();

        phoneNumber = "+" + country_code + "" + phoneNum;
        if (!phoneNum.isEmpty()){
            PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                    .setPhoneNumber(phoneNumber)
                    .setTimeout(60L , TimeUnit.SECONDS)
                    .setActivity(ForgetPasswordSOActivity.this)
                    .setCallbacks(mCallBacks)
                    .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
        }else{
            processText.setText("Please Enter Country Code and Phone Number");
            processText.setTextColor(Color.RED);
            processText.setVisibility(View.VISIBLE);
        }
    }
}
