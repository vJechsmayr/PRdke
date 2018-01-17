package composedOperations;

import java.util.ArrayList;



public class StandardComposedOperation {

	//if size < currenposition  -> all done
	private ArrayList<Operation> AtomicOperations;
	public StandardComposedOperation()
	{
	}

	public ArrayList<Operation> getAtomicOperations() {
		return AtomicOperations;
	}

	public void setAtomicOperations(ArrayList<Operation> atomicOperations) {
		AtomicOperations = atomicOperations;
	}


	public boolean isFinished(int currentPosition)
	{
		for(Operation op : AtomicOperations)
		{
			if(op.getPosition() == currentPosition)
			{
				return op.isLastOperation();
			}
		}

		return false;
	}
	
	public Operation getOperation(int currentPosition)
	{
		for(Operation o : this.getAtomicOperations())
		{
			if(o.getPosition() == currentPosition)
			{
				return o;
			}
		}
		return null;
	}
	
	
}
