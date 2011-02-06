package net.anotheria.access;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represent the object which executes the transaction i.e. current user or the object which is the target of the transaction, i.e. another user.
 * @author lrosenberg
 */
public class SecurityObject {
	/**
	 * Each object has an id.
	 */
	private String id;
	/**
	 * Each object can have some variable attributes, depending on the action, for example gender. The attributes are used by constraints. 
	 * Only one attribute with the same name can exist in one object. 
	 */
	private Map<String, SOAttribute> attributes;
	
	/**
	 * Creates a new security object with no attributes.
	 * @param anId
	 */
	public SecurityObject(String anId){
		id = anId;
		attributes = new HashMap<String, SOAttribute>();
	}
	/**
	 * Creates a new security object with some attributes.
	 * @param anId
	 * @param someAttributes
	 */
	public SecurityObject(String anId, List<SOAttribute> someAttributes){ 
		this(anId);
		addAttributes(someAttributes);
	}
	
	@Override public String toString(){
		return attributes == null || attributes.size() == 0? 
				id : "Id: "+id+", attributes: "+attributes;
	}
	 
	public String getId(){
		return id;
	}
	
	public void setId(String anId){
		id = anId;
	}
	
	public void addAttribute(SOAttribute attribute){
		attributes.put(attribute.getName(), attribute);
	}
	
	public void addAttributes(Collection<SOAttribute> someAttributes){
		for (SOAttribute a : someAttributes)
			addAttribute(a);
	}
	
	/**
	 * Returns the attribute with given name.
	 * @param attributeName the name of the attribute.
	 * @return
	 */
	public SOAttribute getAttribute(String attributeName){
		return attributes.get(attributeName);
	}
	
	/**
	 * Returns the attribute value or null if no such attribute exists.
	 * @param attributeName
	 * @return
	 */
	public String getAttributeValue(String attributeName){
		SOAttribute a = getAttribute(attributeName);
		return a == null ? null : a.getValue();
	
	
	}
}
