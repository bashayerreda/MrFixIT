package com.example.fixawy.Client.AllPreviousQuestions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fixawy.Client.AskQuestionPage.AskQuestionActivity;
import com.example.fixawy.Client.HomePageClient.HomePageClientActivity;
import com.example.fixawy.Client.PreviousQuestionPage.PreviousQuestionActivity;
import com.example.fixawy.Client.PreviousQuestionPage.PreviousQuestionAdapter;
import com.example.fixawy.Pojos.Questions;
import com.example.fixawy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTR_USER_NAME;

public class AllPreviousQuestionsActivity extends AppCompatActivity {

    String phoneNum,clientUserName;
    RecyclerView mRecyclerView;
    DatabaseReference mRef;
    AllPreviousQuestionsAdapter allPreviousQuestionsAdapter;
    Questions questions;
    ImageView imageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_previous_questions);
        mRecyclerView = findViewById(R.id.questionsList);
        imageViewBack = findViewById(R.id.backToPrevious);
        phoneNum = getIntent().getStringExtra("phone");
        clientUserName = getIntent().getStringExtra(EXTR_USER_NAME);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllPreviousQuestionsActivity.this, HomePageClientActivity.class)
                        .putExtra("phone",phoneNum)
                        .putExtra(EXTR_USER_NAME,clientUserName));
            }
        });

        mRef = FirebaseDatabase.getInstance().getReference();
        allPreviousQuestionsAdapter = new AllPreviousQuestionsAdapter(this,phoneNum,clientUserName);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(allPreviousQuestionsAdapter);
        readAllMyQuestions();
       // Toast.makeText(this, "all questions", Toast.LENGTH_SHORT).show();
    }

    // return all questions for specific phoneNumber...
    public void readAllMyQuestions(){
        mRef.child("Client").child("Data").child(phoneNum).child("Previous Questions").child("Electricity").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    questions = snapshot.getValue(Questions.class);
                    allPreviousQuestionsAdapter.add(questions);
                }
            }
        });
        mRef.child("Client").child("Data").child(phoneNum).child("Previous Questions").child("Carpenter").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    questions = snapshot.getValue(Questions.class);
                    allPreviousQuestionsAdapter.add(questions);
                }
            }
        });

        mRef.child("Client").child("Data").child(phoneNum).child("Previous Questions").child("Plumber").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    questions = snapshot.getValue(Questions.class);
                    allPreviousQuestionsAdapter.add(questions);
                }
            }
        });

        mRef.child("Client").child("Data").child(phoneNum).child("Previous Questions").child("Painter").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    questions = snapshot.getValue(Questions.class);
                    allPreviousQuestionsAdapter.add(questions);
                }
            }
        });

        mRef.child("Client").child("Data").child(phoneNum).child("Previous Questions").child("TilesHandyMan").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    questions = snapshot.getValue(Questions.class);
                    allPreviousQuestionsAdapter.add(questions);
                }
            }
        });

        mRef.child("Client").child("Data").child(phoneNum).child("Previous Questions").child("Mason").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    questions = snapshot.getValue(Questions.class);
                    allPreviousQuestionsAdapter.add(questions);
                }
            }
        });

        mRef.child("Client").child("Data").child(phoneNum).child("Previous Questions").child("Smith").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    questions = snapshot.getValue(Questions.class);
                    allPreviousQuestionsAdapter.add(questions);
                }
            }
        });

        mRef.child("Client").child("Data").child(phoneNum).child("Previous Questions").child("Parquet").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    questions = snapshot.getValue(Questions.class);
                    allPreviousQuestionsAdapter.add(questions);
                }
            }
        });
        mRef.child("Client").child("Data").child(phoneNum).child("Previous Questions").child("Gypsum").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    questions = snapshot.getValue(Questions.class);
                    allPreviousQuestionsAdapter.add(questions);
                }
            }
        });

        mRef.child("Client").child("Data").child(phoneNum).child("Previous Questions").child("Marble").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    questions = snapshot.getValue(Questions.class);
                    allPreviousQuestionsAdapter.add(questions);
                }
            }
        });

        mRef.child("Client").child("Data").child(phoneNum).child("Previous Questions").child("Alumetal").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    questions = snapshot.getValue(Questions.class);
                    allPreviousQuestionsAdapter.add(questions);
                }
            }
        });

        mRef.child("Client").child("Data").child(phoneNum).child("Previous Questions").child("Glasses").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    questions = snapshot.getValue(Questions.class);
                    allPreviousQuestionsAdapter.add(questions);
                }
            }
        });

        mRef.child("Client").child("Data").child(phoneNum).child("Previous Questions").child("WoodPainter").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    questions = snapshot.getValue(Questions.class);
                    allPreviousQuestionsAdapter.add(questions);
                }
            }
        });

        mRef.child("Client").child("Data").child(phoneNum).child("Previous Questions").child("Curtains").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    questions = snapshot.getValue(Questions.class);
                    allPreviousQuestionsAdapter.add(questions);
                }
            }
        });

        mRef.child("Client").child("Data").child(phoneNum).child("Previous Questions").child("Appliances Maintenance").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    questions = snapshot.getValue(Questions.class);
                    allPreviousQuestionsAdapter.add(questions);
                }
            }
        });

        mRef.child("Client").child("Data").child(phoneNum).child("Previous Questions").child("Satellite").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    questions = snapshot.getValue(Questions.class);
                    allPreviousQuestionsAdapter.add(questions);
                }
            }
        });
    }

}