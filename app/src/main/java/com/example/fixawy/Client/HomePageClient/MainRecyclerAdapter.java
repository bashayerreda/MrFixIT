package com.example.fixawy.Client.HomePageClient;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.fixawy.Pojos.Advertisment;
import com.example.fixawy.Pojos.AllCategory;
//import com.example.fixawy.Pojos.EmployeeData;
import com.example.fixawy.Pojos.EmployeeData;
import com.example.fixawy.Pojos.User;
import com.example.fixawy.R;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {

    private Context context;
   // AllCategoryNamesModel allCategoryNamesModel = new AllCategoryNamesModel();
    AdvertisementsModel advertisementsModel = new AdvertisementsModel();
    List<Advertisment> advertisments;
    List<AllCategory>allCategories;
    private List<AllCategory> allCategoryList;
    AllCategory allCategory;

    public MainRecyclerAdapter(Context context,List<AllCategory> allCategoryList) {

        this.context = context;
        this.allCategoryList = allCategoryList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recycler_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {

        //  setCategoryRecyclerView(holder.categoryRecycler,allCategoryNamesModel.getAllCategories());
        // setAdvertisement(holder.viewPager,advertisementsModel.getAllAdvertisement());


        allCategory=allCategoryList.get(position);
//        if(allCategory.getEmployeeDataList().isEmpty()){
//
//
//        }

        holder.categoryTitle.setText(allCategory.getCategoryTitle());
        //if(allCategoryList.get(position).getEmployeeDataList().size()!=0)
        setEmployeeRecyclerView(holder.empItemRecycler,allCategoryList.get(position).getEmployeeDataList());





    }

    @Override
    public int getItemCount() {
//        int count=0;
//
//            for (AllCategory item:allCategoryList) {
//            if(item.getEmployeeDataList()==null)continue;
//            if(!item.getEmployeeDataList().isEmpty())
//                count++;
//
//            }



        return allCategoryList.size();
        //return 2;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder{


        // RecyclerView categoryRecycler;
        // ViewPager viewPager;
        RecyclerView empItemRecycler;
        TextView categoryTitle;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);


            // categoryRecycler = itemView.findViewById(R.id.categoryrv);
            //viewPager = itemView.findViewById(R.id.advertisment_viewPager);
            empItemRecycler = itemView.findViewById(R.id.item_emp_recycler);
            categoryTitle = itemView.findViewById(R.id.cat_emp_title);

        }
    }

//    private void setCategoryRecyclerView(RecyclerView categoryRecyclerView,List<AllCategory>allCategories){
//        CategoryItemRecyclerAdapter categoryItemRecyclerAdapter = new CategoryItemRecyclerAdapter(context,allCategories);
//        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
//        categoryRecyclerView.setAdapter(categoryItemRecyclerAdapter);
//    }

//    private void setAdvertisement(ViewPager viewPager, List<Integer> adv){
//       AdvertismentAdapter advertismentAdapter = new AdvertismentAdapter(adv);
//       viewPager.setAdapter(advertismentAdapter);
//    }

    private void setEmployeeRecyclerView(RecyclerView employeeRecyclerView, List<User> allEmployeeData){
        EmployeeItemRecyclerAdapter employeeItemRecyclerAdapter = new EmployeeItemRecyclerAdapter(context,allEmployeeData);
        employeeRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        employeeRecyclerView.setAdapter(employeeItemRecyclerAdapter);

    }


}