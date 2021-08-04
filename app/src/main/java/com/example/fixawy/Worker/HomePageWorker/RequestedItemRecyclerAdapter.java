package com.example.fixawy.Worker.HomePageWorker;

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

import com.example.fixawy.Client.HomePageClient.CategoryItemRecyclerAdapter;
import com.example.fixawy.Client.MakeOrder.pojos.OrderTree;
import com.example.fixawy.Client.RequestedPage.RequestedAdapter;
import com.example.fixawy.Pojos.MakeOrder;
import com.example.fixawy.R;

import java.util.List;

public class RequestedItemRecyclerAdapter extends RecyclerView.Adapter<RequestedItemRecyclerAdapter.RequestedItemRecyclerViewHolder> {
    private Context context;
    List<OrderTree>makeOrders;
    private onItemClickListener mListener;




    public RequestedItemRecyclerAdapter(Context context, List<OrderTree> makeOrders) {
        this.context = context;
        this.makeOrders = makeOrders;
    }

    public void setListMakeOrders(List<OrderTree> makeOrderList) {
        this.makeOrders = makeOrderList;
    }
    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public RequestedItemRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.requested_worker_item,null,false);
        RequestedItemRecyclerAdapter.RequestedItemRecyclerViewHolder viewHolder = new RequestedItemRecyclerAdapter.RequestedItemRecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestedItemRecyclerViewHolder holder, int position) {


        holder.worker_clocktv.setText(makeOrders.get(position).getTime());
        holder.worker_datetv.setText(makeOrders.get(position).getDate());
        holder.worker_name_clienttv.setText(makeOrders.get(position).getUserName());
        holder.worker_kindjobtv.setText(makeOrders.get(position).getTypeOfOrder());
        holder.worker_address_clientv.setText(makeOrders.get(position).getLocation());
        holder.worker_phone_client.setText(makeOrders.get(position).getPhone());


        //make call
//        holder.worker_phone_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String mobileNumber =  makeOrders.get(position).getRequestedPhone();
//                String call = "tel:" +mobileNumber.trim();
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse(call));
//                context.startActivity(intent);
//            }
//        });

        //open whatsApp chat
//        holder.worker_chat_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String mobileNumber =  makeOrders.get(position).getRequestedPhone();
//                String whatsapp_URL = "http://api.whatsapp.com/send?phone=";
//                boolean Installed = isAppInstalled("com.whatsapp");
//                if(Installed)
//                {
//                    Intent whatsapp_intent = new Intent(Intent.ACTION_VIEW);
//                    whatsapp_intent.setData(Uri.parse(whatsapp_URL+"02"+mobileNumber));
//                    context.startActivity(whatsapp_intent);
//                }
//                else{
//                    Toast.makeText(context,"whatsapp is not installed",Toast.LENGTH_SHORT).show();}
//            }
//        });


    }

    @Override
    public int getItemCount() {
        if(makeOrders == null){
            return 0;
        } else {
            return makeOrders.size();
        }
    }

    public class RequestedItemRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView worker_clocktv,worker_datetv,worker_name_clienttv,worker_kindjobtv,worker_address_clientv,worker_phone_client;
        Button worker_phone_btn,worker_chat_btn;
        public RequestedItemRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            worker_clocktv = itemView.findViewById(R.id.worker_requested_clock);
            worker_datetv = itemView.findViewById(R.id.worker_requested_date);
            worker_name_clienttv = itemView.findViewById(R.id.worker_name_of_client);
            worker_kindjobtv = itemView.findViewById(R.id.worker_requsted_kind_job);
            worker_address_clientv = itemView.findViewById(R.id.worker_requsted_address);
            worker_phone_client = itemView.findViewById(R.id.worker_requsted_phone);

            worker_phone_btn = itemView.findViewById(R.id.worker_request_call_button);
            worker_chat_btn = itemView.findViewById(R.id.worker_request_chat_button);


            //toClick on the item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });


        }
    }



    public interface onItemClickListener{
        void onItemClick(int position);

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


