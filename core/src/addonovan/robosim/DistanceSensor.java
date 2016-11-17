package addonovan.robosim;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * A sensor which detects any objects within a certain distance.
 *
 * This sensor will draw a straight line in the angle it's pointed which
 * will become more bright the closer an object is to it.
 *
 * @author addonovan
 * @since 11/13/16
 */
public class DistanceSensor implements Sensor
{

    /** The maximum distance away the distance sensor can detect things [m]. */
    public static final float MAX_DISTANCE = 2.55f;

    //
    // Fields
    //

    /** The robot to which this sensor is attached.*/
    private final Robot robot;

    /** The vector that points to the position of this sensor on the robot. */
    private final Vector2 positionVector;

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
    DistanceSensor( Robot robot, float x, float y, float angle )
    {
        x = Units.inToM( x );
        y = Units.inToM( y );
        positionVector = new Vector2( x, y );

        this.robot = robot;
        this.angle = Math.toRadians( angle );
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
                alpha = ( 0.7f ) * ( 1 - ( distance / MAX_DISTANCE ) ) + 0.3f;
            }

            sr.setColor( new Color( 1, 1, 0, alpha ) );

            Vector2 start = getStartPosition();
            Vector2 end = getEndPosition( start );

            sr.line( Units.mToPx( start ), Units.mToPx( end ) );
        } );
    }

    public void update()
    {
        Vector2 start = getStartPosition();
        Vector2 end = getEndPosition( start );

        distance = -1f;
        Simulation.getWorld().rayCast( ( fixture, point, normal, fraction ) ->
        {
            if ( fraction > 1f ) return -1f;
            else
            {
                float newDistance = fraction * MAX_DISTANCE;
                distance = Math.max( distance, newDistance );
                return 0f;
            }
        }, start, end );

    }

    private Vector2 getStartPosition()
    {
        float robotAngle = robot.getAngle();
        float x = positionVector.len() * Math.cos( robotAngle + positionVector.angle() );
        float y = positionVector.len() * Math.sin( robotAngle + positionVector.angle() );

        return robot.getBody().getPosition().add( x, y );
    }

    private Vector2 getEndPosition( Vector2 start )
    {
        float robotAngle = robot.getAngle();

        Vector2 end = new Vector2( start );
        end.x += MAX_DISTANCE * Math.cos( angle + robotAngle );
        end.y += MAX_DISTANCE * Math.sin( angle + robotAngle );

        return end;
    }

}
