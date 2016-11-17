package addonovan.robosim;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.ArrayList;
import java.util.List;

/**
 * A definition for the robot and its hardware. This is
 * the interface between the script and the rest of the
 * world.
 *
 * @author addonovan
 * @since 11/11/16
 */
public class Robot extends Entity
{

    //
    // Constants
    //

    /** The width of all robots. [px] */
    private static final float WIDTH = Units.inToPx( 18f );

    /** The height of all robots. [px] */
    private static final float HEIGHT = Units.inToPx( 18f );

    private static final float WIDTH_M = Units.pxToM( WIDTH );

    private static final float HEIGHT_M = Units.pxToM( HEIGHT );

    //
    // Fields
    //

    /** The body this uses in the physics simulations. */
    private final Body body;

    /** The sensors in this robot. */
    private final List< Sensor > sensors = new ArrayList<>();

    //
    // Constructors
    //

    Robot()
    {
        body = makeBody();
        body.setTransform( Units.inToM( 11 ) + Units.pxToM( WIDTH / 2 ), Units.inToM( 11 ) + Units.pxToM( HEIGHT / 2 ), 0f );

        sensors.add( new DistanceSensor( this, 9f, 0f, 0f ) );
    }

    //
    // Rendering
    //

    @Override
    public void render()
    {
        Simulation.renderShape( ShapeRenderer.ShapeType.Line, sr ->
        {
            sr.setColor( Color.WHITE );

            float bodyX = Units.mToPx( getX() );
            float bodyY = Units.mToPx( getY() );

            float x = bodyX - ( WIDTH / 2 );
            float y = bodyY - ( WIDTH / 2 );

            sr.rect( x, y, WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT, 1, 1, Math.toDegrees( body.getAngle() ) );
        } );

        Simulation.renderShape( ShapeRenderer.ShapeType.Filled, sr ->
        {
            sr.setColor( Color.GREEN );

            float x = Units.mToPx( getX() );
            float y = Units.mToPx( getY() );
            float xOffset = Units.mToPx( getMotorOffsetX() );
            float yOffset = Units.mToPx( getMotorOffsetY() );

            sr.circle( x + xOffset, y + yOffset, 2 );
            sr.circle( x - xOffset, y + yOffset, 2 );
            sr.circle( x - xOffset, y - yOffset, 2 );
            sr.circle( x + xOffset, y - yOffset, 2 );
        } );

        sensors.forEach( Sensor::update );
        sensors.forEach( Sensor::render );
    }

    //
    // Body
    //

    /**
     * @return The newly created body for this robot.
     */
    private Body makeBody()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        Body body = Simulation.getWorld().createBody( bodyDef );

        PolygonShape shape = new PolygonShape();
        shape.setAsBox( Units.pxToM( WIDTH / 2 ), Units.pxToM( HEIGHT / 2 ) );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 25f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.1f;

        body.createFixture( fixtureDef );
        body.setLinearDamping( 20f );
        body.setAngularDamping( 20f );
        shape.dispose();

        return body;
    }

    @Override
    Body getBody()
    {
        return body;
    }

    //
    // Actions
    //

    public Sensor getSensor( int i )
    {
        return sensors.get( i );
    }

    private float getMotorOffsetX()
    {
        return 0.45f * WIDTH_M * Math.sin( getAngle() );
    }

    private float getMotorOffsetY()
    {
        return 0.40f * HEIGHT_M * Math.sin( getAngle() );
    }

    public void powerMotor( float power, String motorName )
    {
        boolean onLeft = motorName.contains( "left" );
        boolean onFront = motorName.contains( "front" );

        // the center of the robot
        float x = getX() + ( onLeft ? -1 : 1 ) * getMotorOffsetX();
        float y = getY() + ( onFront ? 1 : -1 ) * getMotorOffsetY();

        // NeveRest 40 scaled to power
        float force = 40 * power;

        float force_x = force * Math.cos( getAngle() );
        float force_y = force * Math.sin( getAngle() );

        // apply the force from one motor on there
        body.applyForce( force_x, force_y, x, y, true );
    }

    void move( float power )
    {
        if ( power < -1f || power > 1f )
        {
            throw new IllegalArgumentException( "Power must be on the interval [-1f, 1f]. (was " + power + ")" );
        }

        // 4x NeveRest 40 scaled to power
        float force = 160 * power;

        float force_x = force * Math.cos( getAngle() );
        float force_y = force * Math.sin( getAngle() );

        body.applyForceToCenter( force_x, force_y, true );
    }

    void rotate( float power )
    {
        if ( power < -1f  || power > 1f )
        {
            throw new IllegalArgumentException( "Power must be on the interval [-1f, 1f]. (was " + power + ")" );
        }

        body.applyTorque( 40 * power, true );
    }

}
