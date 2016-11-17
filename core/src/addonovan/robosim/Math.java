package addonovan.robosim;

import com.badlogic.gdx.math.Vector2;

/**
 * @author addonovan
 * @since 11/17/2016
 */
public final class Math
{

    //
    // Trig
    //

    public static float cos( float angle )
    {
        return ( float ) java.lang.Math.cos( angle );
    }

    public static float sin( float angle )
    {
        return ( float ) java.lang.Math.sin( angle );
    }

    public static float toRadians( float degrees )
    {
        return ( float ) java.lang.Math.toRadians( degrees );
    }

    //
    // Min/Max
    //

    public static float max( float a, float b )
    {
        return a > b ? a : b;
    }

    public static float min( float a, float b )
    {
        return a < b ? a : b;
    }

}
