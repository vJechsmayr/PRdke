package composedOperations;

public class ContexualizeRule extends StandardComposedOperation{


	public ContexualizeRule()
	{
		super();
		Operation op = new Operation("Change Context");
		op.setRole("Rule Developer");
		op.setPosition(1);
		op.setLastOperation(true);
		this.getAtomicOperations().add(op);
	}
}
