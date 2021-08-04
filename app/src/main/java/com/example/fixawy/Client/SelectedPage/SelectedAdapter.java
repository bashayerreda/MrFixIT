package com.example.fixawy.Client.SelectedPage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixawy.Client.AcceptedWorkerPage.AcceptedWorkActivity;
import com.example.fixawy.Client.ReplyQuestions.AnswerActivity;
import com.example.fixawy.Client.RequestedPage.RequestedAdapter;
import com.example.fixawy.Pojos.Accepted;
import com.example.fixawy.Pojos.MakeOrder;
import com.example.fixawy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class SelectedAdapter extends RecyclerView.Adapter<SelectedAdapter.SelectedItemViewHolder> {
    private Context context;
    List<Accepted>accepteds;
    String phoneWorkerNum,phoneClientNum,workerJobTitle,nameOfWorker;
    DatabaseReference ref;

    String address1;
    String address2;
    String lat1,lat2,log1,log2;
    double clientLat,clientLog,workerLat,workerLog;
    double distance;

    Dialog dialog;
    int i =0;







    public SelectedAdapter(Context context, List<Accepted> accepteds,String phoneClientNum) {
        this.context = context;
        this.accepteds = accepteds;
        this.phoneClientNum = phoneClientNum;
    }
    @NonNull
    @Override
    public SelectedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_item,null,false);
        SelectedAdapter.SelectedItemViewHolder viewHolder = new SelectedAdapter.SelectedItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedItemViewHolder holder, int position) {

        if (accepteds.get(position).getImage().isEmpty()) {
            holder.worker_selected_imageView.setImageResource(R.drawable.profile);
        } else{
            Picasso.get().load(accepteds.get(position).getImage()).into(holder.worker_selected_imageView);
        }
       // Picasso.get().load(accepteds.get(position).getImage()).placeholder(R.drawable.person).into(holder.worker_selected_imageView);
        holder.worker_name.setText(accepteds.get(position).getNameOfWorker());
        holder.worker_phone.setText(accepteds.get(position).getPhoneOfWorker());
        holder.worker_comment.setText(accepteds.get(position).getCommentLine());
        holder.worker_job_title.setText(accepteds.get(position).getJobTitle());



        holder.client_map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get address of worker
                String addressOfWorker = accepteds.get(position).getAddressOfWorker();
                Log.d("addddddworker",addressOfWorker);

                //get address of client from database
                //String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String jobTitle = accepteds.get(position).getJobTitle();
                ref= FirebaseDatabase.getInstance().getReference("Client").child("make order").child(phoneClientNum).child(jobTitle).child("order Details");
                //Query query = ref.orderByChild("tablenme").getRef();
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String key = snapshot.getChildren().iterator().next().getKey();
                        String addressOfClient = snapshot.child(key).child("location").getValue().toString();
                        Log.d("addddddclient",addressOfClient);

                        //get langtude and latitude of Client
                        GeoLocation geoLocation = new GeoLocation();
                        geoLocation.getAddress(addressOfClient,context,new GeoHandler());

                        //get langtude and latitude of Worker
                        GeoLocation geoLocation2 = new GeoLocation();
                        geoLocation2.getAddress(addressOfWorker,context,new GeoHandler2());

                       // distance(clientLat,clientLog,workerLat,workerLog);
                        openDialog(distance);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneWorkerNum = accepteds.get(position).getPhoneOfWorker();
                workerJobTitle = accepteds.get(position).getJobTitle();
                nameOfWorker = accepteds.get(position).getNameOfWorker();
                v.getContext().startActivity(new Intent(v.getContext(), AcceptedWorkActivity.class)
                        .putExtra("phoneWorker",phoneWorkerNum)
                        .putExtra("phoneClient",phoneClientNum)
                        .putExtra("workerJobTitle",workerJobTitle)
                        .putExtra("nameOfWorker",nameOfWorker));

               // Toast.makeText(v.getContext(),phoneClientNum, Toast.LENGTH_SHORT).show();
            }
        });


        //make call
        holder.client_phone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNumber =  accepteds.get(position).getPhoneOfWorker();
                String call = "tel:" +mobileNumber.trim();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(call));
                context.startActivity(intent);
            }
        });

        //open whatsApp chat
        holder.client_chat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNumber =  accepteds.get(position).getPhoneOfWorker();
                String whatsapp_URL = "http://api.whatsapp.com/send?phone=";
                boolean Installed = isAppInstalled("com.whatsapp");
                if(Installed)
                {
                    Intent whatsapp_intent = new Intent(Intent.ACTION_VIEW);
                    whatsapp_intent.setData(Uri.parse(whatsapp_URL+"02"+mobileNumber));
                    context.startActivity(whatsapp_intent);
                }
                else{
                    Toast.makeText(context,"whatsapp is not installed",Toast.LENGTH_SHORT).show();}
            }
        });




    }

    private void distance(double clientLat, double clientLog, double workerLat, double workerLog) {
        //calculate longtiude difference
        double longDiff = clientLog-workerLog;
        //calculate distance
         distance = Math.sin(deg2rad(clientLat))
                *Math.sin(deg2rad(workerLat))
                +Math.cos(deg2rad(clientLat))
                *Math.cos(deg2rad(workerLat))
                *Math.cos(deg2rad(longDiff));
        distance = Math.acos(distance);
        //convert distance radian to degree
        distance = rad2deg(distance);
        //Distance in miles
        distance = distance * 60 * 1.1515;
        //Distance in Kilometers
        distance = distance * 1.609344;

        openDialog(distance);

        //Toast.makeText(context, String.format(Locale.US,"%2f Kilometers",distance), Toast.LENGTH_SHORT).show();

    }

    //create the dialog
    private void openDialog(double distance) {

        dialog = new Dialog(this.context);
        dialog.setContentView(R.layout.distance_dialog);
        TextView distanceTv = dialog.findViewById(R.id.distance);
        ProgressBar progressBar = dialog.findViewById(R.id.progressBar);

        distanceTv.setVisibility(View.INVISIBLE);
        //to stop progress after some time
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                progressBar.setVisibility(View.GONE);
                distanceTv.setVisibility(View.VISIBLE);
                distanceTv.setText(String.format(Locale.US,"%2f",distance));
                do{
                    distance(clientLat,clientLog,workerLat,workerLog);
                    i++;

                }while (i==1);
                dialog.dismiss();
            }
        },2000);

        dialog.show();

    }

    //convert radian to degree
    private double rad2deg(double distance) {
        return (distance*180.0/Math.PI);
    }

    //convert degree to radian
    private double deg2rad(double clientLat){
        return (clientLat*Math.PI/180.0);
    }

    @Override
    public int getItemCount() {
        if(accepteds == null){
            return 0;
        } else {
            return accepteds.size();
        }
    }

    public class SelectedItemViewHolder extends RecyclerView.ViewHolder {

        ImageView worker_selected_imageView;
        TextView worker_name,worker_phone,worker_comment,worker_job_title;
        Button client_phone_btn,client_chat_btn,client_map_btn;

        public View layout;
        public SelectedItemViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            worker_selected_imageView = itemView.findViewById(R.id.worker_img);
            worker_name = itemView.findViewById(R.id.worker_name);
            worker_phone = itemView.findViewById(R.id.worker_phone);
            worker_comment = itemView.findViewById(R.id.worker_description);
            worker_job_title = itemView.findViewById(R.id.worker_job_title);

            client_phone_btn = itemView.findViewById(R.id.call_btn);
            client_chat_btn = itemView.findViewById(R.id.chat_btn);
            client_map_btn = itemView.findViewById(R.id.mab_btn);

        }
    }


    // for whatsApp chat
    private boolean isAppInstalled(String url){

        PackageManager packageManager = context.getPackageManager();
        boolean appInstalled = false;
        try
        {
            packageManager.getPackageInfo(url,packageManager.GET_ACTIVITIES);
            appInstalled = true;
        }
        catch(PackageManager.NameNotFoundException e) {appInstalled = false;}

        return appInstalled;
    }

    private class GeoHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 1:
                    Bundle bundle = msg.getData();
                    address1 = bundle.getString("address");
                    break;
                default:
                    address1 = null;
            }
      //      Log.d("logtttttt",address1);
            String[] parts = address1.split(",,,");
             lat1 = parts[0];
             log1 = parts[1];
            clientLat = Double.parseDouble(lat1);
            clientLog = Double.parseDouble(log1);

        }
    }

    private class GeoHandler2 extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 1:
                    Bundle bundle = msg.getData();
                    address2 = bundle.getString("address");
                    break;
                default:
                    address2 = null;
            }
//            Log.d("log2222",address2);
            String[] parts2 = address2.split(",,,");
             lat2 = parts2[0];
             log2 = parts2[1];
            workerLat = Double.parseDouble(lat2);
            workerLog = Double.parseDouble(log2);
        }
    }
}