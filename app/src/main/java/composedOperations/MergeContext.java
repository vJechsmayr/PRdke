package composedOperations;

import g4dke.app.SystemHelper;

public class MergeContext  extends StandardComposedOperation{

	public MergeContext()
	{
		super();
		Operation op = new Operation(SystemHelper.CHANGECONTEXT);
		op.setRole(SystemHelper.RULE_DEVELOPER);
		op.setPosition(1);
		this.getAtomicOperations().add(op);
		 op = new Operation(SystemHelper.DELETE_CONTEXT);
			op.setRole(SystemHelper.REPOSITORY_ADMINISTRATOR);
			op.setPosition(2);
		op.setLastOperation(true);
		this.getAtomicOperations().add(op);
	}
}
