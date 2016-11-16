package addonovan.robosim;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * An implementation of the observable pattern for any value. It was easier to
 * write this from scratch than try to use the java.util version.
 *
 * @author addonovan
 * @since 11/13/16
 */
public class Observable< T >
{

    /** The observers attached to this state. */
    private List< Consumer< T > > observers = new ArrayList<>();

    /** The backing field of this observer. */
    private T value = null;

    //
    // Constructors
    //

    /**
     * Initializes the observable with the backing value set to null.
     */
    public Observable()
    {
        this( null );
    }

    /**
     * Initializes the observable with the given default value.
     *
     * @param defaultValue
     *          The default value of the backing field.
     */
    Observable( T defaultValue )
    {
        value = defaultValue;
    }

    //
    // Actions
    //

    /**
     * Attaches a new listener.
     *
     * @param listener
     *          The listener for state changes.
     */
    public void attach( Consumer< T > listener )
    {
        observers.add( listener );
    }

    /**
     * Dispatches all the listeners as if there was a state change.
     */
    private void dispatchListeners()
    {
        observers.forEach( booleanConsumer -> booleanConsumer.accept( value ) );
    }

    /**
     * Sets the value of this observable. This will trigger the
     * listeners.
     *
     * @param value
     *          The backing value of this observable.
     */
    public void setValue( T value )
    {
        this.value = value;
        dispatchListeners();
    }

    /**
     * @return The value of this observable.
     */
    public T getValue()
    {
        return value;
    }

}
