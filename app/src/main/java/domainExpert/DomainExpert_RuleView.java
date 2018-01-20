package domainExpert;

import com.vaadin.navigator.View;

import g4dke.app.MainUI;

/**
 * @author Viktoria J.
 * 
 * Liste aller Rules
 * Anfrage update Rule -> RuleDev (nice to have)
 * Anfrage neue Rule -> RuleDev
 * Anfrage delete Rule -> RuleDev
 * Anfrage contextualise Rule -> RuleDev
 * 
 * */
public class DomainExpert_RuleView extends DomainExpertViews implements View {
	private static final long serialVersionUID = 1L;
	
	public DomainExpert_RuleView(){
		super(MainUI.DE_RULE_VIEW);
		super.setTitle("Domain Expert - Rule View");
		super.initInterface();
		

	}
}
