package addonovan.robosim;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.sun.istack.internal.NotNull;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.util.function.Consumer;

/**
 * @author addonovan
 * @since 11/11/16
 */
public final class Simulation
{

    //
    // Constants
    //

    public static final String EMPTY_PROGRAM = "def loop():\n    return";

    //
    // Fields
    //

    /** The robot being simulated. */
    @NotNull public static Robot robot;

    /** The python interpreter in case access is needed. */
    @NotNull private static PythonInterpreter interpreter;

    /** The update loop for the robot. */
    @NotNull private static PyObject loop;

    /** The world being used for simulations. */
    @NotNull private static World world;

    /** If the simulation is currently running or not (may be paused). */
    public static Observable< Boolean > running = new Observable<>( false );

    /** If the simulation is currently paused. */
    public static Observable< Boolean > paused = new Observable<>( false );

    /** The time the simulation has been running. (Measured in seconds). */
    public static Observable< Double > runtime = new Observable<>( 0.0 );

    /** The callback used whenever the runtime is updated. */
    @NotNull private static Consumer< Double > runtimeCallback;

    /** The renderer used to draw new shapes and whatnot. */
    private static final ShapeRenderer shapeRenderer = new ShapeRenderer();

    //
    // Constructors
    //

    static
    {
        newInterpreter( EMPTY_PROGRAM );
    }

    //
    // Actions
    //

    /**
     * Creates a new PythonInterpreter for the given source code.
     *
     * @param source
     *          The new source of the file.
     */
    public static void newInterpreter( String source )
    {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.set( "robot", robot );
        interpreter.exec( source );

        loop = interpreter.get( "loop" );
        Simulation.interpreter = interpreter;
    }

    /**
     * Initializes a new simulation. This should be called before
     * start() is called again if a new interpreter has been created.
     */
    public static void initialize()
    {
        if ( world != null )
        {
            world.dispose();
        }
        world = new World( new Vector2( 0, 0 ), false );
        robot = new Robot();

        runtime.setValue( 0.0 );
        running.setValue( false );
        paused.setValue( false );
    }

    /**
     * Renders the simulation onto the screen.
     */
    static void render()
    {
        robot.render();
    }

    /**
     * Loops the robot and moves on.
     */
    static void update()
    {
        // only update if we're running
        if ( running.getValue() && !paused.getValue() )
        {
            runtime.setValue( runtime.getValue() + 1 / 60f );
            world.step( 1 / 60f, 6, 2 );
            loop.__call__();
        }
    }

    /**
     * Performs a rendering call in the given context.
     *
     * @param shapeType
     *          The type of shape to render.
     * @param lambda
     *          The rendering commands.
     */
    static void renderShape( ShapeRenderer.ShapeType shapeType, Consumer< ShapeRenderer > lambda )
    {
        shapeRenderer.begin( shapeType );
        lambda.accept( shapeRenderer );
        shapeRenderer.end();
    }

    //
    // Getters/Setters
    //

    public static void start()
    {
        running.setValue( true );
    }

    public static void stop()
    {
        running.setValue( false );
    }

    public static void togglePause()
    {
        paused.setValue( !paused.getValue() );
    }

    public static World getWorld()
    {
        return world;
    }

}
