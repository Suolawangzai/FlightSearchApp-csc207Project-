package group_0700.flighthelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import user.Client;

public class client_menu extends AppCompatActivity {

    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);
        Intent intent = getIntent();
        client = (Client) intent.getSerializableExtra("Client");
    }

    public void goToViewPersonalInfo(View view){
        Intent intent = new Intent(this, ViewPersonalInfor.class);
        intent.putExtra("Client",client);
        startActivity(intent);
    }

    public void goToViewBookedItineraries(View view){
        Intent intent = new Intent(this, BookedItineraryView.class);
        intent.putExtra("Client",client);
        startActivity(intent);
    }

}
