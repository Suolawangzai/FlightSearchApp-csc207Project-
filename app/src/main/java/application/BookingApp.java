package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Calendar;

import user.Client;
import flight.*;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;

/**
 * A class that represents the basic functionality of the booking application.
 * This class is responsible for performing the following operations:
 * 1. At creation, it initializes the instance variables used to store the
 *        current flights information and clients information of the booking
 *            application.
 * 2. Find all possible itineraries by given the origin, destination, and date.
 * 3. Find all possible flights by given the origin, destination, and date.
 * 4. Display all possible itineraries found sorted by the total cost.
 * 5. Display all possible itineraries found sorted by the total travel time.
 * 6. Update the clients' information from the given input file.
 * 7. Update the flights' information from the given input file.
 */
public class BookingApp implements Serializable{

    // the flight manager
    private FlightManager flightManager;

    //the client manager
    private ClientManager clientManager;

    private PasswordManager passwordManager;

    /**
     * Creates a new BookingApp.
     * @throws ParseException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     */
    public BookingApp(){
        this.flightManager = new FlightManager();
        this.clientManager = new ClientManager();
        this.passwordManager = new PasswordManager();
    }

    /**
     * Creates a new BookingApp and stores all flights information and clients
     * information from the constant file paths. Assumes that the format of the
     * given files are consistent with the given format.
     * @param flightFilePath an input file path that stores the information of
     *                       all flights
     * @param userFilePath an input file path that stores the information of all
     *                     clients
     * @throws FileNotFoundException if the file path is invalid
     * @throws ParseException if a Date object can not be parsed into correctly
     *                        and if a Double object can not be parsed into
     *                        correctly
     */
	public BookingApp(String flightFilePath, String userFilePath)
			throws FileNotFoundException, ParseException {

		this.flightManager = new FlightManager();
		this.flightManager.readFromCSVFile(flightFilePath);
		this.clientManager = new ClientManager();
		this.clientManager.readFromCSVFile(userFilePath);

	}

    public PasswordManager getPasswordManager(){return this.passwordManager;}

    /**
     * Return the instance variable, flightManager, of this BookingApp.
     * @return flightManager
     */
    public FlightManager getFlightManager() {
        return this.flightManager;
    }

    /**
     * Return the instance variable, clientManager, of this BookingApp.
     * @return clientManager
     */
    public ClientManager getClientManager() {
        return this.clientManager;
    }

    /**
     * Upload and update the information of clients from this given filePath.
     * @param filePath an input file path that stores the information of all
     *                 clients
     * @throws FileNotFoundException if the file path is invalid
     * @throws ClassNotFoundException
     */
    public void updateClients(String filePath) throws FileNotFoundException {
        this.clientManager.readFromCSVFile(filePath);
    }

    /**
     * Upload and update the information of flights from this given filePath.
     * @param filePath an input file path that stores the information of all
     *                 flights
     * @throws FileNotFoundException if the file path is invalid
     * @throws ParseException if a Date object can not be parsed into correctly
     *                        and if a Double object can not be parsed into
     *                        correctly
     * @throws ClassNotFoundException
     */
    public void updateFlights(String filePath) throws FileNotFoundException,
            ParseException, ClassNotFoundException {
        this.flightManager.readFromCSVFile(filePath);
    }

    /**
     * Search through the flight graph, return all itineraries from origin to
     * destination departing on the given date.
     * @param origin
     * 			a flight origin
     * @param destination
     * 			a flight destination
     * @param sdate
     * 			a departure date (in the format YYYY-MM-DD)
     * @return ArrayList of Itinerary
     * 			the list of itineraries that depart from origin and arrive
     * 			at destination on the given date
     * @throws ParseException if date cannot be parse into a Date correctly
     */
    public ArrayList<Itinerary> searchItineraries(String origin,
                                                  String destination, String sdate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(sdate);
        ArrayList<Itinerary> result = new ArrayList<Itinerary>();

        Set<Flight> allFlightsSet = (Set<Flight>) this.flightManager.getFlightGraph()
                .getFlights();
        Flight[] allFlights = allFlightsSet.toArray(new Flight[allFlightsSet.size()]);
        Calendar myDate = Calendar.getInstance();
        myDate.setTime(date);
        for (int i = 0; i < allFlights.length; i++) {
            Flight curFlight = allFlights[i];
            Calendar curDate = Calendar.getInstance();
            curDate.setTime(curFlight.getDeptDateTime());
            if ((curFlight.getOrigin().equals(origin))
                    &((myDate.get(Calendar.YEAR)) == (curDate.get(Calendar.YEAR)))
                    &((myDate.get(Calendar.DAY_OF_YEAR)) == curDate.get(Calendar.DAY_OF_YEAR))
                    &(curFlight.getNumSeats()>0)) {
                // if a flight have the right origin and departure date, find all possible
                // flight combinations starting with this flight.
                ArrayList<String> avoid = new ArrayList<String>();
                avoid.add(origin);
                avoid.add(curFlight.getDestination());
                ArrayList<ArrayList<Flight>> temp = findFlights(curFlight,
                        destination, avoid);
                for (int j = 0; j < temp.size(); j++) {
                    // turn all the flight combinations into itineraries.
                    result.add(new Itinerary(temp.get(j)));
                }
            }

        }
        return result;
    }

    /**
     * Search through the flight graph, return all flights from origin to
     * destination on the given date.
     * @param origin
     * 			a flight origin
     * @param destination
     * 			a flight destination
     * @param sdate
     * 			a departure date (in the format YYYY-MM-DD)
     * @return ArrayList of Flight
     * 			the list of flights that depart from origin and arrive
     * 			at destination on the given date
     * @throws ParseException
     */
    public ArrayList<Flight> searchFlights(String origin,
                                           String destination, String sdate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(sdate);
        ArrayList<Flight> result = new ArrayList<Flight>();
        Set<Flight> allFlightsSet = (Set<Flight>) this.flightManager.getFlightGraph()
                .getFlights();
        Flight[] allFlights = allFlightsSet.toArray(new Flight[allFlightsSet.size()]);
        Calendar myDate = Calendar.getInstance();
        myDate.setTime(date);

        for (int i = 1; i < allFlights.length; i++) {
            Flight curFlight = allFlights[i];
            Calendar curDate = Calendar.getInstance();
            curDate.setTime(curFlight.getDeptDateTime());
            // loop all the flights, find the ones satisfying the requirement.
            if ((curFlight.getOrigin().equals(origin))
                    &((myDate.get(Calendar.YEAR)) == (curDate.get(Calendar.YEAR)))
                    &((myDate.get(Calendar.DAY_OF_YEAR)) == curDate.get(Calendar.DAY_OF_YEAR))
                    &(curFlight.getDestination().equals(destination))
                    &(curFlight.getNumSeats()>0)){
                result.add(curFlight);
            }
        }
        return result;
    }

    /**
     * Return a string representation, displaying all itineraries in the given
     * list itineraries sorted by the total cost.
     * @param itineraries ArrayList of all itineraries
     * @return String representation of all itineraries that sorted by the total
     *         cost.
     */
    public String displayByCost(ArrayList<Itinerary> itineraries) {
        this.sort(itineraries, "cost");
        String result = "";
        for (Itinerary itinerary : itineraries) {
            result = result + itinerary + "\n";
        }

        return result;
    }

    /**
     * Return a string representation, displaying all itineraries in the given
     * list itineraries sorted by the total travel time.
     * @param itineraries ArrayList of all itineraries
     * @return String representation of all itineraries that sorted by the total
     *         travel time.
     */
    public String displayByTime(ArrayList<Itinerary> itineraries) {
        this.sort(itineraries, "time");
        String result = "";
        for (Itinerary itinerary : itineraries) {
            result = result + itinerary + "\n";
        }

        return result;
    }

    /**
     * Books the given Itinerary for the given Client
     * @param client 	the Client which is going to book the Itinerary.
     * @param itinerary	the Itinerary which is going to be booked.
     * @throws IOException
     */
    public void bookItinerary(Client client, Itinerary itinerary) throws IOException{
        for(Flight flight : itinerary.getFlights()){
            flight.setNumSeats(flight.getNumSeats() - 1);
        }
        this.flightManager.saveToFile();
        client.addBookedItinerary(itinerary);
        this.clientManager.saveToFile();
    }

   /* public String passwordMatched(String username, String password, File file){
        String line = null;
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(file);

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                String[] info = line.split(",",3);
                if ((username.equals(info[0])) & (password.equals(info[1]))){
                    bufferedReader.close();
                    return info[2];
                }
            }

            bufferedReader.close();
            return "NoMatchFound";
        }
        catch(FileNotFoundException ex) {
            return "Unable to open password file";
        }
        catch(IOException ex) {
            return "Error reading password file" ;
        }

    }*/

    /**
     * Sort this itineraries by the given category which is either time or cost.
     * Do nothing when the given list of itineraries is empty.
     * @param itineraries All possible itineraries
     * @param category Category that the itineraries is going to be sorted by
     */
    private void sort(ArrayList<Itinerary> itineraries, String category) {

        if (!itineraries.isEmpty()) {

            // Sort the given itineraries by the total cost and put the
            // itinerary with the least total cost at the first
            if (category.equals("cost")) {
                for (int i = 0; i < itineraries.size() - 1; i++) {
                    for (int j = i + 1; j < itineraries.size(); j++) {
                        if (itineraries.get(i).getTotalCost()
                                > itineraries.get(j).getTotalCost()) {
                            Itinerary tempMin = itineraries.get(i);
                            itineraries.set(i, itineraries.get(j));
                            itineraries.set(j, tempMin);
                        }
                    }
                }

                // Sort the given itineraries by the total travel time and put the
                // itinerary with the least total travel time at the first
            } else if (category.equals("time")) {
                for (int i = 0; i < itineraries.size() - 1; i++) {
                    for (int j = i + 1; j < itineraries.size(); j++) {
                        if (itineraries.get(i).getTotalTime()
                                > itineraries.get(j).getTotalTime()) {
                            Itinerary tempMin = itineraries.get(i);
                            itineraries.set(i, itineraries.get(j));
                            itineraries.set(j, tempMin);
                        }
                    }
                }
            }
        }
    }

    /**
     * Return the list of flight combinations that can go to the destination
     * without looping, starting with the departure Flight
     * @param departure
     * 			a Flight to start with
     * @param destination
     * 			a flight destination
     * @param avoid
     * 			the cities which should be avoid in the following travels
     * @return
     * 			an ArrayList of flight combinations that can go to the
     *          destination, without going to any city twice, starting with the
     *          departure flight. A flight combination is sorted in travel order.
     */
    private ArrayList<ArrayList<Flight>> findFlights(Flight departure,
                                                     String destination, ArrayList<String> avoid) {
        ArrayList<ArrayList<Flight>> result = new ArrayList<ArrayList<Flight>>();
        if (departure.getDestination().equals(destination)){
            // if this flight arrives at the destination, return it
            ArrayList<Flight> temp = new ArrayList<Flight>();
            temp.add(0, departure);
            result.add(temp);
        }else{
            // otherwise, check all the following flights of this flight to
            // find all available flight combinations
            Set<Flight> followingFlightsSet = (Set<Flight>) this.flightManager.getFlightGraph()
                    .getNextStop(departure);
            Flight[] followingFlights = followingFlightsSet.toArray(new Flight[followingFlightsSet.size()]);
            // loop through all the following flights of the departure flight
            for (int i = 0; i < followingFlights.length; i++){
                if (!avoid.contains(followingFlights[i].getDestination())
                        &(followingFlights[i].getNumSeats()>0)){
                    // if the following flight fly to some place that we have
                    // been to already, we don't need it. So we are only
                    // checking those flight which goes to the places where we
                    // have not been to.
                    ArrayList<String> newAvoid = new ArrayList<String>();
                    newAvoid.addAll(avoid);
                    // add the destination of the following flight to the list
                    // of cities to avoid.
                    newAvoid.add(followingFlights[i].getDestination());
                    ArrayList<ArrayList<Flight>> temp = this.findFlights(
                            followingFlights[i], destination, newAvoid);
                    for (int j = 0; j < temp.size(); j++) {
                        // add the departure flight to all the result
                        // combination of flights
                        temp.get(j).add(0, departure);
                        result.add(temp.get(j));
                    }
                }
            }
        }
        return result;
    }


}
