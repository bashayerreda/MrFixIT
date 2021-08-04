package com.example.fixawy.Client.HomePageClient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.fixawy.Pojos.EmployeeData;
import com.example.fixawy.Pojos.User;
import com.example.fixawy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EmployeeItemRecyclerAdapter extends RecyclerView.Adapter<EmployeeItemRecyclerAdapter.EmployeeItemViewHolder> {

    private Context context;
    private List<User> employeeDataItemList;

    public EmployeeItemRecyclerAdapter(Context context, List<User> employeeDataItemList) {
        this.context = context;
        this.employeeDataItemList = employeeDataItemList;
    }
    @NonNull
    @Override
    public EmployeeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EmployeeItemViewHolder(LayoutInflater.from(context).inflate(R.layout.employee_row_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeItemViewHolder holder, int position) {
        int rate = employeeDataItemList.get(position).getRating();
        holder.empNameTv.setText(employeeDataItemList.get(position).getUserName());
        holder.empAddTv.setText(employeeDataItemList.get(position).getAddress());
        holder.empPhoneTv.setText(employeeDataItemList.get(position).getPhone());
        Picasso.get().load(employeeDataItemList.get(position).getImage()).placeholder(R.drawable.person).into(holder.imageView);

        holder.ratingBar.setRating(employeeDataItemList.get(position).getRating());
        //workerRatingBarProfile.setRating(Float.parseFloat(rate));

       // holder.ratingBar.setText(employeeDataItemList.get(position).getEmp_rate());


    }

    @Override
    public int getItemCount() {
        if(employeeDataItemList == null){
            return 0;
        } else {
            return employeeDataItemList.size();
        }
    }

    public class EmployeeItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView empNameTv;
        private TextView empAddTv;
        private TextView empPhoneTv;
        private RatingBar ratingBar;
        public EmployeeItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.emp_img);
            empNameTv = itemView.findViewById(R.id.emp_name);
            empAddTv = itemView.findViewById(R.id.emp_address);
            empPhoneTv = itemView.findViewById(R.id.emp_phone);
            ratingBar = itemView.findViewById(R.id.emp_rate);
        }
    }
}