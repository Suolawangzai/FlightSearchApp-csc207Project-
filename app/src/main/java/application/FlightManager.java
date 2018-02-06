package application;

import flight.Flight;
import application.Constants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import user.Client;

/**
 * This class is responsible for creating Flight objects by reading flight
 * information from information file and maintain all updated flight information
 * during running of the program.
 *
 */
public class FlightManager implements Serializable{

    // A direct graph contains all flights
    private Map<String, Flight> flightsWithInfo;
    private FlightGraph flights;

    public FlightManager(){
        this.flightsWithInfo = new HashMap<String, Flight>();
        this.flights = new FlightGraph();
    }

    /**
     * Construct a new empty FlightManager
     * @throws ClassNotFoundException
     * @throws ParseException
     * @throws FileNotFoundException
     */
   /* public FlightManager(String filePath) throws ClassNotFoundException, FileNotFoundException, ParseException {
        this.flightsWithInfo = new HashMap<String, Flight>();
        this.flights = new FlightGraph();
        File file = new File();
        if (file.exists()){
            readFromSerFile(Constants.SERFLIGHTFILEPATH);
        }else{
            readFromCSVFile(Constants.FLIGHTFILEPATH);
        }
    }*/

    public void readFromSerFile(String filePath) throws ClassNotFoundException {
        try {
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            //deserialize the Map
            this.flightsWithInfo = (Map<String,Flight>) input.readObject();
            input.close();
        } catch (IOException ex) {
            System.out.println("Cannot read from input.");
        }

    }

    public void readFromSerFile(File file) throws ClassNotFoundException {
        try {
            InputStream inputFile = new FileInputStream(file);
            InputStream buffer = new BufferedInputStream(inputFile);
            ObjectInput input = new ObjectInputStream(buffer);

            //deserialize the Map
            this.flightsWithInfo = (Map<String,Flight>) input.readObject();
            input.close();
        } catch (IOException ex) {
            System.out.println("Cannot read from input.");
        }

    }

    /**
     * Reads the contents of the CSV file given the path of the file and
     * construct Flight object according to information read and stored these
     * Flights in this ClientManager. Assumes that the input file flight
     * information has the format as following:
     * Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,
     * Destination,Price (the dates are in the format YYYY-MM-DD; the price has
     * exactly two decimal places)
     *
     * @param filePath
     *            path of the file that containing flight info in the correct
     *            format to be read from
     * @throws FileNotFoundException
     *             thrown if the file is not found in the filePath provided
     * @throws ParseException
     */
    public void readFromCSVFile(String filePath) throws FileNotFoundException,
            ParseException {

        Scanner scanner = new Scanner(new FileInputStream(filePath));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm");
        String[] record;
        Flight flight;

        while (scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");
            Date deptDateTime = sdf.parse(record[1]);
            Date arvlDateTime = sdf.parse(record[2]);
            flight = new Flight(record[0], deptDateTime, arvlDateTime,
                    record[3], record[4], record[5],
                    Double.parseDouble(record[6]), Integer.parseInt(record[7]));
            this.flights.addNode(flight);
            this.flightsWithInfo.put(flight.getNumber(), flight);
        }
        scanner.close();
    }

    public void readFromCSVFile(File file) throws FileNotFoundException,
            ParseException {

        Scanner scanner = new Scanner(new FileInputStream(file));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm");
        String[] record;
        Flight flight;

        while (scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");
            Date deptDateTime = sdf.parse(record[1]);
            Date arvlDateTime = sdf.parse(record[2]);
            flight = new Flight(record[0], deptDateTime, arvlDateTime,
                    record[3], record[4], record[5],
                    Double.parseDouble(record[6]), Integer.parseInt(record[7]));
            this.flights.addNode(flight);
            this.flightsWithInfo.put(flight.getNumber(), flight);
        }
        scanner.close();
    }


    /**
     * Returns the Map of all pairs of flight number and corresponding Flight of
     * that contains stored in this FilghtManager.
     * @return return the Map that contains all Flights in this FlightManager
     *         with flight number of each Flight as key and Flight as value
     */
    public Map<String, Flight> getFlightsWithInfo() {
        return flightsWithInfo;
    }

    /**
     * Returns all Flights maintained in FlightManager
     *
     * @return the graph of all flights construct and maintained by
     *         FlightManager
     */
    public FlightGraph getFlightGraph() {
        return this.flights;
    }

    /**
     * Writes the flights to the ser file at the constant path.
     * @throws IOException
     */
    public void saveToFile() throws IOException{
        OutputStream file = new FileOutputStream(Constants.SERUSERFILEPATH);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);
        output.writeObject(flightsWithInfo);
        output.close();
    }
}
