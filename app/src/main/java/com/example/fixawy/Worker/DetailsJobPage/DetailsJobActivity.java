package com.example.fixawy.Worker.DetailsJobPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fixawy.Client.HomePageClient.HomePageClientActivity;
import com.example.fixawy.Firebase.FirebaseHandlerClient;
import com.example.fixawy.NotificationToClient.Client;
import com.example.fixawy.NotificationToClient.Data;
import com.example.fixawy.NotificationToClient.MyResponse;
import com.example.fixawy.NotificationToClient.NotificationAPI;
import com.example.fixawy.NotificationToClient.NotificationSender;
import com.example.fixawy.Pojos.Accepted;
import com.example.fixawy.Pojos.WorkersAccepted;
import com.example.fixawy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.fixawy.Share.LoginPage.LoginActivity.EXTRA_JOB_TITLE;
import static com.example.fixawy.Share.LoginPage.LoginActivity.EXTRA_WORKER_IMAGE;
import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.EXTRA_ORDER_JOB_TITLE;
import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.EXTRA_ORDER_PHONE;
import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.EXTRA_TOKEN_ID;
import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.EXTRA_WORKER_ADDRESS;
import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.EXTRA_WORKER_DIS_LIKE;
import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.EXTRA_WORKER_LIKE;
import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.EXTRA_WORKER_NAME;
import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.EXTRA_WORKER_NUM_OF_JOB;
import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.EXTRA_WORKER_PHONE;
import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.EXTRA_WORKER_RATING;

//import static com.example.fixawy.Share.LoginPage.LoginActivity.EXTRA_DIS_LIKE;
//import static com.example.fixawy.Share.LoginPage.LoginActivity.EXTRA_LIKE;
//import static com.example.fixawy.Share.LoginPage.LoginActivity.EXTRA_NUM_OF_JOB;
//import static com.example.fixawy.Share.LoginPage.LoginActivity.EXTRA_RATING;
//import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.EXTRA_ORDER_JOB_TITLE;
//import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.EXTRA_ORDER_PHONE;
//import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.EXTRA_WORKER_ADDRESS;
//import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.EXTRA_WORKER_NAME;
//import static com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity.EXTRA_WORKER_PHONE;

public class DetailsJobActivity extends AppCompatActivity {



    TextView typeOfOrder,detailsOfJobs,location,phone_details,time,date,payment;
    Button accept;
    DatabaseReference ref;
    FirebaseHandlerClient firebaseHandlerClient;


    NotificationAPI notificationAPI;

    String jobDate,jobDetails,jobLocation,jobPhone,jobTime,jobTypeOfOrder,clientName,clientTokenId;


    //public int worker_likes,worker_dislikes,worker_num_of_job,worker_rating;
//   public String phone,jobTitle,Worker_phone,worker_name,worker_address,comment;

    public String workerName,workerPhone,workerAddress,workerNumOfJob,workerLikes,workerDisLikes,workerRating,orderPhone,orderJobTitle,workerImage,workerJob,workerTokenid,comment,w_image;


    Dialog dialog;
    Dialog alertDialog;
    LayoutInflater inflater;
    Button btnAddReplyDialog,btnCancelDialog;
    EditText addReplyText;
    String valPhoneNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_job);


        Intent intent = getIntent();
        orderPhone = intent.getStringExtra(EXTRA_ORDER_PHONE);
        orderJobTitle = intent.getStringExtra(EXTRA_ORDER_JOB_TITLE);

        workerName = intent.getStringExtra(EXTRA_WORKER_NAME);
        workerPhone = intent.getStringExtra(EXTRA_WORKER_PHONE);
        workerAddress = intent.getStringExtra(EXTRA_WORKER_ADDRESS);

        workerNumOfJob = intent.getStringExtra(EXTRA_WORKER_NUM_OF_JOB);
        workerLikes = intent.getStringExtra(EXTRA_WORKER_LIKE);
        workerDisLikes = intent.getStringExtra(EXTRA_WORKER_DIS_LIKE);
        workerRating = intent.getStringExtra(EXTRA_WORKER_RATING);

        workerImage = intent.getStringExtra("img2");
        w_image = intent.getStringExtra("img");
        workerJob = intent.getStringExtra(EXTRA_JOB_TITLE);


        workerTokenid = intent.getStringExtra(EXTRA_TOKEN_ID);

        clientName = intent.getStringExtra("clientName");

        dialog = new Dialog(this);
        alertDialog = new Dialog(this);




//        ref = FirebaseDatabase.getInstance().getReference("Client").child("make order").child(orderPhone).child("Accepted").child(workerPhone);
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                valPhoneNum = snapshot.child("phoneOfWorker").getValue().toString();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


//        Toast.makeText(this, orderPhone, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, workerName, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, workerNumOfJob, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, workerTokenid, Toast.LENGTH_SHORT).show();

//        Intent intent = getIntent();
//        String phone = intent.getStringExtra(EXTRA_ORDER_PHONE);
//        String jobTitle = intent.getStringExtra(EXTRA_ORDER_JOB_TITLE);
//
//         worker_likes = intent.getIntExtra(EXTRA_LIKE,0);
//         worker_dislikes = intent.getIntExtra(EXTRA_DIS_LIKE,0);
//         worker_num_of_job = intent.getIntExtra(EXTRA_NUM_OF_JOB,0);
//         worker_rating = intent.getIntExtra(EXTRA_RATING,0);
//         worker_name = intent.getStringExtra(EXTRA_WORKER_NAME);
//         worker_address = intent.getStringExtra(EXTRA_WORKER_ADDRESS);
//         Worker_phone = intent.getStringExtra(EXTRA_WORKER_PHONE);

        //Toast.makeText(this, worker_address, Toast.LENGTH_SHORT).show();

//        Toast.makeText(this, phone, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, jobTitle, Toast.LENGTH_SHORT).show();

        //to notification
        notificationAPI = Client.getClient("https://fcm.googleapis.com/").create(NotificationAPI.class);




        typeOfOrder = findViewById(R.id.text_typeOfOrder_details);
        detailsOfJobs = findViewById(R.id.text_detailsOfJob_details);
        location = findViewById(R.id.text_location_details);
        phone_details = findViewById(R.id.text_phone_details);
        time = findViewById(R.id.text_time_details);
        date = findViewById(R.id.text_date_details);
        payment = findViewById(R.id.text_payment_details);
        accept = findViewById(R.id.btn_accept_details);

        ref = FirebaseDatabase.getInstance().getReference().child("Worker").child(orderJobTitle).child("order Details");
        ref.child(orderPhone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    /* ----- 1-Retrieve the data from firebase ----- */
                    Map<String,Object> map = (Map<String, Object>) snapshot.getValue();
                    jobDate = (String) map.get("date");
                    jobDetails =(String) map.get("details");
                    //Object jobTitle = map.get("jobTitle");
                    jobLocation = (String) map.get("location");
                    //String jobPaymentMethod = (String) map.get("paymentMethod");
                    jobPhone = (String) map.get("phone");
                    jobTime =(String) map.get("time");
                    jobTypeOfOrder = (String) map.get("typeOfOrder");
                    clientTokenId = (String) map.get("tokenid");

                    /* ----- 2-Set the values in the text fields ----- */
                    date.setText(jobDate);
                    detailsOfJobs.setText(jobDetails);
                    location.setText(jobLocation);
                    // payment.setText(jobPaymentMethod);
                    phone_details.setText(jobPhone);
                    time.setText(jobTime);
                    typeOfOrder.setText(jobTypeOfOrder);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCommentDialog();

            }
        });


//        accept.setOnClickListener(v -> {
////            // 1- Create object from alertDialog
////            AlertDialog.Builder builder = new AlertDialog.Builder(DetailsJobActivity.this);
////            // 2-Set builder Title
////            builder.setTitle("Alert Title");
////            // 3-Set Dialog Message
////            builder.setMessage("Enter Data");
////            // 4-Set Dialog EditText
////            final EditText inputData = new EditText(DetailsJobActivity.this);
////            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
////                    LinearLayout.LayoutParams.MATCH_PARENT,
////                    LinearLayout.LayoutParams.MATCH_PARENT);
////            inputData.setLayoutParams(lp);
////            builder.setView(inputData);
////            comment = inputData.getText().toString();
////
////            // 5- set cancelable false
////            builder.setCancelable(false);
////            // 6-Positive button
////            builder.setPositiveButton("DONE", (dialog, which) -> sendData());
////            // 7-Negative Button
////            builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.cancel());
////            // 8-Show the Alert Dialog box
////            builder.show();
//
//
//
//
//        });

    }
    public void sendData()
    {

  //      Accepted accepted = new Accepted(workerName,workerAddress,workerPhone,comment,workerNumOfJob,workerRating,workerLikes,workerDisLikes,workerImage,workerJob,workerTokenid);
//        Toast.makeText(this, orderPhone, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, accepted.getJobTitle(), Toast.LENGTH_SHORT).show();
//
//        Toast.makeText(this, comment, Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, workerTokenid, Toast.LENGTH_SHORT).show();


        Accepted accepted = new Accepted(workerName,workerAddress,workerPhone,comment,workerNumOfJob,workerRating,workerLikes,workerDisLikes,w_image,workerJob,workerTokenid);

//        Toast.makeText(this, orderPhone, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, accepted.getJobTitle(), Toast.LENGTH_SHORT).show();
//
//        Toast.makeText(this, comment, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "date is " + jobDate + "time is " + jobTime + "typeOfOrder is " + jobTypeOfOrder, Toast.LENGTH_SHORT).show();

        firebaseHandlerClient = new FirebaseHandlerClient();
        firebaseHandlerClient.addAcceptedWorker(accepted,orderPhone,orderJobTitle,workerPhone);
        WorkersAccepted workersAccepted = new WorkersAccepted(jobTime,jobDate,jobTypeOfOrder,jobLocation,orderPhone,orderJobTitle,clientName,workerName,workerAddress,workerPhone,workerNumOfJob,workerRating,workerLikes,workerDisLikes,w_image,workerTokenid,clientTokenId);
        firebaseHandlerClient.addAcceptedPath(workersAccepted,orderPhone,orderJobTitle,workerPhone);

//        FirebaseDatabase.getInstance().getReference("Worker").child(orderJobTitle).child("order Details").child(orderPhone).child("tokenId").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String usertoken=dataSnapshot.getValue(String.class);
//                sendNotifications(usertoken, "Accepted Your Job","??????");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        ref = FirebaseDatabase.getInstance().getReference().child("Client").child("make order").child(orderPhone).child("Workers Accepted Jobs").child(workerPhone);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String usertoken = snapshot.child("clientTokenId").getValue().toString();
                Log.d("tooooookkk",usertoken);
                sendNotifications(usertoken, "Accepted Your Job","Check your select worker");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        openDialog();
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
                        Toast.makeText(DetailsJobActivity.this, "Failed ", Toast.LENGTH_LONG);
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

    //create the dialog
    private void openDialog() {


        dialog.setContentView(R.layout.details_dialog);

        dialog.show();

    }

    //create dialog for write comment
    private void openCommentDialog(){
//        alertDialog = new AlertDialog.Builder(v.getContext()).create();
//        inflater = LayoutInflater.from(v.getContext());
//        View dialogView = inflater.inflate(R.layout.add_comment_dialog, null);
        alertDialog.setContentView(R.layout.add_comment_dialog);
        btnAddReplyDialog= alertDialog.findViewById(R.id.btn_addNoteDialog);
        btnCancelDialog=alertDialog.findViewById(R.id.btn_cancelNoteDialog);
        addReplyText=alertDialog.findViewById(R.id.add_comment);
        addReplyText = new EditText(DetailsJobActivity.this);
        addReplyText.setInputType(InputType.TYPE_CLASS_TEXT);
        //alertDialog.setContentView(addReplyText);



        btnAddReplyDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(valPhoneNum==workerPhone){
                    Toast.makeText(DetailsJobActivity.this, "You already send for this order", Toast.LENGTH_SHORT).show();
                }else{
                    EditText edit=alertDialog.findViewById(R.id.add_comment);
                    comment=edit.getText().toString();

                    sendData();
                }


            }
        });

        btnCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }


}