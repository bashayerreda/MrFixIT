package com.example.fixawy.ShopOwner.SignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fixawy.R;
import com.example.fixawy.Share.LoginPage.LoginActivity;
import com.example.fixawy.Share.RegisterPage.RegisterActivity;
import com.example.fixawy.Share.VerifyCode.VerificationCode;
import com.example.fixawy.ShopOwner.Login.ShopOwnerLoginActivity;
import com.example.fixawy.ShopOwner.VerifiyCode.ShopOwnerVerifiyCodeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ShopOwnerSignUPActivity extends AppCompatActivity {

    TextInputLayout editTextPassword,editTextShopName,editTextConfirmPassword,editTextPhone,editTextAddress;
    Button btnContinue;
    TextView textViewLogin;
    FirebaseAuth mAuth;
    String shopName,phoneNum,address,password,confirmPassword,codeSend,phoneNumber,type,shopType,image;
    private TextView processText;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_owner_sign_u_p);

        editTextPassword = findViewById(R.id.edit_password);
        btnContinue = findViewById(R.id.btnContinue);
        editTextShopName=findViewById(R.id.edit_shopName);
        editTextConfirmPassword=findViewById(R.id.edit_confirmPassword);
        editTextPhone=findViewById(R.id.edit_phone);
        editTextAddress=findViewById(R.id.edit_address);
        textViewLogin=findViewById(R.id.alreadyHaveAccount);
        processText=findViewById(R.id.progress);
        mAuth=FirebaseAuth.getInstance();
        type=getIntent().getExtras().getString("type");
        shopType=getIntent().getExtras().getString("shopType");


        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ShopOwnerSignUPActivity.this, ShopOwnerLoginActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("shopType",shopType);
                startActivity(intent);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });


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
                    public void run() {
                        Intent otpIntent = new Intent(ShopOwnerSignUPActivity.this , ShopOwnerVerifiyCodeActivity.class);

                        otpIntent.putExtra("auth" , s);
                        otpIntent.putExtra("shopName",shopName);
                        otpIntent.putExtra("phone",phoneNum);
                        otpIntent.putExtra("address",address);
                        otpIntent.putExtra("type",type);
                        otpIntent.putExtra("password",password);
                        otpIntent.putExtra("shopType",shopType);
                        startActivity(otpIntent);
                    }
                }, 10000);

            }
        };
    }

    private void sendToMain(){
        startActivity(new Intent(ShopOwnerSignUPActivity.this,ShopOwnerVerifiyCodeActivity.class));
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

    public void register(){
        String country_code = "20";
        phoneNum = editTextPhone.getEditText().getText().toString().trim();
        shopName=editTextShopName.getEditText().getText().toString().trim();
        address=editTextAddress.getEditText().getText().toString().trim();
        password = editTextPassword.getEditText().getText().toString().trim();
        confirmPassword=editTextConfirmPassword.getEditText().toString().trim();
        type=getIntent().getExtras().getString("type");
        shopType=getIntent().getExtras().getString("shopType");


        if (shopName.isEmpty()){
            editTextShopName.setError("ShopName is required");
            editTextShopName.requestFocus();
            return;
        }

        if (phoneNum.isEmpty()){
            editTextPhone.setError("Phone is required");
            editTextPhone.requestFocus();
            return;
        }

        if (!Patterns.PHONE.matcher(phoneNum).matches()) {
            editTextPhone.setError("Please Enter Valid Phone");
            editTextPhone.requestFocus();
            return;
        }

        if (address.isEmpty()){
            editTextAddress.setError("Address is required");
            editTextAddress.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()){
            editTextConfirmPassword.setError("ConfirmPassword is required");
            editTextConfirmPassword.requestFocus();
            return;
        }

        if (password.length() < 6){
            editTextPassword.setError("password is required 6 character");
            editTextPassword.requestFocus();
            return;
        }

             /*   if (!(confirmPassword.equals(password))) {
                    editTextConfirmPassword.setError("Password isn't matched");
                    editTextConfirmPassword.requestFocus();
                    return;
                }*/

        phoneNumber = "+" + country_code + "" + phoneNum;
        if (!phoneNum.isEmpty()){
            PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                    .setPhoneNumber(phoneNumber)
                    .setTimeout(60L , TimeUnit.SECONDS)
                    .setActivity(ShopOwnerSignUPActivity.this)
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
