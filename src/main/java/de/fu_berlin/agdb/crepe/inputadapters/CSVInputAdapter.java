package de.fu_berlin.agdb.crepe.inputadapters;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.fu_berlin.agdb.crepe.core.Tag;
import de.fu_berlin.agdb.crepe.data.Attribute;
import de.fu_berlin.agdb.crepe.data.Event;
import de.fu_berlin.agdb.crepe.data.IAttribute;
import de.fu_berlin.agdb.crepe.data.IEvent;

/**
 * InputAdapter for comma separated files.
 * @author Ralf Oechsner
 *
 */
public class CSVInputAdapter implements IInputAdapter {

	private List<IEvent> events;
	private String timeStampCaption;
	private String timeStampFormat;
	private String delimiter;
	private Constructor<?>[] columnTypes = null;
	
	private static Logger logger = LogManager.getLogger();
	
	/**
	 * InputAdapter for CSV files.
	 * @param csvText text of CSV file. First line must contain captions of columns.
	 * @param timeStampCaption caption of time stamp column (case sensitive!).
	 * @param timeStampFormat format string of time stamp (see java.text.SimpleDateFormat).
	 * @param delimter delimiter of columns (e.g. "," or ";" or "\t").
	 */
	public CSVInputAdapter(@Tag("timeStampCaption") String timeStampCaption, @Tag("timeStampFormat") String timeStampFormat, @Tag("delimiter") String delimiter) {

		this.timeStampCaption = timeStampCaption;
		this.timeStampFormat = timeStampFormat;
		this.delimiter = delimiter;
		this.events = new ArrayList<IEvent>();
	}
	

	/**
	 * InputAdapter for CSV files.
	 * @param csvText text of CSV file. First line must contain captions of columns.
	 * @param timeStampCaption caption of time stamp column (case sensitive!).
	 * @param timeStampFormat format string of time stamp (see java.text.SimpleDateFormat).
	 * @param delimter delimiter of columns (e.g. "," or ";" or "\t").
	 * @param columnTypes types of the columns
	 */
	public CSVInputAdapter(@Tag("timeStampCaption") String timeStampCaption, @Tag("timeStampFormat") String timeStampFormat, @Tag("delimiter") String delimiter, @Tag("columnTypes") String columnTypes) {

		this(timeStampCaption, timeStampFormat, delimiter);		
		String[] types = columnTypes.split("\\s*,\\s*");
		this.columnTypes = new Constructor<?>[types.length];
		int i = 0;
		for (String curType : types) {
			
			try {
				Class<?> cl = Class.forName(curType);
				this.columnTypes[i] = cl.getConstructor(String.class);
			} catch (NoSuchMethodException e) {
				logger.debug("CSVInputAdapter: Type of column " + i + " has no constructor with String as parameter! Falling back to type String.");
			} catch (SecurityException e) {
				// TODO: when does this happen?
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				logger.debug("CSVInputAdapter: Type of column " + i + " cannot be found! Falling back to type String.");
			}
			i++;
		}
	}
	
	/**
	 * Parses the CSV file.
	 */
	private void parseCSV(String csvText) {
			
		Scanner scanner = new Scanner(csvText);
		
		// first line contains attribute names. So it must exist.
		if (!scanner.hasNextLine()) {
			scanner.close();
			return;
		}
		
		// first line is stored as a list which is used for keys
		String line = scanner.nextLine();
		StringTokenizer st = new StringTokenizer(line, this.delimiter);
		List<String> keys = new ArrayList<String>();
		int timeStampCol = 0;
		int col = 0;
		while(st.hasMoreTokens()) {
			
			String token = st.nextToken();
			if (!token.equals(this.timeStampCaption)) {
				keys.add(token);  // TODO: check on duplicate entries
			}
			else {
				timeStampCol = col;
				keys.add(null);  // seems stupid but is important: keys are mapped to columns so time stamp column
				                 // is represented as an empty list element
			}
			
			col++;
		}
						
		// parse every line and look for attributes
		while (scanner.hasNextLine()) {
			
			Event curEvent = new Event();
			Map<String, IAttribute> curAttributes = new HashMap<String, IAttribute>();
			
			line = scanner.nextLine();

			st = new StringTokenizer(line, this.delimiter);
			col = 0;
			while(st.hasMoreTokens()) {
			
				String token = st.nextToken();
				// normal attribute
				if (col != timeStampCol) {
					
					// convert to columnType (defined in a tag) otherwise fall back to String
					if (this.columnTypes != null && this.columnTypes.length > 0 && this.columnTypes[col] != null) {
						try {
							curAttributes.put(keys.get(col), new Attribute(this.columnTypes[col].newInstance(token)));
						} catch (InstantiationException e) {
							logger.error("CSV parsing error: ", e);
						} catch (IllegalAccessException e) {
							logger.error("CSV parsing error: ", e);
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							logger.error("CSV parsing error: ", e);
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							logger.error("CSV parsing error: ", e);
							e.printStackTrace();
						}
					}
					else {
						curAttributes.put(keys.get(col), new Attribute(token));
					}
				}
				// time stamp
				else {

					DateFormat df = new SimpleDateFormat(this.timeStampFormat);
					try {
						curEvent.setTimeStamp(df.parse(token));
					} catch (ParseException e) {
						// wrong format so ignored TODO: what then?
						e.printStackTrace();
					}
				}
				
				col++;
			}
			
			curEvent.setAttributes(curAttributes);
			this.events.add(curEvent);
		}
		
		scanner.close();
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.inputadapters.IInputAdapter#getEvents()
	 */
	public List<IEvent> getEvents() {

		return this.events;
	}

	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.inputadapters.IInputAdapter#load(java.lang.String)
	 */
	@Override
	public void load(String text) {
	
		this.parseCSV(text);
	}
}
