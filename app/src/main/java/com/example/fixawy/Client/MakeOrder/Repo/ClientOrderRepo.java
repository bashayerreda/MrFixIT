package com.example.fixawy.Client.MakeOrder.Repo;

import androidx.annotation.NonNull;

import com.example.fixawy.Client.MakeOrder.pojos.OrderTree;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ClientOrderRepo {

    public DatabaseReference addData(String phoneNum, String categoryType) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Client").child("make order").child(phoneNum).child(categoryType).child("order Details");
        //TODO //child(FirebaseAuth.getInstance().getCurrentUser().getUid())
        //TODO //FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()
        return myRef;
    }

    public DatabaseReference addData(String phoneNum) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Client").child("make order").child(phoneNum);

        return myRef;
    }
    public DatabaseReference addDataEdit(String phoneNum, String categoryType, String uid) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Client").child("make order").child(phoneNum).child(categoryType).child("order Details").child(uid);
        return myRef;
    }

    public DatabaseReference retrieveData(String phoneNum, String categoryType) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Client").child("make order").child(phoneNum).child(categoryType).child("order Details");
        return myRef;
    }
    public DatabaseReference removeDataFromWorker(String categoryType , String phoneNum) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef =  database.getReference("Worker").child(categoryType).child("order Details").child(phoneNum);
        return myRef;
    }

    public DatabaseReference retrieveDataEdit(String phoneNum, String categoryType,String uid) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Client").child("make order").child(phoneNum).child(categoryType).child("order Details").child(uid);
        return myRef;
    }

    public DatabaseReference addDataToWorker(String categoryType,String phoneNum) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Worker").child(categoryType).child("order Details").child(phoneNum);
        return myRef;
    }
    public DatabaseReference editDataWorker(String categoryType,String phoneNum) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Worker").child(categoryType).child("order Details").child(phoneNum);

        return myRef;
    }

}
