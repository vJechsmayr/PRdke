package composedOperations;

public class MergeContext  extends StandardComposedOperation{

	public MergeContext()
	{
		super();
		Operation op = new Operation("Change Context");
		op.setRole("Rule Developer");
		op.setPosition(1);
		this.getAtomicOperations().add(op);
		 op = new Operation("Delete Context");
			op.setRole("Rule Developer");
			op.setPosition(2);
		op.setLastOperation(true);
		this.getAtomicOperations().add(op);
	}
}
