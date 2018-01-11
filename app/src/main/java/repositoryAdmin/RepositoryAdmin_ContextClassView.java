package repositoryAdmin;

import java.io.IOException;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Button.ClickEvent;

import dke.pr.cli.CBRInterface;
import g4.templates.RepositoryAdminDesign;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;

/*
 * @author Thomas L.
 * 
 * 
 * */
public class RepositoryAdmin_ContextClassView extends RepositoryAdminDesign implements View {
	private static final long serialVersionUID = 1L;


	Button showCtx = new Button("show Context");
	TextArea contextArea = new TextArea();

	public RepositoryAdmin_ContextClassView() throws Exception {

		viewTitle.setValue("RepositoryAdmin - ContextClassView");
		initView();
	}

	private void initView() {
		
		initButtonsFromDesign();
		
		/*
		 * author: Marcel G.
		 * */
		Button loadContextClass = new Button("Load Context Data");
		loadContextClass.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				loadContextClass();
			}
		});
		contentPanel.setContent(loadContextClass);		
	}
	
	/*
	 * author Marcel G.
	 * loadContextClass()
	 * */
	private void loadContextClass()
	{
		try {
			CBRInterface fl = new CBRInterface(SystemHelper.PFAD + "/ctxModelAIM.flr", SystemHelper.PFAD + "/bc.flr", "AIMCtx",
					"SemNOTAMCase");
			//TODO: is there any getter for that?
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * initButtonsFromDesign()
	 * author: Viktoria
	 */
	private void initButtonsFromDesign() {
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
	}
}
