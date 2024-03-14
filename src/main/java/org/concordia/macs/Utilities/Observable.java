package org.concordia.macs.Utilities;

/**
 * It's the Observable Interface
 */
public interface Observable {

    /**
     * This function updates the log message
     * @param p_log - log message
     */
    public void notifyObservers(String p_log);
}
