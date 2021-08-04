package com.example.fixawy.Client.HomePageClient;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.fixawy.Client.AllPreviousQuestions.AllPreviousQuestionsActivity;
import com.example.fixawy.Client.AllTypesOfShops.AllTypesOfShopsActivity;
import com.example.fixawy.Client.ClientSettingPage.ClientSettingsActivity;
import com.example.fixawy.Client.HistoryPage.HistoryActivity;
import com.example.fixawy.Client.RequestedPage.RequestedActivity;
import com.example.fixawy.Client.SelectKindOfChoicePage.SelectKindOfChoiceActivity;
import com.example.fixawy.Client.SelectedPage.SelectedActivity;
//import com.example.fixawy.Notification.FirebasePushNotification;
import com.example.fixawy.NotificationToClient.FirebasePushNotification;
import com.example.fixawy.Pojos.AllCategory;

import com.example.fixawy.Pojos.User;
import com.example.fixawy.R;
import com.example.fixawy.Share.Preferences.preferences;
import com.example.fixawy.Share.SelectionPage.SelectMembershipType;
import com.example.fixawy.Share.SessionManager;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTR_PHONE_NUM;
import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTR_USER_NAME;


public class HomePageClientActivity extends AppCompatActivity implements OnItemClick, NavigationView.OnNavigationItemSelectedListener {
    RecyclerView mainRecyclerView;
    RecyclerView categoryRecyclerView;
    private Context context;
    private AllCategoryNamesModel allCategoryNamesModel;
    DatabaseReference database;
    TextView textViewUserName, textViewUserPhone;
    String client_phone_num, client_user_name;

    SessionManager sessionManager;

    SharedPreferences sp;

    DatabaseReference ref;

    List<AllCategory>allCategories;


    static  MainRecyclerAdapter mainRecyclerAdapter;
    CategoryItemRecyclerAdapter categoryItemRecyclerAdapter;

    public static List<AllCategory> allCategoryList = new ArrayList<>();


    //DrawLayout side-menubar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    String phoneNum,phoneClient;

    String user_token_id;
    String tokenID;


    int position;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_client);

        phoneClient = getIntent().getStringExtra("phoneClient");
        Intent intentBackgroundService = new Intent(this, FirebasePushNotification.class);
        startService(intentBackgroundService);

        sessionManager = new SessionManager(getApplicationContext());


        phoneNum = getIntent().getStringExtra("phone");
        //phoneNum = sp.getString("phone","no Num");



        //DrawLayout sidemenu-bar
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //get data from verifiaction code page
        Intent intent = getIntent();
        client_phone_num = intent.getStringExtra(EXTR_PHONE_NUM);
        client_user_name = intent.getStringExtra(EXTR_USER_NAME);
        client_user_name = sessionManager.getOwnerName();
        client_phone_num = sessionManager.getOwnerPhone();
        phoneNum = sessionManager.getOwnerPhone();
        user_token_id = intent.getStringExtra("token");
        user_token_id = sessionManager.getOwnerTokenId();
 //       Toast.makeText(this, user_token_id, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, client_user_name, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, client_phone_num, Toast.LENGTH_SHORT).show();

//       client_phone_num =  sp.getString(EXTR_PHONE_NUM,"No num");
//       client_user_name = sp.getString(EXTR_USER_NAME,"No name");
//        user_token_id = sp.getString("token","No name");




        //DrawLayout sidemenu-bar
        //start
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        //Hide or show items
//        Menu menu = navigationView.getMenu();
//        menu.findItem(R.id.nav_logout).setVisible(false);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.primaryColor));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        //show username and phone on side menuebar
        View headerView = navigationView.getHeaderView(0);
        textViewUserName = headerView.findViewById(R.id.header_client_name);
        textViewUserPhone = headerView.findViewById(R.id.header_client_phone);
        textViewUserName.setText(client_user_name);
        textViewUserPhone.setText(client_phone_num);
        //end

        allCategoryNamesModel = new AllCategoryNamesModel(this);


        //CategoryRecyclerView
        categoryRecyclerView = findViewById(R.id.categoryrv);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        categoryItemRecyclerAdapter = new CategoryItemRecyclerAdapter(this, allCategoryNamesModel.getAllCategories(), this);
        categoryRecyclerView.setAdapter(categoryItemRecyclerAdapter);



    /*    //Lists of employees
        List<User> employeeDataListElectricity = new ArrayList<>();
        List<User> employeeDataListCarpenter = new ArrayList<>();
        List<User> employeeDataListPulmber = new ArrayList<>();
        List<User> employeeDataListPainter = new ArrayList<>();
        List<User> employeeDataListTilesHandy = new ArrayList<>();
        List<User> employeeDataListMason = new ArrayList<>();
        List<User> employeeDataListSmith = new ArrayList<>();
        List<User> employeeDataListParquet = new ArrayList<>();
        List<User> employeeDataListGypsum = new ArrayList<>();
        List<User> employeeDataListMarble = new ArrayList<>();
        List<User> employeeDataListAlumetal = new ArrayList<>();
        List<User> employeeDataListGlasses = new ArrayList<>();
        List<User> employeeDataListWoodPainter = new ArrayList<>();
        List<User> employeeDataListCurtains = new ArrayList<>();
        List<User> employeeDataListSatellite = new ArrayList<>();
        List<User> employeeDataListAppliances = new ArrayList<>();
        List<AllCategory> allCategoryList = new ArrayList<>();*/




        //MainRecyclerView
        mainRecyclerView = findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(this, allCategoryList);
        mainRecyclerView.setAdapter(mainRecyclerAdapter);

        //retrive employee of carpenter
        HomePageReposatory homePageReposatory = new HomePageReposatory();
        homePageReposatory = new HomePageReposatory();
        List<User>employeeDataListCarpenter = new ArrayList<>();
        homePageReposatory.getEmployeeData("Carpenter",employeeDataListCarpenter);


        List<User>employeeDataListPulmber = new ArrayList<>();
        homePageReposatory.getEmployeeData("Plumber",employeeDataListPulmber);

        List<User>employeeDataListElectricity = new ArrayList<>();
        homePageReposatory.getEmployeeData("Electricity",employeeDataListElectricity);


        List<User>employeeDataListPainter = new ArrayList<>();
        homePageReposatory.getEmployeeData("Painter",employeeDataListPainter);

        List<User>employeeDataListGlasses = new ArrayList<>();
        homePageReposatory.getEmployeeData("Glasses",employeeDataListGlasses);

        List<User>employeeDataListMason = new ArrayList<>();
        homePageReposatory.getEmployeeData("Mason",employeeDataListMason);

        List<User>employeeDataListSmith = new ArrayList<>();
        homePageReposatory.getEmployeeData("Smith",employeeDataListSmith);

        List<User>employeeDataListParquet = new ArrayList<>();
        homePageReposatory.getEmployeeData("Parquet",employeeDataListParquet);

        List<User>employeeDataListTilesHandy = new ArrayList<>();
        homePageReposatory.getEmployeeData("TilesHandyMan",employeeDataListTilesHandy);

        List<User>employeeDataListGypsum = new ArrayList<>();
        homePageReposatory.getEmployeeData("Gypsum",employeeDataListGypsum);

        List<User>employeeDataListMarble = new ArrayList<>();
        homePageReposatory.getEmployeeData("Marble",employeeDataListMarble);

        List<User>employeeDataListAlumetal = new ArrayList<>();
        homePageReposatory.getEmployeeData("Alumetal",employeeDataListAlumetal);

        List<User>employeeDataListAppliances = new ArrayList<>();
        homePageReposatory.getEmployeeData("Appliances",employeeDataListAppliances);

        List<User>employeeDataListWoodPainter = new ArrayList<>();
        homePageReposatory.getEmployeeData("WoodPainter",employeeDataListWoodPainter);

        List<User>employeeDataListCurtains = new ArrayList<>();
        homePageReposatory.getEmployeeData("Curtains",employeeDataListCurtains);

        List<User>employeeDataListSatellite = new ArrayList<>();
        homePageReposatory.getEmployeeData("Satellite",employeeDataListSatellite);

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);


        } else {
            super.onBackPressed();
        }
        super.onBackPressed();


        //backButton
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
        return;
    }


    //to select page from side menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_requested:
                Intent intent = new Intent(HomePageClientActivity.this, RequestedActivity.class);

                intent.putExtra("phone",phoneNum);
                AllCategory selectCategory = allCategoryNamesModel.getAllCategories().get(position);
                intent.putExtra("CategoryType", selectCategory.getCategoryTitle());

                intent.putExtra("phone", phoneNum);
                intent.putExtra("token",tokenID);
                startActivity(intent);
                break;

            case R.id.nav_previous_requested:
                Intent intent2 = new Intent(HomePageClientActivity.this, HistoryActivity.class);
                intent2.putExtra("phone", phoneNum);
                startActivity(intent2);
                break;

            case R.id.all_shops:
                Intent intentShowAllShops = new Intent(HomePageClientActivity.this, AllTypesOfShopsActivity.class);
                intentShowAllShops.putExtra("phone",phoneNum);
                intentShowAllShops.putExtra(EXTR_USER_NAME,client_user_name);
                startActivity(intentShowAllShops);
                break;


            case R.id.nav_selected_worker:
                Intent intentSelectedWorker = new Intent(HomePageClientActivity.this, SelectedActivity.class);
                intentSelectedWorker.putExtra("phone", phoneNum);
                startActivity(intentSelectedWorker);
                break;

            case R.id.nav_all_previous_questions:
                allCategoryList.clear();
                Intent intent3 = new Intent(HomePageClientActivity.this, AllPreviousQuestionsActivity.class);
                intent3.putExtra("phone", phoneNum);
                intent3.putExtra(EXTR_USER_NAME,client_user_name);
                startActivity(intent3);
                break;

            case R.id.nav_client_settings:
                Intent settingintent = new Intent(HomePageClientActivity.this, ClientSettingsActivity.class);
                settingintent.putExtra("phone",phoneNum);
                startActivity(settingintent);
                break;

            case R.id.nav_logout:
                allCategoryList.clear();
                sessionManager.setLogin(false);
                sessionManager.setOwnerData("","","");
                Intent intent5 = new Intent(HomePageClientActivity.this, SelectMembershipType.class);
                startActivity(intent5);

                finish();
                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }




    //To click on buttons of categories
    //AllCategoryNamesModel allCategoryNamesModel2 = new AllCategoryNamesModel();


    @Override
    public void onItemClick(int position) {

        //Get Token id
        ref = FirebaseDatabase.getInstance().getReference().child("Client").child("Data").child(phoneNum);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tokenID = snapshot.child("tokenId").getValue().toString();



                AllCategory selectCategory = allCategoryNamesModel.getAllCategories().get(position);
                Intent intent = new Intent(HomePageClientActivity.this, SelectKindOfChoiceActivity.class);
                intent.putExtra("CategoryType", selectCategory.getCategoryTitle());
                intent.putExtra("phoneClient", phoneNum);
                intent.putExtra("token",tokenID);
                intent.putExtra(EXTR_USER_NAME,client_user_name);
                startActivity(intent);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        allCategoryList.clear();
    }
}