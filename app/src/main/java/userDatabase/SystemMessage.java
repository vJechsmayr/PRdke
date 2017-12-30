package userDatabase;

import java.util.Date;
/*
 * @author Marcel G.
 * */
public class SystemMessage extends Message{

	private String atomicOperation;
	private String concernedRuleTerm;
	private String containingContext;
	
	public SystemMessage()
	{
		
	}
	
	public SystemMessage(int id, Date timestamp, int authorId, int receiverId,String text, String atmoicOperation, String concernedRuleTerm, String containingContext)
	{
		super(id,timestamp,authorId,receiverId, text);
		this.atomicOperation = atmoicOperation;
		this.concernedRuleTerm = concernedRuleTerm;
		this.containingContext = containingContext;
	}

	public String getAtomicOperation() {
		return atomicOperation;
	}

	public void setAtomicOperation(String atomicOperation) {
		this.atomicOperation = atomicOperation;
	}

	public String getConcernedRuleTerm() {
		return concernedRuleTerm;
	}

	public void setConcernedRuleTerm(String concernedRuleTerm) {
		this.concernedRuleTerm = concernedRuleTerm;
	}

	public String getContainingContext() {
		return containingContext;
	}

	public void setContainingContext(String containingContext) {
		this.containingContext = containingContext;
	}

	
}
