package flight;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * A class that creates Flight object. It is responsible for performing the 
 * following operations:
 * 1. At creation, it initializes all the instance variables used to store the 
 *        up-to-date information of this flight.
 * 2. It sets all the information excluding flight number of this flight.
 * 3. It gets all the information of this flight.
 * 4. It returns a string representation of this flight.
 * 5. It implements compareTo, equals, and hashCode methods.
 */
public class Flight implements Comparable<Flight>,Serializable{
    /*This is the flight number*/
    private String number;

    /* This Flight's departure date and time in format YYYY-MM-DD HH:MM */
    private Date deptDateTime;

    /* This Flight's arrival date and time in format YYYY-MM-DD HH:MM */
    private Date arvlDateTime;

    /* This Flight's airline */
    private String airline;

    /*This is the origin place for the flight*/
    private String origin;

    /*This is the destination of the flight*/
    private String destination;

    /* This Flight's ticket price. */
    private double price;

    /*This is the travel time of a flight*/
    private long travelTime;

    /*This is the number of seats of a flight*/
    private int numSeats;

    /**
     * Creates a new Flight with specified flight number, departure date and time
     * arrival date and time, origin, destination, airline and price.
     * @param number number representing flight number
     * @param deptDateTime the date and time the flight depart
     * @param arvlDateTime the date and the time the flight arrive
     * @param airline airline the flight belongs to
     * @param origin origin place of the flight
     * @param destination destination of the flight
     * @param price price of the flight ticket
     */
    public Flight(String number, Date deptDateTime, Date arvlDateTime,
                  String airline, String origin, String destination, double price,
                  int numSeats) {
        super();
        this.number = number;
        this.deptDateTime = deptDateTime;
        this.arvlDateTime = arvlDateTime;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.price = price;
        this.numSeats = numSeats;

        // Compute the travel time in milliseconds
        this.travelTime = arvlDateTime.getTime() - deptDateTime.getTime();
    }

    /**
     * Returns the number of seats remaining of this Flight
     * @return the flight number of the flight
     */
    public int getNumSeats() {
        return numSeats;
    }

    /**
     * Returns the flight number of this Flight
     * @return the flight number of the flight
     */
    public String getNumber() {
        return this.number;
    }

    /**
     * Returns the departure time of this Flight
     * @return the deptDateTime of the flight
     */
    public Date getDeptDateTime() {
        return this.deptDateTime;
    }


    /**
     * Returns the arrival time of this Flight
     * @return the arvlDateTime of the flight
     */
    public Date getArvlDateTime() {
        return this.arvlDateTime;
    }

    /**
     * Returns the airline info of this Flight
     *@return the airline of this Flight

     */
    public String getAirline() {
        return this.airline;
    }

    /**
     * Returns the origin of this Flight
     * @return the origin of this Flight
     */
    public String getOrigin() {
        return this.origin;
    }

    /**
     * Returns the destination of this Flight
     * @return the destination of this Flight
     */
    public String getDestination() {
        return this.destination;
    }

    /**
     * Returns the price of this Flight
     * @return the price of the ticket of the flight
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Returns the travel time of this Flight
     * @return the travelTime of the flight
     */
    public long getTravelTime(){
        return this.travelTime;
    }

    /**
     * Sets the departure date and time of this Flight to deparDateTime
     * @param deptDateTime the departure date of the flight
     */
    public void setDeptDateTime(Date deptDateTime) {
        this.deptDateTime = deptDateTime;
    }

    /**
     * Sets the arrival date and time of this Flight to deparDateTime
     * @param arvlDateTime the departure date of the flight
     */
    public void setArvlDateTime(Date arvlDateTime) {
        this.arvlDateTime = arvlDateTime;
    }

    /**
     * Sets the airline of this Flight to airline
     * @param airline the departure date of the flight
     */
    public void setAirline(String airline) {
        this.airline = airline;
    }

    /**
     * Sets the origin place of this Flight
     * @param origin the origin of this Flight
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Sets the arrival place of this Flight
     * @param destination the destination this Flight
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Sets the price of the ticket for this Flight
     * @param price of the flight
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the total travel time this Flight
     * @param travelTime the travel time of the flight
     */
    public void setTravelTime(long travelTime) {
        this.travelTime = travelTime;
    }

    /**
     * Sets the the number of seats of this Flight
     * @param numSeats the travel time of the flight
     */
    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    /**
     * Returns a string representation of this flight.
     * @return the full information of the flight
     */
    @Override
    public String toString() {
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm");

        // make the date and time for departure and arrival in the format as
        // in format YYYY-MM-DD HH:MM
        return  this.number + "," + dFormat.format(this.deptDateTime) + ","
                + dFormat.format(this.arvlDateTime) + "," + this.airline
                + "," + this.origin + "," + this.destination + ","
                + String.format("%.2f",this.price);
    }

    /**
     * Compares this Flight toFlight other. Comparison is based on comparison
     * of Flight flight number since we assume that flight number of each flight
     *  is unique.
     * @return an integer < 0, if number of this Flight is less than number of 
     *                 other 0, if number of this Flight is equal to number of 
     *                 other an integer > 0, otherwise
     */

    @Override
    public int compareTo(Flight other) {
        int flightnum1 = Integer.parseInt(this.getNumber());
        int flightnum2 = Integer.parseInt(other.getNumber());
        return flightnum1 - flightnum2;
    }


    /**
     * Returns true iff input object is a Flight object and is the same Flight
     * as this Flight.
     *
     * @return return true iff input object has the same type as this Flight and
     *         input has the same flight number, departure date and time,
     *         arrival date and time, origin, destination and cost.
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Flight other = (Flight) obj;
        if (airline == null) {
            if (other.airline != null)
                return false;
        } else if (!airline.equals(other.airline))
            return false;
        if (arvlDateTime == null) {
            if (other.arvlDateTime != null)
                return false;
        } else if (!arvlDateTime.equals(other.arvlDateTime))
            return false;
        if (deptDateTime == null) {
            if (other.deptDateTime != null)
                return false;
        } else if (!deptDateTime.equals(other.deptDateTime))
            return false;
        if (destination == null) {
            if (other.destination != null)
                return false;
        } else if (!destination.equals(other.destination))
            return false;
        if (number == null) {
            if (other.number != null)
                return false;
        } else if (!number.equals(other.number))
            return false;
        if (origin == null) {
            if (other.origin != null)
                return false;
        } else if (!origin.equals(other.origin))
            return false;
        if (Double.doubleToLongBits(price) != Double
                .doubleToLongBits(other.price))
            return false;
        return true;
    }

    /**
     * Returns a hash code value of this Flight.
     * If two Flights are equal according to the equals(Flight) method,
     * then calling the hashCode method on each of the two objects must
     * produce the same integer result.
     * @return the hash code of this Flight
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((airline == null) ? 0 : airline.hashCode());
        result = prime * result
                + ((arvlDateTime == null) ? 0 : arvlDateTime.hashCode());
        result = prime * result
                + ((deptDateTime == null) ? 0 : deptDateTime.hashCode());
        result = prime * result
                + ((destination == null) ? 0 : destination.hashCode());
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        result = prime * result + ((origin == null) ? 0 : origin.hashCode());
        long temp;
        temp = Double.doubleToLongBits(price);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
