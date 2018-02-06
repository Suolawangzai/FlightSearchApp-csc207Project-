package user;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.text.ParseException;
import application.BookingApp;
import application.Constants;

/**
 * A class that creates User object. This class is responsible for performing
 * following operations:
 * 1. At creation, it creates a new User object with no specifications.
 * 2. It launches the booking application.
 */
public abstract class User implements Serializable{

    // the booking application
    private BookingApp myApp;

    /**
     * Returns the instance variable, myApp, of this user.
     * @return myApp of this user, which is a BookingApp object
     */
    public BookingApp getMyApp() {
        return myApp;
    }

    public void setBookingApp(BookingApp bookingApp) {
        this.myApp = bookingApp;
    }

    /**
     * Launches the booking application, which creates a new BookingApp.
     * @throws FileNotFoundException if the file path is invalid
     * @throws ParseException if a Date object can not be parsed into correctly
     *                        and if a Double object can not be parsed into
     *                        correctly
     *//*
	public void launchApp() throws FileNotFoundException, ParseException {
		myApp = new BookingApp(Constants.FLIGHTFILEPATH,
				Constants.USERFILEPATH);	
	}*/

    // More Methods needed to be defined here in phase III

}
