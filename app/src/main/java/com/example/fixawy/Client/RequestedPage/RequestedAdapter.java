package com.example.fixawy.Client.RequestedPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixawy.Client.HomePageClient.OnItemClick;
import com.example.fixawy.Client.MakeOrder.Repo.ClientOrderRepo;
import com.example.fixawy.Client.MakeOrder.pojos.OrderTree;
import com.example.fixawy.Pojos.ClientHistory;
import com.example.fixawy.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RequestedAdapter extends RecyclerView.Adapter<RequestedAdapter.RequestedItemViewHolder> {
     Context context;
    List<OrderTree> orderTreeItems;
   onitemclick onItemClick;
    RequestedPageViewModel requestedPageViewModel;
     OrderTree orderTree;
    DatabaseReference reference;
    FirebaseDatabase db ;
    List<String>uIds;
    AlertDialog alertDialog;
    LayoutInflater inflater;
    Button btnDelete,btnCancel;
    RequestedActivity requestedActivity;

    DatabaseReference database;
    DatabaseReference database2;

    public RequestedAdapter(Context context, List<OrderTree> orderTreeItems,onitemclick onItemClick) {
        this.context = context;
        this.orderTreeItems = orderTreeItems;
        this.onItemClick = onItemClick;
    }

    public RequestedAdapter(RequestedActivity requestedActivity, List<String> uIds) {
        this.context = requestedActivity;
        this.uIds = uIds;
    }


    @NonNull
    @Override
    public RequestedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.requested_item, null, false);
        RequestedItemViewHolder viewHolder = new RequestedItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestedItemViewHolder holder, int position) {
        String my_select_job =  orderTreeItems.get(position).getJobTitle();
        String my_phone = orderTreeItems.get(position).getRequestedPhone();

        holder.jobTitle.setText(orderTreeItems.get(position).getJobTitle());
        holder.typeOfOrder.setText(orderTreeItems.get(position).getTypeOfOrder());
        holder.desc.setText(orderTreeItems.get(position).getDetails());
        holder.location.setText(orderTreeItems.get(position).getLocation());
        holder.phone.setText(orderTreeItems.get(position).getPhone());
        holder.username.setText(orderTreeItems.get(position).getUserName());
        holder.time.setText(orderTreeItems.get(position).getTime());
        holder.date.setText(orderTreeItems.get(position).getDate());
       // holder.anotherphone.setText(orderTreeItems.get(position).getRequestedPhone());
        holder.payment.setText(orderTreeItems.get(position).getPaymentMethod());
        holder.request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onItemClick.onclick(position,0);
            }
        });
        db = FirebaseDatabase.getInstance();
        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = new AlertDialog.Builder(v.getContext()).create();
                inflater = LayoutInflater.from(v.getContext());
                View dialogView = inflater.inflate(R.layout.delete_dialog, null);
                btnDelete= dialogView.findViewById(R.id.btnDelete);
                btnCancel=dialogView.findViewById(R.id.btnCancel);
                db = FirebaseDatabase.getInstance();
                reference = db.getReference("Client");
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      onItemClick.onclick(position,1);
                        //requestedActivity = new RequestedActivity();
                        //String my_phone = requestedActivity.phoneNum;
                        //database = FirebaseDatabase.getInstance().getReference("Client").child("make order").child(my_phone).child(my_select_job);
                        //database2 = FirebaseDatabase.getInstance().getReference("Worker").child(my_select_job).child("order Details").child(my_phone);
                        //database.removeValue();
                        //database2.removeValue();
                        //alertDialog.cancel();

                      alertDialog.cancel();

                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        alertDialog.cancel();
                    }
                });
                alertDialog.setView(dialogView);
                alertDialog.show();

            }
        });


    }

    @Override
    public int getItemCount() {

        return orderTreeItems.size();
    }

    class RequestedItemViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitle, typeOfOrder, location, phone, date, time,username,desc,anotherphone ,payment;
        Button request_btn,delete_btn;
        public RequestedItemViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.requested_job_title);
            typeOfOrder = itemView.findViewById(R.id.requsted_kind_job);
            location = itemView.findViewById(R.id.requsted_address);
            username = itemView.findViewById(R.id.requested_user_name);
            desc= itemView.findViewById(R.id.requested_desc);
            phone = itemView.findViewById(R.id.requsted_phone);
            date = itemView.findViewById(R.id.requested_date);
            time = itemView.findViewById(R.id.requested_clock);
            request_btn = itemView.findViewById(R.id.request_edit_button);
            delete_btn = itemView.findViewById(R.id.request_delete_button);
            //anotherphone= itemView.findViewById(R.id.another_requsted_phone);
            payment = itemView.findViewById(R.id.requested_payment);
        }

    }


}
