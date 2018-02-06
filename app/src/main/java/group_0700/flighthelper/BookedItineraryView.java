package group_0700.flighthelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.LinkedList;

import flight.Itinerary;
import user.Client;

public class BookedItineraryView extends ListActivity {

    Client client;
    LinkedList<Itinerary> itineraries;
    ArrayList<String> itinerariesStr = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_booked_itinerary_view);
        Intent intent = getIntent();
        client = (Client) intent.getSerializableExtra("Client");
        itineraries = client.getBookedItineraries();
        for (Itinerary itinerary: itineraries){
            itinerariesStr.add(itinerary.toString());
        }
        setListAdapter(new ArrayAdapter<String>(this,
                R.layout.activity_booked_itinerary_view,itinerariesStr));

        ListView listView = getListView();
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
