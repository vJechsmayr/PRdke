package composedOperations;

public class DeContextualizeRule extends StandardComposedOperation{

	//Needs Actually from user to user and not from role.
	public DeContextualizeRule()
	{
		super();
		Operation op = new Operation("Change Context");
		op.setRole("Rule Developer");
		op.setPosition(1);
		op.setLastOperation(true);
		this.getAtomicOperations().add(op);
	}
}
