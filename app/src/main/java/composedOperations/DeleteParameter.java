package composedOperations;

public class DeleteParameter extends StandardComposedOperation{
 

	public DeleteParameter()
	{
		super();
		Operation op = new Operation("Delete Rules from Context");
		op.setRole("Rule Developer");
		op.setPosition(1);
		this.getAtomicOperations().add(op);
		op = new Operation("Delete Context");
		op.setRole("Repository Administrator");
		op.setPosition(2);
		this.getAtomicOperations().add(op);
		op = new Operation("Delete Parameter");
		op.setRole("Repository Administrator");
		op.setPosition(3);
		op.setLastOperation(true);
		this.getAtomicOperations().add(op);
	}
}
