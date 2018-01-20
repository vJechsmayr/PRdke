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
	private static final long serialVersionUID = 1L;
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
	
	rules.addClickListener(new Button.ClickListener() {
		private static final long serialVersionUID = 1L;
		@Override
		public void buttonClick(ClickEvent event) {
			getUI().getNavigator().navigateTo(MainUI.DE_RULE_VIEW);
			
		}
	});
	
	contexts.addClickListener(new Button.ClickListener() {
		private static final long serialVersionUID = 1L;
		@Override
		public void buttonClick(ClickEvent event) {
			getUI().getNavigator().navigateTo(MainUI.DE_CONTEXT_VIEW);
			
		}
	});
	
	parameter.addClickListener(new Button.ClickListener() {
		private static final long serialVersionUID = 1L;
		@Override
		public void buttonClick(ClickEvent event) {
			getUI().getNavigator().navigateTo(MainUI.DE_PARAMETER_VIEW);
			
		}
	});
	
	parameterValue.addClickListener(new Button.ClickListener() {
		private static final long serialVersionUID = 1L;
		@Override
		public void buttonClick(ClickEvent event) {
			getUI().getNavigator().navigateTo(MainUI.DE_PARAMETERVALUE_VIEW);
			
		}
	});
		
		businessCase.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.DE_BUSINESSCASE_VIEW);
			}
		});
		
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
