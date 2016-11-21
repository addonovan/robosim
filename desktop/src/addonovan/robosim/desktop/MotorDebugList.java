package addonovan.robosim.desktop;

import addonovan.robosim.Motor;
import addonovan.robosim.Simulation;

import javax.swing.*;
import java.util.ArrayList;

/**
 * @author addonovan
 * @since 11/20/16
 */
public class MotorDebugList
{

    //
    // Components
    //

    private JPanel rootPanel;
    private JPanel motorPanel;

    //
    // Fields
    //

    /** The motor forms we've created. */
    private final ArrayList< MotorForm > motorForms = new ArrayList<>();

    //
    // Constructors
    //

    /**
     * Constructs a new MotorDebugList, which displays a MotorForm for every motor
     * on the robot.
     */
    public MotorDebugList()
    {
        SwingUtilities.invokeLater( () ->
        {
            Simulation.running.attach( running -> init() );
        } );
    }

    //
    // Initialization
    //

    private void init()
    {
        motorForms.clear();
        motorPanel.removeAll();

        for ( Motor motor : Simulation.robot.getMotors() )
        {
            MotorForm form = new MotorForm( motor );
            motorForms.add( form );
            motorPanel.add( form.rootPanel );
        }

        new Thread( () ->
        {
            while ( true )
            {
                motorForms.forEach( MotorForm::update );

                try
                {
                    Thread.sleep( 20 );
                }
                catch ( InterruptedException e ) {}
            }

        } ).start();
    }

}
