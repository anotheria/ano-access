package net.anotheria.access.custom;

import net.anotheria.access.SecurityObject;
import net.anotheria.access.impl.AccessContext;

/**
 * Gender constraint that operates on subjects gender.
 * @author another
 *
 */
public abstract class AbstractSubjectGenderConstraint extends AbstractGenderConstraint{

	@Override
	public SecurityObject getRelatedObject() {
		return AccessContext.getContext().getSubject();
	}

}
