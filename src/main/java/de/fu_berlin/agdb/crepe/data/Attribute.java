package de.fu_berlin.agdb.crepe.data;

/**
 * Generic attribute.
 * @author Ralf Oechsner
 *
 */
public class Attribute implements IAttribute {

	private Object value;
	
	/**
	 * Creates an attribute with an object as a value.
	 * @param value value of attribute
	 */
	public Attribute(Object value) {
		
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see de.fu_berlin.agdb.ems.data.IAttribute#getValue()
	 */
	public Object getValue() {
		
		return value;
	}

	/**
	 * Setter for attribute value.
	 * @param value attribute value.
	 */
	public void setValue(Object value) {
		
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		
		return this.value.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
	
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attribute other = (Attribute) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
