package com.example.fixawy.ShopOwner.AddNewProduct;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fixawy.Client.AskQuestionPage.AskQuestionActivity;
import com.example.fixawy.Client.AskQuestionPage.AskQuestionViewModel;
import com.example.fixawy.Client.PreviousQuestionPage.PreviousQuestionActivity;
import com.example.fixawy.Pojos.Product;
import com.example.fixawy.Pojos.Questions;
import com.example.fixawy.R;
import com.example.fixawy.ShopOwner.ShowProducts.ShowProductsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddNewProductActivity extends AppCompatActivity {

    TextInputLayout textInputLayoutProductName,textInputLayoutProductPrice,textInputLayoutProductDesc;
    ImageView imageViewUploadProductPhoto;
    Button buttonPostProduct,buttonUploadPhoto;
    String phoneNum,shopType,shopName,productName,productPrice,productDesc;
    AddNewProductViewModel addNewProductViewModel;
    Product product;
    ImageView imageViewBack;

    StorageReference storageReference;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);

        textInputLayoutProductName = findViewById(R.id.edit_nameOfProduct);
        textInputLayoutProductPrice = findViewById(R.id.edit_priceOfProduct);
        textInputLayoutProductDesc = findViewById(R.id.edit_descOfProduct);
        buttonPostProduct=findViewById(R.id.btn_post);
        buttonUploadPhoto = findViewById(R.id.image_btn);
        imageViewUploadProductPhoto = findViewById(R.id.imageOfProduct);
        imageViewBack = findViewById(R.id.backToPrevious);
        addNewProductViewModel = new AddNewProductViewModel();
        storageReference = FirebaseStorage.getInstance().getReference();
        phoneNum = getIntent().getStringExtra("phone");
        shopType = getIntent().getStringExtra("shopType");
        shopName = getIntent().getStringExtra("shopName");

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddNewProductActivity.this, phoneNum + " " + shopType + " " + shopName, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddNewProductActivity.this,ShowProductsActivity.class)
                        .putExtra("phone",phoneNum)
                        .putExtra("shopType",shopType)
                        .putExtra("shopName",shopName));
            }
        });

        buttonUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/");
                startActivityForResult(galleryIntent,2);

            }
        });
        buttonPostProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null){
                    uploadToFirebase(imageUri);
                }
                else {
                    Toast.makeText(AddNewProductActivity.this, "Please Add Image For Product", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data !=null){
            imageUri = data.getData();
            imageViewUploadProductPhoto.setImageURI(imageUri);
        }
    }

    private void uploadToFirebase(Uri uri){

        final StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        productName = textInputLayoutProductName.getEditText().getText().toString().trim();
                        productPrice = textInputLayoutProductPrice.getEditText().getText().toString().trim();
                        productDesc = textInputLayoutProductDesc.getEditText().getText().toString().trim();

                        product = new Product(productName,productDesc,productPrice,uri.toString(),phoneNum,shopName);

                        //add in Product path for specific shopType...
                        addNewProductViewModel.addProduct(product,phoneNum,shopType);


                        Toast.makeText(AddNewProductActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddNewProductActivity.this, ShowProductsActivity.class)
                                .putExtra("shopType",shopType)
                                .putExtra("phone",phoneNum)
                                .putExtra("shopName",shopName));


                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddNewProductActivity.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

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

