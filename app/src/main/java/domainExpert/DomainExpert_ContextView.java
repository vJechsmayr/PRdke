package domainExpert;

import java.util.List;


import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextArea;

import dke.pr.cli.CBRInterface;
import g4.templates.DomainExpertDesign;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;

/*
 * @author Philip H.
 * 
 * */

public class DomainExpert_ContextView extends DomainExpertViews implements View {
	private static final long serialVersionUID = 1L;
	

	TextArea contextArea = new TextArea();
	
	public DomainExpert_ContextView() throws Exception {
		super(MainUI.DE_CONTEXT_VIEW);
		super.setTitle("Domain Expert - Context View");
		super.initInterface();
		
		showContexts();
		
	}
	
	
	
	
	private void showContexts() throws Exception{
		
		String value = new String();
			
		for (String x : fl.getCtxs()){
			value += x + "\t";	
		}

			contextArea.setValue(value);
			contextArea.setRows(25);
			
			//contentPanel.setContent(contextArea);		
			super.setContent(contextArea);
		
	}
	
	
	
}
