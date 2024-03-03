package org.concordia.macs.Models;
import java.io.BufferedWriter;


/**
 *  This class refers to the log entry in the file.
 * @author - Blesslin Jeba Shiny
 */
public class LogEntryBuffer implements Observable
{
    private LogWriter d_logWriter=new LogWriter();

    /**
     * This function writes data to the log file.
     * @param p_log  - data written in the file
     */
     public void log(String p_log)
     {
         notifyObservers(p_log);
     }

    /**
     * This function notifies all the observers if any object is changed
     */
    @override
    public void notifyObservers(String p_log)
    {
        d_logWriter.update(p_log);
    }

    /**
     * This function cleans the log file
     */
    public void clearFile()
    {
        PrintWriter l_writeData=null;
        String l_fileName="LogEntry";
        try
        {
            l_writeData=new PrintWriter(new BufferedWriter(new FileWriter("logFiles/"+l_fileName+".log",false)));
        }
        catch (Exception e)
        {
            //error handler
        }
    }
}