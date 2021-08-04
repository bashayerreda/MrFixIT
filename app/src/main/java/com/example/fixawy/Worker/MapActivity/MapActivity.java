package com.example.fixawy.Worker.MapActivity;

import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fixawy.Pojos.ClientHistory;
import com.example.fixawy.Pojos.HistoryWorker;
import com.example.fixawy.R;
import com.example.fixawy.reminder.MyService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference2;
    List<HistoryWorker> historyWorkers;
    AlertDialog alertDialog;
    LayoutInflater inflater;
    String worker_jobTitle, Worker_phone, phone;
    TextView btnYes, btnNo;
    long maxid = 0;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


//        NotificationManager manager = (NotificationManager)getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
//        manager.cancelAll();
        Intent intent2 = new Intent(this, MyService.class);
        this.stopService(intent2);

        Intent intent = getIntent();
        Worker_phone = intent.getStringExtra("phone_worker");
        worker_jobTitle = intent.getStringExtra("jobTitle_worker");


        String name = intent.getStringExtra("dataHistoryName");
        phone = intent.getStringExtra("dataHistoryPhone");
        String date = intent.getStringExtra("dataHistoryDate");
        String time = intent.getStringExtra("dataHistoryTime");
        String address = intent.getStringExtra("dataHistoryLocation");

//        Toast.makeText(this, Worker_phone, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, worker_jobTitle, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, phone, Toast.LENGTH_SHORT).show();
        alertDialog = new AlertDialog.Builder(MapActivity.this).create();
        inflater = LayoutInflater.from(MapActivity.this);
        View dialogView = inflater.inflate(R.layout.dialogconfirm, null);
        btnYes = dialogView.findViewById(R.id.text_yes);
        btnNo = dialogView.findViewById(R.id.text_no);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              DisplayTrack(address);
                alertDialog.cancel();
            }
        });
      btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.cancel();
            }
        });
        alertDialog.setView(dialogView);
        alertDialog.show();







        historyWorkers = new ArrayList<>();
        HistoryWorker historyWorker =new HistoryWorker();
        historyWorker.setName(name);
        historyWorker.setPhone(phone);
        historyWorker.setDate(date);
        historyWorker.setTime(time);
        historyWorker.setAddress(address);

        historyWorkers.add(historyWorker);





        //to add history data
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Worker");

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxid = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference2.child(worker_jobTitle).child("Data").child(Worker_phone).child("HistoryWorker").child(phone).setValue(historyWorkers);


        //to delete data from request
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Worker").child(worker_jobTitle).child("Data");
        databaseReference2.child(Worker_phone).child("Job Accepted").child(phone).setValue(null);







    }
    private void DisplayTrack(String sDestination) {
        //if device dosnt have mape installed then redirect it to play store

        try {
            //when google map installed
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + "/" + sDestination);

            //Action view with uri
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            //set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);


        } catch (ActivityNotFoundException e) {
            //when google map is not initialize
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            //set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }


    }





}