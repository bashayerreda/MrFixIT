package com.example.fixawy.ShopOwner.VerifiyCode;

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
import com.example.fixawy.Firebase.FirebaseHandlerClient;
import com.example.fixawy.Firebase.FirebaseHandlerShopOwner;
import com.example.fixawy.Firebase.FirebaseHandlerWorker;
import com.example.fixawy.Pojos.ShopOwnerUser;
import com.example.fixawy.Pojos.User;
import com.example.fixawy.R;
import com.example.fixawy.Share.VerifyCode.VerificationCode;
import com.example.fixawy.ShopOwner.ShowProducts.ShowProductsActivity;
import com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTR_PHONE_NUM;
import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTR_USER_NAME;

public class ShopOwnerVerifiyCodeActivity extends AppCompatActivity {


    private Button mVerifyCodeBtn;
    private TextInputLayout otpEdit;
    private String OTP;
    private FirebaseAuth mAuth;
    String shopName,phoneNum,address,password,verification_code,type,shopType,image;
    ShopOwnerUser shopOwnerUser;

    PhoneAuthCredential credential;

    public static final String EXTRA_SHOP_NAME ="shopName";
    public static final String EXTRA_PHONE_NUM ="phone";
    public static final String EXTRA_SHOP_TYPE ="shopType";

    FirebaseHandlerShopOwner firebaseHandlerShopOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_owner_verifiy_code);

        otpEdit = findViewById(R.id.edit_code);
        mVerifyCodeBtn = findViewById(R.id.btnNext);
        mAuth = FirebaseAuth.getInstance();


        OTP = getIntent().getStringExtra("auth");
        shopName=getIntent().getStringExtra("shopName");
        phoneNum=getIntent().getStringExtra("phone");
        address=getIntent().getStringExtra("address");
        type=getIntent().getStringExtra("type");
        password=getIntent().getStringExtra("password");
        shopType=getIntent().getStringExtra("shopType");



        mVerifyCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verification_code = otpEdit.getEditText().getText().toString();
                if(verification_code.isEmpty()){
                    Toast.makeText(ShopOwnerVerifiyCodeActivity.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(ShopOwnerVerifiyCodeActivity.this, "OTP is invalid", Toast.LENGTH_SHORT).show();
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
        shopOwnerUser = new ShopOwnerUser(phoneNum,address,type,password,shopName,shopType);
        if(type.equals("ShopOwner")) {
            registerShopOwner(shopOwnerUser);
            startActivity(new Intent(ShopOwnerVerifiyCodeActivity.this, ShowProductsActivity.class)
                    .putExtra(EXTRA_PHONE_NUM, phoneNum)
                    .putExtra(EXTRA_SHOP_NAME, shopName)
                    .putExtra(EXTRA_SHOP_TYPE,shopType)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
        else {
            Toast.makeText(ShopOwnerVerifiyCodeActivity.this, "Faillllllllllllllllled", Toast.LENGTH_SHORT).show();
        }


    }


    public void registerShopOwner(ShopOwnerUser shopOwnerUser){
        firebaseHandlerShopOwner = new FirebaseHandlerShopOwner();
        firebaseHandlerShopOwner.addShopOwnerData(shopOwnerUser,shopOwnerUser.getPhone(),shopOwnerUser.getShopType()).addOnSuccessListener(suc->{
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