package group_0700.flighthelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import user.Client;

public class ViewPersonalInfor extends AppCompatActivity {

    Client client;
    EditText firstNameField;
    EditText lastNameField;
    EditText addressField;
    EditText emailAddressField;
    EditText creditCardNumField;
    EditText expiryYearField;
    EditText expiryMonthField;
    EditText expiryDayField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_personal_infor);
        Intent intent = getIntent();
        client = (Client) intent.getSerializableExtra("Client");
        firstNameField = (EditText) findViewById(R.id.first_name);
        lastNameField = (EditText) findViewById(R.id.last_name);
        addressField = (EditText) findViewById(R.id.address);
        emailAddressField = (EditText) findViewById(R.id.email_address);
        creditCardNumField = (EditText) findViewById(R.id.credit_card_num);
        expiryYearField = (EditText) findViewById(R.id.expiry_yr);
        expiryMonthField = (EditText) findViewById(R.id.expiry_mo);
        expiryDayField = (EditText) findViewById(R.id.expiry_day);

        /*firstNameField.setFocusable(false);
        lastNameField.setFocusable(false);
        addressField.setFocusable(false);
        emailAddressField.setFocusable(false);
        creditCardNumField.setFocusable(false);

        firstNameField.setClickable(false);
        lastNameField.setClickable(false);
        addressField.setClickable(false);
        emailAddressField.setClickable(false);
        creditCardNumField.setClickable(false);*/

        firstNameField.setText(client.getFirstNames());
        lastNameField.setText(client.getLastName());
        addressField.setText(client.getAddress());
        emailAddressField.setText(client.getEmail());
        creditCardNumField.setText(client.getCreditCardNumber());

        String expiryDate = client.getExpireDate();
        expiryYearField.setText(expiryDate.substring(0,4));
        expiryMonthField.setText(expiryDate.substring(5,7));
        expiryDayField.setText(expiryDate.substring(8));


       /*
        int firstDash = expiryDate.indexOf("-");
        int secondDash = expiryDate.substring(firstDash+1).indexOf("-");
        expiryYearField.setText(expiryDate.substring(0,firstDash));
        expiryMonthField.setText(expiryDate.substring(firstDash+1,secondDash));
        expiryDayField.setText(expiryDate.substring(secondDash+1));*/

        /*InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(4);
        expiryYearField.setFilters(FilterArray);

        InputFilter[] FilterArray1 = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(2);
        expiryMonthField.setFilters(FilterArray1);
        expiryDayField.setFilters(FilterArray1);*/
    }

    public void enableEdit(View view){
        firstNameField.setFocusable(true);
        lastNameField.setFocusable(true);
        addressField.setFocusable(true);
        creditCardNumField.setFocusable(true);

        firstNameField.setClickable(false);
        lastNameField.setClickable(false);
        addressField.setClickable(false);
        emailAddressField.setClickable(false);
        creditCardNumField.setClickable(false);
    }

   /* public void editInfo(View view) {
        firstNameField.setFocusable(false);
        lastNameField.setFocusable(false);
        addressField.setFocusable(false);
        emailAddressField.setFocusable(false);
        client.setFirstNames(firstNameField.getText().toString());
        client.setLastName(lastNameField.getText().toString());
        client.setAddress(addressField.getText().toString());
        client.setCreditCardNumber(creditCardNumField.getText().toString());
        Log.d("Debug", "Set Focusable and Clikable");
    }*/
}
