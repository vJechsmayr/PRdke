package repositoryAdmin;

import java.io.IOException;
import java.util.List;

import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

import dke.pr.cli.CBRInterface;
import g4.templates.RepositoryAdminDesign;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;

/*
 * @author Marcel G.
 * 
 * */
public class RepositoryAdmin_ParameterValueView extends RepositoryAdminDesign implements View {
	
	
	public RepositoryAdmin_ParameterValueView()
	{
		viewTitle.setValue("Repository Administrator - Parameter Value View");
		initView();
	}

	private void initView()
	{
		//TODO:
		//protected Button contextsClass;
		
		parameterValue.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RA_PARAMETERVALUE_VIEW);
				
			}
		});//end  ClickListener
		
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
		
		Button loadParameterValues = new Button();
		loadParameterValues.addClickListener( new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				loadParameterValues();
			}
		});
		contentPanel.setContent(loadParameterValues);
	}
	
	private void loadParameterValues()
	{
		
		CBRInterface fl;
		try {
			VerticalLayout layout = new VerticalLayout();
			fl = new CBRInterface(
					SystemHelper.PFAD + "/ctxModelAIM.flr",
					SystemHelper.PFAD + "/bc.flr", "AIMCtx",
					"SemNOTAMCase");
			fl.setDebug(false);
			List<String> parameters = fl.getParameters();


			ComboBox<String> select = new ComboBox<>("Select a parameter");
			select.setItems(parameters);
			
			select.addSelectionListener(new SingleSelectionListener<String>() {
				
				@Override
				public void selectionChange(SingleSelectionEvent<String> event) {
					
					try {
						layout.removeAllComponents();
						List<String[]> values = fl.getParameterValuesHiearchy(select.getSelectedItem().get().toString());
						Tree<String> tree = new Tree<>();
						TreeData<String> data = new TreeData<>();
						for(String[] array : values)
						{
							
							if(!data.contains(array[0]))
							{
								data.addItem(null, array[0]);
							}
							if(data.contains(array[1]))
							{
								//copy tree if parent gets parent 
								TreeData<String> help = new TreeData<>();
								help.addItem(null, array[0]);
								help.addItem(array[0], array[1]);
								List<String> listHelp = data.getChildren(array[1]);
								help.addItems(array[1], listHelp);
								data.clear();
								data = help;
							}
							else
								data.addItem(array[0], array[1]);
						}
						tree.setDataProvider(new TreeDataProvider<>(data));
						layout.addComponent(select);
						layout.addComponent(tree);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			
			layout.addComponent(select);
			contentPanel.setContent(layout);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
