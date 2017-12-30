package g4dke.app;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

import g4.templates.RuleDeveloperDesign;

/*
 * @author Viktoria J.
 * 
 * */
public class RuleDev_ContextView extends RuleDeveloperDesign implements View{
	private static final long serialVersionUID = 1L;

	public RuleDev_ContextView() {
		viewTitle.setValue("Rule Developer - Context View");
		initView();
		
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
	
	private void logout() {
		//toDO 
		
	}
}
