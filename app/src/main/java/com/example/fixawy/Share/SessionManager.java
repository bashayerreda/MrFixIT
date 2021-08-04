package com.example.fixawy.Share;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManager(Context context){
        sharedPreferences = context.getSharedPreferences("AppKey",0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    //create set loginMethod
    public void setLogin(boolean login){
        editor.putBoolean("KEY_LOGIN",login);
        editor.commit();
    }

    //create get login method
    public boolean getLogin(){
        return sharedPreferences.getBoolean("KEY_LOGIN",false);
    }

    //create set data mehod for owner
    public void setOwnerData(String phone,String name,String tokenid){
        editor.putString("KEY_PHONE_O",phone);
        editor.putString("KEY_NAME_O",name);
        editor.putString("KEY_TOKEN_O",tokenid);
        editor.commit();
    }

    //create set data mehod for worker
    public void setWorkerData(String phone,String name,String tokenid,String jobTitle,String image){
        editor.putString("KEY_PHONE_W",phone);
        editor.putString("KEY_NAME_W",name);
        editor.putString("KEY_TOKEN_W",tokenid);
        editor.putString("KEY_JOB_W",jobTitle);
        editor.putString("KEY_IMAGE_W",image);
        editor.commit();
    }

    //create get OwnerData method
    public String getOwnerName(){
        return sharedPreferences.getString("KEY_NAME_O","");
    }

    public String getOwnerPhone(){
        return sharedPreferences.getString("KEY_PHONE_O","");
    }

    public String getOwnerTokenId(){
        return sharedPreferences.getString("KEY_TOKEN_O","");
    }


    //create get WorkerData mehod
    public String getWorkerName(){
        return sharedPreferences.getString("KEY_NAME_W","");
    }

    public String getWorkerPhone(){
        return sharedPreferences.getString("KEY_PHONE_W","");
    }

    public String getWorkerTokenId(){
        return sharedPreferences.getString("KEY_TOKEN_W","");
    }

    public String getWorkerJobTitle(){
        return sharedPreferences.getString("KEY_JOB_W","");
    }

    public String getWorkerImage(){
        return sharedPreferences.getString("KEY_IMAGE_W","");
    }



    //setcheck your Language
    public void setLanguage(String lang){
        editor.putString("KEY_LANG",lang);
    }

    public String getLang(){
        return sharedPreferences.getString("KEY_LANG","");
    }


}
