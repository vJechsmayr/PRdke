package app;

public class RuleDeveloper extends Role{

	public RuleDeveloper(String name) {
super.setName(name);
		
		/**
		 * set UserRights
		 * */
		super.setContextClassRead(false);
		super.setContextClassUpdate(false);
		
		super.setContextRead(true);
		super.setContextNew(false);
		super.setContextUpdate(false);
		super.setContextDelete(false);
		super.setContextMerge(false);
		super.setContextSplit(false);
		
		super.setParameterRead(true);
		super.setParameterNew(false);
		super.setParameterUpdate(false);
		super.setParameterDelete(false);
		
		super.setParameterValueRead(true);
		super.setParameterValueNew(false);
		super.setParameterValueUpdate(false);
		super.setParameterValueDelete(false);
		
		super.setMessageRead(true);
		super.setMessageNew(true);
		super.setMessageDelete(true);
		
		super.setSystemMessageRead(true);
		super.setSystemMessageNew(true);
		super.setSystemMessageOK(true);
		
		super.setBusinessCaseClassRead(false);
		super.setBusinessCaseClassUpdate(false);
		
		super.setBusinessCaseRead(true);
		super.setBusinessCaseNew(true);
		super.setBusinessCaseUpdate(true);
		super.setBusinessCaseDelete(true);
		
		super.setRuleRead(true);
		super.setRuleNew(true);
		super.setRuleUpdate(true);
		super.setRuleDelete(true);
		
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
