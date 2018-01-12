package domainExpert;

import java.io.IOException;
import java.util.ArrayList;
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
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

import dke.pr.cli.CBRInterface;
import g4.templates.DomainExpertDesign;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;

/*
 * @author Philip H.
 * 
 * */

public class DomainExpert_BusinessCaseView extends DomainExpertDesign implements View {

	private static final long serialVersionUID = 1L;
	CBRInterface fl;
	VerticalLayout layout;
	ComboBox<String> select;
	Tree<String> tree;
	TreeData<String> data;
	Button delBusinessCase;
	boolean treeLoadedFirst;
	boolean addComponentsLoadedFirst;
	TextField parentCase;
	TextField CaseName;
	Button addCase;

	public DomainExpert_BusinessCaseView() throws Exception{
			
			viewTitle.setValue("Domain Expert - Business Case View");
			treeLoadedFirst = false;
			addComponentsLoadedFirst = false;
			initView();
		
		}
	
	private void initView() {
		initButtonsFromDesign();
		
		Button loadBusinessCases = new Button("Load Data");
		loadBusinessCases.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				loadParameterValues();
			}
		});
		contentPanel.setContent(loadBusinessCases);
		
	}
	
	/*
	 * initButtonsFromDesign()
	 * author: Viktoria
	 */
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
				layout.removeComponent(delBusinessCase);
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

			delBusinessCase = new Button("delete selected Business Case");
			delBusinessCase.addClickListener(new Button.ClickListener() {

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
			
			layout.addComponent(delBusinessCase);
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
			layout.removeComponent(parentCase);
			layout.removeComponent(CaseName);
			layout.removeComponent(addCase);
		}
		else
			addComponentsLoadedFirst = true;
		parentCase  = new TextField();
		parentCase.setCaption("selected value");
		tree.addSelectionListener(new SelectionListener<String>() {
			
			@Override
			public void selectionChange(SelectionEvent<String> event) {
				if(tree.getSelectedItems()!=null && tree.getSelectedItems().size()!=0)
				parentCase.setValue(tree.getSelectedItems().toArray()[0].toString());
				
			}
		});
		parentCase.setReadOnly(true);
		layout.addComponent(parentCase);
		
		CaseName = new TextField();
		CaseName.setCaption("Enter here new param value");
		layout.addComponent(CaseName);
		
		addCase = new Button("Add");
		addCase.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(CaseName.getValue()==null || CaseName.getValue().equals(""))
				{
					Notification.show("Please enter a text!");
				}
				else if(parentCase.getValue()==null || parentCase.getValue().equals(""))
					Notification.show("Please select a parameter value");
				else
				{
					try {
						
						String[] parents = new String[1];
						parents[0] = parentCase.getValue();
						
						fl.addParameterValue(select.getSelectedItem().get().toString(), CaseName.getValue(), parents, null);
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
		layout.addComponent(addCase);
	}

	/*
	 * @author Marcel G.
	 * 
	 * */
	private void loadParameterValues() {

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
