package application;

import java.io.Serializable;

/** Constants used by the BookingApp. */
public class Constants implements Serializable{

    // ----- constants for BookingApp to load required information -----

    /** The file path that stores all the detailed information for flights. */
    public static final String FLIGHTFILEPATH = "/Users/zuoyuzhang/Desktop/group_0700/pII/sampleTests/flights1.txt";
    public static final String SERFLIGHTFILEPATH = "/Users/shirleyhao/Desktop/csc207/group_0700/pII/sampleTests/flights1.ser";
    /** The file path that stores all the detailed information for clients. */
    public static final String USERFILEPATH = "/Users/zuoyuzhang/Desktop/group_0700/pII/sampleTests/clients.txt";
    public static final String SERUSERFILEPATH = "/Users/shirleyhao/Desktop/csc207/group_0700/pII/clients1.ser";
    /** The file path that stores all passwords. */
    public static final String PASSWORD = "/Users/zuoyuzhang/Desktop/group_0700/pII/sampleTests/password.txt";
}
