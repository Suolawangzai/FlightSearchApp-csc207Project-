package application;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.text.ParseException;
import java.util.Date;
import flight.Flight;

/**
 * This class representing a collection of all flights that connected together.
 * Flight graph is responsible for storing flights and connecting any tow
 * flights if one can be transfer flight for another.
 */
public class FlightGraph implements Serializable{

    // Map representing a direct graph
    private Map<Flight, Set<Flight>> contents;

    /**
     * Constructs a new empty FlightGraph
     */
    public FlightGraph() {
        contents = new HashMap<Flight, Set<Flight>>();
    }

    /**
     * Adds new Flight to this FlightGraph that connected all FLights
     *
     * @param flight
     *            the flight to be added to this Flight Graph
     * @throws ParseException
     */
    public void addNode(Flight flight) throws ParseException {
        // Check if Graph is empty
        if (contents.isEmpty()) {
            contents.put(flight, new TreeSet<Flight>());
        } else {
            Set<Flight> nextStops = new TreeSet<Flight>();
            // Traverse through every node in the graph
            Set<Flight> keys = this.contents.keySet();
            for (Flight oddFlt : keys) {
                // Check if flight to be added can be transfer flight to
                // existing one
                if (isNextStop(oddFlt, flight)) {
                    contents.get(oddFlt).add(flight);
                }
                // Check if existing flight can be transfer flight to the flight
                // to be added
                if (isNextStop(flight, oddFlt)) {
                    nextStops.add(oddFlt);
                }
            }
            contents.put(flight, nextStops);
        }
    }

    /**
     * Returns all Flights stored in this FlightGraph.
     *
     * @return the set of all flights stored in this FlightGraph
     */
    public Set<Flight> getFlights() {
        return this.contents.keySet();
    }

    /**
     * Returns the Flight that can be consider as the connected flight to this
     * Flight.
     *
     * @param flight
     * @return the
     */
    public Set<Flight> getNextStop(Flight flight) {
        return this.contents.get(flight);
    }

    /**
     * Returns the Map representing this FlightGraph.
     *
     * @return the Map contains all pairs of flights and set of their transfer
     *         flight stored in this FlightGraph.
     */
    public Map<Flight, Set<Flight>> getContents() {
        return contents;
    }

    /* Return true iff flight2 can be the transfer flight after flight1 */
    private boolean isNextStop(Flight flight1, Flight flight2)
            throws ParseException {
        String destination = flight1.getDestination();
        String origin = flight2.getOrigin();

        // Check if the destination of flight1 is the same as origin of flight2
        if (destination.equals(origin)) {
            Date arvlDateTime = flight1.getArvlDateTime();
            Date deptDateTime = flight2.getDeptDateTime();
            long diffms = deptDateTime.getTime() - arvlDateTime.getTime();
            long diffmins = diffms / (60 * 1000);

			/*
			 * Check if departure date of flight2 is after or equal to arrival
			 * time of flight1 and is within or equal to 6 hours.
			 */
            if (diffmins <= 360 && diffmins >= 0) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

}
