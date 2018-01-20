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


public class DomainExpert_BusinessCaseClassView extends DomainExpertViews implements View {
	private static final long serialVersionUID = 1L;


	
	
	TextArea BCCsArea = new TextArea();
	
	public DomainExpert_BusinessCaseClassView() throws Exception {
		super(MainUI.DE_BUSINESSCASECLASS_VIEW);
		super.setTitle("Domain Expert - Business Case Class View");
		super.initInterface();
		
		
		showBusinessCases();
	}
	

		private void showBusinessCases() throws Exception{
			
			System.out.println("Business Cases: " + fl.getCtxs());

			String value = new String();
				
			for (String x : fl.getCtxs()){
				value += x + "\t";	
			}

			BCCsArea.setValue(value);
			BCCsArea.setRows(25);
				
			//contentPanel.setContent(BCCsArea);
			super.setContent(BCCsArea);
		}
		

}
