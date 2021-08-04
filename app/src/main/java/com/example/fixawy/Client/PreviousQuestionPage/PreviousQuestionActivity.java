package com.example.fixawy.Client.PreviousQuestionPage;
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
import com.example.fixawy.Client.AskQuestionPage.AskQuestionActivity;
import com.example.fixawy.Client.SelectKindOfChoicePage.SelectKindOfChoiceActivity;
import com.example.fixawy.Pojos.Questions;
import com.example.fixawy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTR_USER_NAME;

public class PreviousQuestionActivity extends AppCompatActivity {

    FloatingActionButton floatingButtonAsk;
    String phoneClient,jobTitle,clientName,phoneOfCard;
    RecyclerView mRecyclerView;
    DatabaseReference mRef;
    PreviousQuestionAdapter questionAdapter;
    Questions questions;
    TextView textViewJobTitle;
    ImageView imageViewBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_question);
        mRecyclerView = findViewById(R.id.questionsList);
        floatingButtonAsk = findViewById(R.id.addQuestion);
        textViewJobTitle=findViewById(R.id.txt_job_title);
        imageViewBack = findViewById(R.id.backToPrevious);
        phoneClient = getIntent().getStringExtra("phoneClient");
        jobTitle = getIntent().getStringExtra("CategoryType");
        clientName = getIntent().getStringExtra(EXTR_USER_NAME);
        phoneOfCard = getIntent().getStringExtra("phoneOfCard");
        textViewJobTitle.setText("All Questions for "+jobTitle);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PreviousQuestionActivity.this, SelectKindOfChoiceActivity.class)
                        .putExtra("phoneClient",phoneClient)
                        .putExtra("CategoryType",jobTitle)
                        .putExtra(EXTR_USER_NAME,clientName));
            }
        });

        floatingButtonAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreviousQuestionActivity.this, AskQuestionActivity.class);
                intent.putExtra("phoneClient", phoneClient);
                intent.putExtra("CategoryType",jobTitle);
                intent.putExtra(EXTR_USER_NAME,clientName);
                startActivity(intent);
            }
        });

        mRef = FirebaseDatabase.getInstance().getReference();
        questionAdapter = new PreviousQuestionAdapter(this,phoneClient,clientName);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(questionAdapter);
        readData();
        Toast.makeText(this, "all questions", Toast.LENGTH_SHORT).show();
    }
    // return all questions for specific job...
    public void readData(){
        mRef.child("Client").child("Questions").child(jobTitle).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        questions = dataSnapshot.getValue(Questions.class);
                        questionAdapter.add(questions);
                    }

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