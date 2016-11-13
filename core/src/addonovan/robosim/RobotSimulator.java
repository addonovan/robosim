package addonovan.robosim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import org.lwjgl.opengl.GL11;

/**
 *
 *
 * @author addonovan
 * @since 11/11/16
 */
public class RobotSimulator extends ApplicationAdapter
{

    /** Draws the Box2D stuff so we can debug and whatnot. */
    private Box2DDebugRenderer debugRenderer;

    /** The camera used for drawing. */
    private Camera camera;

    @Override
    public void create()
    {
        Box2D.init(); // DON'T forget this lol

        // set up the camera
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        camera = new OrthographicCamera( 30, 30 * ( width / height ) );
        camera.position.set( camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f );
        camera.update();

        Simulation.initialize();

        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void render()
    {
        Gdx.gl.glClear( GL11.GL_DEPTH_BUFFER_BIT );
        Simulation.render();
        Simulation.update();

        if ( Gdx.input.isKeyPressed( Input.Keys.Q ) )
        {
            debugRenderer.render( Simulation.getWorld(), camera.combined );
        }
    }
}
