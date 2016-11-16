package addonovan.robosim.desktop;

import addonovan.robosim.RobotSimulator;
import addonovan.robosim.Simulation;
import addonovan.robosim.Units;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import jsyntaxpane.syntaxkits.PythonSyntaxKit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * @author addonovan
 * @since 11/15/16
 */
public class SimulationWindow implements WindowListener
{
    private JPanel rootPanel;
    private JPanel simulationPanel;
    private JSlider sliderRunSpeed;
    private JEditorPane editorPane;
    private JButton startButton;
    private JButton stopButton;
    private JButton restartButton;
    private JButton resetButton;
    private JLabel lblRuntime;

    //
    // Constructors
    //

    public SimulationWindow()
    {
        setUpSimulationPanel();
        setUpScriptEditor();
        setUpCallbacks();
        setUpControls();
    }

    //
    // Actions
    //

    private void toggleControlButtons( boolean running )
    {
        startButton.setEnabled( !running );
        stopButton.setEnabled( running );
        restartButton.setEnabled( running );
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

    private void setUpScriptEditor()
    {
        PythonSyntaxKit.initKit();
        editorPane.setContentType( "text/python" );
        editorPane.setText( Simulation.EMPTY_PROGRAM );
    }

    private void setUpCallbacks()
    {
        Simulation.runtime.attach( time -> lblRuntime.setText( String.format( "+%.2f s", time ) ) );

        Simulation.runSpeed.attach( speed ->
        {
            int newPos = ( int ) ( speed * 100.0 );
            if ( sliderRunSpeed.getValue() == newPos ) return; // prevent a stack overflow

            sliderRunSpeed.setValue( newPos );
        } );

        Simulation.running.attach( this::toggleControlButtons );
    }

    private void setUpControls()
    {
        startButton.addActionListener( e ->
        {
            resetButton.doClick();
            Simulation.start();
        } );

        stopButton.addActionListener( e -> Simulation.stop() );

        resetButton.addActionListener( e ->
        {
            Simulation.newInterpreter( editorPane.getText() );
            Simulation.initialize();
        } );

        sliderRunSpeed.addChangeListener( e -> Simulation.runSpeed.setValue( sliderRunSpeed.getValue() / 100.0 ) );
    }

    //
    // Main
    //

    public static void main( String[] args )
    {
        try
        {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        // force this to be #FAFAFA, regardless of system theme
        UIManager.getLookAndFeelDefaults().put( "EditorPane.background", new Color( 250, 250, 250 ) );

        SwingUtilities.invokeLater( () ->
        {
            JFrame frame = new JFrame( "Robot Simulator" );
            frame.setContentPane( new SimulationWindow().rootPanel );
            frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
            frame.pack();
            frame.setVisible( true );
        } );
    }

    //
    // WindowListener
    //

    @Override
    public void windowClosed( WindowEvent e )
    {
        Gdx.app.exit();
    }

    @Override public void windowOpened( WindowEvent e ) {}
    @Override public void windowClosing( WindowEvent e ) {}
    @Override public void windowIconified( WindowEvent e ) {}
    @Override public void windowDeiconified( WindowEvent e ) {}
    @Override public void windowActivated( WindowEvent e ) {}
    @Override public void windowDeactivated( WindowEvent e ) {}
}
