package com.example.fixawy.Client.HomePageClient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixawy.Pojos.AllCategory;
import com.example.fixawy.R;

import java.util.List;

public class CategoryItemRecyclerAdapter extends RecyclerView.Adapter<CategoryItemRecyclerAdapter.CategoryItemViewHolder> {

    //private onItemClickListener mListener;
    private Context context;
    private  OnItemClick onItemClick;
    public void setCategoryItemList(List<AllCategory> categoryItemList) {
        this.categoryItemList = categoryItemList;
    }

    /*public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }*/

    private List<AllCategory> categoryItemList;

    public CategoryItemRecyclerAdapter(Context context, List<AllCategory> categoryItemList, OnItemClick onItemClick) {
        this.context = context;
        this.categoryItemList = categoryItemList;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public CategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryItemViewHolder(LayoutInflater.from(context).inflate(R.layout.category_row_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemViewHolder holder, int position) {

        holder.categoryNametv.setText(categoryItemList.get(position).getCategoryTitle());
        holder.categoryNametv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(categoryItemList == null){
            return 0;
        } else {
            return categoryItemList.size();
        }
    }

    public class CategoryItemViewHolder extends RecyclerView.ViewHolder{

        Button categoryNametv;

        public CategoryItemViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryNametv = itemView.findViewById(R.id.categoryNameTextView);


            //toClick on the item
            /*categoryNametv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });*/

        }
    }


    /*public interface onItemClickListener{
        void onItemClick(int position);
    }*/


}
