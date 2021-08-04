package com.example.fixawy.Client.AskQuestionPage;

import androidx.lifecycle.ViewModel;
import com.example.fixawy.Firebase.FirebaseHandlerClient;
import com.example.fixawy.Firebase.FirebaseHandlerWorker;
import com.example.fixawy.Pojos.Questions;
import com.example.fixawy.Pojos.User;


public class AskQuestionViewModel extends ViewModel {

    private FirebaseHandlerClient firebaseHandlerClient;
    private FirebaseHandlerWorker firebaseHandlerWorker;


    public void addQuestionsDataForCategory(Questions questions , String phoneNum,String jobTitle) {
        firebaseHandlerClient = new FirebaseHandlerClient();
        firebaseHandlerClient.addQuestionsDataForCategory(phoneNum,jobTitle).push().setValue(questions);
    }

    public void addPrevQuestionsForClientData(Questions questions , String phoneNum,String jobTitle) {
        firebaseHandlerClient = new FirebaseHandlerClient();
        firebaseHandlerClient.addPrevQuestionsForClientData(phoneNum,jobTitle).push().setValue(questions);
    }

    public void addQuestionsToWorkers(Questions questions , String phoneNum,String jobTitle) {
        firebaseHandlerWorker = new FirebaseHandlerWorker();
        firebaseHandlerWorker.addQuestionsToWorkers(phoneNum,jobTitle).push().setValue(questions);
    }
}
