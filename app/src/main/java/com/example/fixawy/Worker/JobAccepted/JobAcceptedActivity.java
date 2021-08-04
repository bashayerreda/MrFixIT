package com.example.fixawy.Worker.JobAccepted;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.fixawy.Pojos.JobAccepted;
import com.example.fixawy.R;
import com.example.fixawy.reminder.MyReceiver;
import com.example.fixawy.reminder.MyService;
import com.example.fixawy.reminder.MyWorker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.REQUESTED_EXTRA_JOB_TITLE;
import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.REQUESTED_EXTRA_WORKER_PHONE;

public class JobAcceptedActivity extends AppCompatActivity {



    //recyclerView
    RecyclerView recyclerView_worker_accepted;

    static JobAcceptedAdapter jobAcceptedAdapter;

    List<JobAccepted> jobAcceptedList;

    DatabaseReference database;

    public static String worker_job_title,worker_phone;
    public static String historyDate;
    public static String historyTime;
    public static String historyUserName;
    public static String historyUserPhone;
    public static String historyUserAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_accepted);

        jobAcceptedList = new ArrayList<>();

        Intent intent = getIntent();
        worker_job_title = intent.getStringExtra(REQUESTED_EXTRA_JOB_TITLE);
        worker_phone = intent.getStringExtra(REQUESTED_EXTRA_WORKER_PHONE);

        recyclerView_worker_accepted = findViewById(R.id.recyclerviewAcceptJob);
        recyclerView_worker_accepted.setHasFixedSize(true);
        recyclerView_worker_accepted.setLayoutManager(new LinearLayoutManager(this));

        jobAcceptedAdapter = new JobAcceptedAdapter(this,jobAcceptedList);

        recyclerView_worker_accepted.setAdapter(jobAcceptedAdapter);


        database = FirebaseDatabase.getInstance().getReference("Worker").child(worker_job_title).child("Data").child(worker_phone).child("Job Accepted");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jobAcceptedList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    JobAccepted jobAccepted = dataSnapshot.getValue(JobAccepted.class);
                    jobAcceptedList.add(jobAccepted);


                    //String myDate = "2021/6/1 16:33:00";
                    String myDate = jobAccepted.getDate()+" "+jobAccepted.getTime()+":00";
                    Log.d("ooooooo",myDate);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    //DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.FULL).format(myDate2);
                    Log.d("uuuuuuu",myDate);
                    //Log.d("uuuuuuu",myDate2);
                    Date date = null;
                    try {
                        date = sdf.parse(myDate);
                        Log.d("uuuuuuu",date.toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long millis = date.getTime();
                    Log.d("oooo","my time is "+millis);

                    long nowMillis = System.currentTimeMillis();;
                    Log.d("oooo","time is now "+nowMillis);


                    long diff = millis - nowMillis;
                    Log.d("oooo","diff is "+diff);
                    Data inputData = new Data.Builder()
                            // .putString("data", "May 30, 2021 6 : 53")
                            .build();
                    WorkRequest uploadWorkRequest =
                            new OneTimeWorkRequest.Builder(MyWorker.class)
                                    .setInputData(inputData)
                                    .setInitialDelay(diff, TimeUnit.MILLISECONDS)
                                    .build();
                    WorkManager.getInstance().enqueue(uploadWorkRequest);


                    historyDate = jobAccepted.getDate();
                    historyTime = jobAccepted.getTime();
                    historyUserAddress = jobAccepted.getLocation();
                    historyUserName = jobAccepted.getUserName();
                    historyUserPhone = jobAccepted.getPhone();



                }
                JobAcceptedActivity.jobAcceptedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }
}