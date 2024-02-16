package org.concordia.macs.Utilities;

import java.io.File;
import java.io.IOException;

/**
 * The MapCreator class is designed to automatically record to the file each time a user generates a map.
 * @author SanjayBhargavPabbu
 */

public class MapCreator {
	
	/**
	 *
	 * This method is used to generate the map (file).
	 * @param p_fileNameEntered refers to the Map Name being entered.
	 * @param p_path refers to the path.
	 *
	 */

	public static void generateMapFile(String p_fileNameEntered, String p_path) {
		File targetDirectory = new File(p_path);
		File newMapFile = new File(targetDirectory, p_fileNameEntered + ".map");
		try {
			boolean fileCreated = newMapFile.createNewFile();
			if (fileCreated) {
				System.out.println("File was successfully created.");
			} else {
				System.out.println("File creation failed.");
			}
		} catch (IOException exception) {
			System.out.println("Error encountered while creating the file.");
			exception.printStackTrace();
		}
	}
}
