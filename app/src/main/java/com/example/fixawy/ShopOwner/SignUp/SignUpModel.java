package com.example.fixawy.ShopOwner.SignUp;

import androidx.lifecycle.ViewModel;

import com.example.fixawy.Firebase.FirebaseHandlerShopOwner;
import com.example.fixawy.Pojos.ShopOwnerUser;

public class SignUpModel extends ViewModel{

    private FirebaseHandlerShopOwner firebaseHandlerShopOwner;

    public void registerShopOwner(ShopOwnerUser shopOwnerUser){
        firebaseHandlerShopOwner = new FirebaseHandlerShopOwner();
        firebaseHandlerShopOwner.addShopOwnerData(shopOwnerUser,shopOwnerUser.getPhone(),shopOwnerUser.getShopType()).addOnSuccessListener(suc->{
        });
    }
}
