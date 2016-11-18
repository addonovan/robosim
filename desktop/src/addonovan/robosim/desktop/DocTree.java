package addonovan.robosim.desktop;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 * The tree which shows documentation for methods and the
 * members and fields available to the user when scripting.
 *
 * @author addonovan
 * @since 11/17/16
 */
public class DocTree
{

    //
    // Components
    //

    private JPanel rootPanel;
    private JTree actualTree;
    private JTextArea descriptionField;

    //
    // Constructors
    //

    /**
     * Constructs a new Documentation Tree for everything that's available to the
     * user.
     */
    public DocTree()
    {
        actualTree.addTreeSelectionListener( e -> {

            DocTreeNode node = ( DocTreeNode ) actualTree.getLastSelectedPathComponent();
            descriptionField.setText( node.getUserObject().description );

        } );
        buildTreeModel();
    }

    //
    // Actions
    //

    /**
     * Builds the tree model with the current settings.
     */
    public void buildTreeModel()
    {
        DocTreeNode root = new DocTreeNode( "Documentation", "Documentation for everything", true );

        DocTreeNode motors = root.addNode( "Motors", "The motors on the robot", true );
        {
            motors.addNode( "self.mtr_fr", "Motor", "The front-right motor on the robot" );
            motors.addNode( "self.mtr_fl", "Motor", "The front-left motor on the robot" );
            motors.addNode( "self.mtr_br", "Motor", "The back-right motor on the robot" );
            motors.addNode( "self.mtr_bl", "Motor", "The back-left motor on the robot" );

            DocTreeNode motor = motors.addNode( "Motor", "Representation of a motor on a robot.", true );
            {
                motor.addNode( "power", "float", "A value between [-1.0, 1.0] which tells the motor how fast to spin." );
            }
        }

        DocTreeNode sensors = root.addNode( "Sensors", "The sensors on the robot", true );
        {
            sensors.addNode( "self.sensor_distance", "DistanceSensor", "The forward-facing distance sensor." );
            DocTreeNode distanceSensor = sensors.addNode( "DistanceSensor", "Representation of the ModernRobotics distance sensor.", true );
            {
                distanceSensor.addNode(
                        "getDistance()",
                        "=> float",
                        "Returns a number [0.0, 2.55] which represents how far away whatever directly in front of the robot is, in [m].",
                        "Will return a -1.0 if the sensor's range is exceeded."
                );
            }
        }

        actualTree.setModel( new DocTreeModel( root ) );
    }

    //
    // Nested Classes
    //

    /**
     * An item in the DocTree.
     */
    private class DocTreeItem
    {

        /** The name of the item. */
        public final String name;

        /** The description of the item. */
        public final String description;

        /** If this is a folder or not. */
        public final boolean isFolder;

        //
        // Constructors
        //

        /**
         * Creates a new DocTreeItem with the given attributes.
         *
         * @param name
         *          The name of this item.
         * @param description
         *          The description of this item.
         * @param isFolder
         *          If this item may have children or not.
         */
        public DocTreeItem( String name, String description, boolean isFolder )
        {
            if ( name == null ) throw new NullPointerException( "DocTreeItem cannot have a null name!" );
            if ( description == null ) throw new NullPointerException( "DocTreeItem cannot have a null description!" );


            this.name = name;
            this.description = description;
            this.isFolder = isFolder;
        }

    }

    /**
     * A special type of DefaultMutableTreeNode that only accepts
     * and returns DocTreeItems.
     */
    private class DocTreeNode extends DefaultMutableTreeNode
    {

        //
        // Constructors
        //

        /**
         * Constructs a new DocTreeNode based off of the given DocTreeItem
         * parameters.
         */
        public DocTreeNode( String name, String description, boolean isFolder )
        {
            super( new DocTreeItem( name, description, isFolder ) );
        }

        //
        // Actions
        //

        /**
         * Creates a new node with the given information.
         *
         * @param name
         *          {@see DocTreeItem.name}
         * @param description
         *          {@see DocTreeItem.description}
         * @param isFolder
         *          {@see DocTreeItem.isFolder}
         * @return The newly created node.
         */
        public DocTreeNode addNode( String name, String description, boolean isFolder )
        {
            DocTreeNode node = new DocTreeNode( name, description, isFolder );
            add( node );
            return node;
        }

        /**
         * Creates a new node with the given information.
         *
         * @param name
         *          {@see DocTreeItem.name}
         * @param descriptions
         *          The parts of the description, stitched together into one, delimited
         *          with \n\n.
         */
        public void addNode( String name, String... descriptions )
        {
            String description = "";
            for ( String s : descriptions )
            {
                description += s;
                description += "\n\n";
            }

            addNode( name, description, false );
        }

        //
        // Overrides
        //

        @Override
        public void setUserObject( Object userObject )
        {
            if ( !( userObject instanceof  DocTreeItem ) )
            {
                throw new IllegalArgumentException( "All items must be DocTreeItems!" );
            }

            super.setUserObject( userObject );
        }

        @Override
        public DocTreeItem getUserObject()
        {
            return ( DocTreeItem ) super.getUserObject();
        }

        @Override
        public String toString()
        {
            return getUserObject().name;
        }
    }

    /**
     * The custom tree model for the DocTree.
     */
    private class DocTreeModel extends DefaultTreeModel
    {

        //
        // Constructors
        //

        /**
         * Constructs a new DocTreeModel based off of the given node.
         *
         * @param root
         *          The root node.
         */
        public DocTreeModel( DocTreeNode root )
        {
            super( root );
        }

    }

}
