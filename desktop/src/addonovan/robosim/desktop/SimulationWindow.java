package addonovan.robosim.desktop;

import addonovan.robosim.RobotSimulator;
import addonovan.robosim.Units;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;

import javax.swing.*;
import java.awt.*;

/**
 * @author addonovan
 * @since 11/15/16
 */
public class SimulationWindow
{
    private JPanel rootPanel;
    private JPanel simulationPanel;
    private JCheckBox runningCheckBox;
    private JCheckBox pausedCheckBox;
    private JSlider sliderRunSpeed;
    private JEditorPane editorPane;
    private JButton resetButton;
    private JButton restartButton;

    public SimulationWindow()
    {
        setUpSimulationPanel();
    }

    //
    // Set ups
    //

    /**
     * Sets up the simulation panel with run-time stuff.
     */
    private void setUpSimulationPanel()
    {
        int size = ( int ) Units.inToPx( 160f );
        Dimension dimensions = new Dimension( size, size );
        simulationPanel.setMinimumSize( dimensions );
        simulationPanel.setPreferredSize( dimensions );
        simulationPanel.setMaximumSize( dimensions );

        // set up the canvas
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = config.height = size;
        config.title = "Robot Simulator";
        LwjglAWTCanvas lwjglCanvas = new LwjglAWTCanvas( new RobotSimulator(), config );

        simulationPanel.add( lwjglCanvas.getCanvas(), BorderLayout.CENTER );
    }

    //
    // Main
    //


    public static void main( String[] args )
    {
        SwingUtilities.invokeLater( () ->
        {
            JFrame frame = new JFrame( "Robot Simulator" );
            frame.setContentPane( new SimulationWindow().rootPanel );
            frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
            frame.pack();
            frame.setVisible( true );
        } );
    }
}
