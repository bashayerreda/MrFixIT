package com.example.fixawy.Client.EditPage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.fixawy.Client.MakeOrder.FirstOrderViewModel;
import com.example.fixawy.Client.MakeOrder.pojos.OrderTree;
import com.example.fixawy.Client.RequestedPage.RequestedActivity;
import com.example.fixawy.R;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class EditActivity extends AppCompatActivity {
EditActivityViewModel editActivityViewModel;
EditText editDetails,editLocation,editNumber,editAnotherPhone;
TextView time,date,jobtitle,username;
RadioButton editTypeofOrderHard , editTypeofOrderEasy,paymentCash,paymentCreditCard,paymentPayPal;
RadioGroup rg;
ImageView timePicker, datePicker;
String phoneNum;
String categoryType;
String uid;
String jobTitle;
String tokenid;
Button updates;
OrderTree orderTree;
int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        phoneNum = getIntent().getStringExtra("phone");
        categoryType = getIntent().getExtras().getString("CategoryType");
        uid = getIntent().getExtras().getString("uid");
        jobTitle =  getIntent().getExtras().getString("job Title");
        tokenid = getIntent().getExtras().getString("tokenid");
        editNumber = findViewById(R.id.edit_phone_modified);
       editDetails = findViewById(R.id.edittext_view_addnotes);
       editLocation= findViewById(R.id.edit_location);
       editTypeofOrderHard = findViewById(R.id.radio_edit_long_work);
       editTypeofOrderEasy = findViewById(R.id.radio_edit_short_work);
       paymentCash =findViewById(R.id.radio_edit_cash);
       paymentCreditCard = findViewById(R.id.radio_edit_credit_card);
       paymentPayPal= findViewById(R.id.radio_edit_pay_pal);
       time = findViewById(R.id.edit_time);
       date = findViewById(R.id.edit_date);
       jobtitle = findViewById(R.id.jobt_title_Edit);
       timePicker = findViewById(R.id.time_picker);
       datePicker = findViewById(R.id.date_picker);
       rg = findViewById(R.id.radio_gp_edit_order);
       updates = findViewById(R.id.edit_make_order);
       username = findViewById(R.id.user_name_Edit);
       editAnotherPhone= findViewById(R.id.edit_another_phone_modified);
        editTypeofOrderEasy.setText("Maintenance and repair");
        editTypeofOrderHard.setText("Hard work");
        paymentCash.setText("cash");
        paymentPayPal.setText("paypal");
        paymentCreditCard.setText("creditCard");

        Places.initialize(this.getApplicationContext(), getString(R.string.api_key));
       editLocation.setOnClickListener(v -> {
            List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(this);
            startActivityForResult(intent, 1);
        });

        editActivityViewModel = new ViewModelProvider(this).get(EditActivityViewModel.class);

       editActivityViewModel.editActivityLiveData.observe(this, new Observer<OrderTree>() {

           @Override
           public void onChanged(OrderTree orderTree) {
               editDetails.setText(orderTree.getDetails());
               editLocation.setText(orderTree.getLocation());
               editNumber.setText(orderTree.getPhone());
               jobtitle.setText(orderTree.getJobTitle());
               //editTypeofOrderEasy.setText(orderTree.getTypeOfOrder());
               //editTypeofOrderHard.setText(orderTree.getTypeOfOrder());
               if(orderTree.getTypeOfOrder().equals(editTypeofOrderEasy.getText().toString())){
                   editTypeofOrderEasy.setChecked(true);
                   editTypeofOrderHard.setChecked(false);
               }
               else {


                       editTypeofOrderHard.setChecked(true);
                       editTypeofOrderEasy.setChecked(false);
                   }



               time.setText(orderTree.getTime());
               date.setText(orderTree.getDate());
               username.setText(orderTree.getUserName());
               editAnotherPhone.setText(orderTree.getRequestedPhone());
               if(orderTree.getPaymentMethod().equals(paymentCash.getText().toString())){
                   paymentCash.setChecked(true);
               }
               else if(orderTree.getPaymentMethod().equals(paymentCreditCard.getText().toString())){
                   paymentCreditCard.setChecked(true);
               }
               else{
                   paymentPayPal.setChecked(true);
               }



           }

       });
       editActivityViewModel.retrieveData(phoneNum,categoryType,uid);
       updates.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               orderTree = new OrderTree();
               if (editTypeofOrderHard.isChecked()) {
                   orderTree.setTypeOfOrder("Hard work");
               }
               else if (editTypeofOrderEasy.isChecked()) {
                   orderTree.setTypeOfOrder("Maintenance and repair");
               }
               orderTree.setLocation(editLocation.getText().toString());
               orderTree.setPhone(editNumber.getText().toString());
               orderTree.setUserName(username.getText().toString());
               orderTree.setTime(time.getText().toString());
               orderTree.setJobTitle(jobtitle.getText().toString());
               orderTree.setRequestedPhone(editAnotherPhone.getText().toString());
               orderTree.setDate(date.getText().toString());
               orderTree.setDetails(editDetails.getText().toString());
               orderTree.setTokenid(tokenid);
               if (paymentCash.isChecked()) {
                   orderTree.setPaymentMethod("cash");
               } else if (paymentPayPal.isChecked()) {
                   orderTree.setPaymentMethod("paypal");
               } else if (paymentCreditCard.isChecked()) {
                   orderTree.setPaymentMethod("creditCard");
               }
               editActivityViewModel.addData(orderTree,phoneNum,categoryType,uid);

               Intent intent = new Intent(EditActivity.this, RequestedActivity.class);
               intent.putExtra("phone",phoneNum);
               intent.putExtra("CategoryType",categoryType);
               intent.putExtra("job Title", jobTitle);
               startActivity(intent);
           }

       });
      timePicker.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Calendar timeSelected = Calendar.getInstance();
              int hour = timeSelected.get(Calendar.HOUR_OF_DAY);
              int minute = timeSelected.get(Calendar.MINUTE);
              TimePickerDialog mTimePicker;
              mTimePicker = new TimePickerDialog(EditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                  @Override
                  public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                      timeSelected.set(Calendar.MINUTE, selectedMinute);
                      timeSelected.set(Calendar.HOUR_OF_DAY, selectedHour);
                      timeSelected.set(Calendar.SECOND, 0);
                      time.setText(selectedHour + " : " + selectedMinute);
                  }
              }, hour, minute, true);//Yes 24 hour time
              mTimePicker.setTitle("Select Time");
              mTimePicker.show();

          }
      });
      datePicker.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Calendar c = Calendar.getInstance();
              DatePickerDialog dialog = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                  @Override
                  public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                      c.set(Calendar.YEAR, year);
                      c.set(Calendar.MONTH, month);
                      c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                      String datec = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
                      date.setText(datec);
                  }

              }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
              dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
              dialog.show();
          }
      });

    }
    @Override  // google API and list of notes
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            editLocation.setText(place.getAddress());
        }
    }

}