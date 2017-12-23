package app;

public class RepositoryAdministrator extends Role{
	public RepositoryAdministrator(String name) {
super.setName(name);
		
		/**
		 * set UserRights
		 * */
		super.setContextClassRead(true);
		super.setContextClassUpdate(true);
		
		super.setContextRead(true);
		super.setContextNew(true);
		super.setContextUpdate(true);
		super.setContextDelete(true);
		super.setContextMerge(true);
		super.setContextSplit(true);
		
		super.setParameterRead(true);
		super.setParameterNew(true);
		super.setParameterUpdate(true);
		super.setParameterDelete(true);
		
		super.setParameterValueRead(true);
		super.setParameterValueNew(true);
		super.setParameterValueUpdate(true);
		super.setParameterValueDelete(true);
		
		super.setMessageRead(true);
		super.setMessageNew(true);
		super.setMessageDelete(true);
		
		super.setSystemMessageRead(false);
		super.setSystemMessageNew(false);
		super.setSystemMessageOK(false);
		
		super.setBusinessCaseClassRead(true);
		super.setBusinessCaseClassUpdate(true);
		
		super.setBusinessCaseRead(true);
		super.setBusinessCaseNew(true);
		super.setBusinessCaseUpdate(true);
		super.setBusinessCaseDelete(true);
		
		super.setRuleRead(false);
		super.setRuleNew(false);
		super.setRuleUpdate(false);
		super.setRuleDelete(false);
		
		super.setInterestSpecRead(false);
		super.setInterestSpecNew(false);
		
	}
	
	public String getName(){
		return super.getName();
	}
	public boolean isContextClassRead() {
		return super.isContextClassRead();
	}
	public boolean isContextClassUpdate() {
		return super.isContextClassUpdate();
	}
	public boolean isContextRead() {
		return super.isContextRead();
	}
	public boolean isContextNew() {
		return super.isContextNew();
	}
	public boolean isContextUpdate() {
		return super.isContextUpdate();
	}
	public boolean isContextDelete() {
		return super.isContextDelete();
	}
	public boolean isContextSplit() {
		return super.isContextSplit();
	}
	public boolean isContextMerge() {
		return super.isContextMerge();
	}
	public boolean isParameterRead() {
		return super.isParameterRead();
	}
	public boolean isParameterNew() {
		return super.isParameterNew();
	}
	public boolean isParameterUpdate() {
		return super.isParameterUpdate();
	}
	public boolean isParameterDelete() {
		return super.isParameterDelete();
	}
	public boolean isParameterValueRead() {
		return super.isParameterValueRead();
	}
	public boolean isParameterValueNew() {
		return super.isParameterValueNew();
	}
	public boolean isParameterValueUpdate() {
		return super.isParameterValueUpdate();
	}
	public boolean isParameterValueDelete() {
		return super.isParameterValueDelete();
	}
	public boolean isMessageRead() {
		return super.isMessageRead();
	}
	public boolean isMessageNew() {
		return super.isMessageNew();
	}
	public boolean isMessageDelete() {
		return super.isMessageDelete();
	}
	public boolean isSystemMessageRead() {
		return super.isSystemMessageRead();
	}
	public boolean isSystemMessageNew() {
		return super.isSystemMessageNew();
	}
	public boolean isSystemMessageOK() {
		return super.isSystemMessageOK();
	}
	public boolean isBusinessCaseClassRead() {
		return super.isBusinessCaseClassRead();
	}
	public boolean isBusinessCaseClassUpdate() {
		return super.isBusinessCaseClassUpdate();
	}
	public boolean isBusinessCaseRead() {
		return super.isBusinessCaseRead();
	}
	public boolean isBusinessCaseNew() {
		return super.isBusinessCaseNew();
	}
	public boolean isBusinessCaseUpdate() {
		return super.isBusinessCaseUpdate();
	}
	public boolean isBusinessCaseDelete() {
		return super.isBusinessCaseDelete();
	}
	public boolean isRuleRead() {
		return super.isRuleRead();
	}
	public boolean isRuleNew() {
		return super.isRuleNew();
	}
	public boolean isRuleUpdate() {
		return super.isRuleUpdate();
	}
	public boolean isRuleDelete() {
		return super.isRuleDelete();
	}
	public boolean isInterestSpecRead() {
		return super.isInterestSpecRead();
	}
	public boolean isInterestSpecNew() {
		return super.isInterestSpecNew();
	}
}
