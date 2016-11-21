package addonovan.robosim.desktop;

import addonovan.robosim.Motor;
import addonovan.robosim.Simulation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

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
    private MotorForm frontLeft;
    private MotorForm frontRight;
    private MotorForm backLeft;
    private MotorForm backRight;

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
        motorForms.add( frontLeft );
        motorForms.add( frontRight );
        motorForms.add( backLeft );
        motorForms.add( backRight );

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
        List< Motor > motors = Simulation.robot.getMotors();

        for ( Motor m : motors )
        {
            switch ( m.name.toLowerCase() )
            {
                case "mtr_fl":
                    frontLeft.attachTo( m );
                    break;

                case "mtr_fr":
                    frontRight.attachTo( m );
                    break;

                case "mtr_bl":
                    backLeft.attachTo( m );
                    break;

                case "mtr_br":
                    backRight.attachTo( m );
                    break;
            }
        }
    }

}
