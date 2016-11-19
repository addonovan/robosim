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
 * The window that displays information to the user.
 *
 * @author addonovan
 * @since 11/15/16
 */
public class SimulationWindow implements WindowListener
{

    //
    // Components
    //

    private JPanel rootPanel;
    private JPanel simulationPanel;
    private JSlider sliderRunSpeed;
    private JEditorPane scriptEditor;
    private JButton startButton;
    private JButton stopButton;
    private JButton restartButton;
    private JButton resetButton;
    private JLabel lblRuntime;
    private DocTree docTree; // this isn't actually an error, the IDE is just being wonky, I guess
    private JButton openSampleButton;
    private JButton openButton;
    private JButton saveButton;

    //
    // Constructors
    //

    public SimulationWindow()
    {
        setUpSimulationPanel();
        setUpEditor();
        setUpCallbacks();
        setUpControls();
    }

    //
    // Actions
    //

    /**
     * Toggles the control buttons to update to the current state.
     *
     * @param running
     *          If the game is running.
     */
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
        config.vSyncEnabled = true;
        config.samples = 4;

        LwjglAWTCanvas lwjglCanvas = new LwjglAWTCanvas( new RobotSimulator(), config );
        simulationPanel.add( lwjglCanvas.getCanvas(), BorderLayout.CENTER );
    }

    /**
     * Sets up the script editor.
     */
    private void setUpEditor()
    {
        PythonSyntaxKit.initKit();
        scriptEditor.setContentType( "text/python" );
        scriptEditor.setText( Simulation.EMPTY_PROGRAM );

        openButton.addActionListener( e -> {
            // TODO show the open file dialog and let the user open a file
        } );

        saveButton.addActionListener( e -> {
            // TODO show the save file dialog and let the user save a file
        } );

        openSampleButton.addActionListener( e -> {
            SamplesDialog.showDialog( rootPanel, s -> {
                scriptEditor.setText( s );
            } );
        } );
    }

    /**
     * Attaches the call backs to all the simulation observables which
     * need to be hooked.
     */
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

    /**
     * Sets up the simulation controls.
     */
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
            Simulation.newInterpreter( scriptEditor.getText() );
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
            SimulationWindow window = new SimulationWindow();
            JFrame frame = new JFrame( "Robot Simulator" );
            frame.setContentPane( window.rootPanel );
            frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
            frame.addWindowListener( window );
            frame.pack();
            frame.setVisible( true );
        } );
    }

    //
    // WindowListener
    //

    @Override public void windowClosing( WindowEvent e )
    {
        Gdx.app.exit();
    }

    @Override public void windowClosed( WindowEvent e ) {}
    @Override public void windowOpened( WindowEvent e ) {}
    @Override public void windowIconified( WindowEvent e ) {}
    @Override public void windowDeiconified( WindowEvent e ) {}
    @Override public void windowActivated( WindowEvent e ) {}
    @Override public void windowDeactivated( WindowEvent e ) {}

}
