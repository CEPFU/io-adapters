/**
 * 
 */
package de.fu_berlin.agdb.crepe.writers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Simple file writer.
 * @author Ralf Oechsner
 *
 */
public class FileWriter implements IWriter {
	
	private String text;
	private String path;
	private boolean append = false;
	
	private static Logger logger = LogManager.getLogger();
	
	/**
	 * Writes output to a text file. UTF-8 encoding is used.
	 * @param path path where the file is written (including file name)
	 */
	public FileWriter(String path) {
		
		this(path, true);
	}
	
	/**
	 * Writes output to a text file. Encoding can be specified.
	 * @param path path where the file is written (including file name)
	 * @param append if true the new text is append, else overwritten
	 */
	public FileWriter(String path, boolean append) {
		
		this.path = path;
		this.append = append;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.writers.IWriter#write()
	 */
	@Override
	public boolean write() {

		Writer writer = null;

		// the file output stream can also create a file
		// from a string as a path but we create a File object
		// here to create necessary folders 
		File file = new File(this.path);
		if (file.getParentFile() != null) {
			file.getParentFile().mkdirs();
		}
		
		try {
    		java.io.FileWriter fileWriter = new java.io.FileWriter(file, this.append);
	        writer = new BufferedWriter(fileWriter);
		    writer.write(text);
		} catch (IOException ex) {
		  
			logger.error("Could not write to file: " + this.path);
			return false;
		} finally {
		   try {writer.close();} catch (Exception ex) {}
		}

		return true;
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.writers.IWriter#setText(java.lang.String)
	 */
	@Override
	public void setText(String text) {

		this.text = text;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "fileWriter(" + path + ", "
				+ append + ")";
	}
}
