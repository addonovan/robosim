package addonovan.robosim;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * An object in the simulation that has physics.
 *
 * @author addonovan
 * @since 11/11/16
 */
public abstract class Entity implements Renderable
{

    //
    // Actions
    //

    public float getMass()
    {
        return getBody().getMass();
    }

    public float getX()
    {
        return getBody().getPosition().x;
    }

    public float getY()
    {
        return getBody().getPosition().y;
    }

    public float getAngle()
    {
        return getBody().getAngle();
    }

    //
    // Abstract
    //

    /**
     * @return The body used by this entity in the simulation.
     */
    abstract Body getBody();

}
