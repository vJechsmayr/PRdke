package repositoryAdmin;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Button.ClickEvent;

import g4.templates.RepositoryAdminDesign;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;

public class RepositoryAdmin_ContextClassView extends RepositoryAdminDesign implements View {
	private static final long serialVersionUID = 1L;

	// Flora-2 Installationspfad
	final static String PFAD = SystemHelper.PFAD;

	Button showCtx = new Button("show Context");
	TextArea contextArea = new TextArea();

	public RepositoryAdmin_ContextClassView() throws Exception {

		viewTitle.setValue("RepositoryAdmin - ContextView");
		initView();
	}

	private void initView() {

		// Contexts
		contexts.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RA_CONTEXT_VIEW);

			}
		});// end ClickListener

		// ContextClass
		contextsClass.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RA_CONTEXTCLASS_VIEW);
				
			}
		});
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

		
		//TODO show contextclass
		
		
	}
}
