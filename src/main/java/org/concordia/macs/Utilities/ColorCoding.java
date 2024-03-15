package org.concordia.macs.Utilities;

/**
 * The ColoCoding class manages color codes for console output.
 */

public class ColorCoding {

    // ANSI escape codes for colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static final String ANSI_CYAN="\u001B[36m";

    /**
     * Returns the ANSI escape code for red color
     * 
     * @return ANSI escape code for red
     */
    public static String getRed() {
        return ANSI_RED;
    }

    /**
     * Returns the ANSI escape code for green color
     * 
     * @return ANSI escape code for green
     */
    public static String getGreen() {
        return ANSI_GREEN;
    }

    /**
     * Returns the ANSI reset escape code to reset color
     * 
     * @return ANSI reset escape code
     */
    public static String getReset() {
        return ANSI_RESET;
    }

    /**
     * Returns the ANSI  escape code to Cyan color
     *
     * @return ANSI Cyan escape code
     */
    public static String getCyan() {
        return ANSI_CYAN;
    }
}