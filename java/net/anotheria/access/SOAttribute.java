package net.anotheria.access;

import net.anotheria.util.BasicComparable;
/**
 * An attribute in the Security Object. Attributes may be different things evaluated by the constraints. An attribute can be the registration country,
 * the number of messages in the inbox, number of stored favorites, user gender or status etc. 
 * @author lrosenberg
 *
 */
public class SOAttribute {
	/**
	 * The name of the attribute.
	 */
	private String name;
	/**
	 * The value of the attribute. The value is interpreted by the constraint and can be virtually anything. However the bouncer treats the value as string.
	 */
	private String value;
	/**
	 * Creates a new empty attribute.
	 */
	public SOAttribute(){
		
	}
	/**
	 * Creates a new attribute.
	 * @param aName
	 * @param aValue
	 */
	public SOAttribute(String aName, String aValue){
		name = aName;
		value = aValue;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override public String toString(){
		return getName()+"="+getValue();
	}
	
	@Override public boolean equals(Object o){
		return o instanceof SOAttribute ? 
				BasicComparable.compareString(getName(), ((SOAttribute)o).getName()) == 0 &&
				BasicComparable.compareString(getValue(), ((SOAttribute)o).getValue()) == 0 
					:
				false;
	}
	
}
