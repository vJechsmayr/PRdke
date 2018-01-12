package ruleDeveloper;

import java.io.IOException;
import java.util.List;

import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid.SelectionMode;

import dke.pr.cli.CBRInterface;
import g4.templates.RuleDeveloperDesign;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;

public class RuleDev_ParameterValueView  extends RuleDeveloperDesign implements View{
	private static final long serialVersionUID = 1L;
	CBRInterface fl;
	VerticalLayout layout;
	ComboBox<String> select;
	Tree<String> tree;
	TreeData<String> data;
	Button delParamValue;
	boolean treeLoadedFirst;
	boolean addComponentsLoadedFirst;
	TextField parentValue;
	TextField paramValueName;
	Button addValue;

	public RuleDev_ParameterValueView() throws Exception{
			
			viewTitle.setValue("Rule Developer - Rule View");
			treeLoadedFirst = false;
			addComponentsLoadedFirst = false;
			initView();
		
		}
	
	private void initView() {
		initButtonsFromDesign();
		
		Button loadParameterValues = new Button("Load Data");
		loadParameterValues.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				loadParameterValues();
			}
		});
		contentPanel.setContent(loadParameterValues);
		
	}
	
	private void initButtonsFromDesign() {
		rules.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RD_RULE_VIEW);
				
			}
		});//end rules ClickListener
		
		contexts.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RD_CONTEXT_VIEW);
				
			}
		});//end contexts ClickListener
		
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
	
	/*
	 * @author Marcel G.
	 * 
	 * */
	private CBRInterface initInterface() {
		try {
			fl = new CBRInterface(SystemHelper.PFAD + "/ctxModelAIM.flr", SystemHelper.PFAD + "/bc.flr", "AIMCtx",
					"SemNOTAMCase");
			fl.setDebug(false);
			return fl;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	/*
	 * @author Marcel G.
	 * 
	 * */
	private void initTree()
	{
		try {
			if(treeLoadedFirst)
			{
				layout.removeComponent(tree);
				layout.removeComponent(delParamValue);
			}
			else
				treeLoadedFirst = true;
			
			List<String[]> values = fl
					.getParameterValuesHiearchy(select.getSelectedItem().get().toString());
			tree = new Tree<>();
			data = new TreeData<>();
			for (String[] array : values) {

				if (!data.contains(array[0])) {
					data.addItem(null, array[0]);
				}
				if (data.contains(array[1])) {
					// copy tree if parent gets parent
					TreeData<String> help = new TreeData<>();
					help.addItem(null, array[0]);
					help.addItem(array[0], array[1]);
					List<String> listHelp = data.getChildren(array[1]);
					help.addItems(array[1], listHelp);
					data.clear();
					data = help;
				} else
					data.addItem(array[0], array[1]);
			}
			tree.setDataProvider(new TreeDataProvider<>(data));
			tree.setSelectionMode(SelectionMode.SINGLE);
			layout.addComponent(tree);

			delParamValue = new Button("delete selected paramValue");
			delParamValue.addClickListener(new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {

					for (String paramValue : tree.getSelectedItems()) {
						try {
							if(!fl.delParameterValue(paramValue))
							{
								Notification.show("An error occoured");
							}
							else
							{
								fl.close();
								fl = initInterface();
								initTree();
								initAddComponents();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				}
			});
			
			layout.addComponent(delParamValue);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * @author Marcel G.
	 * 
	 * */
	private void initAddComponents()
	{
		if(addComponentsLoadedFirst)
		{
			layout.removeComponent(parentValue);
			layout.removeComponent(paramValueName);
			layout.removeComponent(addValue);
		}
		else
			addComponentsLoadedFirst = true;
		parentValue  = new TextField();
		parentValue.setCaption("selected value");
		tree.addSelectionListener(new SelectionListener<String>() {
			
			@Override
			public void selectionChange(SelectionEvent<String> event) {
				if(tree.getSelectedItems()!=null && tree.getSelectedItems().size()!=0)
				parentValue.setValue(tree.getSelectedItems().toArray()[0].toString());
				
			}
		});
		parentValue.setReadOnly(true);
		layout.addComponent(parentValue);
		
		paramValueName = new TextField();
		paramValueName.setCaption("Enter here new param value");
		layout.addComponent(paramValueName);
		
		addValue = new Button("Add");
		addValue.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(paramValueName.getValue()==null || paramValueName.getValue().equals(""))
				{
					Notification.show("Please enter a text!");
				}
				else if(parentValue.getValue()==null || parentValue.getValue().equals(""))
					Notification.show("Please select a parameter value");
				else
				{
					try {
						
						String[] parents = new String[1];
						parents[0] = parentValue.getValue();
						
						fl.addParameterValue(select.getSelectedItem().get().toString(), paramValueName.getValue(), parents, null);
						fl.close();
						fl = initInterface();
						initTree();
						initAddComponents();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		layout.addComponent(addValue);
	}

	/*
	 * @author Marcel G.
	 * 
	 * */
	private void loadParameterValues() {

		CBRInterface fl;
		try {
			layout = new VerticalLayout();
			fl = initInterface();
			List<String> parameters = fl.getParameters();
			select = new ComboBox<>("Select a parameter");
			select.setItems(parameters);
			select.addSelectionListener(new SingleSelectionListener<String>() {

				@Override
				public void selectionChange(SingleSelectionEvent<String> event) {
					initTree();
					initAddComponents();
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
