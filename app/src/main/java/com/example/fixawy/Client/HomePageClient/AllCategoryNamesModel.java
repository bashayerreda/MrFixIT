package com.example.fixawy.Client.HomePageClient;

import android.content.Context;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;


import com.example.fixawy.Pojos.AllCategory;
import com.example.fixawy.R;

import java.util.ArrayList;
import java.util.List;

public class AllCategoryNamesModel {
    List<AllCategory> categories = new ArrayList<>();

    private Context context;

    SharedPreferences sp;
    SharedPreferences.Editor edit;

    String lang;

    public AllCategoryNamesModel(Context context){
        lang = "en";
       // sp = context.getSharedPreferences("AppKey",0);
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        edit = sp.edit();
        edit.apply();

        lang = sp.getString("lang","no");
        Log.d("langggggggg",lang);


    }





    // AllCategory electricity = new AllCategory(1, R.string.electricity+"");
    AllCategory electricity = new AllCategory(1, "Electricity");
    AllCategory plumber = new AllCategory(2,"Plumber");
    AllCategory carpenter = new AllCategory(3,"Carpenter");
    AllCategory painter = new AllCategory(4,"Painter");
    AllCategory TilesHandyMan = new AllCategory(5,"tilesHandyman");
    AllCategory mason = new AllCategory(6,"Mason");
    AllCategory smith = new AllCategory(7,"Smith");
    AllCategory parquet = new AllCategory(8,"Parquet");
    AllCategory gypsum = new AllCategory(9,"Gypsum");
    AllCategory marble = new AllCategory(10,"Marble");
    AllCategory alumetal = new AllCategory(11,"Alumetal");
    AllCategory glasses = new AllCategory(12,"Glasses");
    AllCategory woodPainter = new AllCategory(13,"WoodPainter");
    AllCategory curtains = new AllCategory(14,"Curtains");
    AllCategory satellite = new AllCategory(15,"Satellite");
    AllCategory Appliances = new AllCategory(16,"Appliances Maintenances");


    AllCategory electricityAr = new AllCategory(1, "كهربائى");
    AllCategory plumberAr = new AllCategory(2,"سباك");
    AllCategory carpenterAr = new AllCategory(3,"نجار");
    AllCategory painterAr = new AllCategory(4,"نقاش");
    AllCategory TilesHandyManAr = new AllCategory(5,"سيراميك");
    AllCategory masonAr = new AllCategory(6,"بناء");
    AllCategory smithAr = new AllCategory(7,"حداد");
    AllCategory parquetAr = new AllCategory(8,"باركيه");
    AllCategory gypsumAr = new AllCategory(9,"جبس");
    AllCategory marbleAr = new AllCategory(10,"رخام");
    AllCategory alumetalAr = new AllCategory(11,"الوميتال");
    AllCategory glassesAr = new AllCategory(12,"زجاج");
    AllCategory woodPainterAr = new AllCategory(13,"استرجى");
    AllCategory curtainsAr = new AllCategory(14,"ستائر");
    AllCategory satelliteAr = new AllCategory(15,"صيانه دش");
    AllCategory AppliancesAr = new AllCategory(16,"صيانه اجهزه منزليه");


    public List<AllCategory>getAllCategories(){

        if(lang == "en"){
            categories.add(electricity);
            categories.add(plumber);
            categories.add(carpenter);
            categories.add(painter);
            categories.add(TilesHandyMan);
            categories.add(mason);
            categories.add(smith);
            categories.add(parquet);
            categories.add(gypsum);
            categories.add(marble);
            categories.add(alumetal);
            categories.add(glasses);
            categories.add(woodPainter);
            categories.add(curtains);
            categories.add(satellite);
            categories.add(Appliances);


        }
        else if(lang == "ar"){
            categories.add(electricityAr);
            categories.add(plumberAr);
            categories.add(carpenterAr);
            categories.add(painterAr);
            categories.add(TilesHandyManAr);
            categories.add(masonAr);
            categories.add(smithAr);
            categories.add(parquetAr);
            categories.add(gypsumAr);
            categories.add(marbleAr);
            categories.add(alumetalAr);
            categories.add(glassesAr);
            categories.add(woodPainterAr);
            categories.add(curtainsAr);
            categories.add(satelliteAr);
            categories.add(AppliancesAr);


        }else{
            categories.add(electricity);
            categories.add(plumber);
            categories.add(carpenter);
            categories.add(painter);
            categories.add(TilesHandyMan);
            categories.add(mason);
            categories.add(smith);
            categories.add(parquet);
            categories.add(gypsum);
            categories.add(marble);
            categories.add(alumetal);
            categories.add(glasses);
            categories.add(woodPainter);
            categories.add(curtains);
            categories.add(satellite);
            categories.add(Appliances);
        }

        return categories;

    }


String[] returnCategory(Context context){
    String[] stringArray = context.getResources().getStringArray(R.array.categories);
    return stringArray;
}





}
