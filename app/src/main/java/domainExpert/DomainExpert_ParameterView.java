package domainExpert;

import com.vaadin.navigator.View;

import g4dke.app.MainUI;

/**
 * @author Viktoria J.
 * 
 * Anfrage new Parameter -> RepAdmin (Fall2)
 * Anfrage delete Parameter -> RepAdmin
 * 
 * */
public class DomainExpert_ParameterView extends DomainExpertViews implements View {

private static final long serialVersionUID = 1L;
	
	public DomainExpert_ParameterView(){
		super(MainUI.DE_PARAMETER_VIEW);
		super.setTitle("Domain Expert - Parameter View");
		super.initInterface();
		

	}

}
