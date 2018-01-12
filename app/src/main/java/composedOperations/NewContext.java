package composedOperations;

import g4dke.app.SystemHelper;

public class NewContext extends StandardComposedOperation{


	public NewContext()
	{
		super();
		Operation op = new Operation(SystemHelper.NEW_PARAMETER_VALUE);
		op.setRole(SystemHelper.REPOSITORY_ADMINISTRATOR);
		op.setPosition(1);
		this.getAtomicOperations().add(op);
		op = new Operation(SystemHelper.NEW_CONTEXT);
		op.setRole(SystemHelper.REPOSITORY_ADMINISTRATOR);
		op.setPosition(2);
		op.setLastOperation(true);
		this.getAtomicOperations().add(op);
	}
}
