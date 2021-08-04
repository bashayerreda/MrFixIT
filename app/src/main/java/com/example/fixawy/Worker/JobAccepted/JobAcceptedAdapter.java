package com.example.fixawy.Worker.JobAccepted;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixawy.Pojos.JobAccepted;
import com.example.fixawy.R;

import java.util.List;

public class JobAcceptedAdapter extends RecyclerView.Adapter<JobAcceptedAdapter.JobAcceptedItemRecyclerViewHolder> {
   private Context context;
    List<JobAccepted> jobAccepteds;

    public JobAcceptedAdapter(Context context, List<JobAccepted> jobAccepteds) {
        this.context = context;
        this.jobAccepteds = jobAccepteds;
    }
    @NonNull
    @Override
    public JobAcceptedItemRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_accepted_item,null,false);
        JobAcceptedItemRecyclerViewHolder viewHolder = new JobAcceptedItemRecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull JobAcceptedItemRecyclerViewHolder holder, int position) {

        holder.worker_accept_job_date.setText(jobAccepteds.get(position).getTime());
        holder.worker_accept_job_time.setText(jobAccepteds.get(position).getDate());
        holder.worker_accept_job_name_of_user.setText(jobAccepteds.get(position).getUserName());
        holder.worker_accept_job_address_of_user.setText(jobAccepteds.get(position).getLocation());
        holder.worker_accept_job_phone_of_user.setText(jobAccepteds.get(position).getPhone());


        //make call
        holder.worker_accept_phone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNumber =  jobAccepteds.get(position).getPhone();
                String call = "tel:" +mobileNumber.trim();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(call));
                context.startActivity(intent);
            }
        });

        //open whatsApp chat
        holder.worker_accept_chat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNumber =  jobAccepteds.get(position).getPhone();
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

    @Override
    public int getItemCount() {
        if(jobAccepteds == null){
            return 0;
        } else {
            return jobAccepteds.size();
        }
    }

    public class JobAcceptedItemRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView worker_accept_job_date;
        TextView worker_accept_job_time;
        TextView worker_accept_job_name_of_user;
        TextView worker_accept_job_address_of_user;
        TextView worker_accept_job_phone_of_user;

        Button worker_accept_phone_btn,worker_accept_chat_btn;
        public JobAcceptedItemRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            worker_accept_job_date = itemView.findViewById(R.id.worker_Accepted_Job_clock);
            worker_accept_job_time = itemView.findViewById(R.id.worker_Accepte_Job_date);
            worker_accept_job_name_of_user = itemView.findViewById(R.id.worker_Accepted_Job_name_of_client);
            worker_accept_job_address_of_user = itemView.findViewById(R.id.worker_Accepted_Job_requsted_address);
            worker_accept_job_phone_of_user = itemView.findViewById(R.id.worker_requsted_Accepted_Job_phone);

            worker_accept_phone_btn = itemView.findViewById(R.id.worker_request_Accepted_Job_call_button);
            worker_accept_chat_btn = itemView.findViewById(R.id.worker_Accepted_Job_request_chat_button);
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
}
