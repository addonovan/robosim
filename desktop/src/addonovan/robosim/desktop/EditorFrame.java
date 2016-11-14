package addonovan.robosim.desktop;

import addonovan.robosim.Simulation;

import javax.swing.*;
import java.awt.*;

/**
 * @author addonovan
 * @since 11/11/16
 */
public class EditorFrame extends JFrame
{

    private final JTextPane textEditor = new JTextPane();

    // General Panel
    private final JButton btnStart = new JButton( "Start" );
    private final JButton btnReset = new JButton( "Reset" );

    // Simulation Control Panel
    private final JToggleButton btnTogglePause = new JToggleButton( "Pause" );
    private final JButton btnRestart = new JButton( "Restart" );
    private final JButton btnStop = new JButton( "Stop" );
    private final JSlider sliderSpeed = new JSlider( 1, 500, 100 );

    private final JLabel lblRuntime = new JLabel( "" );

    public EditorFrame()
    {
        try
        {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        setLayout( new BorderLayout() );

        final JPanel generalControlPanel = makeGeneralControlsPanel();
        final JPanel simControlPanel = makeSimulationControlsPanel();

        textEditor.setText( Simulation.EMPTY_PROGRAM );
        add( BorderLayout.CENTER, textEditor );
        add( BorderLayout.SOUTH, lblRuntime );

        setSize( 450, 350 );
        setTitle( "Script Editor" );
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );

        // set up the callbacks on a different thread so this isn't blocked while the simulation
        // is being set up
        new Thread( () ->
        {
            Simulation.runtime.attach( time -> lblRuntime.setText( String.format( "+%.2f s", time ) ) );
            Simulation.running.attach( running ->
            {
                remove( generalControlPanel );
                remove( simControlPanel );

                JPanel toAdd = running ? simControlPanel : generalControlPanel;

                add( toAdd, BorderLayout.NORTH );

                revalidate();
                repaint();
            } );

            Simulation.paused.attach( paused ->
            {
                if ( paused )
                {
                    btnTogglePause.setText( "Unpause" );
                    btnTogglePause.setSelected( true );
                }
                else
                {
                    btnTogglePause.setText( "Pause" );
                    btnTogglePause.setSelected( false );
                }
            } );

            Simulation.runSpeed.attach( speed ->
            {
                int newPos = ( int ) ( speed * 100 );
                if ( sliderSpeed.getValue() == newPos ) return; // this prevents an endless cycle of calls

                sliderSpeed.setValue( newPos );
            } );
        } ).start();
    }

    private JPanel makeGeneralControlsPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout( new BoxLayout( panel, BoxLayout.X_AXIS ) );
        {
            btnStart.addActionListener( e ->
            {
                btnReset.doClick();
                Simulation.start();
            } );
            panel.add( btnStart );

            btnReset.addActionListener( e ->
            {
                Simulation.newInterpreter( textEditor.getText() );
                Simulation.initialize();
            } );
            panel.add( btnReset );
        }
        return panel;
    }

    private JPanel makeSimulationControlsPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout( new BoxLayout( panel, BoxLayout.X_AXIS ) );
        {
            btnTogglePause.addActionListener( e -> Simulation.togglePause() );
            panel.add( btnTogglePause );

            btnRestart.addActionListener( e -> {
                btnStop.doClick();
                btnStart.doClick();
            } );
            panel.add( btnRestart );

            btnStop.addActionListener( e -> Simulation.stop() );
            panel.add( btnStop );

            sliderSpeed.addChangeListener( e -> Simulation.runSpeed.setValue( sliderSpeed.getValue() / 100f ) );
            panel.add( sliderSpeed );
        }
        return panel;
    }

}
