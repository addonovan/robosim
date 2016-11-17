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
 * @since 11/13/16
 */
public final class Units
{

    private static float PIXELS_TO_INCHES = 0.40f;

    private static float INCHES_TO_METERS = 0.0254f;

    private static float PIXELS_TO_METERS = PIXELS_TO_INCHES * INCHES_TO_METERS;

    //
    // Conversions
    //

    public static float pxToIn( float pixels )
    {
        return pixels * PIXELS_TO_INCHES;
    }

    public static float inToPx( float inches )
    {
        return inches / PIXELS_TO_INCHES;
    }

    public static float pxToM( float pixels )
    {
        return pixels * PIXELS_TO_METERS;
    }

    public static float mToPx( float meters )
    {
        return meters / PIXELS_TO_METERS;
    }

    public static Vector2 mToPx( Vector2 input )
    {
        return new Vector2( mToPx( input.x ), mToPx( input.y ) );
    }

    public static float inToM( float inches )
    {
        return inches * INCHES_TO_METERS;
    }

    public static float mToIn( float meters )
    {
        return meters / INCHES_TO_METERS;
    }

}
