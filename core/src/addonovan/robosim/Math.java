package addonovan.robosim;

import com.badlogic.gdx.math.Vector2;

/**
 * @author addonovan
 * @since 11/17/2016
 */
public final class Math
{

    public static final float PI = ( float ) java.lang.Math.PI;

    public static final float TWO_PI = PI * 2f;

    public static final float HALF_PI = PI / 2f;

    public static final float QUARTER_PI = PI / 4f;

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

    public static float toDegrees( float radians )
    {
        return ( float ) java.lang.Math.toDegrees( radians );
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

    //
    // Sqrt/Pow
    //

    public static float sqrt( float f )
    {
        return ( float ) java.lang.Math.sqrt( f );
    }

    public static float pow( float a, float b )
    {
        return ( float ) java.lang.Math.pow( a, b );
    }

    //
    // Vectors
    //

    public static float magnitude( float x, float y )
    {
        return sqrt( x * x + y * y );
    }

    /**
     * Creates a vector from the given magnitude and angle.
     *
     * @param magnitude
     *          The magnitude (length) of the vector.
     * @param angle
     *          The angle of the vector.
     * @return The vector representing the given inputs.
     */
    public static Vector2 vectorFrom( float magnitude, float angle )
    {
        Vector2 v = new Vector2( 1f, 1f );
        v.setLength( magnitude );
        v.setAngleRad( angle );
        return v;
    }

    /**
     * Creates a vector with the given input x and y components (which
     * will be converted into a magnitude) and angle.
     *
     * @param x
     *          The x component of the magnitude.
     * @param y
     *          The y component of the magnitude.
     * @param angle
     *          The angle (radians) of the vector.
     * @return The vector created by this.
     */
    public static Vector2 vectorFrom( float x, float y, float angle )
    {
        return vectorFrom( magnitude( x, y ), angle );
    }

}
