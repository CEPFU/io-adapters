package de.fu_berlin.agdb.crepe.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import de.fu_berlin.agdb.crepe.core.Tag;


/**
 * Loader for files via URL.
 * @author Ralf Oechsner
 *
 */
public class URLLoader implements ILoader {

	private String text;
	private URL url;
	private boolean hasMoreData;
	
	private static Logger logger = LogManager.getLogger(URLLoader.class);
	
	/**
	 * Loader for any file that can be loaded with an URL (e.g. via HTTP).
	 * @param url URL of file to load.
	 */
	public URLLoader(@Tag("url") String url) {
		
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			logger.error("Malformed URL: ", e);
		}
		this.text = "";  // start with an empty string
		hasMoreData = true;
	}

	/**
	 * Loads file from URL via HTTP.
	 */
	public boolean load() {

        BufferedReader in;
		try {
			in = new BufferedReader(
			new InputStreamReader(url.openStream()));
	        String inputLine;
	        while ((inputLine = in.readLine()) != null)
	            text += inputLine + "\n";
	        in.close();
	     
	        return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			hasMoreData = false;
		}
	}

	/**
	 * Returns loaded file as text (for example content of HTML or XML file).
	 */
	public String getText() {
		
		return this.text;
	}

	@Override
	public boolean hasMoreData() {
		return hasMoreData;
	}
}
