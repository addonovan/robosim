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
 * A motor that's attached to a robot.
 *
 * @author addonovan
 * @since 11/17/2016
 */
public class Motor implements HardwareDevice
{

    //
    // Fields
    //

    private final Robot robot;

    /** The vector that points to the location of the motor [m].*/
    private final Vector2 position;

    /** The power of this motor [-1f, 1f]*/
    public float power = 0.0f;

    //
    // Constructors
    //

    public Motor( float x, float y )
    {
        robot = Simulation.robot;
        position = Units.pxToM( Math.vectorFrom( x, y, robot.getAngle() ) );
    }

    //
    // Overrides
    //

    @Override
    public void render()
    {
        Vector2 start = Units.mToPx( getStartPosition() );
        Vector2 end = Units.mToPx( getEndPosition() );

        Simulation.renderShape( ShapeRenderer.ShapeType.Filled, sr ->
        {
            sr.setColor( Color.RED );

            sr.circle( start.x, start.y, 2 );
            sr.line( start, end );
        } );
    }

    @Override
    public void update()
    {
        if ( power < -1f || power > 1f )
        {
            Gdx.app.log( "Motor", "Invalid power setting: " + power );
            power = 0f;
            return;
        }

        // apply the force to the robot
        Vector2 position = robot.getBody().getPosition().add( this.position );
        Vector2 force = Math.vectorFrom( 40f * power, robot.getAngle() );

        robot.getBody().applyForce( force, position, true );
    }

    //
    // Actions
    //

    private Vector2 getStartPosition()
    {
        return Math.vectorFrom( position.len(), position.angleRad() + robot.getAngle() ).add( robot.getX(), robot.getY() );
    }

    private Vector2 getEndPosition()
    {
        Vector2 start = getStartPosition();
        return Math.vectorFrom( power * Robot.WIDTH_M * 0.4f, robot.getAngle() ).add( start.x, start.y );
    }

}
