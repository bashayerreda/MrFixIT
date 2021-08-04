package com.example.fixawy.Share.LoginPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fixawy.Client.HomePageClient.HomePageClientActivity;
import com.example.fixawy.Share.ForgetPassword.ForgetPassword;

import com.example.fixawy.R;
import com.example.fixawy.Share.RegisterPage.RegisterActivity;

import com.example.fixawy.Share.SessionManager;
import com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Arrays;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {


    TextInputLayout editTextPhone, editTextPassword;
    Button buttonSignIn;
    String phone,password;
    TextView textViewSignUp,textViewForgetPassword;
    FirebaseAuth fAuth;
    ImageView gmailLogin,facebookLogin;
    GoogleSignInClient mGoogleSignInClient;
    static final int GOOGLE_SIGN_IN = 123;
    private CallbackManager mCallbackManager;
    String email,type,userName,jobTitle,image;
    int numOfJob,rating,like,disLike;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    DatabaseReference mDatabaseReference;


    SessionManager sessionManager;

    String tokenId;
    String newTokenID;

    public static final String EXTR_USER_NAME ="userName";
    public static final String EXTR_PHONE_NUM ="phone";
    public static final String EXTRA_JOB_TITLE ="jobTitle";
    public static final String EXTRA_WORKER_IMAGE ="image";

    //another worker data
    public static final String EXTRA_NUM_OF_JOB ="numOfJob";
    public static final String EXTRA_LIKE ="numOfLike";
    public static final String EXTRA_DIS_LIKE ="numOfDisLike";
    public static final String EXTRA_RATING ="rating";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(getApplicationContext());

        editTextPhone = findViewById(R.id.edit_phone);
        editTextPassword = findViewById(R.id.edit_password);
        buttonSignIn = findViewById(R.id.login);
        textViewSignUp=findViewById(R.id.SignUp);
        gmailLogin=findViewById(R.id.gmail_login);
        facebookLogin=findViewById(R.id.facebook_login);
        textViewForgetPassword=findViewById(R.id.forgetPassword);
        fAuth = FirebaseAuth.getInstance();
        mCallbackManager=CallbackManager.Factory.create();

        database = FirebaseDatabase.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        mDatabaseReference = database.getReference();
        user=fAuth.getCurrentUser();
        type=getIntent().getExtras().getString("type");
        jobTitle=getIntent().getExtras().getString("jobTitle");
        tokenId = getIntent().getExtras().getString("token");

        //another worker data
        numOfJob = getIntent().getIntExtra("numOfJob",0);
        like = getIntent().getIntExtra("numOfLike",0);
        disLike = getIntent().getIntExtra("numOfDisLike",0);
        rating = getIntent().getIntExtra("rating",0);

        tokenId = getIntent().getStringExtra("token");


        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class)
                        .putExtra("type",type)
                        .putExtra("jobTitle",jobTitle));
            }
        });

        textViewForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ForgetPassword.class);
                intent.putExtra("type",type);
                intent.putExtra("jobTitle",jobTitle);
                startActivity(intent);
            }
        });

        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookLogin(mCallbackManager);
            }
        });

        gmailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInGoogle();
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneLogin();
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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut();
    }

    private void facebookLogin(CallbackManager mCallbackManager) {

        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
                Log.d("TAG2", "facebook:onCancel");
            }
            @Override
            public void onError(FacebookException error) {
                Log.d("TAG2", "facebook:onError", error);
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("TAG", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = fAuth.getCurrentUser(); FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("name").addListenerForSingleValueEvent(listener);

                            updateUIFacebook(user);
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (fAuth.getCurrentUser() != null){
        }
    }

    // sign with google..........
    void SignInGoogle(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {

                Log.w("TAG", "Google sign in failed", e);

            }
        }
        else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    //Google Authentication
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = fAuth.getCurrentUser();
                            updateUI(user);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .addListenerForSingleValueEvent(listener);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    ValueEventListener listener =new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            intent.putExtra("userName",userName);
            intent.putExtra("email",email);
            intent.putExtra("type",type);
            intent.putExtra("jobTitle",jobTitle);
            intent.putExtra("image",image);

            //another data to worker
            intent.putExtra("numOfJob",numOfJob);
            intent.putExtra("numOfLike",like);
            intent.putExtra("numOfDisLike",disLike);
            intent.putExtra("rating",rating);

            intent.putExtra("token",tokenId);
            startActivity(intent);
            finish();


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void updateUIFacebook(FirebaseUser user) {
        if (user != null) {
            userName = user.getDisplayName();
            email= user.getEmail();
        }
    }

    private void updateUI(FirebaseUser user) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null) {
            userName = account.getDisplayName();
            email = account.getEmail();

        }

    }

//    //get TokenId
//    public String getTokenID(){
//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            return;
//                        }
//
//                        // Get new FCM registration token
//                        tokenId = task.getResult();
//                        Log.d("tokeniddddddd",tokenId);
//
//                        // Log and toast
//                        // String msg = getString(R.string.msg_token_fmt, token);
//                        Toast.makeText(LoginActivity.this, tokenId, Toast.LENGTH_SHORT).show();
//                    }
//                });
//        return tokenId;
//    }


    //update TokenId for client
    public void updateTokinIDClient(String phone){

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        tokenId = task.getResult();
                        HashMap hashMap = new HashMap();
                        hashMap.put("tokenId",tokenId);
                        mDatabaseReference.child("Client").child("Data").child(phone).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                              //  Toast.makeText(LoginActivity.this, tokenId, Toast.LENGTH_SHORT).show();
                            }
                        });

                        // Log and toast
                        // String msg = getString(R.string.msg_token_fmt, token);
                      //  Toast.makeText(LoginActivity.this, tokenId, Toast.LENGTH_SHORT).show();
                    }
                });


    }

    //update TokenId for worker
    public void updateTokinIDWorker(String jobTitle,String phone){
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
                        HashMap hashMap = new HashMap();
                        hashMap.put("tokenId",tokenId);
                        databaseReference.child("Worker").child(jobTitle).child("Data").child(phone).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                              //  Toast.makeText(LoginActivity.this, tokenId, Toast.LENGTH_SHORT).show();

                            }
                        });

                        // Log and toast
                        // String msg = getString(R.string.msg_token_fmt, token);
                       // Toast.makeText(LoginActivity.this, tokenId, Toast.LENGTH_SHORT).show();
                    }
                });


    }

    public void phoneLogin() {

        phone = editTextPhone.getEditText().getText().toString();
        password = editTextPassword.getEditText().getText().toString();

        if (phone.isEmpty()) {
            editTextPhone.setError("phone is required");
            editTextPhone.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("password is required");
            editTextPassword.requestFocus();
            return;
        }
// check if Owner...
        if (type.equals("Owner")){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Client").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child(phone).exists()) {
                        if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                            userName = dataSnapshot.child(phone).child("userName").getValue(String.class);
                            updateTokinIDClient(phone);
                            sessionManager.setLogin(true);
                            sessionManager.setOwnerData(phone,userName,tokenId);
                            startActivity(new Intent(LoginActivity.this, HomePageClientActivity.class)
                                    .putExtra(EXTR_PHONE_NUM,phone).putExtra(EXTR_USER_NAME,userName).putExtra("token",tokenId));





                        } else {
                            Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
// check if worker with jobTitle...
        else if (type.equals("Worker")) {
            if (jobTitle.equals("Electricity")) {
                databaseReference.child("Worker").child("Electricity").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                userName = dataSnapshot.child(phone).child("userName").getValue(String.class);
                                jobTitle = dataSnapshot.child(phone).child("jobTitle").getValue(String.class);
                                image = dataSnapshot.child(phone).child("image").getValue(String.class);
                               // Toast.makeText(LoginActivity.this, jobTitle, Toast.LENGTH_LONG).show();
                                updateTokinIDWorker(jobTitle,phone);

                                sessionManager.setLogin(true);
                                sessionManager.setWorkerData(phone,userName,tokenId,jobTitle,image);

                                startActivity(new Intent(LoginActivity.this, RequestedHomePageActivity.class)
                                        .putExtra(EXTR_PHONE_NUM,phone).putExtra(EXTR_USER_NAME,userName).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_WORKER_IMAGE,image)
                                        .putExtra(EXTRA_NUM_OF_JOB,numOfJob).putExtra(EXTRA_LIKE,like).putExtra(EXTRA_DIS_LIKE,disLike).putExtra(EXTRA_RATING,rating));



                            } else {
                                Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (jobTitle.equals("Plumber")){
                databaseReference.child("Worker").child("Plumber").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                userName = dataSnapshot.child(phone).child("userName").getValue(String.class);
                                jobTitle = dataSnapshot.child(phone).child("jobTitle").getValue(String.class);
                                image = dataSnapshot.child(phone).child("image").getValue(String.class);
                              //  Toast.makeText(LoginActivity.this, jobTitle, Toast.LENGTH_LONG).show();
                                sessionManager.setLogin(true);
                                sessionManager.setWorkerData(phone,userName,tokenId,jobTitle,image);
                                startActivity(new Intent(LoginActivity.this, RequestedHomePageActivity.class)
                                        .putExtra(EXTR_PHONE_NUM,phone).putExtra(EXTR_USER_NAME,userName).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_WORKER_IMAGE,image)
                                        .putExtra(EXTRA_NUM_OF_JOB,numOfJob).putExtra(EXTRA_LIKE,like).putExtra(EXTRA_DIS_LIKE,disLike).putExtra(EXTRA_RATING,rating));


                                updateTokinIDWorker(jobTitle,phone);
                            } else {
                                Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (jobTitle.equals("Carpenter")){
                databaseReference.child("Worker").child("Carpenter").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                userName = dataSnapshot.child(phone).child("userName").getValue(String.class);
                                jobTitle = dataSnapshot.child(phone).child("jobTitle").getValue(String.class);
                                image = dataSnapshot.child(phone).child("image").getValue(String.class);
                              //  Toast.makeText(LoginActivity.this, jobTitle, Toast.LENGTH_LONG).show();
                                sessionManager.setLogin(true);
                                sessionManager.setWorkerData(phone,userName,tokenId,jobTitle,image);
                                startActivity(new Intent(LoginActivity.this, RequestedHomePageActivity.class)
                                        .putExtra(EXTR_PHONE_NUM,phone).putExtra(EXTR_USER_NAME,userName).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_WORKER_IMAGE,image)
                                        .putExtra(EXTRA_NUM_OF_JOB,numOfJob).putExtra(EXTRA_LIKE,like).putExtra(EXTRA_DIS_LIKE,disLike).putExtra(EXTRA_RATING,rating));


                                updateTokinIDWorker(jobTitle,phone);
                            } else {
                                Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (jobTitle.equals("Painter")){
                databaseReference.child("Worker").child("Painter").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                userName = dataSnapshot.child(phone).child("userName").getValue(String.class);
                                jobTitle = dataSnapshot.child(phone).child("jobTitle").getValue(String.class);
                                image = dataSnapshot.child(phone).child("image").getValue(String.class);
                             //   Toast.makeText(LoginActivity.this, jobTitle, Toast.LENGTH_LONG).show();
                                sessionManager.setLogin(true);
                                sessionManager.setWorkerData(phone,userName,tokenId,jobTitle,image);
                                startActivity(new Intent(LoginActivity.this, RequestedHomePageActivity.class)
                                        .putExtra(EXTR_PHONE_NUM,phone).putExtra(EXTR_USER_NAME,userName).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_WORKER_IMAGE,image)
                                        .putExtra(EXTRA_NUM_OF_JOB,numOfJob).putExtra(EXTRA_LIKE,like).putExtra(EXTRA_DIS_LIKE,disLike).putExtra(EXTRA_RATING,rating));


                                updateTokinIDWorker(jobTitle,phone);
                            } else {
                                Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (jobTitle.equals("TilesHandyMan")){
                databaseReference.child("Worker").child("TilesHandyMan").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                userName = dataSnapshot.child(phone).child("userName").getValue(String.class);
                                jobTitle = dataSnapshot.child(phone).child("jobTitle").getValue(String.class);
                                image = dataSnapshot.child(phone).child("image").getValue(String.class);
                              //  Toast.makeText(LoginActivity.this, jobTitle, Toast.LENGTH_LONG).show();
                                sessionManager.setLogin(true);
                                sessionManager.setWorkerData(phone,userName,tokenId,jobTitle,image);
                                startActivity(new Intent(LoginActivity.this, RequestedHomePageActivity.class)
                                        .putExtra(EXTR_PHONE_NUM,phone).putExtra(EXTR_USER_NAME,userName).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_WORKER_IMAGE,image)
                                        .putExtra(EXTRA_NUM_OF_JOB,numOfJob).putExtra(EXTRA_LIKE,like).putExtra(EXTRA_DIS_LIKE,disLike).putExtra(EXTRA_RATING,rating));

                                updateTokinIDWorker(jobTitle,phone);
                            } else {
                                Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (jobTitle.equals("Mason")){
                databaseReference.child("Worker").child("Mason").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                userName = dataSnapshot.child(phone).child("userName").getValue(String.class);
                                jobTitle = dataSnapshot.child(phone).child("jobTitle").getValue(String.class);
                                image = dataSnapshot.child(phone).child("image").getValue(String.class);
                              //  Toast.makeText(LoginActivity.this, jobTitle, Toast.LENGTH_LONG).show();
                                sessionManager.setLogin(true);
                                sessionManager.setWorkerData(phone,userName,tokenId,jobTitle,image);
                                startActivity(new Intent(LoginActivity.this, RequestedHomePageActivity.class)
                                        .putExtra(EXTR_PHONE_NUM,phone).putExtra(EXTR_USER_NAME,userName).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_WORKER_IMAGE,image)
                                        .putExtra(EXTRA_NUM_OF_JOB,numOfJob).putExtra(EXTRA_LIKE,like).putExtra(EXTRA_DIS_LIKE,disLike).putExtra(EXTRA_RATING,rating));

                                updateTokinIDWorker(jobTitle,phone);
                            } else {
                                Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (jobTitle.equals("Smith")){
                databaseReference.child("Worker").child("Smith").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                userName = dataSnapshot.child(phone).child("userName").getValue(String.class);
                                jobTitle = dataSnapshot.child(phone).child("jobTitle").getValue(String.class);
                                image = dataSnapshot.child(phone).child("image").getValue(String.class);
                           //     Toast.makeText(LoginActivity.this, jobTitle, Toast.LENGTH_LONG).show();
                                sessionManager.setLogin(true);
                                sessionManager.setWorkerData(phone,userName,tokenId,jobTitle,image);
                                startActivity(new Intent(LoginActivity.this, RequestedHomePageActivity.class)
                                        .putExtra(EXTR_PHONE_NUM,phone).putExtra(EXTR_USER_NAME,userName).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_WORKER_IMAGE,image)
                                        .putExtra(EXTRA_NUM_OF_JOB,numOfJob).putExtra(EXTRA_LIKE,like).putExtra(EXTRA_DIS_LIKE,disLike).putExtra(EXTRA_RATING,rating));

                                updateTokinIDWorker(jobTitle,phone);
                            } else {
                                Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (jobTitle.equals("Parquet")){
                databaseReference.child("Worker").child("Parquet").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                userName = dataSnapshot.child(phone).child("userName").getValue(String.class);
                                jobTitle = dataSnapshot.child(phone).child("jobTitle").getValue(String.class);
                                image = dataSnapshot.child(phone).child("image").getValue(String.class);
                             //   Toast.makeText(LoginActivity.this, jobTitle, Toast.LENGTH_LONG).show();
                                sessionManager.setLogin(true);
                                sessionManager.setWorkerData(phone,userName,tokenId,jobTitle,image);
                                startActivity(new Intent(LoginActivity.this, RequestedHomePageActivity.class)
                                        .putExtra(EXTR_PHONE_NUM,phone).putExtra(EXTR_USER_NAME,userName).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_WORKER_IMAGE,image)
                                        .putExtra(EXTRA_NUM_OF_JOB,numOfJob).putExtra(EXTRA_LIKE,like).putExtra(EXTRA_DIS_LIKE,disLike).putExtra(EXTRA_RATING,rating));

                                updateTokinIDWorker(jobTitle,phone);
                            } else {
                                Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (jobTitle.equals("Gypsum")){
                databaseReference.child("Worker").child("Gypsum").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                userName = dataSnapshot.child(phone).child("userName").getValue(String.class);
                                jobTitle = dataSnapshot.child(phone).child("jobTitle").getValue(String.class);
                                image = dataSnapshot.child(phone).child("image").getValue(String.class);
                              //  Toast.makeText(LoginActivity.this, jobTitle, Toast.LENGTH_LONG).show();
                                sessionManager.setLogin(true);
                                sessionManager.setWorkerData(phone,userName,tokenId,jobTitle,image);
                                startActivity(new Intent(LoginActivity.this, RequestedHomePageActivity.class)
                                        .putExtra(EXTR_PHONE_NUM,phone).putExtra(EXTR_USER_NAME,userName).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_WORKER_IMAGE,image)
                                        .putExtra(EXTRA_NUM_OF_JOB,numOfJob).putExtra(EXTRA_LIKE,like).putExtra(EXTRA_DIS_LIKE,disLike).putExtra(EXTRA_RATING,rating));

                                updateTokinIDWorker(jobTitle,phone);
                            } else {
                                Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


            else if (jobTitle.equals("Marble")){
                databaseReference.child("Worker").child("Marble").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                userName = dataSnapshot.child(phone).child("userName").getValue(String.class);
                                jobTitle = dataSnapshot.child(phone).child("jobTitle").getValue(String.class);
                                image = dataSnapshot.child(phone).child("image").getValue(String.class);
                             //   Toast.makeText(LoginActivity.this, jobTitle, Toast.LENGTH_LONG).show();
                                sessionManager.setLogin(true);
                                sessionManager.setWorkerData(phone,userName,tokenId,jobTitle,image);
                                startActivity(new Intent(LoginActivity.this, RequestedHomePageActivity.class)
                                        .putExtra(EXTR_PHONE_NUM,phone).putExtra(EXTR_USER_NAME,userName).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_WORKER_IMAGE,image)
                                        .putExtra(EXTRA_NUM_OF_JOB,numOfJob).putExtra(EXTRA_LIKE,like).putExtra(EXTRA_DIS_LIKE,disLike).putExtra(EXTRA_RATING,rating));

                                updateTokinIDWorker(jobTitle,phone);
                            } else {
                                Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (jobTitle.equals("Alumetal")){
                databaseReference.child("Worker").child("Alumetal").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                userName = dataSnapshot.child(phone).child("userName").getValue(String.class);
                                jobTitle = dataSnapshot.child(phone).child("jobTitle").getValue(String.class);
                                image = dataSnapshot.child(phone).child("image").getValue(String.class);
                             //   Toast.makeText(LoginActivity.this, jobTitle, Toast.LENGTH_LONG).show();
                                sessionManager.setLogin(true);
                                sessionManager.setWorkerData(phone,userName,tokenId,jobTitle,image);
                                startActivity(new Intent(LoginActivity.this, RequestedHomePageActivity.class)
                                        .putExtra(EXTR_PHONE_NUM,phone).putExtra(EXTR_USER_NAME,userName).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_WORKER_IMAGE,image)
                                        .putExtra(EXTRA_NUM_OF_JOB,numOfJob).putExtra(EXTRA_LIKE,like).putExtra(EXTRA_DIS_LIKE,disLike).putExtra(EXTRA_RATING,rating));

                                updateTokinIDWorker(jobTitle,phone);
                            } else {
                                Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (jobTitle.equals("Glass")){
                databaseReference.child("Worker").child("Glasses").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                userName = dataSnapshot.child(phone).child("userName").getValue(String.class);
                                jobTitle = dataSnapshot.child(phone).child("jobTitle").getValue(String.class);
                                image = dataSnapshot.child(phone).child("image").getValue(String.class);
                             //   Toast.makeText(LoginActivity.this, jobTitle, Toast.LENGTH_LONG).show();
                                sessionManager.setLogin(true);
                                sessionManager.setWorkerData(phone,userName,tokenId,jobTitle,image);
                                startActivity(new Intent(LoginActivity.this, RequestedHomePageActivity.class)
                                        .putExtra(EXTR_PHONE_NUM,phone).putExtra(EXTR_USER_NAME,userName).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_WORKER_IMAGE,image)
                                        .putExtra(EXTRA_NUM_OF_JOB,numOfJob).putExtra(EXTRA_LIKE,like).putExtra(EXTRA_DIS_LIKE,disLike).putExtra(EXTRA_RATING,rating));

                                updateTokinIDWorker(jobTitle,phone);
                            } else {
                                Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (jobTitle.equals("WoodPainter")){
                databaseReference.child("Worker").child("WoodPainter").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                userName = dataSnapshot.child(phone).child("userName").getValue(String.class);
                                jobTitle = dataSnapshot.child(phone).child("jobTitle").getValue(String.class);
                                image = dataSnapshot.child(phone).child("image").getValue(String.class);
                             //   Toast.makeText(LoginActivity.this, jobTitle, Toast.LENGTH_LONG).show();
                                sessionManager.setLogin(true);
                                sessionManager.setWorkerData(phone,userName,tokenId,jobTitle,image);
                                startActivity(new Intent(LoginActivity.this, RequestedHomePageActivity.class)
                                        .putExtra(EXTR_PHONE_NUM,phone).putExtra(EXTR_USER_NAME,userName).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_WORKER_IMAGE,image)
                                        .putExtra(EXTRA_NUM_OF_JOB,numOfJob).putExtra(EXTRA_LIKE,like).putExtra(EXTRA_DIS_LIKE,disLike).putExtra(EXTRA_RATING,rating));

                                updateTokinIDWorker(jobTitle,phone);
                            } else {
                                Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (jobTitle.equals("Curtains")){
                databaseReference.child("Worker").child("Curtains").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                userName = dataSnapshot.child(phone).child("userName").getValue(String.class);
                                jobTitle = dataSnapshot.child(phone).child("jobTitle").getValue(String.class);
                                image = dataSnapshot.child(phone).child("image").getValue(String.class);
                             //   Toast.makeText(LoginActivity.this, jobTitle, Toast.LENGTH_LONG).show();
                                sessionManager.setLogin(true);
                                sessionManager.setWorkerData(phone,userName,tokenId,jobTitle,image);
                                startActivity(new Intent(LoginActivity.this, RequestedHomePageActivity.class)
                                        .putExtra(EXTR_PHONE_NUM,phone).putExtra(EXTR_USER_NAME,userName).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_WORKER_IMAGE,image)
                                        .putExtra(EXTRA_NUM_OF_JOB,numOfJob).putExtra(EXTRA_LIKE,like).putExtra(EXTRA_DIS_LIKE,disLike).putExtra(EXTRA_RATING,rating));

                                updateTokinIDWorker(jobTitle,phone);
                            } else {
                                Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (jobTitle.equals("Satellite")){
                databaseReference.child("Worker").child("Satellite").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                userName = dataSnapshot.child(phone).child("userName").getValue(String.class);
                                jobTitle = dataSnapshot.child(phone).child("jobTitle").getValue(String.class);
                                image = dataSnapshot.child(phone).child("image").getValue(String.class);
                            //    Toast.makeText(LoginActivity.this, jobTitle, Toast.LENGTH_LONG).show();
                                sessionManager.setLogin(true);
                                sessionManager.setWorkerData(phone,userName,tokenId,jobTitle,image);
                                startActivity(new Intent(LoginActivity.this, RequestedHomePageActivity.class)
                                        .putExtra(EXTR_PHONE_NUM,phone).putExtra(EXTR_USER_NAME,userName).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_WORKER_IMAGE,image)
                                        .putExtra(EXTRA_NUM_OF_JOB,numOfJob).putExtra(EXTRA_LIKE,like).putExtra(EXTRA_DIS_LIKE,disLike).putExtra(EXTRA_RATING,rating));

                                updateTokinIDWorker(jobTitle,phone);
                            } else {
                                Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (jobTitle.equals("Appliances Maintenance")){
                databaseReference.child("Worker").child("Appliances Maintenance").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                userName = dataSnapshot.child(phone).child("userName").getValue(String.class);
                                jobTitle = dataSnapshot.child(phone).child("jobTitle").getValue(String.class);
                                image = dataSnapshot.child(phone).child("image").getValue(String.class);
                            //    Toast.makeText(LoginActivity.this, jobTitle, Toast.LENGTH_LONG).show();
                                sessionManager.setLogin(true);
                                sessionManager.setWorkerData(phone,userName,tokenId,jobTitle,image);
                                startActivity(new Intent(LoginActivity.this, RequestedHomePageActivity.class)
                                        .putExtra(EXTR_PHONE_NUM,phone).putExtra(EXTR_USER_NAME,userName).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_JOB_TITLE,jobTitle).putExtra(EXTRA_WORKER_IMAGE,image)
                                        .putExtra(EXTRA_NUM_OF_JOB,numOfJob).putExtra(EXTRA_LIKE,like).putExtra(EXTRA_DIS_LIKE,disLike).putExtra(EXTRA_RATING,rating));

                                updateTokinIDWorker(jobTitle,phone);
                            } else {
                                Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        }

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