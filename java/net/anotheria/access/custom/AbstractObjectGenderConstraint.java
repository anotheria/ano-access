package net.anotheria.access.custom;

import net.anotheria.access.SecurityObject;
import net.anotheria.access.impl.AccessContext;

/**
 * Gender constraint that works on objects (action taker).
 * @author another
 *
 */
public abstract class AbstractObjectGenderConstraint extends AbstractGenderConstraint{

	@Override
	public SecurityObject getRelatedObject() {
		return AccessContext.getContext().getObject();
	}

}
