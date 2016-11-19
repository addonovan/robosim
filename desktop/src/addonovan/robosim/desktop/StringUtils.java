package addonovan.robosim.desktop;

import java.util.regex.Pattern;

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

    //
    // Constants
    //

    /** System.lineSeparator() */
    private static final String NEW_LINE = System.lineSeparator();

    /** Matches all the instances where a line begins with # */
    private static final Pattern LEADING_HASHTAGS = Pattern.compile( "^# ?", Pattern.MULTILINE );

    /** Mathces all the instances where a line begins with whitspace */
    private static final Pattern LEADING_SPACES = Pattern.compile( "^ +", Pattern.MULTILINE );

    /** Matches all the instances where there is a single new line. */
    private static final Pattern SINGLE_NEWLINES = Pattern.compile( "(?<!" + NEW_LINE +")" + NEW_LINE + "(?!" + NEW_LINE + ")" );

    //
    // Actions
    //

    /**
     * Trims the input string with custom things.
     *
     * @param input
     *          The input string.
     * @return The trimmed string.
     */
    public static String trim( String input )
    {
        input = LEADING_HASHTAGS.matcher( input ).replaceAll( "" );
        input = LEADING_SPACES.matcher( input ).replaceAll( "" );
        input = SINGLE_NEWLINES.matcher( input ).replaceAll( " " );

        return input.trim();
    }

}
