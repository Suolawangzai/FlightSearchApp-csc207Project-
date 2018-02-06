package flight;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;

/**
 * A class that creates Itinerary object. It is responsible for performing the
 * following operations:
 * 1. At creation, it initializes all the instance variables used to store the
 *        up-to-date information of this itinerary.
 * 2. It gets all the information of this itinerary.
 * 3. It returns a string representation of this itinerary.
 */
public class Itinerary implements Serializable{

    // all flights contained in this itinerary, sorted by travel order
    private ArrayList<Flight> flights;

    // total cost of this itinerary
    private double totalCost;

    // total travel time of this itinerary, including the stop-over time
    private long totalTime;

    /**
     * Constructs a new itinerary with the given flights
     *
     * @param flights
     *            the list of flights contained in this itinerary, sorted travel
     *            order
     */
    public Itinerary(ArrayList<Flight> flights) {
        this.flights = flights;

        // Compute the total stopover time for an itinerary
        long totalStopover = 0;
        for (int i = 0; i < this.flights.size() - 1; i++) {
            int j = i + 1;
            totalStopover = totalStopover
                    + (this.flights.get(j).getDeptDateTime().getTime()
                    - this.flights.get(i).getArvlDateTime().getTime());
        }

        // Compute the total cost and total travel time (including stopover
        // time) for this itinerary
        this.totalCost = 0;
        this.totalTime = 0;
        for (Flight flight : this.flights) {
            this.totalCost = this.totalCost + flight.getPrice();
            this.totalTime = this.totalTime + flight.getTravelTime();
        }
        this.totalTime = this.totalTime + totalStopover;
    }

    /**
     * Gets the flights of this itinerary
     *
     * @return the flights of this itinerary
     */
    public ArrayList<Flight> getFlights() {
        return this.flights;
    }

    /**
     * Gets the total cost of this itinerary
     *
     * @return total cost of this itinerary
     */

    public double getTotalCost() {
        return this.totalCost;
    }

    /**
     * Gets the total travel time of this itinerary
     *
     * @return total travel time of this itinerary
     */
    public long getTotalTime() {
        return this.totalTime;
    }

    /**
     * Returns a string representation of this itinerary.
     *
     * @return a string representation of this itinerary
     */
    @Override
    public String toString() {
        String result = "";
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm");

        // compute the total travel hours
        long travelHours = TimeUnit.MILLISECONDS.toHours(this.totalTime);

        // compute the rest of time (excluding the hours) this itinerary takes
        long travelMins = TimeUnit.MILLISECONDS.toMinutes(this.totalTime)
                - travelHours * 60;

        for (Flight flight : this.flights) {
            result = result + flight.getNumber() + ","
                    + dFormat.format(flight.getDeptDateTime()) + ","
                    + dFormat.format(flight.getArvlDateTime()) + ","
                    + flight.getAirline() + "," + flight.getOrigin() + ","
                    + flight.getDestination() + "\n";
        }

        // make the price has exactly two decimal points and make travel hours
        // and travel minutes has exactly two digits
        result = result + String.format("%.2f", this.totalCost) + "\n"
                + String.format("%02d", travelHours) + ":"
                + String.format("%02d", travelMins);
        return result;
    }

}
