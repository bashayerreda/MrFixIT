package com.example.fixawy.ShopOwner.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fixawy.Client.HomePageClient.HomePageClientActivity;
import com.example.fixawy.R;
import com.example.fixawy.Share.ForgetPassword.ForgetPassword;
import com.example.fixawy.Share.LoginPage.LoginActivity;
import com.example.fixawy.Share.RegisterPage.RegisterActivity;
import com.example.fixawy.ShopOwner.ForgetPassword.ForgetPasswordSOActivity;
import com.example.fixawy.ShopOwner.ShowProducts.ShowProductsActivity;
import com.example.fixawy.ShopOwner.SignUp.ShopOwnerSignUPActivity;
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

import java.util.Arrays;

public class ShopOwnerLoginActivity extends AppCompatActivity {


    TextInputLayout editTextPhone, editTextPassword;
    Button buttonSignIn;
    String phone,password;
    TextView textViewSignUp,textViewForgetPassword;
    FirebaseAuth fAuth;


    String type,shopName,shopType;

    FirebaseUser user;
    DatabaseReference databaseReference;

    public static final String EXTRA_SHOP_NAME ="shopName";
    public static final String EXTRA_PHONE_NUM ="phone";
    public static final String EXTRA_SHOP_TYPE ="shopType";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_owner_login);


        editTextPhone = findViewById(R.id.edit_phone);
        editTextPassword = findViewById(R.id.edit_password);
        buttonSignIn = findViewById(R.id.login);
        textViewSignUp=findViewById(R.id.SignUp);
        textViewForgetPassword=findViewById(R.id.forgetPassword);
        fAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        user=fAuth.getCurrentUser();
        type=getIntent().getExtras().getString("type");
        shopType=getIntent().getExtras().getString("shopType");



        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShopOwnerLoginActivity.this, ShopOwnerSignUPActivity.class)
                        .putExtra("type",type)
                        .putExtra("shopType",shopType));
            }
        });

        textViewForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShopOwnerLoginActivity.this, ForgetPasswordSOActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("shopType",shopType);
                startActivity(intent);
            }
        });


        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneLogin();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        if (fAuth.getCurrentUser() != null){
        }
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

// check if ShopOwner with shopType...
        else if (type.equals("ShopOwner")) {
            if (shopType.equals("Electricity")) {
                databaseReference.child("ShopOwner").child("Electricity").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                shopName = dataSnapshot.child(phone).child("shopName").getValue(String.class);
                                Toast.makeText(ShopOwnerLoginActivity.this, shopType, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, phone, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, shopName, Toast.LENGTH_LONG).show();

                                startActivity(new Intent(ShopOwnerLoginActivity.this, ShowProductsActivity.class)
                                        .putExtra(EXTRA_PHONE_NUM,phone)
                                        .putExtra(EXTRA_SHOP_NAME,shopName)
                                        .putExtra(EXTRA_SHOP_TYPE,shopType));
                            } else {
                                Toast.makeText(ShopOwnerLoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ShopOwnerLoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (shopType.equals("Plumber")){
                databaseReference.child("ShopOwner").child("Plumber").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                shopName = dataSnapshot.child(phone).child("shopName").getValue(String.class);
                                Toast.makeText(ShopOwnerLoginActivity.this, shopType, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, phone, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, shopName, Toast.LENGTH_LONG).show();

                                startActivity(new Intent(ShopOwnerLoginActivity.this, ShowProductsActivity.class)
                                        .putExtra(EXTRA_PHONE_NUM,phone)
                                        .putExtra(EXTRA_SHOP_NAME,shopName)
                                        .putExtra(EXTRA_SHOP_TYPE,shopType));
                            } else {
                                Toast.makeText(ShopOwnerLoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ShopOwnerLoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (shopType.equals("Carpenter")){
                databaseReference.child("ShopOwner").child("Carpenter").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                shopName = dataSnapshot.child(phone).child("shopName").getValue(String.class);
                                Toast.makeText(ShopOwnerLoginActivity.this, shopType, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, phone, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, shopName, Toast.LENGTH_LONG).show();

                                startActivity(new Intent(ShopOwnerLoginActivity.this, ShowProductsActivity.class)
                                        .putExtra(EXTRA_PHONE_NUM,phone)
                                        .putExtra(EXTRA_SHOP_NAME,shopName)
                                        .putExtra(EXTRA_SHOP_TYPE,shopType));
                            } else {
                                Toast.makeText(ShopOwnerLoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ShopOwnerLoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (shopType.equals("Painter")){
                databaseReference.child("ShopOwner").child("Painter").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                shopName = dataSnapshot.child(phone).child("shopName").getValue(String.class);
                                Toast.makeText(ShopOwnerLoginActivity.this, shopType, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, phone, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, shopName, Toast.LENGTH_LONG).show();

                                startActivity(new Intent(ShopOwnerLoginActivity.this, ShowProductsActivity.class)
                                        .putExtra(EXTRA_PHONE_NUM,phone)
                                        .putExtra(EXTRA_SHOP_NAME,shopName)
                                        .putExtra(EXTRA_SHOP_TYPE,shopType));
                            } else {
                                Toast.makeText(ShopOwnerLoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ShopOwnerLoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (shopType.equals("TilesHandyMan")){
                databaseReference.child("ShopOwner").child("TilesHandyMan").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                shopName = dataSnapshot.child(phone).child("shopName").getValue(String.class);
                                Toast.makeText(ShopOwnerLoginActivity.this, shopType, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, phone, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, shopName, Toast.LENGTH_LONG).show();

                                startActivity(new Intent(ShopOwnerLoginActivity.this, ShowProductsActivity.class)
                                        .putExtra(EXTRA_PHONE_NUM,phone)
                                        .putExtra(EXTRA_SHOP_NAME,shopName)
                                        .putExtra(EXTRA_SHOP_TYPE,shopType));
                            } else {
                                Toast.makeText(ShopOwnerLoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ShopOwnerLoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (shopType.equals("Mason")){
                databaseReference.child("ShopOwner").child("Mason").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                shopName = dataSnapshot.child(phone).child("shopName").getValue(String.class);
                                Toast.makeText(ShopOwnerLoginActivity.this, shopType, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, phone, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, shopName, Toast.LENGTH_LONG).show();

                                startActivity(new Intent(ShopOwnerLoginActivity.this, ShowProductsActivity.class)
                                        .putExtra(EXTRA_PHONE_NUM,phone)
                                        .putExtra(EXTRA_SHOP_NAME,shopName)
                                        .putExtra(EXTRA_SHOP_TYPE,shopType));
                            } else {
                                Toast.makeText(ShopOwnerLoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ShopOwnerLoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (shopType.equals("Smith")){
                databaseReference.child("ShopOwner").child("Smith").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                shopName = dataSnapshot.child(phone).child("shopName").getValue(String.class);
                                Toast.makeText(ShopOwnerLoginActivity.this, shopType, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, phone, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, shopName, Toast.LENGTH_LONG).show();

                                startActivity(new Intent(ShopOwnerLoginActivity.this, ShowProductsActivity.class)
                                        .putExtra(EXTRA_PHONE_NUM,phone)
                                        .putExtra(EXTRA_SHOP_NAME,shopName)
                                        .putExtra(EXTRA_SHOP_TYPE,shopType));
                            } else {
                                Toast.makeText(ShopOwnerLoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ShopOwnerLoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (shopType.equals("Parquet")){
                databaseReference.child("ShopOwner").child("Parquet").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                shopName = dataSnapshot.child(phone).child("shopName").getValue(String.class);
                                Toast.makeText(ShopOwnerLoginActivity.this, shopType, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, phone, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, shopName, Toast.LENGTH_LONG).show();

                                startActivity(new Intent(ShopOwnerLoginActivity.this, ShowProductsActivity.class)
                                        .putExtra(EXTRA_PHONE_NUM,phone)
                                        .putExtra(EXTRA_SHOP_NAME,shopName)
                                        .putExtra(EXTRA_SHOP_TYPE,shopType));
                            } else {
                                Toast.makeText(ShopOwnerLoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ShopOwnerLoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (shopType.equals("Gypsum")){
                databaseReference.child("ShopOwner").child("Gypsum").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                shopName = dataSnapshot.child(phone).child("shopName").getValue(String.class);
                                Toast.makeText(ShopOwnerLoginActivity.this, shopType, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, phone, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, shopName, Toast.LENGTH_LONG).show();

                                startActivity(new Intent(ShopOwnerLoginActivity.this, ShowProductsActivity.class)
                                        .putExtra(EXTRA_PHONE_NUM,phone)
                                        .putExtra(EXTRA_SHOP_NAME,shopName)
                                        .putExtra(EXTRA_SHOP_TYPE,shopType));
                            } else {
                                Toast.makeText(ShopOwnerLoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ShopOwnerLoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


            else if (shopType.equals("Marble")){
                databaseReference.child("ShopOwner").child("Marble").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                shopName = dataSnapshot.child(phone).child("shopName").getValue(String.class);
                                Toast.makeText(ShopOwnerLoginActivity.this, shopType, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, phone, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, shopName, Toast.LENGTH_LONG).show();

                                startActivity(new Intent(ShopOwnerLoginActivity.this, ShowProductsActivity.class)
                                        .putExtra(EXTRA_PHONE_NUM,phone)
                                        .putExtra(EXTRA_SHOP_NAME,shopName)
                                        .putExtra(EXTRA_SHOP_TYPE,shopType));
                            } else {
                                Toast.makeText(ShopOwnerLoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ShopOwnerLoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (shopType.equals("Alumetal")){
                databaseReference.child("ShopOwner").child("Alumetal").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                shopName = dataSnapshot.child(phone).child("shopName").getValue(String.class);
                                Toast.makeText(ShopOwnerLoginActivity.this, shopType, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, phone, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, shopName, Toast.LENGTH_LONG).show();

                                startActivity(new Intent(ShopOwnerLoginActivity.this, ShowProductsActivity.class)
                                        .putExtra(EXTRA_PHONE_NUM,phone)
                                        .putExtra(EXTRA_SHOP_NAME,shopName)
                                        .putExtra(EXTRA_SHOP_TYPE,shopType));
                            } else {
                                Toast.makeText(ShopOwnerLoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ShopOwnerLoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (shopType.equals("Glass")){
                databaseReference.child("ShopOwner").child("Glass").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                shopName = dataSnapshot.child(phone).child("shopName").getValue(String.class);
                                Toast.makeText(ShopOwnerLoginActivity.this, shopType, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, phone, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, shopName, Toast.LENGTH_LONG).show();

                                startActivity(new Intent(ShopOwnerLoginActivity.this, ShowProductsActivity.class)
                                        .putExtra(EXTRA_PHONE_NUM,phone)
                                        .putExtra(EXTRA_SHOP_NAME,shopName)
                                        .putExtra(EXTRA_SHOP_TYPE,shopType));
                            } else {
                                Toast.makeText(ShopOwnerLoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ShopOwnerLoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (shopType.equals("WoodPainter")){
                databaseReference.child("ShopOwner").child("WoodPainter").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                shopName = dataSnapshot.child(phone).child("shopName").getValue(String.class);
                                Toast.makeText(ShopOwnerLoginActivity.this, shopType, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, phone, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, shopName, Toast.LENGTH_LONG).show();

                                startActivity(new Intent(ShopOwnerLoginActivity.this, ShowProductsActivity.class)
                                        .putExtra(EXTRA_PHONE_NUM,phone)
                                        .putExtra(EXTRA_SHOP_NAME,shopName)
                                        .putExtra(EXTRA_SHOP_TYPE,shopType));
                            } else {
                                Toast.makeText(ShopOwnerLoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ShopOwnerLoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (shopType.equals("Curtains")){
                databaseReference.child("ShopOwner").child("Curtains").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                shopName = dataSnapshot.child(phone).child("shopName").getValue(String.class);
                                Toast.makeText(ShopOwnerLoginActivity.this, shopType, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, phone, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, shopName, Toast.LENGTH_LONG).show();

                                startActivity(new Intent(ShopOwnerLoginActivity.this, ShowProductsActivity.class)
                                        .putExtra(EXTRA_PHONE_NUM,phone)
                                        .putExtra(EXTRA_SHOP_NAME,shopName)
                                        .putExtra(EXTRA_SHOP_TYPE,shopType));
                            } else {
                                Toast.makeText(ShopOwnerLoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ShopOwnerLoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (shopType.equals("Satellite")){
                databaseReference.child("ShopOwner").child("Satellite").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                shopName = dataSnapshot.child(phone).child("shopName").getValue(String.class);
                                Toast.makeText(ShopOwnerLoginActivity.this, shopType, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, phone, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, shopName, Toast.LENGTH_LONG).show();

                                startActivity(new Intent(ShopOwnerLoginActivity.this, ShowProductsActivity.class)
                                        .putExtra(EXTRA_PHONE_NUM,phone)
                                        .putExtra(EXTRA_SHOP_NAME,shopName)
                                        .putExtra(EXTRA_SHOP_TYPE,shopType));
                            } else {
                                Toast.makeText(ShopOwnerLoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ShopOwnerLoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            else if (shopType.equals("Appliances Maintenance")){
                databaseReference.child("ShopOwner").child("Appliances Maintenance").child("Data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phone).exists()) {
                            if (dataSnapshot.child(phone).child("password").getValue(String.class).equals(password)) {
                                shopName = dataSnapshot.child(phone).child("shopName").getValue(String.class);
                                Toast.makeText(ShopOwnerLoginActivity.this, shopType, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, phone, Toast.LENGTH_LONG).show();
                                Toast.makeText(ShopOwnerLoginActivity.this, shopName, Toast.LENGTH_LONG).show();

                                startActivity(new Intent(ShopOwnerLoginActivity.this, ShowProductsActivity.class)
                                        .putExtra(EXTRA_PHONE_NUM,phone)
                                        .putExtra(EXTRA_SHOP_NAME,shopName)
                                        .putExtra(EXTRA_SHOP_TYPE,shopType));
                            } else {
                                Toast.makeText(ShopOwnerLoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ShopOwnerLoginActivity.this, "No Data is existed", Toast.LENGTH_SHORT).show();
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