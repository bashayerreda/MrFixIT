package com.example.fixawy.Client.AcceptedWorkerPage;

import androidx.lifecycle.ViewModel;

import com.example.fixawy.Firebase.FirebaseHandlerClient;
import com.example.fixawy.Firebase.FirebaseHandlerWorker;
import com.example.fixawy.Pojos.MakeOrder;

class AcceptWorkModel extends ViewModel {
    FirebaseHandlerClient firebaseHandlerClient;
    FirebaseHandlerWorker firebaseHandlerWorker;


    public void addJobAcceptedToWorker(MakeOrder order){
        firebaseHandlerWorker = new FirebaseHandlerWorker();
        firebaseHandlerWorker.addJobAcceptedToWorker(order,order.getRequestedPhone(),order.getJobTitle(),order.getPhone()).addOnSuccessListener(suc->{

        });
    }

    public void addHistoryJobsToClient(MakeOrder historyOrder){
        firebaseHandlerClient = new FirebaseHandlerClient();
        firebaseHandlerClient.addHistoryJobsToClient(historyOrder,historyOrder.getRequestedPhone(),historyOrder.getPhone()).addOnSuccessListener(suc->{

        });
    }



}
