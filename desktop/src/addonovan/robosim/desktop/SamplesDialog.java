package addonovan.robosim.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.StringBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

public class SamplesDialog extends JDialog
{
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList< Sample > sampleList;
    private JTextArea descriptionArea;

    /**
     * Constructs a new SamplesDialog to display the samples available
     * to the user.
     *
     * @param onChoose
     *          The action to run after the sample is chosen.
     */
    public SamplesDialog( Consumer< String > onChoose )
    {
        setContentPane( contentPane );
        setModal( true );
        getRootPane().setDefaultButton( buttonOK );

        buttonOK.addActionListener( e ->
        {
            // if the user hasn't chosen anything, what are we supposed to do?
            if ( sampleList.getSelectedValue() == null ) return;

            // return the source of the sample
            onChoose.accept( sampleList.getSelectedValue().source );

            dispose();
        } );

        buttonCancel.addActionListener( e -> onCancel() );

        setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
        addWindowListener( new WindowAdapter()
        {
            public void windowClosing( WindowEvent e )
            {
                onCancel();
            }
        } );

        contentPane.registerKeyboardAction(
                e -> onCancel(),
                KeyStroke.getKeyStroke( KeyEvent.VK_ESCAPE, 0 ),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );

        setUpList();
    }

    private void onCancel()
    {
        dispose();
    }

    //
    // Setup
    //

    /**
     * Sets up the list for choosing samples.
     */
    private void setUpList()
    {
        DefaultListModel< Sample > listModel = new DefaultListModel<>();

        // create all of the samples from the samples directory
        FileHandle[] files = Gdx.files.internal( "samples/" ).list();
        for ( FileHandle f : files )
        {
            listModel.addElement( new Sample( f ) );
        }

        // update the list and add a listener to show the sample descriptions
        sampleList.setModel( listModel );
        sampleList.addListSelectionListener( e ->
        {
            descriptionArea.setText( sampleList.getSelectedValue().description );
        } );
    }

    //
    // Static
    //

    /**
     * Shows the dialog.
     *
     * @param reference
     *          The reference for the dialog's position.
     */
    public static void showDialog( Component reference, Consumer< String > onChoose )
    {
        SamplesDialog dialog = new SamplesDialog( onChoose );
        dialog.pack();
        dialog.setLocationRelativeTo( reference );
        dialog.setVisible( true );
    }

    //
    // Nested Classes
    //

    /**
     * A sample that can be displayed in the list.
     */
    private class Sample
    {

        /** The name of the sample. */
        public final String name;

        /** The description of this sample. */
        public final String description;

        /** The source of this sample (without the metadata). */
        public final String source;

        //
        // Constructors
        //

        /**
         * Creates a new sample from the given file.
         *
         * @param location
         *          The location of the sample file.
         */
        public Sample( FileHandle location )
        {
            name = location.nameWithoutExtension();
            Gdx.app.log( "SamplesDialog", "Parsing sample: " + name );

            String text = location.readString();
            String descriptionDelimiter = "</description>";
            int descriptionEnd = text.indexOf( descriptionDelimiter );

            // the description is everything before the delimiter
            description = StringUtils.trim( text.substring( 0, descriptionEnd ) );

            // the source is everything after the it
            source = text
                    .substring( descriptionEnd + descriptionDelimiter.length() ) // only the following part after the previous
                    .trim(); // remove any trailing or leading stuffs
        }

        //
        // Overrides
        //

        @Override
        public String toString()
        {
            return name;
        }
    }

}
