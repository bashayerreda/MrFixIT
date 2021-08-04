package com.example.fixawy.Client.HistoryPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.fixawy.Client.PreviousQuestionPage.PreviousQuestionAdapter;
import com.example.fixawy.Pojos.ClientHistory;
import com.example.fixawy.Pojos.Questions;
import com.example.fixawy.R;
import com.example.fixawy.Worker.WorkerQuestions.WorkerQuestionsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    DatabaseReference mRef;
    HistoryAdapter historyAdapter;
    ClientHistory clientHistory;
   static String phoneClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mRecyclerView = findViewById(R.id.historyrv);
        phoneClient = getIntent().getStringExtra("phone");

        mRef = FirebaseDatabase.getInstance().getReference();
        historyAdapter = new HistoryAdapter(HistoryActivity.this,phoneClient);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(historyAdapter);
        readData();
    }
    // return all questions for specific job...
    public void readData(){
        mRef.child("Client").child("Data").child(phoneClient).child("History Jobs").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    clientHistory = snapshot.getValue(ClientHistory.class);
                    historyAdapter.get(clientHistory);

                }
            }
        });

    }
}