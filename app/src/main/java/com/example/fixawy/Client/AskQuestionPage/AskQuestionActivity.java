package com.example.fixawy.Client.AskQuestionPage;

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
import android.widget.TextView;
import android.widget.Toast;
import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTR_USER_NAME;
import com.example.fixawy.Client.PreviousQuestionPage.PreviousQuestionActivity;
import com.example.fixawy.Pojos.Questions;
import com.example.fixawy.Pojos.User;
import com.example.fixawy.R;
import com.example.fixawy.Share.VerifyCode.VerificationCode;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class AskQuestionActivity extends AppCompatActivity {

    EditText editTextQuestion;
    Button buttonPostQuestion,buttonUploadPhoto;
    String question,phoneClient,jobTitle,clientName;
    AskQuestionViewModel askQuestionViewModel;
    Questions userQuestions,workerQuestions;
    ImageView imageViewUploadPhoto;
    StorageReference storageReference;
    Uri imageUri;
    TextView textViewClientName;
    ImageView imageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        editTextQuestion=findViewById(R.id.worker_rating_comment_et);
        buttonPostQuestion=findViewById(R.id.postQuestion);
        buttonUploadPhoto = findViewById(R.id.image_btn);
        imageViewUploadPhoto = findViewById(R.id.uploadPhoto);
        textViewClientName = findViewById(R.id.client_name);
        imageViewBack = findViewById(R.id.backToPrevious);
        askQuestionViewModel = new AskQuestionViewModel();
        storageReference = FirebaseStorage.getInstance().getReference();
        phoneClient=getIntent().getStringExtra("phoneClient");
        jobTitle = getIntent().getStringExtra("CategoryType");
        clientName = getIntent().getStringExtra(EXTR_USER_NAME);


        textViewClientName.setText(clientName);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AskQuestionActivity.this,PreviousQuestionActivity.class)
                        .putExtra("phoneClient",phoneClient)
                        .putExtra("CategoryType",jobTitle)
                        .putExtra(EXTR_USER_NAME,clientName));
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
        buttonPostQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null){
                    uploadToFirebase(imageUri);
                }
                else {
                    uploadQuestionToFirebase();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data !=null){
            imageUri = data.getData();
            imageViewUploadPhoto.setImageURI(imageUri);
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
                        question = editTextQuestion.getText().toString().trim();
                        userQuestions = new Questions(phoneClient,question,jobTitle,uri.toString());
                        workerQuestions = new Questions(phoneClient,question,jobTitle,uri.toString());

                        //add in Questions path for specific job...
                        askQuestionViewModel.addQuestionsDataForCategory(userQuestions,phoneClient,jobTitle);
                        //add in prev questions in clientData path...
                        askQuestionViewModel.addPrevQuestionsForClientData(userQuestions,phoneClient,jobTitle);
                        //add in worker path..
                        askQuestionViewModel.addQuestionsToWorkers(workerQuestions,phoneClient,jobTitle);
                        Toast.makeText(AskQuestionActivity.this, "your question is added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AskQuestionActivity.this, PreviousQuestionActivity.class)
                                .putExtra("CategoryType",jobTitle)
                                .putExtra("phoneClient",phoneClient)
                                .putExtra(EXTR_USER_NAME,clientName));


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
                Toast.makeText(AskQuestionActivity.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

    private void uploadQuestionToFirebase() {

        question = editTextQuestion.getText().toString().trim();
        userQuestions = new Questions(phoneClient, question, jobTitle);
        workerQuestions = new Questions(phoneClient, question, jobTitle);

        //add in Questions path for specific job...
        askQuestionViewModel.addQuestionsDataForCategory(userQuestions, phoneClient, jobTitle);
        //add in prev questions in clientData path...
        askQuestionViewModel.addPrevQuestionsForClientData(userQuestions, phoneClient, jobTitle);
        //add in worker path..
        askQuestionViewModel.addQuestionsToWorkers(workerQuestions, phoneClient, jobTitle);
        Toast.makeText(AskQuestionActivity.this, "your question is added", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(AskQuestionActivity.this, PreviousQuestionActivity.class)
                .putExtra("CategoryType", jobTitle)
                .putExtra("phoneClient", phoneClient)
                .putExtra(EXTR_USER_NAME,clientName));
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

