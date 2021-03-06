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
package addonovan.robosim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;

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
    public static final float WIDTH = Units.inToPx( 18f );

    /** The height of all robots. [px] */
    public static final float HEIGHT = Units.inToPx( 18f );

    /** The width of all robots. [m]*/
    public static final float WIDTH_M = Units.pxToM( WIDTH );

    /** The height of all robots. [m] */
    public static final float HEIGHT_M = Units.pxToM( HEIGHT );

    //
    // Fields
    //

    /** The body this uses in the physics simulations. */
    private final Body body;

    /** The motors on this robot. */
    private final List< Motor > motors = new ArrayList<>();

    /** The sensors in this robot. */
    private final List< Sensor > sensors = new ArrayList<>();

    //
    // Constructors
    //

    /**
     * Constructs a new robot out of nothing.
     */
    Robot()
    {
        body = makeBody();
        body.setTransform( Units.inToM( 11 ) + Units.pxToM( WIDTH / 2 ), Units.inToM( 11 ) + Units.pxToM( HEIGHT / 2 ), 0f );
    }

    //
    // Rendering
    //

    @Override
    public void update()
    {
        motors.forEach( HardwareDevice::update );
        sensors.forEach( Sensor::update );
    }

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
        } );

        motors.forEach( HardwareDevice::render );
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
        fixtureDef.restitution = 0.1f;

        body.createFixture( fixtureDef );
        body.setLinearDamping( 21f );
        body.setAngularDamping( 21f );
        shape.dispose();

        return body;
    }

    @Override
    Body getBody()
    {
        return body;
    }

    //
    // Sensors/Motors
    //

    /**
     * Adds a new motor to this robot.
     *
     * @param motor
     *          The new motor.
     * @return The same motor, so you can store it if you'd like.
     */
    public Motor addMotor( Motor motor )
    {
        motors.add( motor );
        return motor;
    }

    /**
     * Adds a new sensor to this robot.
     *
     * @param sensor
     *          The new sensor.
     * @return The same sensor, so you can store it if you'd like.
     */
    public Sensor addSensor( Sensor sensor )
    {
        sensors.add( sensor );
        return sensor;
    }

    //
    // Debug Movement
    //

    void move( float power )
    {
        if ( power < -1f || power > 1f )
        {
            throw new IllegalArgumentException( "Power must be on the interval [-1f, 1f]. (was " + power + ")" );
        }

        float angle = getAngle();
        if ( power < 0 )
        {
            angle += Math.PI;
        }

        body.applyForceToCenter( Math.vectorFrom( 200f * power, angle ), true );
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
