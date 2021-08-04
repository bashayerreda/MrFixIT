package com.example.fixawy.Client.MakeOrder;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fixawy.R;
import com.example.fixawy.databinding.ActivityClientMakeOrderBinding;

public class ClientMakeOrder extends AppCompatActivity {
    Button Next;
    private long backPressedTime;
    ActivityClientMakeOrderBinding binding;
    String[] DescriptionData = {"", "", ""};
    int current_state = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new FirstOrderFragment());
        binding = ActivityClientMakeOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.stepIndicator.setLabels(DescriptionData)
                .setBarColorIndicator(Color.BLACK)
                .setProgressColorIndicator(getResources().getColor(R.color.white))
                .setLabelColorIndicator(getResources().getColor(R.color.colorPurple))
                .setCompletedPosition(0)
                .drawView();
        binding.stepIndicator.setCompletedPosition(current_state);
        Next = findViewById(R.id.Next_btn);


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (current_state < (DescriptionData.length - 1))
                    current_state = current_state + 1;
                binding.stepIndicator.setCompletedPosition(current_state).drawView();
                int count = getSupportFragmentManager().getBackStackEntryCount();
                Fragment current = getSupportFragmentManager().getFragments().get(count > 0 ? count - 1 : count);
                if (current instanceof FirstOrderFragment) {
                    replaceFragment(new SecondOrderFragment());
                } else if (current instanceof SecondOrderFragment) {
                    replaceFragment(new ThirdOrderFragment());
                    Next.setText("finish");
                } else {
                    //ToDo
                }
            }
        });

    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.first_fragment_order, fragment).addToBackStack(null);
        fragmentTransaction.commit();

    }



}