package addonovan.robosim;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

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

    private static final float WIDTH = 30;

    private static final float HEIGHT = 30;

    //
    // Fields
    //

    /** The body this uses in the physics simulations. */
    private final Body body;

    //
    // Constructors
    //

    public Robot()
    {
        body = makeBody();
    }

    //
    // Rendering
    //

    @Override
    void render()
    {
        Simulation.renderShape( ShapeRenderer.ShapeType.Line, sr ->
                {
                    sr.setColor( Color.WHITE );

                    float x = body.getPosition().x * SIM_TO_SCREEN;
                    float y = body.getPosition().y * SIM_TO_SCREEN;

                    // draw a box on the robot
                    sr.line( x,         y,         x + WIDTH, y         );
                    sr.line( x + WIDTH, y,         x + WIDTH, y + WIDTH );
                    sr.line( x + WIDTH, y + WIDTH, x,         y + WIDTH );
                    sr.line( x,         y + WIDTH, x,         y         );
                }
        );
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
        shape.setAsBox( WIDTH * SCREEN_TO_SIM, HEIGHT * SCREEN_TO_SIM );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;

        body.createFixture( fixtureDef );

        return body;
    }

    @Override
    Body getBody()
    {
        return body;
    }

}
