package addonovan.robosim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * @author addonovan
 * @since 11/13/16
 */
public class DistanceSensor implements Sensor
{

    /** The maximum distance away the distance sensor can detect things [m]. */
    public static final float MAX_DISTANCE = 0.255f;

    //
    // Fields
    //

    /** The robot to which this sensor is attached.*/
    private final Robot robot;

    /** The position from the center of the robot [m]. */
    private final float x;

    /** The position from the center of the robot [m]. */
    private final float y;

    /** The angle of the sensor when attached. */
    private final float angle;

    /** The distance currently being read by the sensor. */
    private float distance;

    //
    // Constructors
    //

    /**
     * Constructs a new distance sensor on the given robot.
     *
     * @param robot
     *          The robot this is attached to.
     * @param x
     *          The x position on the robot (relative to its center) [in].
     * @param y
     *          The y position on the robot (relative to its center) [in].
     * @param angle
     *          The angle of the sensor (relative to the robot) [°].
     */
    public DistanceSensor( Robot robot, float x, float y, float angle )
    {
        this.robot = robot;
        this.x = Units.inToM( x );
        this.y = Units.inToM( y );
        this.angle = ( float ) Math.toRadians( angle );
    }

    //
    // Getters
    //

    public float getDistance()
    {
        return distance;
    }

    //
    // Overrides
    //

    @Override
    public void render()
    {
        Simulation.renderShape( ShapeRenderer.ShapeType.Line, sr ->
        {

            float alpha = 0.15f;
            if ( distance > 0.001f )
            {
                alpha = ( 0.7f ) * ( distance / MAX_DISTANCE ) + 0.3f;
            }

            sr.setColor( new Color( 1, 1, 0, alpha ) );

            float drawX = Units.mToPx( robot.getX() );
            float drawY = Units.mToPx( robot.getY() );
            float rotation = ( float ) Math.toDegrees( robot.getAngle() );

            // transform the drawing table so we can easily draw the line
            sr.translate( drawX, drawY, 0 );
            sr.rotate( 0, 0, 1, rotation );

            float x = Units.mToPx( this.x );
            float y = Units.mToPx( this.y );

            float dX = Units.mToPx( MAX_DISTANCE ) * ( float ) Math.cos( angle );
            float dY = Units.mToPx( MAX_DISTANCE ) * ( float ) Math.sin( angle );

            sr.line( x, y, x + dX, y + dY );

            // undo our transformations
            sr.rotate( 0, 0, 1, -rotation );
            sr.translate( -drawX, -drawY, 0 );
        } );
    }

    public void update()
    {
        float robotAngle = robot.getAngle();

        Vector2 robotPosition = robot.getBody().getPosition();

        // god, this took far too long to get worked out.
        float magnitude = ( float ) Math.sqrt( ( x * x ) + ( y * y ) );
        float localAngle = ( float ) Math.atan2( y, x );

        float x = magnitude * ( float ) Math.cos( robotAngle + localAngle );
        float y = magnitude * ( float ) Math.sin( robotAngle + localAngle );

        Vector2 start = robotPosition.add( x, y );

        Vector2 end = new Vector2( start );
        end.x += MAX_DISTANCE * ( float ) Math.cos( angle + robotAngle );
        end.y += MAX_DISTANCE * ( float ) Math.sin( angle + robotAngle );

        distance = 0f;
        Simulation.getWorld().rayCast( ( fixture, point, normal, fraction ) ->
        {
            if ( fraction > 1f ) return -1f;
            else
            {
                distance = ( 1 - fraction ) * MAX_DISTANCE;
                return 0f;
            }
        }, start, end );

    }

}
