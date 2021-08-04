
package com.example.fixawy.Client.MakeOrder;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.fixawy.Client.MakeOrder.Repo.ClientOrderRepo;
import com.example.fixawy.Client.MakeOrder.pojos.OrderTree;

public class ThirdOrderViewModel extends AndroidViewModel {
    public ThirdOrderViewModel(@NonNull Application application) {
        super(application);
    }

    public void addData(OrderTree orderTree ,String phoneNum, String category) {
        ClientOrderRepo clientOrderRepo = new ClientOrderRepo();
        clientOrderRepo.addData(phoneNum, category).push().setValue(orderTree);
    }
    public void addDataToWorker(OrderTree orderTree , String category,String phoneNum){
        ClientOrderRepo clientOrderRepo = new ClientOrderRepo();
        clientOrderRepo.addDataToWorker(category,phoneNum).setValue(orderTree);

    }

}
