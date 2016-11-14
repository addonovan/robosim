package addonovan.robosim;

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

    public static float inToM( float inches )
    {
        return inches * INCHES_TO_METERS;
    }

    public static float mToIn( float meters )
    {
        return meters / INCHES_TO_METERS;
    }

}
