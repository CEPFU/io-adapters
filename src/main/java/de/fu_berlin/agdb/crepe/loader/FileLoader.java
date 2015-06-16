/**
 * 
 */
package de.fu_berlin.agdb.crepe.loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fu_berlin.agdb.crepe.core.Tag;

/**
 * Simple loader for local text files.
 * @author Ralf Oechsner
 *
 */
public class FileLoader implements ILoader {

	private String path;
	private StringBuilder text;
	
	private static Logger logger = LogManager.getLogger();
	
	/**
	 * Loads text from a local file.
	 * @param path path of the file
	 */
	public FileLoader(@Tag("path") String path) {
		
		this.path = path;
		this.text = new StringBuilder();
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.loader.ILoader#load()
	 */
	@Override
	public boolean load() {
		
		BufferedReader br = null;
		String nl = System.getProperty("line.separator");
		try {
 
			String currentLine;
			br = new BufferedReader(new FileReader(path));
			
 
			while ((currentLine = br.readLine()) != null) {
				this.text.append(currentLine);
				this.text.append(nl);
			}
 
		} catch (IOException e1) {
			logger.error("Error while reading file: ", e1);
			return false;
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException e2) {
				logger.error("Error while closing file: ", e2);
			}
		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.loader.ILoader#getText()
	 */
	@Override
	public String getText() {

		return this.text.toString();
	}

}
