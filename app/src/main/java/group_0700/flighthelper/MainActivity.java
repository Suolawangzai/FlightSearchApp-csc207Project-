package group_0700.flighthelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import application.BookingApp;
import application.ClientManager;
import application.FlightManager;
import application.PasswordManager;
import user.Admin;
import user.Client;

public class MainActivity extends AppCompatActivity {

    BookingApp bookingApp;
    public static File passWordFile;
    public static final String USERDATADIR = "flightHelperData";
    public static final String SERCLIENTFILE = "clients.ser";
    public static final String CSVCLIENTFILE = "clients.txt";
    public static final String SERFLIGHTFILEPATH = "flights.ser";
    public static final String CSVFLIGHTFILE = "flights.txt";
    public static final String PASSWRDFILENAME = "password.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Create a new BookingApp instance

        try {
            bookingApp = new BookingApp();
        }
        catch (Exception e){
            TextView textView = (TextView) findViewById(R.id.header);
            textView.setText("System Error 1");
            Log.d("Debug", "bookingApp catch");
        }

        ClientManager clientManager = bookingApp.getClientManager();
        FlightManager flightManager = bookingApp.getFlightManager();
        PasswordManager passwordManager = bookingApp.getPasswordManager();

        File flightHelperData = this.getApplicationContext().getDir(USERDATADIR, MODE_PRIVATE);
        String path = flightHelperData.getPath();
        String filename = flightHelperData.getName();
        Log.d("Debug", path);
        Log.d("Debug", filename);
        File clientSerFile = new File(flightHelperData,SERCLIENTFILE);
        File clientCSVFile = new File(flightHelperData,CSVCLIENTFILE);
        File flightSerFile = new File(flightHelperData,SERFLIGHTFILEPATH);
        File flightCSVFile =new File(flightHelperData,CSVFLIGHTFILE);
        passWordFile = new File(flightHelperData,PASSWRDFILENAME);
        if (clientSerFile.exists()){
            try{
                clientManager.readFromSerFile(clientSerFile);
            }
            catch (Exception e){
                TextView textView = (TextView) findViewById(R.id.header);
                textView.setText("System Error");
                Log.d("Debug","read client Ser File");
            }
        }
        else{
            try{
                clientManager.readFromCSVFile(clientCSVFile);
            }
            catch(Exception e){
                String errorMsg = e.getMessage().toString() + "/n"+ e.getStackTrace().toString();
                TextView textView = (TextView) findViewById(R.id.header);
                textView.setText("System Error");
                Log.d("Debug", errorMsg);
            }
        }
        if(flightSerFile.exists()){
            try{
                flightManager.readFromSerFile(flightSerFile);
            }
            catch(Exception e){
                String errorMsg = e.getMessage().toString();
                TextView textView = (TextView) findViewById(R.id.header);
                textView.setText("System Error");
                Log.d("Debug", errorMsg);
            }
         }
        else{
            try{
                flightManager.readFromCSVFile(flightCSVFile);
            }
            catch(Exception e){
                String errorMsg = e.getMessage().toString() + "/n"+ e.getStackTrace().toString();
                TextView textView = (TextView) findViewById(R.id.header);
                textView.setText("System Error");
                Log.d("Debug", errorMsg);
            }
        }
        //read password file
        try{
            passwordManager.readPasswordFile(passWordFile);
        }
        catch (Exception e){
            String errorMsg = e.getMessage().toString() + "/n"+ e.getStackTrace().toString();
            TextView textView = (TextView) findViewById(R.id.header);
            textView.setText("System Error");
            Log.d("Debug", errorMsg);
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void enterData(View view){
        Log.d("Debug","entered data");
        EditText nameField = (EditText) findViewById(R.id.entered_name);
        String userName = nameField.getText().toString();
        EditText pwdField = (EditText) findViewById(R.id.entered_pwd);
        String pwd = pwdField.getText().toString();
        Intent intent;
        PasswordManager passwordManager = bookingApp.getPasswordManager();
        String match = passwordManager.passwordMatch(userName,pwd);

        if(match.equals("Y") ) {
            Admin admin = new Admin(userName);
            admin.setBookingApp(bookingApp);
            intent = new Intent(this, admin_menu.class);
            intent.putExtra("Admin", admin);
            startActivity(intent);
        }
        else if(match.equals("N")){
            ClientManager cm = bookingApp.getClientManager();
            Client client = cm.getClient(userName);
            client.setBookingApp(bookingApp);
            intent = new Intent(this, client_menu.class);
            //intent.putExtra("BookingApp",bookingApp);
            intent.putExtra("Client", client);
            startActivity(intent);
        }

        else if(match.equals("Invalid password!")){
            TextView textView = (TextView) findViewById(R.id.header);
            textView.setText("Invalid password!");
        }

        else{
            TextView textView = (TextView) findViewById(R.id.header);
            textView.setText("Invalid username");
        }

    }



}
