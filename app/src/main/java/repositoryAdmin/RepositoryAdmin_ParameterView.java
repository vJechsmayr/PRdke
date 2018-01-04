package repositoryAdmin;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

import g4.templates.RepositoryAdminDesign;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;

/*
 * @author Marcel G.
 * 
 * */
public class RepositoryAdmin_ParameterView extends RepositoryAdminDesign implements View {
	
	
	public RepositoryAdmin_ParameterView()
	{
		initView();
	}
	
	private void initView()
	{
		//TODO:
		//protected Button contextsClass;
		//protected Button parameterValue;
		
		contexts.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RA_CONTEXT_VIEW);
				
			}
		});//end  ClickListener
		
		parameter.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RA_PARAMETER_VIEW);
				
			}
		});//end  ClickListener
		
		messagingService.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.MS_INBOX);
				
			}
		});//end  ClickListener
		
		logout.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				SystemHelper.logout();
				getUI().getNavigator().navigateTo(MainUI.LOGIN_VIEW);
				
			}
		});//end  ClickListener
	}
}
