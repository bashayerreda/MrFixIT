package com.example.fixawy.ShopOwner.ShowProducts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fixawy.Client.AskQuestionPage.AskQuestionActivity;
import com.example.fixawy.Client.PreviousQuestionPage.PreviousQuestionActivity;
import com.example.fixawy.Client.PreviousQuestionPage.PreviousQuestionAdapter;
import com.example.fixawy.Pojos.ClientHistory;
import com.example.fixawy.Pojos.Product;
import com.example.fixawy.Pojos.Questions;
import com.example.fixawy.R;
import com.example.fixawy.Share.SelectionPage.SelectMembershipType;
import com.example.fixawy.ShopOwner.AddNewProduct.AddNewProductActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowProductsActivity extends AppCompatActivity {

    FloatingActionButton floatingButtonAsk;
    String phoneNum,shopType,shopName;
    RecyclerView mRecyclerView;
    DatabaseReference mRef;
    ShowProductAdapter showProductAdapter;
    Product product;
    TextView textViewJobTitle;
    ImageView imageViewLogout;
    AlertDialog alertDialog;
    LayoutInflater inflater;
    Button btnYes,btnNo;
    FirebaseDatabase db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products);

        mRecyclerView = findViewById(R.id.productList);
        floatingButtonAsk = findViewById(R.id.addProduct);
        textViewJobTitle=findViewById(R.id.txt_job_title);
        imageViewLogout = findViewById(R.id.logout);
        phoneNum = getIntent().getStringExtra("phone");
        shopType = getIntent().getStringExtra("shopType");
        shopName = getIntent().getStringExtra("shopName");

        textViewJobTitle.setText("All Products for "+shopName);

        floatingButtonAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowProductsActivity.this, AddNewProductActivity.class);
                intent.putExtra("phone", phoneNum);
                intent.putExtra("shopType",shopType);
                intent.putExtra("shopName",shopName);
                startActivity(intent);
            }
        });


        imageViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = new AlertDialog.Builder(ShowProductsActivity.this).create();
                inflater = LayoutInflater.from(getApplicationContext());
                View dialogView = inflater.inflate(R.layout.logout_dialog, null);
                btnYes= dialogView.findViewById(R.id.yes);
                btnNo=dialogView.findViewById(R.id.no);
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentBack = new Intent(ShowProductsActivity.this, SelectMembershipType.class);
                        startActivity(intentBack);
                        alertDialog.cancel();
                    }
                });
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    { alertDialog.cancel(); }
                });
                alertDialog.setView(dialogView);
                alertDialog.show();
            }
        });

        mRef = FirebaseDatabase.getInstance().getReference();
        showProductAdapter = new ShowProductAdapter(ShowProductsActivity.this,phoneNum,shopName,shopType);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(showProductAdapter);
        readData();
    }
    // return all questions for specific job...
    public void readData(){
        mRef.child("ShopOwner").child(shopType).child("Product Posts").child(phoneNum).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    product = snapshot.getValue(Product.class);
                    showProductAdapter.add(product);
                }
            }
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