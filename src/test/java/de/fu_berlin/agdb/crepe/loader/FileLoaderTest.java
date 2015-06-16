/**
 * 
 */
package de.fu_berlin.agdb.crepe.loader;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for File loader.
 * @author Ralf Oechsner
 *
 */
public class FileLoaderTest {

	private final static String resource_path = "";
	private final static String expectedText = "Datum;Kurs\n" + 
			"1999-01-04;1.1789\n" + 
			"1999-01-05;1179\n" + 
			"1999-01-06;1.1743\n" + 
			"1999-01-07;1.1632\n" + 
			"1999-01-08;1.1659\n" + 
			"1999-01-11;1.1569\n" + 
			"1999-01-12;1152\n" + 
			"1999-01-13;1.1744\n" + 
			"1999-01-14;1.1653\n" + 
			"1999-01-15;1.1626\n" + 
			"1999-01-18;1.1612\n" + 
			"1999-01-19;1.1616\n" + 
			"1999-01-20;1.1575\n" + 
			"1999-01-21;1.1572\n" + 
			"1999-01-22;1.1567\n" + 
			"1999-01-25;1.1584\n" + 
			"1999-01-26;1.1582\n" + 
			"1999-01-27;1.1529\n" + 
			"1999-01-28;1141\n";

	/**
	 * Tests file loader.
	 */
	@Test
	public void test() {
        String file = getClass().getResource(resource_path).getFile();
        ILoader loader = new FileLoader(file);
		assertTrue("Can't load " + file, loader.load());
		assertEquals("Loaded text doesn't match expected text.", expectedText, loader.getText());
	}

}
