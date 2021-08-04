package com.example.fixawy.Client.ShowProductsOfShopType;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fixawy.Client.AllTypesOfShops.AllTypesOfShopsActivity;
import com.example.fixawy.Pojos.Product;
import com.example.fixawy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTR_USER_NAME;

public class ShowProductsOfShopTypeActivity extends AppCompatActivity {

    String phoneNum,shopType,shopName,phoneClient,clientName;
    RecyclerView mRecyclerView;
    DatabaseReference mRef;
    ShowProductsOfShopTypeAdapter showProductsOfShopTypeAdapter;
    Product product;
    TextView textViewJobTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products_of_shop_type);

        mRecyclerView = findViewById(R.id.productList);
        textViewJobTitle=findViewById(R.id.txt_job_title);


        phoneNum = getIntent().getStringExtra("phone");
        shopType = getIntent().getStringExtra("shopType");
        shopName = getIntent().getStringExtra("shopName");

        phoneClient = getIntent().getStringExtra("phoneClient");
        clientName = getIntent().getStringExtra(EXTR_USER_NAME);

        textViewJobTitle.setText("All Products Of "+ shopType);


        mRef = FirebaseDatabase.getInstance().getReference();
        showProductsOfShopTypeAdapter = new ShowProductsOfShopTypeAdapter(ShowProductsOfShopTypeActivity.this);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(showProductsOfShopTypeAdapter);
        readData();
    }
    // return all questions for specific job...
    public void readData(){
        mRef.child("ShopOwner").child(shopType).child("Product Posts").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        product = dataSnapshot.getValue(Product.class);
                        showProductsOfShopTypeAdapter.add(product);
                    }

                }
            }
        });
    }

}