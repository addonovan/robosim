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

    public static float atan( float x, float y )
    {
        return ( float ) java.lang.Math.atan2( x, y );
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

    public static float abs( float a )
    {
        return a < 0 ? -a : a;
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
        v.setLength( abs( magnitude ) );
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
     *
     * @return The vector created by this.
     */
    public static Vector2 vectorFrom( float x, float y, float angle )
    {
        float localAngle = atan( x, y );
        return vectorFrom( magnitude( x, y ), localAngle + angle );
    }

}
