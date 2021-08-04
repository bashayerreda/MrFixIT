package com.example.fixawy.Client.MakeOrder;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fixawy.Client.MakeOrder.configpaypal.Config;
import com.example.fixawy.Client.MakeOrder.pojos.OrderTree;
import com.example.fixawy.Client.RequestedPage.RequestedActivity;
import com.example.fixawy.Pojos.Questions;
import com.example.fixawy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class ThirdOrderFragment extends Fragment {
    public static final int PAYPAL_REQUIRED_CODE = 7171;
    View view;
    String amount = "";
    RadioButton cash, payPalBtn, creditCard;
    ClientMakeOrder clientMakeOrder;
    OrderTree orderTree;
    ThirdOrderViewModel thirdOrderViewModel;
    EditText username;
    String categoryType;
    String phoneNum;
    String tokinid;
    String valName;
    String getJob;
    List<String>myListJob;
    Dialog dialog;
    DatabaseReference ref;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.payPalKey);

    @Override
    public void onDestroy() {
        getActivity().stopService(new Intent(getActivity(), PayPalService.class));
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getActivity().startService(intent);

        phoneNum = getActivity().getIntent().getStringExtra("phone");
        tokinid = getActivity().getIntent().getStringExtra("token");
        categoryType = getActivity().getIntent().getStringExtra("CategoryType");




        dialog = new Dialog(getContext());
        myListJob = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("Client").child("make order").child(phoneNum).child(categoryType).child("order Details");
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterable<DataSnapshot> children = task.getResult().getChildren();
                for (DataSnapshot snapshot : children) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Map<String,Object> map = (Map<String, Object>) snapshot.getValue();
                        //orderTree = dataSnapshot.getValue(OrderTree.class);
                        getJob = (String) map.get("jobTitle");
                        Log.d("thhhhhhhh",getJob);
                        myListJob.add(getJob);
                    }

                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_third__order_, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        payPalBtn = view.findViewById(R.id.radio_pay_pal);
        cash = view.findViewById(R.id.radio_cash);
        creditCard = view.findViewById(R.id.radio_credit_card);
        username=view.findViewById(R.id.enter_your_name);
        orderTree = new OrderTree();
        orderTree.setLocation(getArguments().getString("Client location"));
        orderTree.setPhone(getArguments().getString("Client phone"));
        orderTree.setDate(getArguments().getString("Order Date"));
        orderTree.setTime(getArguments().getString("Order time"));
        orderTree.setDetails(getArguments().getString("Details"));
        orderTree.setTypeOfOrder(getArguments().getString("Type"));
        phoneNum = getActivity().getIntent().getStringExtra("phone");
//        tokinid = getActivity().getIntent().getStringExtra("token");
     categoryType = getActivity().getIntent().getStringExtra("CategoryType");
        thirdOrderViewModel = new ViewModelProvider(this).get(ThirdOrderViewModel.class);
        clientMakeOrder = (ClientMakeOrder) getActivity();

        clientMakeOrder.Next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(myListJob.contains(categoryType)){
                    openDialog();
                    //Toast.makeText(getContext(), "You already make an order of this job", Toast.LENGTH_SHORT).show();
                }else {
                    if (payPalBtn.isChecked()) {
                        orderTree.setPaymentMethod("paypal");
                        orderTree.setJobTitle(categoryType);
                        orderTree.setRequestedPhone(phoneNum);
                        orderTree.setTokenid(tokinid);
                        orderTree.setUserName(username.getText().toString());
                        valName = username.getText().toString().trim();
                        if (valName.isEmpty()) {
                            username.setError("Enter the name");
                            username.requestFocus();
                            return;
                        }
                        thirdOrderViewModel.addData(orderTree, phoneNum, categoryType);
                        thirdOrderViewModel.addDataToWorker(orderTree, categoryType, phoneNum);
                        processPayment();
                    } else if (creditCard.isChecked()) {
                        orderTree.setPaymentMethod("creditCard");
                        orderTree.setJobTitle(categoryType);
                        orderTree.setRequestedPhone(phoneNum);
                        orderTree.setTokenid(tokinid);
                        orderTree.setUserName(username.getText().toString());
                        valName = username.getText().toString().trim();
                        if (valName.isEmpty()) {
                            username.setError("Enter the name");
                            username.requestFocus();
                            return;
                        }
                        thirdOrderViewModel.addData(orderTree, phoneNum, categoryType);
                        thirdOrderViewModel.addDataToWorker(orderTree, categoryType, phoneNum);
                        Intent intent = new Intent(getActivity(), CreditCardActivity.class);
                        intent.putExtra("phone", phoneNum);
                        intent.putExtra("CategoryType", categoryType);
                        startActivity(intent);
                        getActivity().finish();
                    } else if (cash.isChecked()) {
                        orderTree.setPaymentMethod("cash");
                        orderTree.setJobTitle(categoryType);
                        orderTree.setRequestedPhone(phoneNum);
                        orderTree.setUserName(username.getText().toString());
                        orderTree.setTokenid(tokinid);
                        valName = username.getText().toString().trim();
                        if (valName.isEmpty()) {
                            username.setError("Enter the name");
                            username.requestFocus();
                            return;
                        }
                        thirdOrderViewModel.addData(orderTree, phoneNum, categoryType);
                        thirdOrderViewModel.addDataToWorker(orderTree, categoryType, phoneNum);
                        Intent intent = new Intent(getContext(), RequestedActivity.class);
                        intent.putExtra("phone", phoneNum);
                        intent.putExtra("CategoryType", categoryType);
                        startActivity(intent);
                        getActivity().finish();
                    }


                }
            }
        });



    }

    //create the dialog
    private void openDialog() {


        dialog.setContentView(R.layout.wrong_dialog);

        dialog.show();

    }

    private void processPayment() {
        //TODO //amount = textpay.getText().toString();
        //TODO //  PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"USD","To RepairIt app",PayPalPayment.PAYMENT_INTENT_SALE);
        //TODO add EditText and button to determine amount of money
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf("10")), "USD", "To RepairIt app", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(getActivity(), com.paypal.android.sdk.payments.PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        getActivity().startActivityForResult(intent, PAYPAL_REQUIRED_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PAYPAL_REQUIRED_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation paymentConfirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (paymentConfirmation != null) {
                    try {
                        String paymentDetails = paymentConfirmation.toJSONObject().toString(4);
                        getActivity().startActivity(new Intent(getActivity(), PaymentDetailsActivity.class)
                                .putExtra("payment Details", paymentDetails)
                                .putExtra("payment amount", amount));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                Toast.makeText(getContext(), "cancel", Toast.LENGTH_LONG).show();

            }

        }
    }



}