package repositoryAdmin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Tree;
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

	// Flora-2 Installationspfad
	final static String PFAD = SystemHelper.PFAD;

	Button showCtx = new Button("show Context");
	TextArea contextArea = new TextArea();

	public RepositoryAdmin_ContextView() throws Exception {

		viewTitle.setValue("RepositoryAdmin - ContextView");
		initView();
		initContextView();
	}

	private void initView() {
		
		initButtonsFromDesign();


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

	/*
	 * @author Viktoria J.
	 * 
	 * */
	private void initContextView() throws Exception {
		showCtx.addClickListener(new Button.ClickListener() {

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

	/*
	 * @author Viktoria J.
	 * 
	 * */
	private void drawTreeH(List<String> contexts, List<String[]> ctxList) {

		Tree<String> tree = new Tree<>("Contexts");
		TreeData<String> data = new TreeData<>();
		List<String> roots = new ArrayList<String>();

		// Insert all Roots
		for (String s : contexts) {
			for (String[] sH : ctxList) {
				int i = Arrays.toString(sH).indexOf(", ");
				String dataParent = Arrays.toString(sH).substring(i + 2, Arrays.toString(sH).length() - 1);
				String dataChild = Arrays.toString(sH).substring(1, i);

				if (s.equals(dataParent)) {
					// nix
				} else {
					if (roots.isEmpty()) {
						roots.add(dataParent);
					} else if (roots.contains(dataParent)) {
						// nix
					} else {
						roots.add(dataParent);
					}
				}
			}
		}

		data.addItems(null, roots);

		// Insert all Childs
		for (String[] sH : ctxList) {
			int i = Arrays.toString(sH).indexOf(", ");
			String dataParent = Arrays.toString(sH).substring(i + 2, Arrays.toString(sH).length() - 1);
			String dataChild = Arrays.toString(sH).substring(1, i);

			if (dataParent
					.equals(data.getRootItems().toString().substring(1, data.getRootItems().toString().length() - 1))) {
				data.addItem(dataParent, dataChild);
			}

		}

		tree.setDataProvider(new TreeDataProvider<>(data));
		tree.expand(data.getRootItems());
		contentPanel.setContent(tree);

	}

	/*
	 * @author Viktoria J.
	 * 
	 * */
	 private void showContexts() throws Exception {
	
		 CBRInterface fl = new CBRInterface(
					PFAD + "/ctxModelAIM.flr",
					PFAD + "/bc.flr", "AIMCtx",
					"SemNOTAMCase");

			fl.setDebug(false);

			drawTreeH(fl.getCtxs(),fl.getCtxHierarchy());
			
			fl.close();	
	 }

}
