package com.example.fixawy.Share.RegisterPage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.example.fixawy.Pojos.User;
import com.example.fixawy.R;
import com.example.fixawy.Share.LoginPage.LoginActivity;
import com.example.fixawy.Share.SessionManager;
import com.example.fixawy.Share.VerifyCode.VerificationCode;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {




    TextInputLayout editTextEmail, editTextPassword,editTextUserName,editTextConfirmPassword,editTextPhone,editTextAddress;
    Button btnContinue;
    TextView textViewLogin;
    FirebaseAuth mAuth;
    String userName,email,phoneNum,address,password,confirmPassword,codeSend,phoneNumber,type,jobTitle,image;
    int numOfJob,rating,like,disLike;
    private TextView processText;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    FirebaseUser firebaseUser;

    String tokenId;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);




        editTextEmail = findViewById(R.id.edit_email);
        editTextPassword = findViewById(R.id.edit_password);
        btnContinue = findViewById(R.id.btnContinue);
        editTextUserName=findViewById(R.id.edit_username);
        editTextConfirmPassword=findViewById(R.id.edit_confirmPassword);
        editTextPhone=findViewById(R.id.edit_phone);
        editTextAddress=findViewById(R.id.edit_address);
        textViewLogin=findViewById(R.id.alreadyHaveAccount);
        processText=findViewById(R.id.progress);
        mAuth=FirebaseAuth.getInstance();
        type=getIntent().getExtras().getString("type");
        userName=getIntent().getStringExtra("userName");
        email=getIntent().getStringExtra("email");
        jobTitle=getIntent().getExtras().getString("jobTitle");


        //Detect Location
        Places.initialize(getApplicationContext(),getString(R.string.api_key));
        //setEditText non focusable
        editTextAddress.setFocusable(false);
        editTextAddress.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize place field list
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                //create intent
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(RegisterActivity.this);
                //start Activity result
                startActivityForResult(intent,1);

            }
        });


//        numOfJob = getIntent().getExtras().getInt("numOfJob");
//        like = getIntent().getExtras().getInt("numOfLike");
//        disLike = getIntent().getExtras().getInt("numOfDisLike");
//        rating = getIntent().getExtras().getInt("rating");

        //another data to worker
//        numOfJob = getIntent().getIntExtra("numOfJob",0);
//        like = getIntent().getIntExtra("numOfLike",0);
//        disLike = getIntent().getIntExtra("numOfDisLike",0);
//        rating = getIntent().getIntExtra("rating",0);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();

        if (firebaseUser != null){
            editTextUserName.getEditText().setText(userName);
            editTextEmail.getEditText().setText(email);
        }

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(RegisterActivity.this,LoginActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("jobTitle",jobTitle);
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
                        Intent otpIntent = new Intent(RegisterActivity.this , VerificationCode.class);
                        //Intent otpIntent = new Intent(RegisterActivity.this , VerificationCode.class);

                        otpIntent.putExtra("auth" , s);
                        otpIntent.putExtra("userName",userName);
                        otpIntent.putExtra("email",email);
                        otpIntent.putExtra("phone",phoneNum);
                        otpIntent.putExtra("address",address);
                        otpIntent.putExtra("type",type);
                        otpIntent.putExtra("password",password);
                        otpIntent.putExtra("jobTitle",jobTitle);
                        //another data to worker
                        otpIntent.putExtra("image",image);
                        otpIntent.putExtra("numOfJob",numOfJob);
                        otpIntent.putExtra("numOfLike",like);
                        otpIntent.putExtra("numOfDisLike",disLike);
                        otpIntent.putExtra("rating",rating);

                        otpIntent.putExtra("token",tokenId);

                        startActivity(otpIntent);
                    }
                }, 10000);

            }
        };
    }

    private void sendToMain(){
        startActivity(new Intent(RegisterActivity.this,VerificationCode.class));
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

    public void register() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        tokenId = task.getResult();
                        Log.d("tokeniddddddd",tokenId);

                        // Log and toast
                        // String msg = getString(R.string.msg_token_fmt, token);
                      //  Toast.makeText(RegisterActivity.this, tokenId, Toast.LENGTH_SHORT).show();
                    }
                });


        String country_code = "20";
        phoneNum = editTextPhone.getEditText().getText().toString().trim();
        userName = editTextUserName.getEditText().getText().toString().trim();
        email = editTextEmail.getEditText().getText().toString().trim();
        address = editTextAddress.getEditText().getText().toString().trim();
        password = editTextPassword.getEditText().getText().toString().trim();
        confirmPassword = editTextConfirmPassword.getEditText().toString().trim();
        type = getIntent().getExtras().getString("type");
        jobTitle = getIntent().getExtras().getString("jobTitle");

        //another data to worker
//        numOfJob = getIntent().getExtras().getInt("numOfJob");
//        like = getIntent().getExtras().getInt("numOfLike");
//        disLike = getIntent().getExtras().getInt("numOfDisLike");
//        rating = getIntent().getExtras().getInt("rating");
        //another worker data
        image = getIntent().getStringExtra("image");
        numOfJob = getIntent().getIntExtra("numOfJob", 0);
        like = getIntent().getIntExtra("numOfLike", 0);
        disLike = getIntent().getIntExtra("numOfDisLike", 0);
        rating = getIntent().getIntExtra("rating", 0);
       // Log.d("tokeniddddddd",tokenId);
        tokenId = getIntent().getStringExtra("token");





        if (userName.isEmpty()) {
            editTextUserName.setError("UserName is required");
            editTextUserName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please Enter Valid Email");
            editTextEmail.requestFocus();
            return;
        }

        if (phoneNum.isEmpty()) {
            editTextPhone.setError("Phone is required");
            editTextPhone.requestFocus();
            return;
        }

        if (!Patterns.PHONE.matcher(phoneNum).matches()) {
            editTextPhone.setError("Please Enter Valid Phone");
            editTextPhone.requestFocus();
            return;
        }

        if (address.isEmpty()) {
            editTextAddress.setError("Address is required");
            editTextAddress.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.setError("ConfirmPassword is required");
            editTextConfirmPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
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
        if (!phoneNum.isEmpty()) {
            PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                    .setPhoneNumber(phoneNumber)
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(RegisterActivity.this)
                    .setCallbacks(mCallBacks)
                    .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
        } else {
            processText.setText("Please Enter Country Code and Phone Number");
            processText.setTextColor(Color.RED);
            processText.setVisibility(View.VISIBLE);
        }






      /*  registerViewModel = new RegisterViewModel();
        userClient = new User(userName,email,phoneNum,address,type,password);
        userWorker = new User(userName,email,phoneNum,address,type,password,jobTitle);

        registerViewModel.registerClient(userClient);
        registerViewModel.registerWorker(userWorker);
*/



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            //when success Initialize place
            Place place = Autocomplete.getPlaceFromIntent(data);
            //set address on editText
            editTextAddress.getEditText().setText(place.getAddress());

        }

    }

}
