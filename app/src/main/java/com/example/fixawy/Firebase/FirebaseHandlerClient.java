package com.example.fixawy.Firebase;

import android.util.Log;

import com.example.fixawy.Pojos.Accepted;
//import com.example.fixawy.Pojos.Answer;
import com.example.fixawy.Pojos.MakeOrder;
import com.example.fixawy.Pojos.Questions;
import com.example.fixawy.Pojos.User;
import com.example.fixawy.Pojos.WorkersAccepted;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHandlerClient {
    private DatabaseReference databaseReference;


    public FirebaseHandlerClient() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Client");

    }
    public Task<Void> addClientrData(User user, String phonNum)
    {

        return databaseReference.child("Data").child(phonNum).setValue(user);
    }

    public Task<Void> addClientQuestion(Questions question, String phone,String jobTitle)
    {
        return databaseReference.child("Questions").child(jobTitle).child(phone).child("Data").child("data").setValue(question);

    }

    public Task<Void> addClientQuestionForCategory(Questions question, String phone,String jobTitle)
    {
        return databaseReference.child("Questions").child("Question Category").child(jobTitle).child(phone).setValue(question);

    }
    public Task<Void> addAcceptedWorker(Accepted accepted, String phone, String jobTitle,String phoneOfWorker)
    {
        return databaseReference.child("make order").child(phone).child("Accepted").child(phoneOfWorker).setValue(accepted);

    }

    public Task<Void> addAcceptedPath(WorkersAccepted workersAccepted, String phone, String jobTitle, String phoneOfWorker)
    {
        return databaseReference.child("make order").child(phone).child("Workers Accepted Jobs").child(phoneOfWorker).setValue(workersAccepted);

    }



    public Task<Void> addHistoryJobsToClient(MakeOrder historyOrder, String phoneClient, String phoneWorker)
    {
        return databaseReference.child("Data").child(phoneClient).child("History Jobs").child(phoneWorker).setValue(historyOrder);

    }


    public DatabaseReference addQuestionsDataForCategory(String phoneNum,String jobTitle) {
        return databaseReference.child("Questions").child(jobTitle).child(phoneNum);
    }

    public DatabaseReference addPrevQuestionsForClientData(String phoneNum,String jobTitle) {
        return databaseReference.child("Data").child(phoneNum).child("Previous Questions").child(jobTitle);
    }

}
