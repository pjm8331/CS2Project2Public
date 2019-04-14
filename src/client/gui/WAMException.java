package client.gui;

/**
 * Custom exception for all Whack A Mole classes for errors
 *@author John Baxley(jmb3471)
 *@author Peter Mastropaolo(pjm8331)
 */
public class WAMException extends Exception {

    /**
     * WAM exception with a message
     * @param in message for the error
     */
    public WAMException(String in){
        super(in);
    }

    /**
     * WAM exception with a throwable
     * @param throwable the reason the error was thrown
     */
    public WAMException(Throwable throwable){
        super(throwable);
    }

    /**
     * WAM exception with both a throwable and a message
     * @param in message for the error
     * @param throwable the reason the error was thrown
     */
    public WAMException(String in, Throwable throwable){
        super(in, throwable);
    }
}
