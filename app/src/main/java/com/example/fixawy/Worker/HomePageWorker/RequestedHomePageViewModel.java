package com.example.fixawy.Worker.HomePageWorker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fixawy.Pojos.MakeOrder;

import java.util.List;

public class RequestedHomePageViewModel extends ViewModel {
    RequestedHomePageRepository requestedHomePageRepository = new RequestedHomePageRepository();
    String worker_job_title;

    MutableLiveData<List<MakeOrder>> makeOrderMutableLiveData = new MutableLiveData<>();
    public LiveData<List<MakeOrder>> getRequestedListModelData(String worker_job_title) {
        return makeOrderMutableLiveData;
    }

    public RequestedHomePageViewModel(String worker_job_title){

        makeOrderMutableLiveData.setValue(requestedHomePageRepository.getRequested(worker_job_title));
    }
}
