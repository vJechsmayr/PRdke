package composedOperations;

public class Operation {

	private int position;
	private String nameOfOperation;
	private String role;
	private boolean isLastOperation;
	
	public Operation(String name)
	{
		this.nameOfOperation = name;
		isLastOperation = false;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getNameOfOperation() {
		return nameOfOperation;
	}

	public void setNameOfOperation(String nameOfOperation) {
		this.nameOfOperation = nameOfOperation;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isLastOperation() {
		return isLastOperation;
	}

	public void setLastOperation(boolean isLastOperation) {
		this.isLastOperation = isLastOperation;
	}
	
	
	
}
