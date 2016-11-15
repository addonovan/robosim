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
    static final float WIDTH = Units.inToPx( 18f );

    /** The height of all robots. [px] */
    static final float HEIGHT = Units.inToPx( 18f );

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

    public Robot()
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

            sr.rect( x, y, WIDTH / 2, HEIGHT / 2, WIDTH, HEIGHT, 1, 1, ( float ) Math.toDegrees( body.getAngle() ) );
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

    public void powerMotor( float power, String motorName )
    {
        // the center of the robot
        float x = getX();
        float y = getY();

        // shift over to the correct side
        if ( motorName.contains( "left" ) ) // we're on the LEFT
        {
            x -= WIDTH * ( float ) Math.cos( getAngle() );
        }
        else
        {
            x += WIDTH * ( float ) Math.cos( getAngle() );
        }

        // shift up or down
        if ( motorName.contains( "front" ) ) // we're at the FRONT
        {
            y += HEIGHT * ( float ) Math.sin( getAngle() );
        }
        else
        {
            y -= HEIGHT * ( float ) Math.sin( getAngle() );
        }

        // NeveRest 40 scaled to power
        float force = 40 * power;

        float force_x = force * ( float ) Math.cos( getAngle() );
        float force_y = force * ( float ) Math.sin( getAngle() );

        // apply the force from one motor on there
        body.applyForce( force_x, force_y, x, y, true );
    }

    public void move( float power )
    {
        if ( power < -1f || power > 1f )
        {
            throw new IllegalArgumentException( "Power must be on the interval [-1f, 1f]. (was " + power + ")" );
        }

        // 4x NeveRest 40 scaled to power
        float force = 160 * power;

        float force_x = force * ( float ) Math.cos( getAngle() );
        float force_y = force * ( float ) Math.sin( getAngle() );

        body.applyForceToCenter( force_x, force_y, true );
    }

    public void rotate( float power )
    {
        if ( power < -1f  || power > 1f )
        {
            throw new IllegalArgumentException( "Power must be on the interval [-1f, 1f]. (was " + power + ")" );
        }

        body.applyTorque( 40 * power, true );
    }

}
