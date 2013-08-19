package net.anotheria.access;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represent the object which executes the transaction i.e. current user or the object which is the target of the transaction, i.e. another user.
 * 
 * @author Leon Rosenberg, Alexandr Bolbat
 */
public class SecurityObject implements Serializable {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 1097360594479229222L;

	/**
	 * Unique id.
	 */
	private String id;

	/**
	 * Each object can have some variable attributes, depending on the action, for example gender. <br>
	 * The attributes are used by constraints. Only one attribute with the same name can exist in one object.
	 */
	private Map<String, SOAttribute> attributes;

	/**
	 * Creates a new {@link SecurityObject} with empty attributes.
	 * 
	 * @param aId
	 *            - unique id
	 */
	public SecurityObject(final String aId) {
		this.id = aId;
		this.attributes = new HashMap<String, SOAttribute>();
	}

	/**
	 * Creates a new {@link SecurityObject} with {@link List} of {@link SOAttribute}.
	 * 
	 * @param anId
	 *            - unique id
	 * @param aAttributes
	 *            - {@link List} of {@link SOAttribute}
	 */
	public SecurityObject(final String anId, final List<SOAttribute> aAttributes) {
		this(anId);
		addAttributes(aAttributes);
	}

	public String getId() {
		return id;
	}

	public void setId(final String aId) {
		this.id = aId;
	}

	/**
	 * Add attribute.
	 * 
	 * @param aAttribute
	 *            - {@link SOAttribute}
	 */
	public void addAttribute(final SOAttribute aAttribute) {
		if (aAttribute != null)
			attributes.put(aAttribute.getName(), aAttribute);
	}

	/**
	 * Add attributes.
	 * 
	 * @param aAttributes
	 *            - {@link List} of {@link SOAttribute}
	 */
	public void addAttributes(final Collection<SOAttribute> aAttributes) {
		if (aAttributes == null || aAttributes.isEmpty())
			return;

		for (SOAttribute a : aAttributes)
			addAttribute(a);
	}

	/**
	 * Get attribute.
	 * 
	 * @param attributeName
	 *            - attribute name
	 * @return {@link SOAttribute}
	 */
	public SOAttribute getAttribute(final String attributeName) {
		return attributes.get(attributeName);
	}

	/**
	 * Get the attribute value or <code>null</code> if attribute doesn't exists.
	 * 
	 * @param attributeName
	 *            - attribute name
	 * @return {@link String}
	 */
	public String getAttributeValue(final String attributeName) {
		SOAttribute a = getAttribute(attributeName);
		return a == null ? null : a.getValue();

	}

	@Override
	public String toString() {
		return attributes == null || attributes.size() == 0 ? id : "Id: " + id + ", attributes: " + attributes;
	}
}
