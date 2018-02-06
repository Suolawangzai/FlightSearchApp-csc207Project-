package application;

import user.Client;
import application.Constants;

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
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This class is responsible for creating Client objects by reading Client
 * information from information file and maintain all Clients information during
 * running of the program.
 */
public class ClientManager implements Serializable{

    // Map stores all clients
    private Map<String, Client> clients;
    private File storageFile;

    public ClientManager(){
        this.clients = new HashMap<String, Client>();
    }

    /**
     * Construct a new empty ClientManager.
     * @throws ClassNotFoundException
     * @throws FileNotFoundException
     */
    public ClientManager(String filePath) throws ClassNotFoundException, FileNotFoundException {
        this.clients = new HashMap<String, Client>();
        File file = new File(filePath);
        if (file.exists()){
            readFromSerFile(filePath);
        }else{
            readFromCSVFile(filePath);
        }
    }

    /**
     * Reads client information to the application from the file at the given
     * path and construct Client object corresponding to information read and
     * stored Clients created in this ClientManager. Assumes that the input file
     * of client information has the format as following:
     * LastName,FirstNames,Email,Address,CreditCardNumber,ExpiryDate (the
     * ExpiryDate is stored in the format YYYY-MM-DD)
     *
     * @param filePath
     *            the path of the file that contained client information in the
     *            format stated above to be read from.
     * @throws FileNotFoundException
     *             thrown if the file is not found in the filePath provided
     */
    public void readFromCSVFile(String filePath) throws FileNotFoundException {

        Scanner scanner = new Scanner(new FileInputStream(filePath));
        String[] record;
        Client client;
        // Read client info from files
        while (scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");
            client = new Client(record[0], record[1], record[2], record[3],
                    record[4], record[5]);
            // Add client to the ClientManager
            this.clients.put(client.getEmail(), client);
        }
        scanner.close();
    }

    public void readFromCSVFile(File file) throws FileNotFoundException {

        Scanner scanner = new Scanner(new FileInputStream(file));
        String[] record;
        Client client;
        // Read client info from files
        while (scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");
            client = new Client(record[0], record[1], record[2], record[3],
                    record[4], record[5]);
            // Add client to the ClientManager
            this.clients.put(client.getEmail(), client);
        }
        scanner.close();
    }

    /**
     * Reads the map of client information from the ser file at given path.
     * @param filePath
     * @throws ClassNotFoundException
     */
    public void readFromSerFile(String filePath) throws ClassNotFoundException{
        try {
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            //deserialize the Map
            this.clients = (Map<String,Client>) input.readObject();
            input.close();
        } catch (IOException ex) {
            System.out.println("Cannot read from input.");
        }
    }


    public void readFromSerFile(File file) throws ClassNotFoundException{
        try {
            InputStream inputFile = new FileInputStream(file);
            InputStream buffer = new BufferedInputStream(inputFile);
            ObjectInput input = new ObjectInputStream(buffer);

            //deserialize the Map
            this.clients = (Map<String,Client>) input.readObject();
            input.close();
        } catch (IOException ex) {
            System.out.println("Cannot read from input.");
        }
    }

    /**
     * Returns the Map of all pairs of email address and corresponding Client of
     * that contains stored in this ClientManager.
     *
     * @return return the Map that contains all Clients in this ClientManager
     *         with email address of each Client as key and Client as value
     */
    public Map<String, Client> getClients() {
        return this.clients;
    }

    /**
     * Returns the Client stored in this ClientManager given his email address
     *
     * @param email
     *            the email address of the Client looking for
     * @return the Client who has this email address
     */
    public Client getClient(String email) {
        return clients.get(email);
    }

    /**
     * Writes the clients to the ser file at the constant path.
     * @throws IOException
     */
    public void saveToFile() throws IOException{
        if (storageFile == null){

        }
        OutputStream file = new FileOutputStream(storageFile);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);
        // serialize the Map
        output.writeObject(clients);
        output.close();
    }

}
