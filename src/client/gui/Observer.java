package client.gui;

/**
 * An interface representing any class whose objects get notified when
 * the objects they are observing update them.
 *
 * @author John Baxley(jmb3471)
 * @author Peter Mastropaolo(pjm8331)
 */
public interface Observer< Subject > {

    /**
     * @param subject the object that wishes to inform this object
     *                about something that has happened.
     * Observer design pattern</a>
     */
    void update(Subject subject);
}
