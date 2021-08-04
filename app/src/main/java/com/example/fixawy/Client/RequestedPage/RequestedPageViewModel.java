package com.example.fixawy.Client.RequestedPage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.fixawy.Client.MakeOrder.Repo.ClientOrderRepo;
import com.example.fixawy.Client.MakeOrder.pojos.OrderTree;
import com.example.fixawy.Worker.HomePageWorker.RequestedHomePageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.okhttp.internal.DiskLruCache;

import java.util.ArrayList;
import java.util.List;

public class RequestedPageViewModel extends AndroidViewModel {
    public MutableLiveData<List<OrderTree>> requestedPageLiveData = new MutableLiveData<>();

    OrderTree orderTree = new OrderTree();
    List<OrderTree> orderTreeList;
    List<String> uIds = new ArrayList<>();
    public RequestedPageViewModel(@NonNull Application application) {
        super(application);
    }

    void retrieveData(String phoneNum) {
        ClientOrderRepo clientOrderRepo = new ClientOrderRepo();
        orderTreeList = new ArrayList<>();
//        clientOrderRepo.addData(phoneNum).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                orderTreeList.clear();
//                for (DataSnapshot postSnapshotCategory : snapshot.getChildren()) {
//                   DataSnapshot data = postSnapshotCategory.child("order Details");
//                   for(DataSnapshot postSnapshot : data.getChildren()){
//                       orderTree = postSnapshot.getValue(OrderTree.class);
//                       uIds.add(postSnapshot.getKey());
//                       orderTreeList.add(orderTree);
//                   }
//
//                }
//                requestedPageLiveData.postValue(new ArrayList<OrderTree>());
//                requestedPageLiveData.postValue(orderTreeList);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        clientOrderRepo.addData(phoneNum).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for (DataSnapshot postSnapshotCategory : task.getResult().getChildren()) {
                    DataSnapshot data = postSnapshotCategory.child("order Details");
                    for(DataSnapshot postSnapshot : data.getChildren()){
                        orderTree = postSnapshot.getValue(OrderTree.class);
                        uIds.add(postSnapshot.getKey());
                        orderTreeList.add(orderTree);
                    }

                }
                requestedPageLiveData.postValue(new ArrayList<OrderTree>());
                requestedPageLiveData.postValue(orderTreeList);
            }

        });
    }



}


