package addonovan.robosim.desktop;

import addonovan.robosim.Motor;

import javax.swing.*;

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

    private JLabel motorNameLabel;
    private JSlider powerSlider;

    //
    // Fields
    //

    /** The motor we're showing the values for. */
    private final Motor motor;

    //
    // Constructors
    //

    /**
     * Constructs a new MotorForm for debugging the provided motor.
     *
     * @param name
     *          The name of the motor we're debugging.
     * @param motor
     *          The motor to debug.
     */
    public MotorForm( String name, Motor motor )
    {
        this.motorNameLabel.setText( name );
        this.motor = motor;
    }

    //
    // Updating
    //

    /**
     * Updates the MotorForm to show the new value.
     */
    public void update()
    {
        powerSlider.setValue( Math.round( motor.power * 100 ) );
    }

}
