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

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import org.lwjgl.opengl.GL11;

/**
 * The application class for the robot simulation.
 *
 * @author addonovan
 * @since 11/11/16
 */
public class RobotSimulator extends ApplicationAdapter
{

    /** Draws the Box2D stuff so we can debug and whatnot. */
    private Box2DDebugRenderer debugRenderer;

    /** The camera used for drawing. */
    private OrthographicCamera camera;

    @Override
    public void create()
    {
        Gdx.app.log( "RobotSimulator", "Initializing Box2D" );
        Box2D.init(); // DON'T forget this lol

        // set up the camera
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        camera = new OrthographicCamera( Units.pxToM( width ), Units.pxToM( height ) );
        camera.position.set( camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f );
        camera.update();

        Gdx.app.log( "RobotSimulator", "Initializing the simulation..." );
        Simulation.newInterpreter( Simulation.EMPTY_PROGRAM );
        Simulation.initialize();
        Simulation.shapeRenderer = new ShapeRenderer();
        Gdx.app.log( "RobotSimulator", "Simulation initialized" );

        debugRenderer = new Box2DDebugRenderer();

        Gdx.gl.glEnable( GL11.GL_BLEND );
        Gdx.gl.glBlendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA );
    }

    @Override
    public void render()
    {
        Gdx.gl.glClear( GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT );

        if ( Gdx.input.isKeyPressed( Input.Keys.Q ) )
        {
            debugRenderer.render( Simulation.getWorld(), camera.combined );
        }
        else
        {
            Simulation.render();
        }

        Simulation.update();
    }
}
