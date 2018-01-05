package ruleDeveloper;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

import g4.templates.RuleDeveloperDesign;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;

/*
 * @author Viktoria J.
 * 
 * */
public class RuleDev_ParameterView extends RuleDeveloperDesign implements View{
	private static final long serialVersionUID = 1L;
	
	public RuleDev_ParameterView() {
		viewTitle.setValue("Rule Developer - Parameter View");
		initView();
		
	}
	
	private void initView() {
		initButtons();
		
	}
	
	private void initButtons() {
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
			
			parameter.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;
				@Override
				public void buttonClick(ClickEvent event) {
					getUI().getNavigator().navigateTo(MainUI.RD_PARAMETER_VIEW);
					
				}
			});
			
			parameterValue.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;
				@Override
				public void buttonClick(ClickEvent event) {
					getUI().getNavigator().navigateTo(MainUI.RD_PARAMETERVALUE_VIEW);
					
				}
			});
			
			messagingService.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;
				@Override
				public void buttonClick(ClickEvent event) {
					getUI().getNavigator().navigateTo(MainUI.MS_INBOX);
				}
			});
		
		logout.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				SystemHelper.logout();
				getUI().getNavigator().navigateTo(MainUI.LOGIN_VIEW);
			}
		});//end logout ClickListener
			
			
		}
		
	
}
