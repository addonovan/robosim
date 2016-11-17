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
 * A class for float-based mathematics.
 *
 * @author addonovan
 * @since 11/17/2016
 */
public final class Math
{

    /** The ratio between a circle's circumference and its diameter.  */
    public static final float PI = ( float ) java.lang.Math.PI;

    //
    // Trig
    //

    /**
     * @param angle
     *          The angle (radians).
     * @return The cosine of the angle.
     */
    public static float cos( float angle )
    {
        return ( float ) java.lang.Math.cos( angle );
    }

    /**
     * @param angle
     *          The angle (radians).
     * @return The sine of the angle.
     */
    public static float sin( float angle )
    {
        return ( float ) java.lang.Math.sin( angle );
    }

    /**
     * @param x
     *          The x component of the triangle.
     * @param y
     *          The y component of the triangle.
     * @return ArcTan( y / x )
     */
    public static float atan( float x, float y )
    {
        return ( float ) java.lang.Math.atan2( y, x );
    }

    /**
     * @param degrees
     *          The angle measure in degrees.
     * @return The angle measure in radians.
     */
    public static float toRadians( float degrees )
    {
        return ( float ) java.lang.Math.toRadians( degrees );
    }

    /**
     * @param radians
     *          The angle measure in radians.
     * @return The angle measure in degrees.
     */
    public static float toDegrees( float radians )
    {
        return ( float ) java.lang.Math.toDegrees( radians );
    }

    //
    // Min/Max
    //

    /**
     * @param a
     *          A number.
     * @param b
     *          Another number.
     * @return The larger of the two numbers.
     */
    public static float max( float a, float b )
    {
        return a > b ? a : b;
    }

    /**
     * @param a
     *          A number.
     * @param b
     *          Another number.
     * @return The smaller of the two numbers.
     */
    public static float min( float a, float b )
    {
        return a < b ? a : b;
    }

    /**
     * @param a
     *          A number.
     * @return |a|
     */
    public static float abs( float a )
    {
        return a < 0 ? -a : a;
    }

    //
    // Sqrt/Pow
    //

    /**
     * @param f
     *          A number.
     * @return The square root of the number.
     */
    public static float sqrt( float f )
    {
        return ( float ) java.lang.Math.sqrt( f );
    }

    /**
     * @param a
     *          A number.
     * @param b
     *          The power to raise a to.
     * @return a^b
     */
    public static float pow( float a, float b )
    {
        return ( float ) java.lang.Math.pow( a, b );
    }

    //
    // Vectors
    //

    /**
     * The magnitude of the vecotr with the given components.
     *
     * @param x
     *          The x component of the vector.
     * @param y
     *          The y component of the vector.
     * @return sqrt( x^2 + y^2 )
     */
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
     * will be converted into a magnitude) and desired output angle.
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
        return vectorFrom( magnitude( x, y ), angle );
    }

    /**
     * Creates a vector with the given input x and y components (
     * which will be converted into a magnitude) then adds the angle
     * between the components to the worldAngle and returns the resultant
     * vector.
     *
     * @param x
     *          The x component of the input vector.
     * @param y
     *          The y component of the input vector.
     * @param worldAngle
     *          The world angle this vector is rotate by.
     * @return The resultant vector after the transformations.
     */
    public static Vector2 vectorFromAngle( float x, float y, float worldAngle )
    {
        float localAngle = atan( x, y );
        return vectorFrom( magnitude( x, y ), worldAngle + localAngle );
    }

}
