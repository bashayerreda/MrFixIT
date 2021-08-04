package com.example.fixawy.Client.HomePageClient;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.fixawy.Pojos.AllCategory;
import com.example.fixawy.Pojos.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.example.fixawy.Client.HomePageClient.HomePageClientActivity.allCategoryList;

public class HomePageReposatory {
    private User user = new User();
    private static HomePageReposatory instance;
    private DatabaseReference database;




    public static HomePageReposatory getInstance(){
        if(instance == null){
            instance = new HomePageReposatory();
        }

        return instance;

    }




    public List<User> getEmployeeData(String jobTitle, List<User> employees){
        employees.clear();
        database = FirebaseDatabase.getInstance().getReference("Worker").child(jobTitle).child("Data");

 //       database.addValueEventListener(new ValueEventListener() {

//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                allCategoryList.clear();
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//
//                    User employeeData = (User)dataSnapshot.getValue(User.class);
//                    Log.d("ssssss","user"+employeeData.getUserName());
//                    employees.add( employeeData);
//
//                } if(employees.size()!=0){
//
//                    HomePageClientActivity.mainRecyclerAdapter.notifyDataSetChanged();
//
//
//                    allCategoryList.add(new AllCategory(2, jobTitle, employees));
//                }//HomePageClientActivity.mainRecyclerAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
 //       });


        database.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot dataSnapshot : task.getResult().getChildren()){
                    User employeeData = (User)dataSnapshot.getValue(User.class);
                    Log.d("ssssss","user"+employeeData.getUserName());
                    employees.add( employeeData);
                } if(employees.size()!=0){
                    HomePageClientActivity.mainRecyclerAdapter.notifyDataSetChanged();
                    allCategoryList.add(new AllCategory(2, jobTitle, employees));
                }//HomePageClientActivity.mainRecyclerAdapter.notifyDataSetChanged();
            }

        });

        return  employees;

    }


}



























