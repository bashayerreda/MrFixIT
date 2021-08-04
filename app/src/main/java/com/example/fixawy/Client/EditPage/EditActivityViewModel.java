package com.example.fixawy.Client.EditPage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.fixawy.Client.MakeOrder.Repo.ClientOrderRepo;
import com.example.fixawy.Client.MakeOrder.pojos.OrderTree;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditActivityViewModel  extends AndroidViewModel {
    List<OrderTree> orderTreeList;
    public MutableLiveData<OrderTree> editActivityLiveData = new MutableLiveData<>();
    OrderTree orderTree;
    public EditActivityViewModel(@NonNull Application application) {
        super(application);
    }
    public void addData(OrderTree orderTree ,String phoneNum, String category,String uid) {
        ClientOrderRepo clientOrderRepo = new ClientOrderRepo();
        clientOrderRepo.addDataEdit(phoneNum, category,uid).setValue(orderTree);
        clientOrderRepo.editDataWorker(category,phoneNum).setValue(orderTree);
    }


    void retrieveData(String phoneNum, String category,String uid) {
        ClientOrderRepo clientOrderRepo = new ClientOrderRepo();
        clientOrderRepo.retrieveDataEdit(phoneNum, category,uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    orderTree =snapshot.getValue(OrderTree.class);


              editActivityLiveData.postValue(orderTree);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




}
