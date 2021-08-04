package com.example.fixawy.Client.HomePageClient;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fixawy.Pojos.User;

import java.util.List;

public class HomePageClientViewModel extends ViewModel {

    //private HomePageReposatory homePageReposatory = new HomePageReposatory();
     MutableLiveData<List<User>>users ;
     public void init(Context context){
         if(users != null){
             return;
         }
     }
     public LiveData<List<User>>getUsers(){
         return users;
     }
}