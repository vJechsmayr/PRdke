package composedOperations;

import java.util.ArrayList;

public class StandardComposedOperation {

	//if size < currenposition  -> all done
	private ArrayList<Operation> AtomicOperations;
	private int currenposition;
	public StandardComposedOperation()
	{
		this.currenposition=1;
	}

	public ArrayList<Operation> getAtomicOperations() {
		return AtomicOperations;
	}

	public void setAtomicOperations(ArrayList<Operation> atomicOperations) {
		AtomicOperations = atomicOperations;
	}

	public int getCurrenposition() {
		return currenposition;
	}

	public void setCurrenposition(int currenposition) {
		this.currenposition = currenposition;
	}
	
	
}
