package com.example.fixawy.Client.RatingPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fixawy.Client.HistoryPage.HistoryActivity;
import com.example.fixawy.Client.HistoryPage.HistoryAdapter;
import com.example.fixawy.Pojos.HistoryWorker;
import com.example.fixawy.R;
import com.example.fixawy.Share.LoginPage.LoginActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class RatingActivity extends AppCompatActivity {

    LottieAnimationView lottieLike,lottieDislike;
    private boolean btnLike = false;
    private boolean btnDislike = false;

    String phoneWorker,jobWorker,phoneClient;
    String nameOfWorker,url,comments;
    TextView textViewNameOfWorker;
    ImageView imageViewWorker;
    RatingBar ratingBar;
    EditText comment;
    Button ok;

    long num_like,num_disLike,rate;

    DatabaseReference ref;
    DatabaseReference databaseReference2;
    Task<Void> addCommennt;
    Task databaseReference;

    HistoryWorker historyWorker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);



        phoneWorker = getIntent().getStringExtra("worker_phone");
        jobWorker = getIntent().getStringExtra("worker_job");
        phoneClient = getIntent().getStringExtra("client_phone");

        lottieLike = findViewById(R.id.Likebtn);
        lottieDislike = findViewById(R.id.Dislikebtn);
        textViewNameOfWorker = findViewById(R.id.emp_name);
        imageViewWorker = findViewById(R.id.emp_img);
        ratingBar = findViewById(R.id.emp_rate);
        comment = findViewById(R.id.worker_rating_comment_et);
        ok = findViewById(R.id.ok_btn);

        ref = FirebaseDatabase.getInstance().getReference("Worker").child(jobWorker).child("Data").child(phoneWorker);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                 url = snapshot.child("image").getValue(String.class);
                 nameOfWorker = snapshot.child("userName").getValue(String.class);
                 num_like = (long) snapshot.child("like").getValue();
                 num_disLike = (long) snapshot.child("disLike").getValue();
                 rate = (long) snapshot.child("rating").getValue();

                Picasso.get().load(url).placeholder(R.drawable.person).into(imageViewWorker);
                textViewNameOfWorker.setText(nameOfWorker);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lottieLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lottieLike.playAnimation();
                num_like++;
                Log.d("likeeee",num_like+"");
                lottieDislike.setEnabled(false);
            }
        });

        lottieDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lottieDislike.playAnimation();
                num_disLike++;
                Log.d("likeeee",num_like+"");
                lottieLike.setEnabled(false);
            }
        });




        //ok click
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get rating
                rate = (long) ratingBar.getRating();

                //get Comment
                comments = String.valueOf(comment.getText());

                addCommennt = FirebaseDatabase.getInstance().getReference("Worker").child(jobWorker).child("Data").child(phoneWorker).child("people comment").child(phoneClient).setValue(comments);
                historyWorker = new HistoryWorker(rate);


                HashMap hashMap = new HashMap();
                hashMap.put("like",num_like);
                hashMap.put("disLike",num_disLike);
                hashMap.put("rating",rate);


                databaseReference= FirebaseDatabase.getInstance().getReference("Worker").child(jobWorker).child("Data").child(phoneWorker).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(RatingActivity.this, "Done", Toast.LENGTH_SHORT).show();

                    }
                });




            }
        });


    }
}