package ruleDeveloper;

import java.io.IOException;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Button.ClickEvent;

import dke.pr.cli.CBRInterface;
import g4.templates.RuleDeveloperDesign;
import g4dke.app.FloraInterface;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;

public abstract class RuleDeveloperViews extends RuleDeveloperDesign {
	private static final long serialVersionUID = 1L;

	private final String key;
	protected CBRInterface fl = null;

	public RuleDeveloperViews(String viewKey) {
		key = viewKey;
		initButtonsFromDesign();
		fl = FloraInterface.getInstance();
	}

	protected void setTitle(String title) {
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
				getUI().getNavigator().navigateTo(MainUI.RD_RULE_VIEW);

			}
		});// end rules ClickListener

		contexts.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RD_CONTEXT_VIEW);

			}
		});// end contexts ClickListener

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
		});// end logout ClickListener
	}
}
