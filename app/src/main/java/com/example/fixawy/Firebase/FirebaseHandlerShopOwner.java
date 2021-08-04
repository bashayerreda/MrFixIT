package com.example.fixawy.Firebase;

import com.example.fixawy.Pojos.ShopOwnerUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHandlerShopOwner {

    private DatabaseReference databaseReference;


    public FirebaseHandlerShopOwner() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("ShopOwner");

    }

    public Task<Void> addShopOwnerData(ShopOwnerUser shopOwnerUser, String phoneNum,String shopType)
    {
        return databaseReference.child(shopType).child("Data").child(phoneNum).setValue(shopOwnerUser);
    }


    public DatabaseReference addProduct(String phoneNum,String shopType) {
        return databaseReference.child(shopType).child("Product Posts").child(phoneNum);
    }

}
