package com.example.fixawy.Client.HomePageClient;

import com.denzcoskun.imageslider.models.SlideModel;
import com.example.fixawy.Pojos.Advertisment;
import com.example.fixawy.R;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementsModel {


    List<Integer> advertisments = new ArrayList<>();


//    Advertisment advertisment1 = new Advertisment(1, R.drawable.adv1);
//    Advertisment advertisment2 = new Advertisment(2, R.drawable.adv2);
//    Advertisment advertisment3 = new Advertisment(3, R.drawable.adv3);

    public List<Integer> getAllAdvertisement(){

        advertisments.add(R.drawable.adv1);
        advertisments.add(R.drawable.adv2);
        advertisments.add(R.drawable.adv3);


        return advertisments;
    }



}
