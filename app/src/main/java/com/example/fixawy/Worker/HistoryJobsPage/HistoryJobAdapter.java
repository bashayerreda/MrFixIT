package com.example.fixawy.Worker.HistoryJobsPage;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixawy.Client.RequestedPage.onitemclick;
import com.example.fixawy.Pojos.HistoryWorker;
import com.example.fixawy.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class HistoryJobAdapter extends RecyclerView.Adapter<HistoryJobAdapter.HistoryJobsItemViewHolder> {
    List<HistoryWorker>historyWorkerList;
    Context context;
    AlertDialog alertDialog;
    LayoutInflater inflater;
    Button btnDelete,btnCancel;
    onitemclick onItemClick;

    FirebaseDatabase db ;
    DatabaseReference reference;

    public HistoryJobAdapter(Context context, List<HistoryWorker> historyWorkerList) {
        this.context = context;
        this.historyWorkerList = historyWorkerList;
    }

    @NonNull
    @Override
    public HistoryJobAdapter.HistoryJobsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item,null,false);
        HistoryJobAdapter.HistoryJobsItemViewHolder viewHolder = new HistoryJobAdapter.HistoryJobsItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryJobAdapter.HistoryJobsItemViewHolder holder,final int position) {
        String jobTitle = HistoryJobActivity.worker_job_title;
        String phoneWorker = HistoryJobActivity.worker_phone;
        String phoneClient = historyWorkerList.get(position).getPhone();

        holder.textViewClock.setText(historyWorkerList.get(position).getTime());
        holder.textViewDate.setText(historyWorkerList.get(position).getDate());
        holder.textViewUserAddress.setText(historyWorkerList.get(position).getAddress());
        holder.textViewUserName.setText(historyWorkerList.get(position).getName());
        holder.textViewUserPhone.setText(historyWorkerList.get(position).getPhone());
//        holder.ratingBar.setRating(historyWorkerList.get(position).getRate());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = new AlertDialog.Builder(v.getContext()).create();
                inflater = LayoutInflater.from(v.getContext());
                View dialogView = inflater.inflate(R.layout.delete_dialog, null);
                btnDelete= dialogView.findViewById(R.id.btnDelete);
                btnCancel=dialogView.findViewById(R.id.btnCancel);
                db = FirebaseDatabase.getInstance();
                reference = db.getReference("Worker");
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        onItemClick.onclick(position,1);
//                        notifyDataSetChanged();
//                        Toast.makeText(v.getContext(), "delete", Toast.LENGTH_SHORT).show();

                        historyWorkerList.remove(position);
                        notifyDataSetChanged();
                        reference = FirebaseDatabase.getInstance().getReference().child("Worker").child(jobTitle).child("Data");
                        reference.child(phoneWorker).child("HistoryWorker").child(phoneClient).setValue(null);


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
        if(historyWorkerList == null){
            return 0;
        } else {
            return historyWorkerList.size();
        }
    }

    public class HistoryJobsItemViewHolder extends RecyclerView.ViewHolder {
        TextView textViewClock,textViewDate,textViewUserName,textViewUserAddress,textViewUserPhone;
        Button deleteBtn;
        RatingBar ratingBar;
        public HistoryJobsItemViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewClock=itemView.findViewById(R.id.history_worker_clock);
            textViewDate=itemView.findViewById(R.id.history_worker_date);
            textViewUserName=itemView.findViewById(R.id.history_user_name);
            textViewUserAddress=itemView.findViewById(R.id.history_user_address);
            textViewUserPhone=itemView.findViewById(R.id.history_user_phone);
           // ratingBar = itemView.findViewById(R.id.ratingBar_history);

            deleteBtn = itemView.findViewById(R.id.delete);

        }
    }
}