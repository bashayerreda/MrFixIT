package com.example.fixawy.Worker.DetailsJobPage;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.fixawy.Pojos.MakeOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class DetailsJobReposatory {
    private MakeOrder makeOrder = new MakeOrder();
    private static DetailsJobReposatory instance;
    private DatabaseReference database;


    public static DetailsJobReposatory getInstance(){
        if(instance == null){
            instance = new DetailsJobReposatory();
        }
        return instance;
    }

    public MutableLiveData<MakeOrder>getData(String phone, String jobTitle){
        final MutableLiveData<MakeOrder>data = new MutableLiveData<>();
        database = FirebaseDatabase.getInstance().getReference("Worker").child("Electricity").child("order Details");

        database.child("01225699594").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    /* ----- 1-Retrieve the data from firebase ----- */
                    Map<String,Object> map = (Map<String, Object>) snapshot.getValue();
                    String jobDate = (String) map.get("date");
                    String jobDetails =(String) map.get("details");
                    Object jobTitle = map.get("jobTitle");
                    String jobLocation = (String) map.get("location");
                    int jobPaymentMethod = (int) map.get("paymentMethod");
                    String jobPhone = (String) map.get("phone");
                    String jobTime =(String) map.get("time");
                    //String jobTypeOfOrder =(int) map.get("typeOfOrder");

                    /* ----- 2-Set the values in the text fields ----- */
//                    date.setText(jobDate);
//                    detailsOfJobs.setText(jobDetails);
//                    location.setText(jobLocation);
//                    payment.setText(jobPaymentMethod);
//                    phone_details.setText(jobPhone);
//                    time.setText(jobTime);
//                    typeOfOrder.setText(jobTypeOfOrder);

                    makeOrder.setDate(jobDate);
                    makeOrder.setDetails(jobDetails);
                    makeOrder.setLocation(jobLocation);
                    makeOrder.setPaymentMethod(jobPaymentMethod);
                    makeOrder.setPhone(jobPhone);
                    makeOrder.setTime(jobTime);
                    //makeOrder.setTypeOfOrder(jobTypeOfOrder);

                    data.setValue(makeOrder);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        return data;
    }
}
