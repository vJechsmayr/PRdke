package app;

/*
 * @author Viktoria J.
 * */
public class Role {

	private String name;
	
	/**
	 * ContextClass
	 * */
	private boolean contextClassRead;
	private boolean contextClassUpdate;
	
	/**
	 * Context
	 * */
	private boolean contextRead;
	private boolean contextNew;
	private boolean contextUpdate;
	private boolean contextDelete;
	private boolean contextSplit; //contextUpdate + contextNew
	private boolean contextMerge; //contextNew + contextDelete
	
	/**
	 * Parameter 
	 * */
	private boolean parameterRead;
	private boolean parameterNew;
	private boolean parameterUpdate;
	private boolean parameterDelete;
	
	/**
	 * ParameterValue
	 * */
	private boolean parameterValueRead;
	private boolean parameterValueNew;
	private boolean parameterValueUpdate;
	private boolean parameterValueDelete;
	
	/**
	 * Message
	 * */
	private boolean messageRead;
	private boolean messageNew;
	private boolean messageDelete;
	
	/**
	 * System-Message
	 * */
	private boolean systemMessageRead;
	private boolean systemMessageNew;
	private boolean systemMessageOK; //Acknowledge
	
	/**
	 * BusinessCaseClass
	 * */
	private boolean businessCaseClassRead;
	private boolean businessCaseClassUpdate;
	
	/**
	 * BusinessCase
	 * */
	private boolean businessCaseRead;
	private boolean businessCaseNew;
	private boolean businessCaseUpdate;
	private boolean businessCaseDelete;
	
	/**
	 * Rule
	 * */
	private boolean ruleRead;
	private boolean ruleNew;
	private boolean ruleUpdate;
	private boolean ruleDelete;
	
	/**
	 * InterestSpec
	 * */
	private boolean interestSpecRead;
	private boolean interestSpecNew;
	
	
	public String getName() {
		return name;
	}
	public boolean isContextClassRead() {
		return contextClassRead;
	}
	public boolean isContextClassUpdate() {
		return contextClassUpdate;
	}
	public boolean isContextRead() {
		return contextRead;
	}
	public boolean isContextNew() {
		return contextNew;
	}
	public boolean isContextUpdate() {
		return contextUpdate;
	}
	public boolean isContextDelete() {
		return contextDelete;
	}
	public boolean isContextSplit() {
		return contextSplit;
	}
	public boolean isContextMerge() {
		return contextMerge;
	}
	public boolean isParameterRead() {
		return parameterRead;
	}
	public boolean isParameterNew() {
		return parameterNew;
	}
	public boolean isParameterUpdate() {
		return parameterUpdate;
	}
	public boolean isParameterDelete() {
		return parameterDelete;
	}
	public boolean isParameterValueRead() {
		return parameterValueRead;
	}
	public boolean isParameterValueNew() {
		return parameterValueNew;
	}
	public boolean isParameterValueUpdate() {
		return parameterValueUpdate;
	}
	public boolean isParameterValueDelete() {
		return parameterValueDelete;
	}
	public boolean isMessageRead() {
		return messageRead;
	}
	public boolean isMessageNew() {
		return messageNew;
	}
	public boolean isMessageDelete() {
		return messageDelete;
	}
	public boolean isSystemMessageRead() {
		return systemMessageRead;
	}
	public boolean isSystemMessageNew() {
		return systemMessageNew;
	}
	public boolean isSystemMessageOK() {
		return systemMessageOK;
	}
	public boolean isBusinessCaseClassRead() {
		return businessCaseClassRead;
	}
	public boolean isBusinessCaseClassUpdate() {
		return businessCaseClassUpdate;
	}
	public boolean isBusinessCaseRead() {
		return businessCaseRead;
	}
	public boolean isBusinessCaseNew() {
		return businessCaseNew;
	}
	public boolean isBusinessCaseUpdate() {
		return businessCaseUpdate;
	}
	public boolean isBusinessCaseDelete() {
		return businessCaseDelete;
	}
	public boolean isRuleRead() {
		return ruleRead;
	}
	public boolean isRuleNew() {
		return ruleNew;
	}
	public boolean isRuleUpdate() {
		return ruleUpdate;
	}
	public boolean isRuleDelete() {
		return ruleDelete;
	}
	public boolean isInterestSpecRead() {
		return interestSpecRead;
	}
	public boolean isInterestSpecNew() {
		return interestSpecNew;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setContextClassRead(boolean contextClassRead) {
		this.contextClassRead = contextClassRead;
	}
	public void setContextClassUpdate(boolean contextClassUpdate) {
		this.contextClassUpdate = contextClassUpdate;
	}
	public void setContextRead(boolean contextRead) {
		this.contextRead = contextRead;
	}
	public void setContextNew(boolean contextNew) {
		this.contextNew = contextNew;
	}
	public void setContextUpdate(boolean contextUpdate) {
		this.contextUpdate = contextUpdate;
	}
	public void setContextDelete(boolean contextDelete) {
		this.contextDelete = contextDelete;
	}
	public void setContextSplit(boolean contextSplit) {
		this.contextSplit = contextSplit;
	}
	public void setContextMerge(boolean contextMerge) {
		this.contextMerge = contextMerge;
	}
	public void setParameterRead(boolean parameterRead) {
		this.parameterRead = parameterRead;
	}
	public void setParameterNew(boolean parameterNew) {
		this.parameterNew = parameterNew;
	}
	public void setParameterUpdate(boolean parameterUpdate) {
		this.parameterUpdate = parameterUpdate;
	}
	public void setParameterDelete(boolean parameterDelete) {
		this.parameterDelete = parameterDelete;
	}
	public void setParameterValueRead(boolean parameterValueRead) {
		this.parameterValueRead = parameterValueRead;
	}
	public void setParameterValueNew(boolean parameterValueNew) {
		this.parameterValueNew = parameterValueNew;
	}
	public void setParameterValueUpdate(boolean parameterValueUpdate) {
		this.parameterValueUpdate = parameterValueUpdate;
	}
	public void setParameterValueDelete(boolean parameterValueDelete) {
		this.parameterValueDelete = parameterValueDelete;
	}
	public void setMessageRead(boolean messageRead) {
		this.messageRead = messageRead;
	}
	public void setMessageNew(boolean messageNew) {
		this.messageNew = messageNew;
	}
	public void setMessageDelete(boolean messageDelete) {
		this.messageDelete = messageDelete;
	}
	public void setSystemMessageRead(boolean systemMessageRead) {
		this.systemMessageRead = systemMessageRead;
	}
	public void setSystemMessageNew(boolean systemMessageNew) {
		this.systemMessageNew = systemMessageNew;
	}
	public void setSystemMessageOK(boolean systemMessageOK) {
		this.systemMessageOK = systemMessageOK;
	}
	public void setBusinessCaseClassRead(boolean businessCaseClassRead) {
		this.businessCaseClassRead = businessCaseClassRead;
	}
	public void setBusinessCaseClassUpdate(boolean businessCaseClassUpdate) {
		this.businessCaseClassUpdate = businessCaseClassUpdate;
	}
	public void setBusinessCaseRead(boolean businessCaseRead) {
		this.businessCaseRead = businessCaseRead;
	}
	public void setBusinessCaseNew(boolean businessCaseNew) {
		this.businessCaseNew = businessCaseNew;
	}
	public void setBusinessCaseUpdate(boolean businessCaseUpdate) {
		this.businessCaseUpdate = businessCaseUpdate;
	}
	public void setBusinessCaseDelete(boolean businessCaseDelete) {
		this.businessCaseDelete = businessCaseDelete;
	}
	public void setRuleRead(boolean ruleRead) {
		this.ruleRead = ruleRead;
	}
	public void setRuleNew(boolean ruleNew) {
		this.ruleNew = ruleNew;
	}
	public void setRuleUpdate(boolean ruleUpdate) {
		this.ruleUpdate = ruleUpdate;
	}
	public void setRuleDelete(boolean ruleDelete) {
		this.ruleDelete = ruleDelete;
	}
	public void setInterestSpecRead(boolean interestSpecRead) {
		this.interestSpecRead = interestSpecRead;
	}
	public void setInterestSpecNew(boolean interestSpecNew) {
		this.interestSpecNew = interestSpecNew;
	}
	
	
	
	
	
}
