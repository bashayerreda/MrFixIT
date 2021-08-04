package com.example.fixawy.Client.AcceptedWorkerPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.fixawy.Client.HomePageClient.HomePageClientActivity;
import com.example.fixawy.Client.MakeOrder.pojos.OrderTree;
import com.example.fixawy.NotificationToClient.Client;
import com.example.fixawy.NotificationToClient.Data;
import com.example.fixawy.NotificationToClient.MyResponse;
import com.example.fixawy.NotificationToClient.NotificationAPI;
import com.example.fixawy.NotificationToClient.NotificationSender;


import com.example.fixawy.Client.RequestedPage.RequestedPageViewModel;
import com.example.fixawy.Client.SelectedPage.SelectedActivity;
import com.example.fixawy.MainActivity;

import com.example.fixawy.Pojos.MakeOrder;
import com.example.fixawy.R;

import com.example.fixawy.Worker.DetailsJobPage.DetailsJobActivity;

import com.example.fixawy.Worker.WorkerProfilePage.WorkerProfileActivity;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class AcceptedWorkActivity extends AppCompatActivity {

    ImageView imageViewWorker;
    TextView textViewNameOfWorker,textViewNumOfJobs,textViewNumOfLikes,textViewNumOfDisLike,textViewAddress,textViewPhone;
    RatingBar ratingBar;
    Button buttonAccept,buttonCancel;
    String phoneWorker,phoneClient,date,time,location,phoneClientNum,nameClient,typeOfOrder,nameOfWorker,workerJobTitle;
    DatabaseReference reference1,reference2,reference4,reference5;
    DatabaseReference referenceDelete1,referenceDelete2, updateNumOfJob;
    List<String> uIds = new ArrayList<>();

    int numJob;
    String numOfJob;
    String aftterNumOfJob;
    AcceptWorkModel acceptWorkModel ;
    RequestedPageViewModel requestedPageViewModel;

    NotificationAPI notificationAPI;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_work);

        imageViewWorker = findViewById(R.id.emp_img);
        textViewNameOfWorker = findViewById(R.id.emp_name);
        textViewNumOfJobs = findViewById(R.id.num_of_emp_jobs);
        textViewNumOfLikes = findViewById(R.id.num_of_emp_likes);
        textViewNumOfDisLike = findViewById(R.id.num_of_emp_dislike);
        textViewAddress = findViewById(R.id.emp_address);
        textViewPhone = findViewById(R.id.emp_phone);
        ratingBar = findViewById(R.id.emp_rate);
        buttonAccept = findViewById(R.id.accept_btn);
        buttonCancel = findViewById(R.id.cancel_btn);

        phoneWorker = getIntent().getStringExtra("phoneWorker");
        phoneClient = getIntent().getStringExtra("phoneClient");
        workerJobTitle = getIntent().getStringExtra("workerJobTitle");
        nameOfWorker = getIntent().getStringExtra("nameOfWorker");

        dialog = new Dialog(this);

//        Toast.makeText(this, phoneClient, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, phoneWorker, Toast.LENGTH_SHORT).show();

        notificationAPI = Client.getClient("https://fcm.googleapis.com/").create(NotificationAPI.class);


        //Toast.makeText(this, phoneClient, Toast.LENGTH_SHORT).show();

        reference1 = FirebaseDatabase.getInstance().getReference().child("Client").child("make order").child(phoneClient).child("Workers Accepted Jobs").child(phoneWorker);
        //     reference1 = FirebaseDatabase.getInstance().getReference().child("Worker").child(workerJobTitle).child("Data").child(phoneWorker);
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String url = snapshot.child("image").getValue(String.class);
                String nameOfWorker = snapshot.child("nameOfWorker").getValue(String.class);
                numOfJob = snapshot.child("numOfJob").getValue(String.class);
                String numOfLike = snapshot.child("numOfLike").getValue(String.class);
                String numOfDisLike = snapshot.child("numOfDisLike").getValue(String.class);
                String addressOfWorker = snapshot.child("addressOfWorker").getValue(String.class);
                String phoneOfWorker = snapshot.child("phoneOfWorker").getValue(String.class);
                // String rating = snapshot.child("rating").getValue(String.class);

               // Picasso.get().load(url).placeholder(R.drawable.person).into(imageViewWorker);

                if (url.isEmpty()) {
                    imageViewWorker.setImageResource(R.drawable.profile);
                } else{
                    Picasso.get().load(url).into(imageViewWorker);
                }
                textViewNameOfWorker.setText(nameOfWorker);
                textViewNumOfJobs.setText(numOfJob);
                textViewNumOfLikes.setText(numOfLike);
                textViewNumOfDisLike.setText(numOfDisLike);
                textViewAddress.setText(addressOfWorker);
                textViewPhone.setText(phoneOfWorker);
                //ratingBar.setRating(Float.parseFloat(rating));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //open dialog
                openDialog();

                reference4 = FirebaseDatabase.getInstance().getReference();
                reference2 = FirebaseDatabase.getInstance().getReference().child("Client").child("make order").child(phoneClient).child("Workers Accepted Jobs").child(phoneWorker);
                reference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // get data to restore it to another path
                        //  uIds.add(snapshot.getKey());
                        date = snapshot.child("date").getValue(String.class);
                        time = snapshot.child("time").getValue(String.class);
                        location = snapshot.child("location").getValue(String.class);
                        phoneClientNum = snapshot.child("requestedPhone").getValue(String.class);
                        nameClient = snapshot.child("userName").getValue(String.class);
                        typeOfOrder = snapshot.child("typeOfOrder").getValue(String.class);

                        //Toast.makeText(AcceptedWorkActivity.this, "New Path...."+date+""+time, Toast.LENGTH_SHORT).show();
                        //set time & date & location & phone for client & name of client for job accepted
                        OrderTree order = new OrderTree(time, date, location, phoneClientNum, nameClient);
                        reference4.child("Worker").child(workerJobTitle).child("Data").child(phoneWorker).child("Job Accepted").child(phoneClient).setValue(order);

                        OrderTree historyOrder = new OrderTree(time, date, typeOfOrder, workerJobTitle, nameOfWorker, phoneWorker);
                        reference4.child("Client").child("Data").child(phoneClient).child("History Jobs").child(phoneWorker).setValue(historyOrder);

                      //  Toast.makeText(AcceptedWorkActivity.this, "Job Accepted", Toast.LENGTH_SHORT).show();

                        //delete

                        referenceDelete1 = FirebaseDatabase.getInstance().getReference().child("Client").child("make order");
                        referenceDelete1.child(phoneClient).child("Accepted").setValue(null);

                        referenceDelete1.child(phoneClient).child(workerJobTitle).child("order Details").setValue(null);

                        referenceDelete2 = FirebaseDatabase.getInstance().getReference().child("Worker").child(workerJobTitle).child("order Details");
                        referenceDelete2.child(phoneClient).setValue(null);

//                        startActivity(new Intent(AcceptedWorkActivity.this,SelectedActivity.class)
//                                .putExtra("phone",phoneClient));


                        //send notification
                        reference5 = FirebaseDatabase.getInstance().getReference().child("Client").child("make order").child(phoneClient).child("Workers Accepted Jobs").child(phoneWorker);
                        reference5.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String usertoken = snapshot.child("tokenid").getValue().toString();
                                Log.d("tooooookkk",usertoken);
                                sendNotifications(usertoken, "Accepted Your Requested","You have an appointment to work chek your accept work");
                                HomePageClientActivity.allCategoryList.clear();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                //update num of job
                numJob = Integer.parseInt(numOfJob);
                numJob++;
                aftterNumOfJob = Integer.toString(numJob);
                HashMap hashMap = new HashMap();
                hashMap.put("numOfJob",numJob);
                updateNumOfJob = FirebaseDatabase.getInstance().getReference().child("Worker").child(workerJobTitle).child("Data").child(phoneWorker);
                updateNumOfJob.updateChildren(hashMap);



            }



        });
    }

    //create the dialog
    private void openDialog() {


        dialog.setContentView(R.layout.accept_worker_dialog);

        dialog.show();

    }

    public void sendNotifications(String usertoken, String title, String message) {
        Data data = new Data(title, message);
        NotificationSender sender = new NotificationSender(data, usertoken);
        //Log.d("iiiiiiiiiiiiii",usertoken);
        notificationAPI.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(AcceptedWorkActivity.this, "Failed ", Toast.LENGTH_LONG);
                        HomePageClientActivity.allCategoryList.clear();
                    }
                }
            }
            // Intent intent = new Intent(getApplicationContext(), SelectedWorkerActivity.class);

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }


}