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
    private float distance = MAX_DISTANCE;

    //
    // Constructors
    //

    /**
     * Constructs a new distance sensor on the given robot.
     *
     * @param x
     *          The x position on the robot (relative to its center) [px].
     * @param y
     *          The y position on the robot (relative to its center) [px].
     * @param angle
     *          The angle of the sensor (relative to the robot) [°].
     */
    public DistanceSensor( float x, float y, float angle )
    {
        robot = Simulation.robot;
        this.angle = Math.toRadians( angle );
        positionVector = Units.pxToM( Math.vectorFrom( x, y, this.angle ) );
    }

    //
    // Getters
    //

    /**
     * @return The distance [0, 255] [cm], or -1 if no distance was sensed.
     */
    public int getDistance()
    {
        if ( distance < 0f ) return -1;
        return Math.round( distance * 100 );
    }

    //
    // Overrides
    //

    @Override
    public void render()
    {
        Simulation.renderShape( ShapeRenderer.ShapeType.Filled, sr ->
        {
            float localDistance = distance;
            if ( localDistance < 0f )
            {
                localDistance = MAX_DISTANCE;
            }

            float alpha = 0.15f;
            if ( localDistance > 0.001f )
            {
                alpha = ( 0.7f ) * ( 1 - ( localDistance / MAX_DISTANCE ) ) + 0.3f;
            }

            sr.setColor( new Color( 1, 1, 0, alpha ) );
            Vector2 start = getStartPosition();
            Vector2 end = getEndPosition( start, localDistance );

            sr.rectLine( Units.mToPx( start ), Units.mToPx( end ), 2 );
        } );
    }

    @Override
    public void update()
    {
        Vector2 start = getStartPosition();
        Vector2 end = getEndPosition( start, MAX_DISTANCE );

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

    /**
     * @return The starting position (where the sensor is on the robot).
     */
    private Vector2 getStartPosition()
    {
        return Math.vectorFrom( positionVector.len(), robot.getAngle() + positionVector.angle() ).add( robot.getX(), robot.getY() );
    }

    /**
     * @param start
     *          The starting position (where the sensor is on the robot).
     * @param length
     *          The length of the line to fetch [m].
     *
     * @return The ending position of the line of the distance sensor's range.
     */
    private Vector2 getEndPosition( Vector2 start, float length )
    {
        return Math.vectorFrom( length, angle + robot.getAngle() ).add( start.x, start.y );
    }

}
