package com.example.fixawy.Worker.WorkerProfilePage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fixawy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTRA_JOB_TITLE;
import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTR_PHONE_NUM;
import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTR_USER_NAME;
import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.EXTRA_WORKER_PHONE;
import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.REQUESTED_EXTRA_JOB_TITLE;
import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.REQUESTED_EXTRA_WORKER_PHONE;

public class WorkerProfileActivity extends AppCompatActivity {

    ImageView workerImageView;
    TextView workerNumOfJob,workerNumOfLike,workerNumOfDisLike,workerName,workerAddress,workerPhone;
    RatingBar workerRatingBarProfile;
    private DatabaseReference mDatabaseRef;

    String worker_phone_num, worker_job_title;
    int myRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_profile);

        Intent intent = getIntent();
        worker_phone_num = intent.getStringExtra(EXTRA_WORKER_PHONE);
        worker_job_title = intent.getStringExtra(EXTRA_JOB_TITLE);

        Toast.makeText(this, worker_phone_num, Toast.LENGTH_SHORT).show();
        Log.d("uiuiuiuiu",worker_phone_num);
        Toast.makeText(this, worker_job_title, Toast.LENGTH_SHORT).show();
        Log.d("uiuiuiuiu",worker_phone_num);

        workerImageView = findViewById(R.id.emp_img_worker_profile);
        workerName = findViewById(R.id.emp_name_profile);
        workerNumOfJob = findViewById(R.id.num_of_emp_jobs_profile);
        workerNumOfLike = findViewById(R.id.num_of_emp_likes_profile);
        workerNumOfDisLike = findViewById(R.id.num_of_emp_dislike_profile);
        workerAddress = findViewById(R.id.emp_address_profile);
        workerPhone = findViewById(R.id.emp_phone_profile);
        workerRatingBarProfile = findViewById(R.id.emp_rate_profile);



        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Worker").child(worker_job_title).child("Data").child(worker_phone_num);
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String nameOfWorker = snapshot.child("userName").getValue().toString();
                String addressOfWorker = snapshot.child("address").getValue().toString();
                String numOfLike = snapshot.child("like").getValue().toString();
                String numOfDisLike = snapshot.child("disLike").getValue().toString();
                String numOfJob = snapshot.child("numOfJob").getValue().toString();
                String phoneOfWorker = snapshot.child("phone").getValue().toString();
                String url = snapshot.child("image").getValue().toString();
                String rating = snapshot.child("rating").getValue().toString();

                workerName.setText(nameOfWorker);
                workerAddress.setText(addressOfWorker);
                workerNumOfJob.setText(numOfJob);
                workerNumOfLike.setText(numOfLike);
                workerNumOfDisLike.setText(numOfDisLike);
                workerPhone.setText(phoneOfWorker);
                Picasso.get().load(url).placeholder(R.drawable.person).into(workerImageView);
                workerRatingBarProfile.setRating(Float.parseFloat(rating));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



  /*      mDatabaseRef = FirebaseDatabase.getInstance().getReference("Worker");
        mDatabaseRef.child(worker_job_title).child("Data").child(worker_phone_num).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String name = dataSnapshot.child("userName").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();
                        String like = dataSnapshot.child("like").getValue().toString();
                        String disLike = dataSnapshot.child("disLike").getValue().toString();
                        String numOfJob = dataSnapshot.child("numOfJob").getValue().toString();
                        String phone = dataSnapshot.child("phone").getValue().toString();
                        String image = dataSnapshot.child("image").getValue().toString();
                        String rate = dataSnapshot.child("rating").getValue().toString();

                        workerRatingBarProfile.setRating(Float.parseFloat(rate));


                        workerName.setText(name);
                        workerAddress.setText(address);
                        workerNumOfJob.setText(numOfJob);
                        workerNumOfLike.setText(like);
                        workerNumOfDisLike.setText(disLike);
                        workerPhone.setText(phone);
                        Picasso.get().load(image).placeholder(R.drawable.person).into(workerImageView);
                    }
                }
            }
        });

   */


//        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Worker");
//        mDatabaseRef.child(worker_job_title).child("Data").child(worker_phone_num).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()) {
//                    /* ----- 1-Retrieve the data from firebase ----- */
//                    Map<String,Object> map = (Map<String, Object>) snapshot.getValue();
//                    String name = (String) map.get("userName");
//                    String address =(String) map.get("address");
//                    String phone = (String) map.get("phone");
//
//
//
//                    /* ----- 2-Set the values in the text fields ----- */
//
//                    workerName.setText(name);
//                    workerAddress.setText(address);
//                    workerPhone.setText(phone);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {}
//        });




    }
}