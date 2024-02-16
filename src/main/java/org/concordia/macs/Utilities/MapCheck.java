package org.concordia.macs.Utilities;
import java.io.File;

/**
 * MapCheck class checks if user mentioned map is present
 */
public class MapCheck
{
    /**
     * This method checks if user mentioned map is present.
     * @param p_enteredFileName - Map Name being entered.
     * @param p_path - path.
     */
    public static boolean checkMap(String p_enteredFileName,String p_path)
    {
        p_enteredFileName=p_enteredFileName+".map";
        File l_directory=new File(p_path);
        File[] fileList = l_directory.listFiles();
        if (fileList != null)
        {
            for (File file : fileList)
            {
                if (p_enteredFileName.equals(file.getName()))
                    return true;
            }
        }
        return false;
    }

}