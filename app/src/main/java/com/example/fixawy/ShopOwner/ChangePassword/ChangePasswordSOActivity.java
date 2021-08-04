package com.example.fixawy.ShopOwner.ChangePassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fixawy.R;
import com.example.fixawy.Share.ChangePassword.ChangePassword;
import com.example.fixawy.Share.LoginPage.LoginActivity;
import com.example.fixawy.ShopOwner.Login.ShopOwnerLoginActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePasswordSOActivity extends AppCompatActivity {

    TextInputLayout editTextNewPassword,editTextConfirmPassword;
    Button buttonUpdatePassword;
    String OTP,phoneNum,verification_code,newPassword,confirmPassword,type,shopType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_s_o);

        editTextNewPassword=findViewById(R.id.edit_password);
        editTextConfirmPassword=findViewById(R.id.edit_confirmPassword);
        buttonUpdatePassword=findViewById(R.id.btnUpdatePassword);


        OTP = getIntent().getStringExtra("auth");
        phoneNum=getIntent().getStringExtra("phone");
        verification_code=getIntent().getStringExtra("verification_code");
        type=getIntent().getExtras().getString("type");
        shopType=getIntent().getExtras().getString("shopType");

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


                if (type.equals("ShopOwner")){
                    if (shopType.equals("Electricity")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ShopOwner").child(shopType).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePasswordSOActivity.this, ShopOwnerLoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("shopType", shopType);
                        startActivity(intent);
                        finish();
                    }
                    else if (shopType.equals("Plumber")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ShopOwner").child(shopType).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePasswordSOActivity.this, ShopOwnerLoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("shopType", shopType);
                        startActivity(intent);
                        finish();
                    }
                    else if (shopType.equals("Carpenter")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ShopOwner").child(shopType).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePasswordSOActivity.this, ShopOwnerLoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("shopType", shopType);
                        startActivity(intent);
                        finish();
                    }
                    else if (shopType.equals("Painter")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ShopOwner").child(shopType).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePasswordSOActivity.this, ShopOwnerLoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("shopType", shopType);
                        startActivity(intent);
                        finish();
                    }
                    else if (shopType.equals("Tiles Handyman")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ShopOwner").child(shopType).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePasswordSOActivity.this, ShopOwnerLoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("shopType", shopType);
                        startActivity(intent);
                        finish();
                    }
                    else if (shopType.equals("Mason")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ShopOwner").child(shopType).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePasswordSOActivity.this, ShopOwnerLoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("shopType", shopType);
                        startActivity(intent);
                        finish();
                    }
                    else if (shopType.equals("Smith")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ShopOwner").child(shopType).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePasswordSOActivity.this, ShopOwnerLoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("shopType", shopType);
                        startActivity(intent);
                        finish();
                    }
                    else if (shopType.equals("Parquet")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ShopOwner").child(shopType).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePasswordSOActivity.this, ShopOwnerLoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("shopType", shopType);
                        startActivity(intent);
                        finish();
                    }
                    else if (shopType.equals("Gypsum")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ShopOwner").child(shopType).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePasswordSOActivity.this, ShopOwnerLoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("shopType", shopType);
                        startActivity(intent);
                        finish();
                    }
                    else if (shopType.equals("Marble")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ShopOwner").child(shopType).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePasswordSOActivity.this, ShopOwnerLoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("shopType", shopType);
                        startActivity(intent);
                        finish();
                    }
                    else if (shopType.equals("Alumetal")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ShopOwner").child(shopType).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePasswordSOActivity.this, ShopOwnerLoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("shopType", shopType);
                        startActivity(intent);
                        finish();
                    }
                    else if (shopType.equals("Glass")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ShopOwner").child(shopType).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePasswordSOActivity.this, ShopOwnerLoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("shopType", shopType);
                        startActivity(intent);
                        finish();
                    }
                    else if (shopType.equals("Wood")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ShopOwner").child(shopType).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePasswordSOActivity.this, ShopOwnerLoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("shopType", shopType);
                        startActivity(intent);
                        finish();
                    }
                    else if (shopType.equals("Curtains")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ShopOwner").child(shopType).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePasswordSOActivity.this, ShopOwnerLoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("shopType", shopType);
                        startActivity(intent);
                        finish();
                    }
                    else if (shopType.equals("Satellite")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ShopOwner").child(shopType).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePasswordSOActivity.this, ShopOwnerLoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("shopType", shopType);
                        startActivity(intent);
                        finish();
                    }
                    else if (shopType.equals("Appliances Maintenance")){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ShopOwner").child(shopType).child("Data");
                        reference.child(phoneNum).child("password").setValue(newPassword);
                        Intent intent = new Intent(ChangePasswordSOActivity.this, ShopOwnerLoginActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("shopType", shopType);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

    }
}