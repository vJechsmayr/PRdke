package domainExpert;

import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextArea;

import dke.pr.cli.CBRInterface;
import g4.templates.DomainExpertDesign;
import g4dke.app.MainUI;

public class DomainExpert_ContextView extends DomainExpertDesign implements View {

	private static final long serialVersionUID = 1L;

	// PFAD auf das Verzeichnis der Flora installation ändern
	// Philip D:\Users\Philip\Flora-2\flora2
	
	final static String PFAD = "D:/Users/Philip/Flora-2/flora2";
	
	Button showCtx = new Button("show Context");
	TextArea contextArea = new TextArea();
	
	public DomainExpert_ContextView() throws Exception {
		viewTitle.setValue("Domain Expert - Context View");
		
		initView();
		initContextView();
		
	}
	
	private void initView() {
		
		contexts.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.DE_CONTEXT_VIEW);
				
			}
		});//end contexts ClickListener
		
		contexts.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.DE_BUSINESSCASE_VIEW);
				
			}
		});//end businessCase ClickListener
		
		logout.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				logout();
				getUI().getNavigator().navigateTo(MainUI.LOGIN_VIEW);
			}
		});//end logout ClickListener
		
	}
	
	private void initContextView() throws Exception {
	showCtx.addClickListener(new Button.ClickListener() {
				
				/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					try {
						showContexts();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
	contentPanel.setContent(showCtx);
		

	
	
	}
	
	
	private void showContexts() throws Exception{
		
			
		
		CBRInterface fl = new CBRInterface(
				PFAD + "/ctxModelAIM.flr",
				PFAD + "/bc.flr", "AIMCtx",
				"SemNOTAMCase");

		fl.setDebug(false);

		System.out.println("Contexts: " + fl.getCtxs());

		
		
		String value = new String();
			
		for (String x : fl.getCtxs()){
			value += x + "\t";	
		}

			contextArea.setValue(value);
			contextArea.setRows(25);
			
			contentPanel.setContent(contextArea);
		
			
			
		fl.close();	
	}
	
	private void logout() {
		//toDO 
		
	}
	
}
