package userViews;

import java.io.IOException;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;

import dke.pr.cli.CBRInterface;
import g4.templates.UserDesign;
import g4dke.app.FloraInterface;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;

public abstract class UserViews extends UserDesign{
	private static final long serialVersionUID = 1L;

	private final String key;
	protected CBRInterface fl = null;
	
	public UserViews(String viewKey) {
		key = viewKey;
		initButtonsFromDesign();
		fl = FloraInterface.getInstance();
	}
	
	protected void setTitle(String title)
	{
		viewTitle.setValue(title);
	}
	
	
	protected void setContent(Component c) {
		contentPanel.setContent(c);
		
	}
	
	private void initButtonsFromDesign() {
		businessCase.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;
				
				@Override
				public void buttonClick(ClickEvent event) {
					getUI().getNavigator().navigateTo(MainUI.USER_BUSINESSCASE);
					
				}
			});//end rules ClickListener
			
		interestSpec.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;
				
				@Override
				public void buttonClick(ClickEvent event) {
					getUI().getNavigator().navigateTo(MainUI.USER_INTERESTSPEC);
					
				}
			});//end contexts ClickListener
			
			
			messagingService.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;
				@Override
				public void buttonClick(ClickEvent event) {
					SystemHelper.lastPage = key;
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
