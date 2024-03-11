package org.concordia.macs.View;
import org.concordia.macs.Utilities.Observer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * This class writes the log into the log file.
 * @author - Blesslin Jeba Shiny
 */
public class LogWriter implements Observer {
    private String l_fileName="LogEntry";

    /**
     * Observer interface implementation
     * @param p_log - log message to be updated
     */
    @Override
    public void update(String p_log)
    {
            writeLog(p_log);
    }

    /**
     * This method logs into the log file
     * @param p_logMessage - message to be loged into the file
     */
    public void writeLog(String p_logMessage)
    {
        PrintWriter l_writeData=null;
        try
        {
            File directory=new File("logFiles");
            if(!directory.exists() || !directory.isDirectory())
                directory.mkdir();

            l_writeData=new PrintWriter(new BufferedWriter(new FileWriter("logFiles/"+l_fileName+".log",true)));
            l_writeData.println(p_logMessage);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        finally {
            l_writeData.close();
        }
    }


}
