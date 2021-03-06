package de.fu_berlin.agdb.crepe.writers;

import org.junit.After;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

/**
 * Test for FileWriter.
 * @author Ralf Oechsner
 *
 */
public class FileWriterTest {

	private static final String fileContent = "This should be the content\n"
			+ "of the file that is written to the hard disc by\n"
			+ "the FileWriter class test.\n";
	
	private static final String filePath = "testTmp" + File.separator + "FileWriterTestFile2342.txt";

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		
		File tmpFile = new File(filePath);
		File tmpFolder = tmpFile.getParentFile();
		tmpFile.delete();
		tmpFolder.delete();
	}

	@Test
	public void testWriteFile() {
		
		FileWriter fileWriter = new FileWriter(filePath);
		
		fileWriter.setText(fileContent);
		assertTrue(fileWriter.write());
		
		BufferedReader br = null;
		String readLine;
		String readContent = "";

		try {
			br = new BufferedReader(new FileReader(filePath));
			
			while ((readLine = br.readLine()) != null) {
				readContent += readLine + "\n";  
			}
			
			assertEquals(fileContent, readContent);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("File not found");
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException");
		}
	}

}
