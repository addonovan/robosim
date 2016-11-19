package addonovan.robosim.desktop;

/**
 * A class for performing string manipulations over and over.
 *
 * I hate string manipulations.
 *
 * @author addonovan
 * @since 11/18/16
 */
public final class StringUtils
{

    /**
     * Trims the input string with custom things.
     *
     * @param input
     *          The input string.
     * @return The trimmed string.
     */
    public static String trim( String input )
    {
        return input
                .replaceAll( "^# ?", "" )            // remove leading #'s
                .replaceAll( "^ ", "" )              // remove leading spaces
                .replaceAll( "(?<!\n)\n(?!\n)", "" ) // remove single \n's
                .trim();                             // does regular trimming
    }

}
