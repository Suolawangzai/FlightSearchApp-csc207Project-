package user;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import flight.Flight;
import flight.Itinerary;

public class Admin extends User implements Serializable{

    private String username;

    public Admin(String username) {
        this.username = username;
    }

    public String viewClientInfo(String email){
        String result = "";
        if (this.getMyApp().getClientManager().getClients().containsKey(email)){
            Client client = this.getMyApp().getClientManager().getClients()
                    .get(email);
            result = client.toString();
        }
        return result;
    }

    public void uploadClientInfo(String filePath) throws FileNotFoundException {
        this.getMyApp().getClientManager().readFromCSVFile(filePath);
    }

    public void editClienInfo(String email, String lastName, String firstNames,
                              String address, String creditCardNumber, String expireDate) {
        if (this.getMyApp().getClientManager().getClients().containsKey(email)) {
            Client client = this.getMyApp().getClientManager().getClients()
                    .get(email);
            client.setAddress(address);
            client.setCreditCardNumber(creditCardNumber);
            client.setExpireDate(expireDate);
            client.setFirstNames(firstNames);
            client.setLastName(lastName);
        }
    }

    public void uploadFlightInfo(String filePath) throws FileNotFoundException,
            ParseException {

        this.getMyApp().getFlightManager().readFromCSVFile(filePath);
    }

    public void editFlightInfo(String number, Date deptDateTime,
                               Date arvlDateTime, String airline, String origin,
                               String destination, double price, int numSeats) throws IOException {
        if (this.getMyApp().getFlightManager().getFlightsWithInfo()
                .containsKey(number)) {
            Flight flight = this.getMyApp().getFlightManager()
                    .getFlightsWithInfo().get(number);
            flight.setAirline(airline);
            flight.setArvlDateTime(arvlDateTime);
            flight.setDeptDateTime(deptDateTime);
            flight.setDestination(destination);
            flight.setNumSeats(numSeats);
            flight.setOrigin(origin);
            flight.setPrice(price);
            long newTravelTime = arvlDateTime.getTime()
                    - deptDateTime.getTime();
            flight.setTravelTime(newTravelTime);
            this.getMyApp().getFlightManager().saveToFile();
        }
    }

    public void bookItinerary (Itinerary itinerary, Client client) throws
            IOException {
        this.getMyApp().bookItinerary(client, itinerary);
    }

    public String viewClientItineraries(String email) {
        String result = "";
        LinkedList<Itinerary> bookings = new LinkedList<Itinerary>();
        if (this.getMyApp().getClientManager().getClients()
                .containsKey(email)) {
            Client client = this.getMyApp().getClientManager().getClients()
                    .get(email);
            bookings = client.getBookedItineraries();
            for (int i = 0; i < bookings.size(); i++) {
                result = result + bookings.get(i) + "\n";
            }
        }
        return result;
    }

}
