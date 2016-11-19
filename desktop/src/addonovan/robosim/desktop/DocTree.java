package addonovan.robosim.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

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
        actualTree.setModel( new DocTreeModel( buildNodeFor( Gdx.files.internal( "doc" ) ) ) );
    }

    /**
     * Builds the DocTreeNode for the given handle.
     *
     * This will also recursively build the nodes for any children elements
     * if the handle represents a directory.
     *
     * @param handle
     *          The file handle to build the node for.
     * @return The new DocTreeNode, or null if the handle's name is "_desc.txt"
     */
    private DocTreeNode buildNodeFor( FileHandle handle )
    {
        if ( handle.name().equals( "_desc.txt" ) ) return null;

        // build a folder
        if ( handle.isDirectory() )
        {
            String name = handle.name();
            String description = handle.child( "_desc.txt" ).readString();

            DocTreeNode node = new DocTreeNode( name, description, true );

            // recursively create the child nodes
            for ( FileHandle f : handle.list() )
            {
                DocTreeNode child = buildNodeFor( f );
                if ( child != null )
                {
                    node.add( child );
                }
            }

            return node;
        }
        // build a single element
        else
        {
            String name = handle.nameWithoutExtension();
            String description = handle.readString();

            return new DocTreeNode( name, description, false );
        }
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
