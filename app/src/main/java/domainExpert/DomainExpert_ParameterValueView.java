package domainExpert;

import com.vaadin.navigator.View;

import g4dke.app.MainUI;

/**
 * @author Viktoria J.
 * 
 * 
 * Liste ParameterValues
 * Anfrage add ParameterValue -> RepAdmin 
 * Anfrage delete ParameterValue ->RepAdmin
 * Anfrage update ParameterValue -> RepAdmin (nice to have)
 * 
 * */
public class DomainExpert_ParameterValueView extends DomainExpertViews implements View  {
private static final long serialVersionUID = 1L;
	
	public DomainExpert_ParameterValueView(){
		super(MainUI.DE_PARAMETERVALUE_VIEW);
		super.setTitle("Domain Expert - ParameterValue View");
		super.initInterface();
		

	}

}
