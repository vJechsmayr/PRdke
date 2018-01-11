package composedOperations;

public class DeleteContext extends StandardComposedOperation {

	public DeleteContext()
	{
		super();
		Operation op = new Operation("Delete Rule from Context");
		op.setRole("Rule Developer");
		op.setPosition(1);
		this.getAtomicOperations().add(op);
		op = new Operation("Delete Context");
		op.setRole("Repository Administrator");
		op.setPosition(2);
		op.setLastOperation(true);
		this.getAtomicOperations().add(op);
	}
}
