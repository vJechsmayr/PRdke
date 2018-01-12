package composedOperations;

import g4dke.app.SystemHelper;

public class DeContextualizeRule extends StandardComposedOperation{

	//Needs Actually from user to user and not from role.
	public DeContextualizeRule()
	{
		super();
		Operation op = new Operation(SystemHelper.CHANGECONTEXT);
		op.setRole(SystemHelper.RULE_DEVELOPER);
		op.setPosition(1);
		op.setLastOperation(true);
		this.getAtomicOperations().add(op);
	}
}
