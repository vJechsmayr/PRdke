package composedOperations;

public class ModifyRule extends StandardComposedOperation {

	public ModifyRule()
	{
		super();
		Operation op = new Operation("Delete Rule");
		op.setRole("Rule Developer");
		op.setPosition(1);
		this.getAtomicOperations().add(op);
		op = new Operation("New Rule");
		op.setRole("Rule Developer");
		op.setPosition(2);
		op.setLastOperation(true);
		this.getAtomicOperations().add(op);
	}
}
