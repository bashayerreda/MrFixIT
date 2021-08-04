package com.example.fixawy.Client.SelectedPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.fixawy.Client.HomePageClient.HomePageClientActivity;
import com.example.fixawy.Firebase.FirebaseHandlerClient;
import com.example.fixawy.Pojos.Accepted;
import com.example.fixawy.Pojos.AllCategory;
import com.example.fixawy.Pojos.MakeOrder;
import com.example.fixawy.Pojos.User;
import com.example.fixawy.R;
import com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity;
import com.example.fixawy.Worker.HomePageWorker.RequestedItemRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.fixawy.Client.HomePageClient.HomePageClientActivity.allCategoryList;

public class SelectedActivity extends AppCompatActivity {

    String phoneClientNum;


    //recyclerView
    RecyclerView recyclerView_worker_selected;
    static SelectedAdapter selectedAdapter;
    List<Accepted> acceptedList;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected);

        phoneClientNum = getIntent().getStringExtra("phone");
       // Toast.makeText(this, phoneClientNum, Toast.LENGTH_SHORT).show();


        acceptedList = new ArrayList<>();
        //Recycler data requested
        recyclerView_worker_selected = findViewById(R.id.selectedrv);

        recyclerView_worker_selected.setHasFixedSize(true);
        recyclerView_worker_selected.setLayoutManager(new LinearLayoutManager(this));

        selectedAdapter = new SelectedAdapter(this,acceptedList,phoneClientNum);
        recyclerView_worker_selected.setAdapter(selectedAdapter);



        database = FirebaseDatabase.getInstance().getReference("Client").child("make order").child(phoneClientNum).child("Accepted");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                acceptedList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Accepted accepted = dataSnapshot.getValue(Accepted.class);
                    acceptedList.add(accepted);
                }
                SelectedActivity.selectedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        database.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                for(DataSnapshot dataSnapshot : task.getResult().getChildren()){
//                    Accepted accepted = dataSnapshot.getValue(Accepted.class);
//                    acceptedList.add(accepted);
//                }
//                SelectedActivity.selectedAdapter.notifyDataSetChanged();
//
//            }
//
//        });


    }
}