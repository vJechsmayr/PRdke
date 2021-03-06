package userDatabase;

import java.util.Date;

/*
 * @author Marcel G.
 * */
public class SystemMessage extends Message {

	private String atomicOperation;
	private String concernedRuleTerm;
	private String containingContext;
	private String concernedParameter;
	private boolean acknowledged;
	private String[] additionalData;

	public SystemMessage() {
		super();
		this.acknowledged = false;
		this.atomicOperation = "";
		this.concernedParameter = "";
		this.containingContext = "";
		this.concernedRuleTerm = "";
		this.additionalData = new String[0];
	}

	public SystemMessage(int id, Date timestamp, String authorId, String receiverId, String text,
			String atmoicOperation, String concernedRuleTerm, String containingContext, String parameter,
			String[] additionalData) {
		super(id, timestamp, authorId, receiverId, text);
		this.atomicOperation = atmoicOperation;
		this.concernedRuleTerm = concernedRuleTerm;
		this.containingContext = containingContext;
		this.acknowledged = false;
		this.concernedParameter = parameter;
		this.additionalData = additionalData;
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

	public boolean isAcknowledged() {
		return acknowledged;
	}

	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}

	public String getConcernedParameter() {
		return concernedParameter;
	}

	public void setConcernedParameter(String concernedParameter) {
		this.concernedParameter = concernedParameter;
	}

	public String[] getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(String[] additionalData) {
		this.additionalData = additionalData;
	}

}
