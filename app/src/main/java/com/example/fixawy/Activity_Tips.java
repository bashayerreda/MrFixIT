package com.example.fixawy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.fixawy.Share.RegisterPage.RegisterActivity;
import com.example.fixawy.Share.SelectionPage.SelectMembershipType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Activity_Tips extends AppCompatActivity {
    private ViewPager mainViewPager;
    private LinearLayout mDotsLinearLayout;
    private TextView[] mDots;
    private Button previousBtn ;
    private Button nextBtn ;
    private int currentPage ;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tips);
        if (isOpenAlread())
        {
            Intent intent=new Intent(Activity_Tips.this,SelectMembershipType.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            SharedPreferences.Editor editor=getSharedPreferences("slide",MODE_PRIVATE).edit();
            editor.putBoolean("slide",true);
            editor.commit();
        }



            mainViewPager = (ViewPager) findViewById(R.id.view_pager_id);
            mDotsLinearLayout = (LinearLayout) findViewById(R.id.linear_layout_id);
            previousBtn = (Button) findViewById(R.id.button_previous);
            nextBtn = (Button) findViewById(R.id.button_next);
            SliderAdapter sliderAdapter = new SliderAdapter(this);
            mainViewPager.setAdapter(sliderAdapter);
            addDotsIndicator(0);
            mainViewPager.addOnPageChangeListener(viewlistner);
            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainViewPager.setCurrentItem(currentPage + 1);
                }
            });
            previousBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainViewPager.setCurrentItem(currentPage - 1);
                }
            });
        }







    public void addDotsIndicator(int position){
        mDots = new TextView[3];
        mDotsLinearLayout.removeAllViews();
        for(int i =0 ; i < mDots.length ; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.white));
            mDotsLinearLayout.addView(mDots[i]);
        }
        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.white));
        }

    }
    ViewPager.OnPageChangeListener viewlistner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            currentPage= i;
            if(i==0){
                nextBtn.setEnabled(true);
                previousBtn.setEnabled(false);
                previousBtn.setVisibility(View.INVISIBLE);
                nextBtn.setText("Next");
                previousBtn.setText("");
            }
            else if (i == mDots.length -1){
                nextBtn.setEnabled(true);
                previousBtn.setEnabled(true);
                previousBtn.setVisibility(View.VISIBLE);
                nextBtn.setText("finish");
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      {

                      Intent intent = new Intent(Activity_Tips.this,SelectMembershipType.class);
                      startActivity(intent);
                      }
                    }
                });
                previousBtn.setText("previous");
            } else {
                nextBtn.setEnabled(true);
                previousBtn.setEnabled(true);
                previousBtn.setVisibility(View.VISIBLE);
                nextBtn.setText("Next");
                previousBtn.setText("previous");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };




    private boolean isOpenAlread() {

        SharedPreferences sharedPreferences=getSharedPreferences("slide",MODE_PRIVATE);
        boolean result=sharedPreferences.getBoolean("slide",false);
        return result;

    }
}





