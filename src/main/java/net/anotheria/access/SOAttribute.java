package net.anotheria.access;

import java.io.Serializable;

import net.anotheria.util.BasicComparable;

/**
 * Attribute in the {@link SecurityObject}. <br>
 * Attributes may be different things evaluated by the constraints. <br>
 * An attribute can be the registration country, the number of messages in the inbox, number of stored favorites, user gender, status, etc.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public class SOAttribute implements Serializable {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -3399290755174491710L;

	/**
	 * Attribute name.
	 */
	private String name;

	/**
	 * Attribute value.
	 */
	private String value;

	/**
	 * Default constructor.
	 */
	public SOAttribute() {
	}

	/**
	 * Public constructor.
	 * 
	 * @param aName
	 *            - attribute name
	 * @param aValue
	 *            - attribute value
	 */
	public SOAttribute(final String aName, final String aValue) {
		this.name = aName;
		this.value = aValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String aValue) {
		this.value = aValue;
	}

	@Override
	public String toString() {
        return name + '=' + value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SOAttribute))
			return false;

		SOAttribute toCompare = SOAttribute.class.cast(o);
        return BasicComparable.compareString(name, toCompare.name) == 0 && BasicComparable.compareString(value, toCompare.value) == 0;
	}

}
