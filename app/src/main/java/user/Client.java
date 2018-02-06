package user;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

import flight.Itinerary;

/**
 * A subclass of class User. This class creates Client object. It is responsible
 * for performing the following operations:
 * 1. At creation, it inherits from its super class and initializes all the
 *        instance variables used to store the up-to-date information of this
 *            client.
 * 2. It sets all the information excluding email of this client.
 * 3. It gets all the information of this client.
 * 4. It returns a string representation of this client.
 */
public class Client extends User implements Serializable{

    // the last name
    private String lastName;

    // the first names
    private String firstNames;

    // the email that is unique
    private String email;

    // the address
    private String address;

    // the credit card number
    private String creditCardNumber;

    // the expire date of the credit card
    private String expireDate;

    // the list of booked Itineraries of this Client.
    private LinkedList<Itinerary> bookedItineraries;

    /**
     * Creates a new client with the given lastName, firstNames, email, address,
     * creditCardNumber, and expiryDate.
     * @param lastName last name of this client
     * @param firstNames first names of this client
     * @param email email of this client
     * @param address address of this client
     * @param creditCardNumber credit card number of this client
     * @param expireDate expire date of the credit card of this client
     */
    public Client(String lastName, String firstNames, String email,
                  String address, String creditCardNumber, String expireDate) {
        super();
        this.lastName = lastName;
        this.firstNames = firstNames;
        this.email = email;
        this.address = address;
        this.creditCardNumber = creditCardNumber;
        this.expireDate = expireDate;
        this.bookedItineraries = new LinkedList<Itinerary>();
    }

    /**
     *
     * @return
     */
    public LinkedList<Itinerary> getBookedItineraries() {
        return bookedItineraries;
    }

    /**
     * Books the given Itinerary for this Client.
     * @param itinerary the Itinerary which is going to be booked.
     * @throws IOException
     */
    public void bookItinerary(Itinerary itinerary) throws IOException{
        this.getMyApp().bookItinerary(this, itinerary);
    }

    /**
     * Adds the given Itinerary to the booked itinerary list of this client.
     * @param itinerary the Itinerary which is going to be added to the list.
     */
    public void addBookedItinerary(Itinerary itinerary){
        this.bookedItineraries.add(itinerary);
    }

    /**
     * Returns the instance variable, lastName, of this client.
     * @return last name of this client
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Assigns the instance variable, lastName, of this client by the given
     * lastName.
     * @param lastName last name of this client that is going to be changed to
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    /**
     * Returns the instance variable, firstNames, of this client.
     * @return first names of this client
     */
    public String getFirstNames() {
        return this.firstNames;
    }

    /**
     * Assigns the instance variable, firstNames, of this client by the given
     * firstNames.
     * @param firstNames first names of this client that is going to be changed
     *                   to
     */
    public void setFirstNames(String firstNames){
        this.firstNames = firstNames;
    }

    /**
     * Returns the instance variable, address, of this client.
     * @return address of this client
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Assigns the instance variable, address, of this client by the given
     * address.
     * @param address address of this client that is going to be changed
     *                to
     */
    public void setAddress(String address){
        this.address = address;
    }

    /**
     * Returns the instance variable, creditCardNumber, of this client.
     * @return credit card number of this client
     */
    public String getCreditCardNumber() {
        return this.creditCardNumber;
    }

    /**
     * Assigns the instance variable, creditCardNumber, of this client by the
     * given creditCardNumber.
     * @param creditCardNumber credit card number of this client that is going
     *                         to be changed to
     */
    public void setCreditCardNumber(String creditCardNumber){
        this.creditCardNumber = creditCardNumber;
    }

    /**
     * Returns the instance variable, expiryDate, of this client.
     * @return expire date of the credit card of this client
     */
    public String getExpireDate() {
        return this.expireDate;
    }

    /**
     * Assigns the instance variable, expiryDate, of this client by the given
     * expiryDate.
     * @param expireDate expire date of the credit card of this client that is
     *                   going to be changed to.
     */
    public void setExpireDate(String expireDate){
        this.expireDate = expireDate;
    }

    /**
     * Returns the instance variable, email, of this client.
     * @return email of this client
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Edits the information of this Client
     * @param lastName			the lastName of this Client after editing.
     * @param firstNames		the firstNames of this Client after editing.
     * @param address			the address of this Client after editing.
     * @param creditCardNumber	the creditCardNumber of this Client
     * 							after editing.
     * @param expireDate		the expireDate of this Client's credit card
     * 							after editing
     * @throws IOException
     */
    public void editClientInfo(String lastName, String firstNames,
                               String address, String creditCardNumber, String expireDate)
            throws IOException{
        this.setFirstNames(firstNames);
        this.setAddress(address);
        this.setLastName(lastName);
        this.setExpireDate(expireDate);
        this.setCreditCardNumber(creditCardNumber);
        this.getMyApp().getClientManager().saveToFile();
    }

    /**
     * Returns a string representation of this client.
     * @return a string representation of all information of this client.
     */
    @Override
    public String toString() {
        return this.lastName + "," + this.firstNames + "," + this.email + ","
                + this.address + "," + this.creditCardNumber + ","
                + this.expireDate;
    }


}
