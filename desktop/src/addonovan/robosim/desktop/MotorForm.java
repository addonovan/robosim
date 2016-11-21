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

    JPanel rootPanel;
    private JLabel motorNameLabel;
    private JSlider powerSlider;

    //
    // Updating
    //

    /**
     * Updates the MotorForm to show the new value.
     */
    public void attachTo( Motor motor )
    {
        motorNameLabel.setText( motor.name );
        motor.power.attach( power ->
        {
            powerSlider.setValue( ( int ) Math.round( power * 100 ) );
        } );
    }

}
