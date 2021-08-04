package com.example.fixawy.Client.MakeOrder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fixawy.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetailsActivity extends AppCompatActivity {
    TextView txtId , txtAmount , txtStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        txtId = findViewById(R.id.textView_id);
        txtAmount = findViewById(R.id.textView_amount);
        txtStatus = findViewById(R.id.textView_status);

        Intent intent = getIntent();
        try {
            JSONObject jsonObject =  new JSONObject(intent.getStringExtra("payment Details"));
            showDetails(jsonObject.getJSONObject("response"), intent.getStringExtra("payment amount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showDetails(JSONObject response, String payment_amount) {
        try {
            txtId.setText(response.getString("id"));
            txtAmount.setText(response.getString(String.format("$%s",payment_amount)));
            txtStatus.setText(response.getString("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}