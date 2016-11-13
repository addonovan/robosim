package addonovan.robosim.desktop;

import addonovan.robosim.Simulation;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ItemEvent;

/**
 * @author addonovan
 * @since 11/11/16
 */
public class EditorFrame extends JFrame
{

    private final JTextPane textEditor = new JTextPane();

    // General Panel
    private final JButton btnRun = new JButton( "Run" );

    // Simulation Control Panel
    private final JToggleButton btnTogglePause = new JToggleButton( "Pause" );
    private final JButton btnStop = new JButton( "Stop" );

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
        } ).start();
    }

    private JPanel makeGeneralControlsPanel()
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout( new BoxLayout( buttonPanel, BoxLayout.X_AXIS ) );
        {
            btnRun.addActionListener( e ->
            {
                Simulation.stop();
                Simulation.newInterpreter( textEditor.getText() );
                Simulation.initialize();
                Simulation.start();

                btnTogglePause.setSelected( false );
            } );
            buttonPanel.add( btnRun );
        }
        return buttonPanel;
    }

    private JPanel makeSimulationControlsPanel()
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout( new BoxLayout( buttonPanel, BoxLayout.X_AXIS ) );
        {
            btnTogglePause.addActionListener( e -> Simulation.togglePause() );
            buttonPanel.add( btnTogglePause );

            btnStop.addActionListener( e -> Simulation.stop() );
            buttonPanel.add( btnStop );
        }
        return buttonPanel;
    }

}
