package domainExpert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
	ComboBox<String> iSelect;
	Tree<String> tree;
	Tree<String> sTree;
	TreeData<String> data;
	Button delBusinessCase;
	boolean treeLoadedFirst;
	boolean sTreeLoadedFirst;
	boolean addComponentsLoadedFirst;
	boolean addSpecLoadedFirst;
	TextField parentCase;
	TextField CaseName;
	Button addCase;
	Button addSpec;
	TextField specName;

	public DomainExpert_BusinessCaseView() throws Exception{
			super(MainUI.DE_BUSINESSCASE_VIEW);
			super.setTitle("Domain Expert - Business Case View");
			super.initInterface();
			
			treeLoadedFirst = false;
			addComponentsLoadedFirst = false;
			sTreeLoadedFirst = false;
			addSpecLoadedFirst = false;
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
			//if(!select.getSelectedItem().equals(null) && !select.getSelectedItem().equals("")) {
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
						if(!array[0].equals(allCases)) {
							data.addItem(allCases, array[0]);
						}
					}
					if (data.contains(array[1])) {
						// copy tree if parent gets parent					
						TreeData<String> help = new TreeData<>();
						List<String> listHelp = data.getChildren(array[1]);
						
						if(!array[0].equals(allCases) && !array[1].equals(allCases) && !listHelp.contains(allCases)) {
							help.addItem(null, allCases);
							help.addItem(allCases, array[0]);
						} else {
							help.addItem(null, array[0]);
						}
						
						help.addItem(array[0], array[1]);
						help.addItems(array[1], listHelp);
						data.clear();
						data = help;
										
					} else {
						/*
						if(!data.contains(allCases)) {
							data.addItem(null, allCases);
						}*/
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
			
			//}
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
				{
					parentCase.setValue(tree.getSelectedItems().toArray()[0].toString());
					/*if(parentCase.getValue().toString() != null) {
						try {
						System.out.println("value: " + parentCase.getValue().toString() );
						System.out.println("ISpecs: " + fl.getISpecs().toString());
						for (String[]s:fl.getInterestSpecClass()) {
							System.out.println("ISpecClass: " + Arrays.toString(s));
						}
						for (String[] str: fl.getISpecInfo("iSpec1")){
							System.out.println("iSpec1: " + Arrays.toString(str));
						}
						for (String[] str: fl.getISpecInfo("iSpec2")){
							System.out.println("iSpec2: " + Arrays.toString(str));
						}
						for (String[] str: fl.getISpecInfo("iSpec3")){
							System.out.println("iSpec3: " + Arrays.toString(str));
						}
						//System.out.println("ISpecsInfo: " + fl.getISpecInfo());
						}catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}*/
				}
			}
		});
		parentCase.setReadOnly(true);
		layout.addComponent(parentCase);
		
		CaseName = new TextField();
		CaseName.setCaption("Enter here new Business Case");
		layout.addComponent(CaseName);
		
		addCase = new Button("Add Case");
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
			//Busines Cases
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
			
			//Interest Specs
			List<String> iSpecs = fl.getISpecs();
			iSelect = new ComboBox<>("Select Interest Spect");
			iSelect.setItems(iSpecs);
			iSelect.addSelectionListener(new SingleSelectionListener<String>() {

				@Override
				public void selectionChange(SingleSelectionEvent<String> event) {
					initSTree();
					initAddSpec();
				}
			});
			
			//Layout finalisieren
			layout.addComponent(select);
			layout.addComponent(iSelect);
			contentPanel.setContent(layout);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void initSTree()
	{
		try {
			if(sTreeLoadedFirst)
			{
				layout.removeComponent(sTree);
				//layout.removeComponent(delBusinessCase);
			}
			else
			{
				sTreeLoadedFirst = true;
			}
			//if(!select.getSelectedItem().equals(null) && !select.getSelectedItem().equals("")) {
				List<String[]> values = fl
						.getISpecInfo(iSelect.getSelectedItem().get().toString());
				sTree = new Tree<>();
				data = new TreeData<>();
				String allspecs = iSelect.getSelectedItem().get().toString() + " InterestSpecs";
				if(values.isEmpty()) {
					data.addItem(null, allspecs);
				}
				for (String[] array : values) {
					
					if (!data.contains(array[0])) {
						if(!data.contains(allspecs)) {
							data.addItem(null, allspecs);
						}
						if(!array[0].equals(allspecs)) {
							data.addItem(allspecs, array[0]);
						}
					}
					if (data.contains(array[1])) {
						// copy tree if parent gets parent					
						TreeData<String> help = new TreeData<>();
						List<String> listHelp = data.getChildren(array[1]);
						
						if(!array[0].equals(allspecs) && !array[1].equals(allspecs) && !listHelp.contains(allspecs)) {
							help.addItem(null, allspecs);
							help.addItem(allspecs, array[0]);
						} else {
							help.addItem(null, array[0]);
						}
						
						help.addItem(array[0], array[1]);
						help.addItems(array[1], listHelp);
						data.clear();
						data = help;
										
					} else {
						/*
						if(!data.contains(allCases)) {
							data.addItem(null, allCases);
						}*/
						data.addItem(array[0], array[1]);
					}			
				}
				sTree.setDataProvider(new TreeDataProvider<>(data));
				sTree.setSelectionMode(SelectionMode.SINGLE);
				layout.addComponent(sTree);

				//delBusinessCase = new Button("delete selected Business Case");
				/*delBusinessCase.addClickListener(new Button.ClickListener() {

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
			*/
			//}
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	private void initAddSpec() {
		if(addSpecLoadedFirst)
		{
			//layout.removeComponent(parentCase);
			layout.removeComponent(specName);
			layout.removeComponent(addSpec);
		}
		else
		{
			addSpecLoadedFirst = true;
		}
		/*
		parentCase  = new TextField();
		parentCase.setCaption("selected Business Case");
		tree.addSelectionListener(new SelectionListener<String>() {
			
			@Override
			public void selectionChange(SelectionEvent<String> event) {
				if(tree.getSelectedItems()!=null && tree.getSelectedItems().size()!=0)
				{
					parentCase.setValue(tree.getSelectedItems().toArray()[0].toString());
					/*if(parentCase.getValue().toString() != null) {
						try {
						System.out.println("value: " + parentCase.getValue().toString() );
						System.out.println("ISpecs: " + fl.getISpecs().toString());
						for (String[]s:fl.getInterestSpecClass()) {
							System.out.println("ISpecClass: " + Arrays.toString(s));
						}
						for (String[] str: fl.getISpecInfo("iSpec1")){
							System.out.println("iSpec1: " + Arrays.toString(str));
						}
						for (String[] str: fl.getISpecInfo("iSpec2")){
							System.out.println("iSpec2: " + Arrays.toString(str));
						}
						for (String[] str: fl.getISpecInfo("iSpec3")){
							System.out.println("iSpec3: " + Arrays.toString(str));
						}
						//System.out.println("ISpecsInfo: " + fl.getISpecInfo());
						}catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});*/
		//parentCase.setReadOnly(true);
		//layout.addComponent(parentCase);
		
		specName = new TextField();
		specName.setCaption("Enter here new Interest Spec");
		layout.addComponent(specName);
		
		addSpec = new Button("Add spec");
		addSpec.addClickListener(new Button.ClickListener() {
		
			@Override
			public void buttonClick(ClickEvent event) {
				try {

					if(specName.getValue()==null || specName.getValue().equals(""))
					{				
						Notification.show("Please enter a text!");
					}
					/*else if(parentCase.getValue()==null || parentCase.getValue().equals(""))
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
					//}
					else
					{
						/*
						String[] parents = new String[1];
						parents[0] = parentCase.getValue();
						
						fl.addParameterValue(select.getSelectedItem().get().toString(), CaseName.getValue(), parents, null);
						fl.close();
						*/
						System.out.println("specName: " + specName.getValue().toString());
						//fl.addInterestSpec(specName.getValue().toString());
						fl.close();
						
						initInterface();
						initSTree();
						initAddSpec();
					
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		layout.addComponent(addSpec);
	}
}
