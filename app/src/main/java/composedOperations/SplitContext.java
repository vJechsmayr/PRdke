package composedOperations;

public class SplitContext extends  StandardComposedOperation{

	
	public SplitContext()
	{
		super();
		Operation op = new Operation("Split Context");
		op.setRole("Repository Administrator");
		op.setPosition(1);
		op.setLastOperation(true);
		this.getAtomicOperations().add(op);
	}
}
