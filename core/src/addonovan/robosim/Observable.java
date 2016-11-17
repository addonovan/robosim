/*
 * Copyright (c) 2016 Austin D. Donovan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to
 * do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
