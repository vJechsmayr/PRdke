package composedOperations;

import g4dke.app.SystemHelper;

public class ModifyRule extends StandardComposedOperation {

	public ModifyRule()
	{
		super();
		Operation op = new Operation(SystemHelper.DELETE_RULE);
		op.setRole(SystemHelper.RULE_DEVELOPER);
		op.setPosition(1);
		this.getAtomicOperations().add(op);
		op = new Operation(SystemHelper.NEW_RULE);
		op.setRole(SystemHelper.RULE_DEVELOPER);
		op.setPosition(2);
		op.setLastOperation(true);
		this.getAtomicOperations().add(op);
	}
}
