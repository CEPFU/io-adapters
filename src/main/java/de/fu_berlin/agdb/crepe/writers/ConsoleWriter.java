/**
 * 
 */
package de.fu_berlin.agdb.crepe.writers;

/**
 * Writes to console.
 * @author Ralf Oechsner
 *
 */
public class ConsoleWriter implements IWriter {

	private String text;
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.writers.IWriter#write()
	 */
	@Override
	public boolean write() {

		System.out.println(text);
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
		
		return "consoleWriter()";
	}
}
