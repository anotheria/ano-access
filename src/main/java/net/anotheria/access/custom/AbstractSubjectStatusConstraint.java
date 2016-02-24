package net.anotheria.access.custom;

import net.anotheria.access.SecurityObject;
import net.anotheria.access.impl.AccessContext;

public abstract class AbstractSubjectStatusConstraint extends AbstractStatusConstraint{

	@Override
	public SecurityObject getRelatedObject() {
		return AccessContext.getContext().getSubject();
	}

}
