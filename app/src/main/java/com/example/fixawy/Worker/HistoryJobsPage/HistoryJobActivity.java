package com.example.fixawy.Worker.HistoryJobsPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.fixawy.Client.PreviousQuestionPage.PreviousQuestionAdapter;
import com.example.fixawy.Pojos.HistoryWorker;
import com.example.fixawy.Pojos.JobAccepted;
import com.example.fixawy.Pojos.Questions;
import com.example.fixawy.R;
import com.example.fixawy.Worker.JobAccepted.JobAcceptedActivity;
import com.example.fixawy.Worker.JobAccepted.JobAcceptedAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTRA_JOB_TITLE;
import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.EXTRA_WORKER_PHONE;
import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.REQUESTED_EXTRA_JOB_TITLE;
import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.REQUESTED_EXTRA_WORKER_PHONE;

public class HistoryJobActivity extends AppCompatActivity {


    RecyclerView mRecyclerView;
   static HistoryJobAdapter historyJobAdapter;
    List<HistoryWorker> historyWorkers;
    Task<DataSnapshot> databaseReference;
    public static String worker_job_title, worker_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_job);



        mRecyclerView = findViewById(R.id.historyJobList);

        historyWorkers = new ArrayList<>();
        Intent intent = getIntent();
        worker_job_title = intent.getStringExtra(EXTRA_JOB_TITLE);
        worker_phone = intent.getStringExtra(EXTRA_WORKER_PHONE);

                //databaseReference.child(worker_job_title).child("Data").child(worker_phone).child("job Accepted").child(phone).removeValue();


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        historyJobAdapter = new HistoryJobAdapter(this, historyWorkers);

        mRecyclerView.setAdapter(historyJobAdapter);

        //databaseReference = FirebaseDatabase.getInstance().getReference("Worker").child(worker_job_title).child("Data").child(worker_phone).child("HistoryWorker");

        databaseReference = FirebaseDatabase.getInstance().getReference("Worker").child(worker_job_title).child("Data").child(worker_phone).child("HistoryWorker").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        HistoryWorker historyWorker = dataSnapshot.getValue(HistoryWorker.class);
                        historyWorkers.add(historyWorker);
                    }
                    HistoryJobActivity.historyJobAdapter.notifyDataSetChanged();
                }
            }
        });


    }
}