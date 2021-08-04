package com.example.fixawy.Client.ShowProductsOfShopType;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixawy.Pojos.Product;
import com.example.fixawy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowProductsOfShopTypeAdapter extends RecyclerView.Adapter<ShowProductsOfShopTypeAdapter.ProductItemViewHolder> {

    ArrayList<Product> products = new ArrayList<>();
    private Context context;


    public void clear() {
        products.clear();
    }


    public void add(Product product) {
        products.add(product);
        notifyDataSetChanged();
    }

    public ShowProductsOfShopTypeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ProductItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_client_item, null, false);
        ProductItemViewHolder viewHolder = new ProductItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemViewHolder holder, int position) {
        holder.textViewProductName.setText(products.get(position).getProductName());
        holder.textViewProductPrice.setText(products.get(position).getProductPrice()+" LE");
        holder.textViewProductDesc.setText(products.get(position).getProductDesc());
        holder.textViewShopName.setText(products.get(position).getShopName());
        Picasso.get().load(products.get(position).getProductImage()).into(holder.imageViewProduct);

        holder.buttonCallShopOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNumber =  products.get(position).getPhoneOfShopOwner();
                String call = "tel:" +mobileNumber.trim();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(call));
                context.startActivity(intent);
            }
        });

        holder.buttonChatShopOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNumber =  products.get(position).getPhoneOfShopOwner();
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

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), products.get(position).getShopName(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductItemViewHolder extends RecyclerView.ViewHolder {
        TextView textViewProductName, textViewProductPrice,textViewProductDesc,textViewShopName;
        ImageView imageViewProduct;
        Button buttonCallShopOwner,buttonChatShopOwner;
        public View layout;

        public ProductItemViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            textViewProductName = itemView.findViewById(R.id.nameOfProduct);
            textViewProductPrice = itemView.findViewById(R.id.priceOfProduct);
            textViewProductDesc = itemView.findViewById(R.id.productDesc);
            textViewShopName = itemView.findViewById(R.id.shopName);
            imageViewProduct = itemView.findViewById(R.id.imageProduct);
            buttonCallShopOwner = itemView.findViewById(R.id.call_button);
            buttonChatShopOwner = itemView.findViewById(R.id.chat_button);


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
