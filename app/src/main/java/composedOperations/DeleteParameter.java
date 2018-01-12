package composedOperations;

import g4dke.app.SystemHelper;

public class DeleteParameter extends StandardComposedOperation{
 

	public DeleteParameter()
	{
		super();
		Operation op = new Operation(SystemHelper.DELETE_RULE_FROM_CONTEXT);
		op.setRole(SystemHelper.RULE_DEVELOPER);
		op.setPosition(1);
		this.getAtomicOperations().add(op);
		op = new Operation(SystemHelper.DELETE_CONTEXT);
		op.setRole(SystemHelper.REPOSITORY_ADMINISTRATOR);
		op.setPosition(2);
		this.getAtomicOperations().add(op);
		op = new Operation(SystemHelper.DELETE_PARAMETER);
		op.setRole(SystemHelper.REPOSITORY_ADMINISTRATOR);
		op.setPosition(3);
		op.setLastOperation(true);
		this.getAtomicOperations().add(op);
	}
}
