package composedOperations;

import g4dke.app.SystemHelper;

public class ContexualizeRule extends StandardComposedOperation{


	public ContexualizeRule()
	{
		super();
		Operation op = new Operation(SystemHelper.CHANGECONTEXT);
		op.setRole(SystemHelper.RULE_DEVELOPER);
		op.setPosition(1);
		op.setLastOperation(true);
		this.getAtomicOperations().add(op);
	}
}
