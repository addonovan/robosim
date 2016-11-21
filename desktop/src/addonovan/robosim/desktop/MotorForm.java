package addonovan.robosim.desktop;

import addonovan.robosim.Motor;
import addonovan.robosim.Simulation;

import javax.swing.*;
import javax.swing.event.ChangeListener;

/**
 * Displays debugging information for a single motor.
 *
 * @author addonovan
 * @since 11/20/16
 */
public class MotorForm
{

    //
    // Components
    //

    JPanel rootPanel;
    private JLabel motorNameLabel;
    private JSlider powerSlider;

    //
    // Fields
    //

    /** The motor we're currently watching. */
    private Motor motor;

    /** If the change to the slider's location was programmatically generated. */
    private boolean programmatic = false;

    //
    // Constructor
    //

    public MotorForm()
    {
        SwingUtilities.invokeLater( () ->
        {
            Simulation.running.attach( running -> powerSlider.setEnabled( running ) );
        } );

        powerSlider.addChangeListener( e ->
        {
            if ( motor == null || programmatic ) return;

            motor.power.setValue( powerSlider.getValue() / 100.0 );
            System.out.println( "Updated power to: " + motor.power.getValue() );
        } );
    }

    //
    // Updating
    //

    /**
     * Updates the MotorForm to show the new value.
     */
    public void attachTo( Motor motor )
    {
        this.motor = motor;
        motorNameLabel.setText( motor.name );
        motor.power.attach( power ->
        {
            int value = ( int ) Math.floor( power * 100 );
            if ( powerSlider.getValue() == value ) return;

            programmatic = true;
            powerSlider.setValue( value );
            programmatic = false;

        } );
    }

}
