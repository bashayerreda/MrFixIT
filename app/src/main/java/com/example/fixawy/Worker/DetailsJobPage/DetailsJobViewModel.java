package com.example.fixawy.Worker.DetailsJobPage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fixawy.Pojos.MakeOrder;

public class DetailsJobViewModel extends ViewModel {
    private MutableLiveData<MakeOrder> makeOrderMutableLiveData;
    private DetailsJobReposatory detailsJobReposatory;

    String phone,jobTitle;

    public void init(){
        if(makeOrderMutableLiveData != null){
            return;
        }
        detailsJobReposatory = DetailsJobReposatory.getInstance();
        makeOrderMutableLiveData = detailsJobReposatory.getData(phone,jobTitle);
    }
//return makeOrderMutableLiveData;
}