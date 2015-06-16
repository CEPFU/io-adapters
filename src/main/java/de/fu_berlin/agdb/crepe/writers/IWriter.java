/**
 * 
 */
package de.fu_berlin.agdb.crepe.writers;

/**
 * Interface for writers.
 * @author Ralf Oechsner
 *
 */
public interface IWriter {

	/**
	 * Write to target.
	 * @return true if successful, false otherwise.
	 */
	public boolean write();
	
	/**
	 * Set content of target.
	 * @param content of target.
	 */
	public void setText(String text);
}
