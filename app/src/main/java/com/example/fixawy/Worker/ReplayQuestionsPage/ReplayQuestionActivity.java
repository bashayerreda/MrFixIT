package com.example.fixawy.Worker.ReplayQuestionsPage;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTR_USER_NAME;
import com.example.fixawy.Pojos.Answer;
import com.example.fixawy.R;
import com.example.fixawy.Worker.WorkerQuestions.WorkerQuestionsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTR_USER_NAME;

public class ReplayQuestionActivity extends AppCompatActivity {

    DatabaseReference mRef;
    Answer answer,answerModel;
    ReplyQuestionsAdapter replyQuestionsAdapter;
    RecyclerView mRecyclerView;
    String phoneClient,phoneWorker,jobTitle,reply,clientQuestion,replyQuestion,workerName;
    FloatingActionButton floatingActionButtonOpenDialog;
    AlertDialog alertDialog;
    LayoutInflater inflater;
    Button btnAddReplyDialog,btnCancelDialog;
    EditText addReplyText;
    DatabaseReference databaseReference;
    FirebaseDatabase db ;
    ImageView imageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay_question);
        imageViewBack = findViewById(R.id.backToPrevious);

        phoneClient = getIntent().getStringExtra("phoneClient");
        phoneWorker = getIntent().getStringExtra("phoneWorker");
        jobTitle = getIntent().getStringExtra("jobTitle");
        clientQuestion = getIntent().getStringExtra("clientQuestion");
        replyQuestion = getIntent().getStringExtra("reply");
        workerName = getIntent().getStringExtra(EXTR_USER_NAME);


        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReplayQuestionActivity.this,WorkerQuestionsActivity.class)
                        .putExtra("phoneWorker",phoneWorker)
                        .putExtra("jobTitle",jobTitle)
                        .putExtra(EXTR_USER_NAME,workerName));
            }
        });

        floatingActionButtonOpenDialog = findViewById(R.id.openDialogReply);
        floatingActionButtonOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = new AlertDialog.Builder(v.getContext()).create();
                inflater = LayoutInflater.from(v.getContext());
                View dialogView = inflater.inflate(R.layout.add_reply_dialog, null);
                btnAddReplyDialog= dialogView.findViewById(R.id.btn_addNoteDialog);
                btnCancelDialog=dialogView.findViewById(R.id.btn_cancelNoteDialog);
                addReplyText=dialogView.findViewById(R.id.txt_addNoteDialog);
                db = FirebaseDatabase.getInstance();
                databaseReference = db.getReference("Client");
                btnAddReplyDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ReplayQuestionActivity.this, "add reply" + clientQuestion + " " + phoneClient + " " + phoneWorker +" " + jobTitle, Toast.LENGTH_SHORT).show();
                        reply = addReplyText.getText().toString().trim();
                        answerModel = new Answer(reply,phoneWorker,clientQuestion);
                        databaseReference.child("Questions").child("Replies").child(jobTitle).child(phoneClient).child(phoneWorker).push().setValue(answerModel);
                        Toast.makeText(ReplayQuestionActivity.this, "your reply will be added soon...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ReplayQuestionActivity.this, WorkerQuestionsActivity.class)
                                .putExtra("jobTitle",jobTitle)
                                .putExtra("phoneWorker",phoneWorker)
                                .putExtra(EXTR_USER_NAME,workerName));
                        alertDialog.cancel();
                    }
                });
                btnCancelDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {alertDialog.cancel();}
                });
                alertDialog.setView(dialogView);
                alertDialog.show();
            }
        });




        mRef = FirebaseDatabase.getInstance().getReference();
        replyQuestionsAdapter = new ReplyQuestionsAdapter(this,jobTitle,phoneClient,workerName);
        mRecyclerView = findViewById(R.id.questionsList);


        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(replyQuestionsAdapter);
        // readReply();
        read();
        Toast.makeText(this, "all questions", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, phoneWorker, Toast.LENGTH_SHORT).show();
    }

    public void read(){
        mRef.child("Client").child("Questions").child("Replies").child(jobTitle).child(phoneClient).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        answer = dataSnapshot.getValue(Answer.class);
                        replyQuestionsAdapter.add(answer);

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