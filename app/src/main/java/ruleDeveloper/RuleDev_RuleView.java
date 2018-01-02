package ruleDeveloper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

import dke.pr.cli.CBRInterface;
import g4.templates.RuleDeveloperDesign;
import g4dke.app.MainUI;

/*
 * @author Viktoria J.
 * 
 * */
public class RuleDev_RuleView extends RuleDeveloperDesign implements View{
	private static final long serialVersionUID = 1L;
	
	// PFAD auf das Verzeichnis der Flora installation ändern
	//Viktoria C:/Users/vikto/Flora-2/flora2
	//
	final static String PFAD = "C:/Users/vikto/Flora-2/flora2";
	List<String> contextList = new ArrayList<String>(); 
	
	
	public RuleDev_RuleView() throws Exception{
		
		viewTitle.setValue("Rule Developer - Rule View");
		initView();
		
		CBRInterface fl = new CBRInterface(
				PFAD + "/ctxModelAIM.flr",
				PFAD + "/bc.flr", "AIMCtx",
				"SemNOTAMCase");

		fl.setDebug(false);
		
		//System.out.println("Contexts: " + fl.getCtxs());
		contextList = fl.getCtxs();
		
		for(String s: contextList){
			System.out.println("Context: " + s + "\n Rules: " + fl.getRules(s));
			
		}
		
		fl.close();
		
		
		//showRules("");
		
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
	
	private void showRules(String context) throws Exception{
		
		
		
	}
	
	private void logout() {
		//toDO 
		
	}
	
	
}