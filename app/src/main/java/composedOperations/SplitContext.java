package composedOperations;

import g4dke.app.SystemHelper;

public class SplitContext extends  StandardComposedOperation{

	
	public SplitContext()
	{
		super();
		Operation op = new Operation(SystemHelper.CHANGECONTEXT);
		op.setRole(SystemHelper.RULE_DEVELOPER);
		op.setPosition(1);
		op.setLastOperation(true);
		this.getAtomicOperations().add(op);
	}
}
