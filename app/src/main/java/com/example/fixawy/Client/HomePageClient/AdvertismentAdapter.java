package com.example.fixawy.Client.HomePageClient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.fixawy.Pojos.Advertisment;
import com.example.fixawy.R;

import java.util.List;

public class AdvertismentAdapter extends PagerAdapter {

    List<Integer> list;
    AdvertismentAdapter(List<Integer> imageList){
        this.list=imageList;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.image_layout,container,false);
        ImageView imageView =view.findViewById(R.id.image_view_slider);
        imageView.setImageResource(list.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
