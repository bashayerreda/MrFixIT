package com.example.fixawy.Client.MakeOrder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.fixawy.Client.MakeOrder.pojos.OrderTree;
import com.example.fixawy.R;


public class FirstOrderFragment extends Fragment {
    private ClientMakeOrder clientMakeOrder;
    private FirstOrderViewModel viewModel;
    private EditText addDetails;
    String details;

    View view;
    RadioButton longworkbtn, shortworkbtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_first__order_, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        longworkbtn = view.findViewById(R.id.radio_long_work);
        shortworkbtn = view.findViewById(R.id.radio_short_work);
        addDetails = view.findViewById(R.id.edittext_view_addnotes);
        String phoneNum= getActivity().getIntent().getStringExtra("phone");
        String tokinid = getActivity().getIntent().getStringExtra("token");


        //MVVM
        viewModel = new ViewModelProvider(this).get(FirstOrderViewModel.class);
       /*viewModel.apilivedata.observe(getViewLifecycleOwner(), new Observer<OrderTree>() {
           @Override
           public void onChanged(OrderTree orderTree) {
               Toast.makeText(getActivity(), orderTree.getDetails(), Toast.LENGTH_LONG).show();
           }
       });*/
        if (getActivity() instanceof ClientMakeOrder) {
            clientMakeOrder = (ClientMakeOrder) getActivity();
        }

        clientMakeOrder.Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //indicator
                if (clientMakeOrder.current_state < (clientMakeOrder.DescriptionData.length - 1))
                    clientMakeOrder.current_state = clientMakeOrder.current_state + 1;
                clientMakeOrder.binding.stepIndicator.setCompletedPosition(clientMakeOrder.current_state).drawView(); //-------
                OrderTree orderTree = new OrderTree();
                if (longworkbtn.isChecked())
                    orderTree.setTypeOfOrder("Hard work");

                else if (shortworkbtn.isChecked()) {
                    orderTree.setTypeOfOrder("Maintenance and repair");
                }

                details = addDetails.getText().toString().trim();
                if (details.isEmpty()) {
                    addDetails.setError("You should add your description about your problem");
                    addDetails.requestFocus();
                    return;
                }

                orderTree.setDetails(addDetails.getText().toString());


                int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();
                Fragment current = getActivity().getSupportFragmentManager().getFragments().get(count > 0 ? count - 1 : count);
                if (current instanceof FirstOrderFragment) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Type", orderTree.getTypeOfOrder());
                    bundle.putString("Details", orderTree.getDetails());
                    replaceFragment(new SecondOrderFragment(), bundle);
                } else {
                    //ToDo
                }
            }

        });
    }

    private void replaceFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.first_fragment_order, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
}