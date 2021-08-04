package com.example.fixawy.Share.VerifyCode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fixawy.Client.HomePageClient.HomePageClientActivity;
import com.example.fixawy.Client.SelectKindOfChoicePage.SelectKindOfChoiceActivity;
import com.example.fixawy.Firebase.FirebaseHandlerClient;
import com.example.fixawy.Firebase.FirebaseHandlerWorker;
import com.example.fixawy.Pojos.User;
import com.example.fixawy.R;
import com.example.fixawy.Share.Homes.OwnerHome;
import com.example.fixawy.Share.Homes.WorkerHome;

import com.example.fixawy.Share.SessionManager;
import com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class VerificationCode extends AppCompatActivity {

    private Button mVerifyCodeBtn;
    private TextInputLayout otpEdit;
    private String OTP;
    private FirebaseAuth mAuth;
    String userName,email,phoneNum,address,password,verification_code,type,jobTitle,image;
    int numOfJob,rating,like,disLike;
    User userClient,userWorker;

    PhoneAuthCredential credential;

    String tokenId;

    SessionManager sessionManager;

    public static final String EXTR_USER_NAME ="userName";
    public static final String EXTR_PHONE_NUM ="phone";
    public static final String EXTRA_JOB_TITLE ="jobTitle";

    //tokinId
    public static final String EXTRA_TOKEN_ID ="tokenid";



    private FirebaseHandlerClient firebaseHandlerClient;
    private FirebaseHandlerWorker firebaseHandlerWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        sessionManager = new SessionManager(getApplicationContext());

        otpEdit = findViewById(R.id.edit_code);
        mVerifyCodeBtn = findViewById(R.id.btn_signUp);


        mAuth = FirebaseAuth.getInstance();


        OTP = getIntent().getStringExtra("auth");
        userName=getIntent().getStringExtra("userName");
        email=getIntent().getStringExtra("email");
        phoneNum=getIntent().getStringExtra("phone");
        address=getIntent().getStringExtra("address");
        type=getIntent().getStringExtra("type");
        password=getIntent().getStringExtra("password");
        jobTitle=getIntent().getStringExtra("jobTitle");


        //another worker data
        image=getIntent().getStringExtra("image");
        numOfJob = getIntent().getIntExtra("numOfJob",0);
        like = getIntent().getIntExtra("numOfLike",0);
        disLike = getIntent().getIntExtra("numOfDisLike",0);
        rating = getIntent().getIntExtra("rating",0);

        //tokenid
        tokenId = getIntent().getStringExtra("token");

        mVerifyCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verification_code = otpEdit.getEditText().getText().toString();
                if(verification_code.isEmpty()){
                    Toast.makeText(VerificationCode.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(VerificationCode.this, "OTP is invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });

        if(sessionManager.getLogin()){
            if(type.equals("Owner")){
                startActivity(new Intent(getApplicationContext(),HomePageClientActivity.class));
            }
            else if(type.equals("Worker")){
                startActivity(new Intent(getApplicationContext(),HomePageClientActivity.class));
            }

        }
    }
    private void signIn(PhoneAuthCredential credential){
        sendToMain();
    }



    private void sendToMain(){

        String urlDefaultImage = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.vectorstock.com%2Froyalty-free-vector%2Felectric-man-icon-flat-style-vector-23229025&psig=AOvVaw0TYMxMmyUjvkXdxecYmHg0&ust=1624235781566000&source=images&cd=vfe&ved=0CAoQjRxqFwoTCJi5w_j7pPECFQAAAAAdAAAAABAD";
        userClient = new User(userName,email,phoneNum,address,type,password,tokenId);
        userWorker = new User(userName,email,phoneNum,address,type,password,jobTitle,urlDefaultImage,numOfJob,like,disLike,rating,tokenId);

        if(type.equals("Owner")){
            registerClient(userClient);
            sessionManager.setLogin(true);
            sessionManager.setOwnerData(phoneNum,userName,tokenId);
            startActivity(new Intent(VerificationCode.this, HomePageClientActivity.class)
                    .putExtra(EXTR_PHONE_NUM,phoneNum).putExtra(EXTR_USER_NAME,userName).putExtra(EXTRA_TOKEN_ID,tokenId)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

        }
        else if(type.equals("Worker")){
            registerWorker(userWorker);
            sessionManager.setLogin(true);
            sessionManager.setWorkerData(phoneNum,userName,tokenId,jobTitle,image);
            startActivity(new Intent(VerificationCode.this, RequestedHomePageActivity.class)
                    .putExtra(EXTR_PHONE_NUM,phoneNum).putExtra(EXTR_USER_NAME,userName).putExtra(EXTRA_JOB_TITLE,jobTitle)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
        else {
            Toast.makeText(VerificationCode.this, "Faillllllllllllllllled", Toast.LENGTH_SHORT).show();
        }

    }

    public void registerClient(User user){
        firebaseHandlerClient = new FirebaseHandlerClient();
        firebaseHandlerClient.addClientrData(user,user.getPhone()).addOnSuccessListener(suc->{

        });
    }

    public void registerWorker(User user){
        firebaseHandlerWorker = new FirebaseHandlerWorker();
        firebaseHandlerWorker.addWorkerData(user,user.getPhone(),user.getJobTitle()).addOnSuccessListener(suc->{
        });
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