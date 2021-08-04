package com.example.fixawy.Firebase;

import androidx.annotation.NonNull;

import com.example.fixawy.Client.HomePageClient.MainRecyclerAdapter;
import com.example.fixawy.Pojos.AllCategory;
import com.example.fixawy.Pojos.JobTitleCategory;
import com.example.fixawy.Pojos.MakeOrder;
import com.example.fixawy.Pojos.Questions;
import com.example.fixawy.Pojos.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHandlerWorker {
    private DatabaseReference databaseReference;
    List<JobTitleCategory>allJobCategories = new ArrayList<>();
    List<User>users = new ArrayList<>();


    public FirebaseHandlerWorker() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Worker");

    }
    public Task<Void> addWorkerData(User user, String phonNum,String jobTitle)
    {
        // return databaseReference.child("jobTitle").child(jobTitle).child(phonNum).child("Data").setValue(user);
        return databaseReference.child(jobTitle).child("Data").child(phonNum).setValue(user);
    }

    public Task<Void> addWorkerQuestion(Questions question, String phone , String jobTitle)
    {
        return databaseReference.child(jobTitle).child("Questions").child(phone).setValue(question);
    }

    public Task<Void> addJobAcceptedToWorker(MakeOrder order, String phoneClient, String workerJobTitle, String phoneWorker)
    {
        return databaseReference.child(workerJobTitle).child("Data").child(phoneWorker).child("Job Accepted").child(phoneClient).setValue(order);
    }



    public DatabaseReference addQuestionsToWorkers(String phoneNum,String jobTitle) {
        return databaseReference.child(jobTitle).child("Questions").child(phoneNum);
    }

//    MainRecyclerAdapter mainRecyclerAdapter;
//   public List<JobTitleCategory> getAllCategories(MainRecyclerAdapter mainRecyclerAdapter){
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
//                    JobTitleCategory allCategory = dataSnapshot.getValue(JobTitleCategory.class);
//                    allJobCategories.add(allCategory);
//                }
//                mainRecyclerAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return allJobCategories;
//
//   }
}
