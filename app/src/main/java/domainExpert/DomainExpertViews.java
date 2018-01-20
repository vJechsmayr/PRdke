package domainExpert;

import java.io.IOException;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Button.ClickEvent;

import dke.pr.cli.CBRInterface;
import g4.templates.DomainExpertDesign;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;

public abstract class DomainExpertViews extends DomainExpertDesign{
	private final String key;
	protected CBRInterface fl = null;
	
	public DomainExpertViews(String viewKey) {
		key = viewKey;
		initButtonsFromDesign();
	}
	
	protected void initInterface()
	{
		try {
			fl = new CBRInterface(
					SystemHelper.PFAD + "/ctxModelAIM.flr",
					SystemHelper.PFAD + "/bc.flr", "AIMCtx",
					"SemNOTAMCase");
			fl.setDebug(false);
		} catch (IOException e) {
			e.printStackTrace();
		}

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
				getUI().getNavigator().navigateTo(MainUI.DE_BUSINESSCASE_VIEW);
			}
		});
		
		businessCaseClass.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.DE_BUSINESSCASECLASS_VIEW);
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
