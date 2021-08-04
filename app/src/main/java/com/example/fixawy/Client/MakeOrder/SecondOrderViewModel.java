package com.example.fixawy.Client.MakeOrder;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.fixawy.Client.MakeOrder.Repo.ClientOrderRepo;
import com.example.fixawy.Client.MakeOrder.pojos.OrderTree;

public class SecondOrderViewModel extends AndroidViewModel {


    public SecondOrderViewModel(@NonNull Application application) {
        super(application);
    }

    public void addData(OrderTree orderTree, String phoneNum, String category) {
        ClientOrderRepo clientOrderRepo = new ClientOrderRepo();
        clientOrderRepo.addData(phoneNum,category).setValue(orderTree);

    }

}