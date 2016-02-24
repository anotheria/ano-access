package net.anotheria.access.custom;

import net.anotheria.access.constraints.AbstractCustomConstraint;
import net.anotheria.access.impl.AccessContext;
/**
 * Is met if the action is performed on another object as itself. Useful to prevent user from sending messages to him/herself etc.
 * @author another
 *
 */
public class NotSelfConstraint extends AbstractCustomConstraint{

	@Override
	public boolean isMet() {
		try{
			String objectId = AccessContext.getContext().getObject().getId();
			String subjectId = AccessContext.getContext().getSubject().getId();
			return objectId!=subjectId && !(objectId.equals(subjectId));
		}catch(Exception e){
			return false;
		}
	}
	
}
