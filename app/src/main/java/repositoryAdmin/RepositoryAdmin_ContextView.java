package repositoryAdmin;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Button.ClickEvent;

import dke.pr.cli.CBRInterface;
import g4.templates.RepositoryAdminDesign;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;

/*
 * @author Thomas
 * 
 * */
public class RepositoryAdmin_ContextView extends RepositoryAdminDesign implements View {

	private static final long serialVersionUID = 1L;

	// PFAD auf das Verzeichnis der Flora installation ändern
	final static String PFAD = SystemHelper.PFAD;

	Button showCtx = new Button("show Context");
	TextArea contextArea = new TextArea();

	public RepositoryAdmin_ContextView() {

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
		//TODO
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
		// TODO
		// end ClickListener

		// ParameterVal
		// TODO
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

	private void initContextView() throws Exception {
		showCtx.addClickListener(new Button.ClickListener() {

			/**
			* 
			*/
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					showContexts();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		contentPanel.setContent(showCtx);

	}

	private void showContexts() throws Exception {

		CBRInterface fl = new CBRInterface(PFAD + "/ctxModelAIM.flr", PFAD + "/bc.flr", "AIMCtx", "SemNOTAMCase");

		fl.setDebug(false);

		System.out.println("Contexts: " + fl.getCtxs());

		String value = new String();

		for (String x : fl.getCtxs()) {
			value += x + "\t";
		}

		contextArea.setValue(value);
		contextArea.setRows(25);

		contentPanel.setContent(contextArea);

		fl.close();
	}

}
