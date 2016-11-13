package addonovan.robosim;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * An object in the simulation that has physics.
 *
 * @author addonovan
 * @since 11/11/16
 */
public abstract class Entity
{

    //
    // Constants
    //

    /** The scale for converting from simulation sizes to screen sizes. */
    public static final float SIM_TO_SCREEN = 10f;

    /** The scale for converting from screen sizes to simulation sizes. */
    public static final float SCREEN_TO_SIM = 1 / SIM_TO_SCREEN;

    //
    // Abstract
    //

    /**
     * @return The body used by this entity in the simulation.
     */
    abstract Body getBody();

    abstract void render();

}
