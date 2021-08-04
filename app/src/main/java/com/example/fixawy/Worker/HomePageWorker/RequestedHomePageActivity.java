package com.example.fixawy.Worker.HomePageWorker;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixawy.Client.MakeOrder.pojos.OrderTree;
import com.example.fixawy.Client.ShowProductsOfShopType.ShowProductsOfShopTypeActivity;
import com.example.fixawy.Firebase.FirebaseHandlerClient;
import com.example.fixawy.Pojos.User;
import com.example.fixawy.R;
import com.example.fixawy.Share.Preferences.preferences;
import com.example.fixawy.Share.SelectionPage.SelectMembershipType;
import com.example.fixawy.Share.SessionManager;
import com.example.fixawy.Worker.DetailsJobPage.DetailsJobActivity;
import com.example.fixawy.Worker.HistoryJobsPage.HistoryJobActivity;
import com.example.fixawy.Worker.JobAccepted.JobAcceptedActivity;
import com.example.fixawy.Worker.WorkerProfilePage.WorkerProfileActivity;
import com.example.fixawy.Worker.WorkerQuestions.WorkerQuestionsActivity;
import com.example.fixawy.Worker.WorkerSettingPage.WorkerSettingActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.fixawy.Share.LoginPage.LoginActivity.EXTRA_WORKER_IMAGE;
import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTRA_JOB_TITLE;
import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTR_PHONE_NUM;
import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTR_USER_NAME;


public class RequestedHomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RequestedItemRecyclerAdapter.onItemClickListener {




    //DrawLayout side-menubar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    //textview which apear on side menue bar
    TextView textViewWorkerName,textViewWorkerPhone,textViewWorkerJobTitle;
    ImageButton change_image_button;

    String w_name,w_phone,w_job,w_type,w_address,w_email,w_password,w_image;
    String w_likes,w_numOfJob,w_disLike,w_rating;
    String worker_image;
    String worker_job_title;
    String w_tokenid;
    private Calendar mCalendar;

    private Uri imageUri;
    private CircleImageView profileImageView;
    private StorageReference storageProfilePictureRef;
    private DatabaseReference mDatabaseRef;
    private  StorageTask mUploadTask;
    public static final String EXTRA_ORDER_PHONE ="phonid";
    public static final String EXTRA_ORDER_JOB_TITLE ="titleJobid";

    public static final String EXTRA_WORKER_NAME = "wName";
    public static final String EXTRA_WORKER_ADDRESS = "wAddress";
    public static final String EXTRA_WORKER_PHONE = "wPhone";

    public static final String EXTRA_WORKER_NUM_OF_JOB = "wNumJob";
    public static final String EXTRA_WORKER_LIKE = "wLike";
    public static final String EXTRA_WORKER_DIS_LIKE = "wDisLike";
    public static final String EXTRA_WORKER_RATING = "wRating";

    public static final String REQUESTED_EXTRA_JOB_TITLE = "Requested_jobtitle";
    public static final String REQUESTED_EXTRA_WORKER_PHONE = "Requested_phone";

    public static final String EXTRA_TOKEN_ID = "tokenid";

    private RequestedHomePageViewModel requestedHomePageViewModel;
    String jobTitle,phoneWorker,worker_user_name;
    String worker_phone_num;
    SessionManager sessionManager;


    //recyclerView
    RecyclerView recyclerView_worker_requested;
    FirebaseHandlerClient firebaseHandlerClient;
    public static RequestedItemRecyclerAdapter myAdapter;
    List<OrderTree> list;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_home_page);


        sessionManager = new SessionManager(getApplicationContext());

        list = new ArrayList<OrderTree>();
        mCalendar = Calendar.getInstance();

        //get data from verifiaction code page
        Intent intent = getIntent();
        worker_phone_num = intent.getStringExtra(EXTR_PHONE_NUM);
        worker_user_name = intent.getStringExtra(EXTR_USER_NAME);
        worker_job_title = intent.getStringExtra(EXTRA_JOB_TITLE);
        worker_image = intent.getStringExtra(EXTRA_WORKER_IMAGE);

        worker_user_name = sessionManager.getWorkerName();
        worker_phone_num = sessionManager.getWorkerPhone();
        worker_job_title = sessionManager.getWorkerJobTitle();
        worker_image = sessionManager.getWorkerImage();
        w_tokenid = sessionManager.getWorkerTokenId();




//        Toast.makeText(this, worker_phone_num, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, worker_user_name, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, worker_job_title, Toast.LENGTH_SHORT).show();



        storageProfilePictureRef = FirebaseStorage.getInstance().getReference("WorkerImages");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Worker").child(worker_job_title).child("Data").child(worker_phone_num);

        // mDatabaseRef = FirebaseDatabase.getInstance().getReference("Worker").child("Carpenter").child("Data").child("01225699594");

        jobTitle = getIntent().getStringExtra("jobTitle");
        phoneWorker=getIntent().getStringExtra("phone");

        //DrawLayout sidemenu-bar
        drawerLayout = findViewById(R.id.drawer_layout2);
        navigationView = findViewById(R.id.nav_view2);
        toolbar=findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        //Hide or show items
//        Menu menu = navigationView.getMenu();
//        menu.findItem(R.id.nav_logout).setVisible(false);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.primaryColor));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home2);

        //show username and phone on side menuebar
        View headerView2= navigationView.getHeaderView(0);
        textViewWorkerName = headerView2.findViewById(R.id.header_worker_name);
        textViewWorkerPhone = headerView2.findViewById(R.id.header_worker_phone);
        textViewWorkerJobTitle = headerView2.findViewById(R.id.header_worker_job_title);
        textViewWorkerName.setText(worker_user_name);
        textViewWorkerPhone.setText(worker_phone_num);
        textViewWorkerJobTitle.setText(worker_job_title);

        //CircleImageView profileImageView = headerView2.findViewById(R.id.worker_profile_image);
        profileImageView = headerView2.findViewById(R.id.worker_profile_image);
        change_image_button = headerView2.findViewById(R.id.change_image);

        if (worker_image.isEmpty()) {
            profileImageView.setImageResource(R.drawable.profile);
        } else{
            Picasso.get().load(worker_image).into(profileImageView);
        }
        //Picasso.get().load(worker_image).placeholder(R.drawable.profile).into(profileImageView);
        Log.d("nnnnn","user=="+worker_image);


        //retrive image of profile
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    w_name = dataSnapshot.child("userName").getValue().toString();
                    w_phone = dataSnapshot.child("phone").getValue().toString();
                    w_type = dataSnapshot.child("type").getValue().toString();
                    w_job = dataSnapshot.child("jobTitle").getValue().toString();
                    w_password = dataSnapshot.child("password").getValue().toString();
                    w_address = dataSnapshot.child("address").getValue().toString();
                    w_email = dataSnapshot.child("email").getValue().toString();

                    w_numOfJob = dataSnapshot.child("numOfJob").getValue().toString();
                    w_likes = dataSnapshot.child("like").getValue().toString();
                    w_disLike = dataSnapshot.child("disLike").getValue().toString();
                    w_rating = dataSnapshot.child("rating").getValue().toString();

                     w_image = dataSnapshot.child("image").getValue().toString();
                     w_tokenid = dataSnapshot.child("tokenId").getValue().toString();
                  //  Toast.makeText(RequestedHomePageActivity.this, w_tokenid, Toast.LENGTH_SHORT).show();


                    if(dataSnapshot.child("image").exists())
                    {
                        String image=dataSnapshot.child("image").getValue().toString();
                        Log.d("looooooo","user=="+image);

                        Picasso.get().load(image).placeholder(R.drawable.profile).into(profileImageView);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //image change
        change_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFileChooser();
            }

        });
        //Recycler data requested
        recyclerView_worker_requested = findViewById(R.id.recyclerviewRequestedOwner);

        recyclerView_worker_requested.setHasFixedSize(true);
        recyclerView_worker_requested.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new RequestedItemRecyclerAdapter(this,list);
        recyclerView_worker_requested.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(RequestedHomePageActivity.this);



        database = FirebaseDatabase.getInstance().getReference("Worker").child(worker_job_title).child("order Details");



        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    OrderTree makeOrder = dataSnapshot.getValue(OrderTree.class);
                    list.add(makeOrder);



                }
                RequestedHomePageActivity.myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        database.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                for(DataSnapshot dataSnapshot : task.getResult().getChildren()){
//                    OrderTree makeOrder = dataSnapshot.getValue(OrderTree.class);
//                    list.add(makeOrder);
//                }
//                RequestedHomePageActivity.myAdapter.notifyDataSetChanged();
//            }
//
//        });




        //requestedHomePageViewModel = new ViewModelProvider(this).get(RequestedHomePageViewModel.class);
//        requestedHomePageViewModel = new RequestedHomePageViewModel(worker_job_title);
//        requestedHomePageViewModel.getRequestedListModelData(worker_job_title).observe(this, new Observer<List<MakeOrder>>() {
//            @Override
//            public void onChanged(List<MakeOrder> makeOrders) {
//                myAdapter.setListMakeOrders(makeOrders);
//                myAdapter.notifyDataSetChanged();
//
//            }
//        });


//        movieViewModel.getQuizListModelData().observe(this, new Observer<List<Movie>>() {
//            @Override
//            public void onChanged(List<Movie> movies) {
//                recyclerViewadapter.setListMovies(movies);
//                recyclerViewadapter.notifyDataSetChanged();
//            }
//        });

//        trip.setDate_time(binding.edDate.getText().toString()+" "+binding.edTime.getText().toString());


//        Calendar calendar = Calendar.getInstance();
//        long nowMillis = calendar.getTimeInMillis();
//        long diff = mCalendar.getTimeInMillis() - nowMillis;


        Calendar calendar = Calendar.getInstance();
//        calendar.set(2021, 5, 30,
//                20, 34, 0);
//        long startTime = calendar.getTimeInMillis();
//        Log.d("oooo","my time is "+startTime);


    }










    //Change profile worker image start
    private void OpenFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resulCode, Intent data){
        super.onActivityResult(requestCode,resulCode,data);

        if(requestCode==1 && resulCode==RESULT_OK && data !=null && data.getData()!=null){
            imageUri = data.getData();
            profileImageView.setImageURI(imageUri);
            if(mUploadTask != null && mUploadTask.isInProgress()){
                Toast.makeText(this, "Upload in aprogress", Toast.LENGTH_SHORT).show();
            }else {
                uploadPicture();
            }

        }


    }
    //to get file extension of image
    private String getFileExtension(Uri uri){
        ContentResolver CR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(CR.getType(uri));
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("uploading Image.......");
        pd.show();

        if(imageUri != null){
            StorageReference fileReference = storageProfilePictureRef.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
            mUploadTask = fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();
                    Snackbar.make(findViewById(android.R.id.content),"Image Uploaded",Snackbar.LENGTH_LONG).show();
                    Toast.makeText(RequestedHomePageActivity.this, "upload Successfully", Toast.LENGTH_SHORT).show();
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            User user = new User(w_name.trim(),w_email.trim(),w_phone.trim(),w_address.trim(),w_type.trim(),w_password.trim(),w_job.trim(),w_tokenid.trim(),url);
                            //User user = new User(url);
                            mDatabaseRef.setValue(user);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RequestedHomePageActivity.this, "Faild To Upload", Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    pd.setMessage("Percentage"+(int)progressPercent+"%");
                }
            });
        }else{
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }
//end of change profile image of worker







    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home2:
                break;
            case R.id.nav_accepted_job:
                Intent intent = new Intent(RequestedHomePageActivity.this, JobAcceptedActivity.class);
                intent.putExtra(REQUESTED_EXTRA_WORKER_PHONE,w_phone);
                intent.putExtra(REQUESTED_EXTRA_JOB_TITLE,worker_job_title);
                startActivity(intent);
                break;

            case R.id.nav_all_previous_questions:
                Intent intentPrevQuestions = new Intent(RequestedHomePageActivity.this, WorkerQuestionsActivity.class);
                intentPrevQuestions.putExtra("jobTitle",worker_job_title);
                intentPrevQuestions.putExtra("phoneWorker",w_phone);
                intentPrevQuestions.putExtra(EXTR_USER_NAME,worker_user_name);
//                Toast.makeText(this, "NAME OF WORKER " + worker_user_name, Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, "job : " + jobTitle, Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, "phone" + phoneWorker, Toast.LENGTH_SHORT).show();
                startActivity(intentPrevQuestions);
                break;


            case R.id.nav_profile:
                Intent intent2 = new Intent(RequestedHomePageActivity.this, WorkerProfileActivity.class);
                intent2.putExtra(EXTRA_JOB_TITLE,worker_job_title);
                intent2.putExtra(EXTRA_WORKER_PHONE,w_phone);
                Log.d("uiuiuiuiu",w_phone);
                Log.d("uiuiuiuiu",worker_job_title);
                startActivity(intent2);
                break;

            case R.id.nav_previous_job:
                Intent intent3 = new Intent(RequestedHomePageActivity.this, HistoryJobActivity.class);
                intent3.putExtra(EXTRA_JOB_TITLE,worker_job_title);
                intent3.putExtra(EXTRA_WORKER_PHONE,w_phone);
                startActivity(intent3);
                break;


            case R.id.nav_logout:
                sessionManager.setLogin(false);
                sessionManager.setWorkerData("","","","","");
                Intent intent4 = new Intent(RequestedHomePageActivity.this, SelectMembershipType.class);
                startActivity(intent4);
                finish();
                break;

            case R.id.nav_worker_settings:
                Intent settingIntent = new Intent(RequestedHomePageActivity.this, WorkerSettingActivity.class);
                settingIntent.putExtra("phone",w_phone);
                settingIntent.putExtra("job",worker_job_title);
                startActivity(settingIntent);
                break;



            case R.id.all_shops:
                Intent intentAllShops = new Intent(RequestedHomePageActivity.this, ShowProductsOfShopTypeActivity.class);
                intentAllShops.putExtra("shopType",worker_job_title);
                intentAllShops.putExtra("phone",w_phone);
                startActivity(intentAllShops);

                break;


//            case R.id.nav_all_previous_questions:
//                Intent intent3 = new Intent(HomePageClientActivity.this, AllPreviousQuestionsActivity.class);
//                intent3.putExtra("phone", client_phone_num);
//                startActivity(intent3);
//                break;
/*
            case R.id.nav_selected_worker:
                Intent intent4 = new Intent(HomePageClientActivity.this, SelectedActivity.class);
                intent4.putExtra("phone", phoneNum);
                startActivity(intent4);
                break;

 */


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    //To click on row of requested


    @Override
    public void onItemClick(int position) {
        Intent detailsIntent = new Intent(this, DetailsJobActivity.class);
        OrderTree clickedItem = list.get(position);

       // User user = new User();

        User user = new User();
        String clientName = clickedItem.getUserName();
       // Toast.makeText(this, clientName, Toast.LENGTH_SHORT).show();

        detailsIntent.putExtra(EXTRA_ORDER_PHONE,clickedItem.getRequestedPhone());
        detailsIntent.putExtra(EXTRA_ORDER_JOB_TITLE,clickedItem.getJobTitle());
        detailsIntent.putExtra(EXTRA_WORKER_NAME,w_name);
        detailsIntent.putExtra(EXTRA_WORKER_ADDRESS,w_address);
        detailsIntent.putExtra(EXTRA_WORKER_PHONE,w_phone);
        detailsIntent.putExtra(EXTRA_WORKER_NUM_OF_JOB,w_numOfJob);
        detailsIntent.putExtra(EXTRA_WORKER_LIKE,w_likes);
        detailsIntent.putExtra(EXTRA_WORKER_DIS_LIKE,w_disLike);
        detailsIntent.putExtra(EXTRA_WORKER_RATING,w_rating);
        detailsIntent.putExtra("img2",worker_image);
        detailsIntent.putExtra("img",w_image);
        detailsIntent.putExtra(EXTRA_JOB_TITLE,worker_job_title);

        detailsIntent.putExtra(EXTRA_TOKEN_ID,w_tokenid);

        detailsIntent.putExtra("clientName",clientName);


        startActivity(detailsIntent);
    }

    //backButton
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);

        return;
    }
}