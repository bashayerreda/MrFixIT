package com.example.fixawy.Worker.HomePageWorker;

import androidx.annotation.NonNull;

import com.example.fixawy.Pojos.MakeOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequestedHomePageRepository {

    private List<MakeOrder> makeOrderList = new ArrayList<>();
    private static RequestedHomePageRepository instance;
    private DatabaseReference database;


    public static RequestedHomePageRepository getInstance(){
        if(instance == null){
            instance = new RequestedHomePageRepository();
        }
        return instance;
    }

    List<MakeOrder> getRequested(String worker_job_title){
        database = FirebaseDatabase.getInstance().getReference("Worker").child(worker_job_title).child("order Details");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MakeOrder makeOrder = dataSnapshot.getValue(MakeOrder.class);
                    makeOrderList.add(makeOrder);
                }
                //RequestedHomePageActivity.myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return makeOrderList;
    }


}
