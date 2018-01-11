package composedOperations;

public class NewParameter extends StandardComposedOperation{


	public NewParameter()
	{
		super();
		Operation op = new Operation("New Parameter");
		op.setRole("Repository Administrator");
		op.setPosition(1);
		op.setLastOperation(true);
		this.getAtomicOperations().add(op);
	}
}
