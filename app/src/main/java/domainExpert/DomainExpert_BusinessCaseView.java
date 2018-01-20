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

public class DomainExpert_BusinessCaseView extends DomainExpertViews implements View {

	private static final long serialVersionUID = 1L;
	
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
			super(MainUI.DE_BUSINESSCASE_VIEW);
			super.setTitle("Domain Expert - Business Case View");
			super.initInterface();
			
			treeLoadedFirst = false;
			addComponentsLoadedFirst = false;
			initView();
		
		}
	
	private void initView() {
		
		
		Button loadBusinessCases = new Button("Load Data");
		loadBusinessCases.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				loadParameterValues();
			}
		});
		//contentPanel.setContent(loadBusinessCases);
		super.setContent(loadBusinessCases);
	}
	
	
	/*
	 * @author Marcel G.
	 * @author Philip H.
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
			{
				treeLoadedFirst = true;
			}
			List<String[]> values = fl
					.getParameterValuesHiearchy(select.getSelectedItem().get().toString());
			tree = new Tree<>();
			data = new TreeData<>();
			String allCases = select.getSelectedItem().get().toString() + " Cases";
			if(values.isEmpty()) {
				data.addItem(null, allCases);
			}
			for (String[] array : values) {
				
				if (!data.contains(array[0])) {
					if(!data.contains(allCases)) {
						data.addItem(null, allCases);
					}
					data.addItem(allCases, array[0]);
				}
				if (data.contains(array[1])) {
					// copy tree if parent gets parent
					if(!data.contains(allCases)) {
						data.addItem(null, allCases);
					}
					TreeData<String> help = new TreeData<>();
					help.addItem(allCases, array[0]);
					help.addItem(array[0], array[1]);
					List<String> listHelp = data.getChildren(array[1]);
					help.addItems(array[1], listHelp);
					data.clear();
					data = help;
				} else {
					if(!data.contains(allCases)) {
						data.addItem(null, allCases);
					}
					data.addItem(array[0], array[1]);
				}			
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
								initInterface();
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
	 * @author Philip H.
	 * */
	//Entdeckung: Wenn die .flr Dateien im Ordner geändert werden, wird dies auf der Webpage erst angezeigt, wenn ein add durchgeführt wurde
	private void initAddComponents()
	{
		if(addComponentsLoadedFirst)
		{
			layout.removeComponent(parentCase);
			layout.removeComponent(CaseName);
			layout.removeComponent(addCase);
		}
		else
		{
			addComponentsLoadedFirst = true;
		}
		
		parentCase  = new TextField();
		parentCase.setCaption("selected Business Case");
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
		CaseName.setCaption("Enter here new Business Case");
		layout.addComponent(CaseName);
		
		addCase = new Button("Add");
		addCase.addClickListener(new Button.ClickListener() {
		
			@Override
			public void buttonClick(ClickEvent event) {
				try {

					if(CaseName.getValue()==null || CaseName.getValue().equals(""))
					{				
						Notification.show("Please enter a text!");
					}
					else if(parentCase.getValue()==null || parentCase.getValue().equals(""))
					{
						Notification.show("Please select a Business Case or a Business Case Class");
						/*
						 * Versuch, um ein einem leeren BusinessCaseClass einen BusinessCase hinzuzufügen
						if(select.getValue()==null || select.getValue().equals("")) {
							Notification.show("Please select a Business Case or a Business Case Class");
						} else {

							fl.addParameterValue(select.getSelectedItem().get().toString(), CaseName.getValue(), null, null);
							fl.close();
							fl = initInterface();
							initTree();
							initAddComponents();	
							
						}
						*/					
					}
					else
					{
						
						String[] parents = new String[1];
						parents[0] = parentCase.getValue();
						
						System.out.println(select.getValue().toString());
						System.out.println(parentCase.getValue().toString());
						System.out.println(parentCase.getParent().toString());
						
						fl.addParameterValue(select.getSelectedItem().get().toString(), CaseName.getValue(), parents, null);
						fl.close();
						initInterface();
						initTree();
						initAddComponents();
					
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
			initInterface();
			List<String> parameters = fl.getParameters();
			select = new ComboBox<>("Select a Business Case Class");
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
