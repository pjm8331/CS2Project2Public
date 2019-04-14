package client.gui;

public class WAMException extends Exception {

    public WAMException(String in){
        super(in);
    }

    public WAMException(Throwable throwable){
        super(throwable);
    }

    public WAMException(String in, Throwable throwable){
        super(in, throwable);
    }
}
