package composedOperations;

public class NewContext extends StandardComposedOperation{


	public NewContext()
	{
		super();
		Operation op = new Operation("New Parameter Value");
		op.setRole("Repository Administrator");
		op.setPosition(1);
		this.getAtomicOperations().add(op);
		op = new Operation("New Context");
		op.setRole("Repository Administrator");
		op.setPosition(2);
		op.setLastOperation(true);
		this.getAtomicOperations().add(op);
	}
}
