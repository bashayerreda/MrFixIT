package com.example.fixawy.ShopOwner.AddNewProduct;

import androidx.lifecycle.ViewModel;

import com.example.fixawy.Firebase.FirebaseHandlerShopOwner;
import com.example.fixawy.Pojos.Product;


public class AddNewProductViewModel extends ViewModel {

    FirebaseHandlerShopOwner firebaseHandlerShopOwner;

    public void addProduct(Product product , String phoneNum, String shopType) {
        firebaseHandlerShopOwner = new FirebaseHandlerShopOwner();
        firebaseHandlerShopOwner.addProduct(phoneNum,shopType).push().setValue(product);
    }
}
