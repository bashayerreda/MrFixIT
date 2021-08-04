package com.example.fixawy.ShopOwner.ShowProducts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixawy.Pojos.Product;
import com.example.fixawy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowProductAdapter extends RecyclerView.Adapter<ShowProductAdapter.ProductItemViewHolder> {

     ArrayList<Product> products = new ArrayList<>();
     Context context;
     String phone,shopName,shopType;

     public void clear() {
         products.clear();
     }


     public void add(Product product) {
         products.add(product);
         notifyDataSetChanged();
     }

     public ShowProductAdapter(ShowProductsActivity showProductsActivity, String phone, String shopName, String shopType) {
         this.context = showProductsActivity;
         this.phone = phone;
         this.shopName = shopName;
         this.shopType = shopType;

     }

     @NonNull
     @Override
     public ProductItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, null, false);
         ProductItemViewHolder viewHolder = new ProductItemViewHolder(v);
         return viewHolder;
     }

     @Override
     public void onBindViewHolder(@NonNull ProductItemViewHolder holder, int position) {
         holder.textViewProductName.setText(products.get(position).getProductName());
         holder.textViewProductPrice.setText(products.get(position).getProductPrice()+" LE");
         holder.textViewProductDesc.setText(products.get(position).getProductDesc());
         Picasso.get().load(products.get(position).getProductImage()).into(holder.imageViewProduct);

         holder.layout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(v.getContext(),phone, Toast.LENGTH_SHORT).show();
                 Toast.makeText(v.getContext(), shopName, Toast.LENGTH_SHORT).show();
                 Toast.makeText(v.getContext(),products.get(position).getProductName(), Toast.LENGTH_SHORT).show();
             }
         });

     }

     @Override
     public int getItemCount() {
         return products.size();
     }

     public class ProductItemViewHolder extends RecyclerView.ViewHolder {
         TextView textViewProductName, textViewProductPrice,textViewProductDesc;
         ImageView imageViewProduct;
         public View layout;

         public ProductItemViewHolder(@NonNull View itemView) {
             super(itemView);
             layout = itemView;
             textViewProductName = itemView.findViewById(R.id.nameOfProduct);
             textViewProductPrice = itemView.findViewById(R.id.priceOfProduct);
             textViewProductDesc = itemView.findViewById(R.id.productDesc);
             imageViewProduct = itemView.findViewById(R.id.imageProduct);

         }
     }
 }
