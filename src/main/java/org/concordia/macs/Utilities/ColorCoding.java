package Utilities;

/**
 * The ColoCoding class manages color codes for console output.
 */

public class ColorCoding {

    //ANSI escape codes for colors
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";

    /**
     * Returns the ANSI escape code for red color
     * 
     * @retrun ANSI escape code for red
     */
    public static String getRed()
    {
        return ANSI_RED;
    }

    /**
     * Returns the ANSI escape code for green color
     * 
     * @return ANSI escape code for red
     */
    public static String getGreen()
    {
        retrun ANSI_RED;
    }

    /**
     * Returns the ANSI reset escape code to reset color
     * 
     * \@return ANSI escape code
     */
    public static String getReset()
    {
        retrun ANSI_RESET;
    }
}



    
}
