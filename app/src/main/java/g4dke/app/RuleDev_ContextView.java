package g4dke.app;

import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextArea;

import dke.pr.cli.CBRInterface;
import g4.templates.RuleDeveloperDesign;

/*
 * @author Viktoria J.
 * 
 * */
public class RuleDev_ContextView extends RuleDeveloperDesign implements View{
	private static final long serialVersionUID = 1L;
	
	// PFAD auf das Verzeichnis der Flora installation ändern
	//Viktoria C:/Users/vikto/Flora-2/flora2
	//
	final static String PFAD = "C:/Users/vikto/Flora-2/flora2";
	Button showCtx = new Button("show Context");
	TextArea contextArea = new TextArea();

	public RuleDev_ContextView() throws Exception {
		viewTitle.setValue("Rule Developer - Context View");
		
		initView();
		initContextView();
		
		
		

		
		
		
	}
	
	private void initView() {
		rules.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RD_RULE_VIEW);
				
			}
		});//end rules ClickListener
		
		contexts.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RD_CONTEXT_VIEW);
				
			}
		});//end contexts ClickListener
		
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
