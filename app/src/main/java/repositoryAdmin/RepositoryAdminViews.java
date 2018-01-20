package repositoryAdmin;

import java.io.IOException;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Button.ClickEvent;

import dke.pr.cli.CBRInterface;
import g4.templates.RepositoryAdminDesign;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;

public abstract class RepositoryAdminViews extends RepositoryAdminDesign{
	private static final long serialVersionUID = 1L;
	private final String key;
	protected CBRInterface fl = null;
	
	public RepositoryAdminViews(String viewKey) {
		key = viewKey;
		initButtonsFromDesign();
		
	}
	
	protected void setTitle(String title)
	{
		viewTitle.setValue(title);
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
	
	protected void setContent(Component c) {
		contentPanel.setContent(c);
		
	}
	
	private void initButtonsFromDesign() {
		// Contexts
		contexts.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RA_CONTEXT_VIEW);

			}
		});// end ClickListener

//		// ContextClass
//		contextsClass.addClickListener(new Button.ClickListener() {
//			private static final long serialVersionUID = 1L;
//			
//			@Override
//			public void buttonClick(ClickEvent event) {
//				getUI().getNavigator().navigateTo(MainUI.RA_CONTEXTCLASS_VIEW);
//				
//			}
//		});
		// end ClickListener

		// Parameter
		parameter.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RA_PARAMETER_VIEW);
				
			}
		});
		// end ClickListener

		// ParameterVal
		parameterValue.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RA_PARAMETERVALUE_VIEW);
				
			}
		});
		// end ClickListener

		// MessagingService
		messagingService.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				SystemHelper.lastPage = key;
				getUI().getNavigator().navigateTo(MainUI.MS_INBOX);

			}
		}); // end ClickListener

		// Logout
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
