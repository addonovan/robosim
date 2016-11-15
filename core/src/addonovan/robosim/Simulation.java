package addonovan.robosim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.sun.istack.internal.NotNull;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.util.ArrayList;
import java.util.List;
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

    public static final String EMPTY_PROGRAM = "def loop(self):\n    pass";

    /** The layout for the program. */
    private static final String PROGRAM_LAYOUT = Gdx.files.internal( "PyRobot.py" ).readString();

    //
    // Fields
    //

    /** The robot being simulated. */
    @NotNull public static Robot robot;

    private static final List< Renderable > renderables = new ArrayList<>();

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

    /** The speed at which the simulation runs. */
    public static Observable< Float > runSpeed = new Observable<>( 1f );

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

        String modifiedSource = PROGRAM_LAYOUT + "\n";
        modifiedSource += "    " + source.replaceAll( "\n", "\n    " );
        interpreter.exec( modifiedSource );

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
        world = new World( new Vector2( 0f, 0f ), false );
        robot = new Robot();
        interpreter.set( "robot", robot );
        interpreter.exec( "pyRobot = PyRobot(robot)" );
        loop = interpreter.get( "pyRobot" ).__getattr__( "loop" );

        renderables.clear();
        renderables.add( new Wall(   2f, 144f,   8f,   8f ) );
        renderables.add( new Wall( 144f,   2f,   8f,   8f ) );
        renderables.add( new Wall(   2f, 146f, 152f,   8f ) );
        renderables.add( new Wall( 146f,   2f,   8f, 152f ) );

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
        renderables.forEach( Renderable::render );
    }

    /**
     * Loops the robot and moves on.
     */
    static void update()
    {
        // only update if we're running
        if ( running.getValue() && !paused.getValue() )
        {
            float deltaTime = 1 / 60f * runSpeed.getValue();

            runtime.setValue( runtime.getValue() + deltaTime );
            try
            {
                loop.__call__();
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }

            if ( Gdx.input.isKeyPressed( Input.Keys.W ) )
            {
                robot.move( 1f );
            }
            if ( Gdx.input.isKeyPressed( Input.Keys.S ) )
            {
                robot.move( -1f );
            }
            if ( Gdx.input.isKeyPressed( Input.Keys.A ) )
            {
                robot.rotate( 0.25f );
            }
            if ( Gdx.input.isKeyPressed( Input.Keys.D ) )
            {
                robot.rotate( -0.25f );
            }

            world.step( deltaTime, 6, 2 );
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
