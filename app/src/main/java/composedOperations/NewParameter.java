package composedOperations;

import g4dke.app.SystemHelper;

public class NewParameter extends StandardComposedOperation{


	public NewParameter()
	{
		super();
		Operation op = new Operation(SystemHelper.NEW_PARAMETER);
		op.setRole(SystemHelper.REPOSITORY_ADMINISTRATOR);
		op.setPosition(1);
		op.setLastOperation(true);
		this.getAtomicOperations().add(op);
	}
}
