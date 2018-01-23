package domainExpert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import dke.pr.cli.CBRInterface;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;

import g4dke.app.MainUI;

/*
 * @author Philip H., Viktoria J.
 * 
 * */

public class DomainExpert_BusinessCaseView extends DomainExpertViews implements View {

	private static final long serialVersionUID = 1L;
	
	VerticalLayout viewLayout;
	//HorizontalLayout businessCaseLayout;
	//VerticalLayout interestSpecLayout;
	//TextField businessCaseName;
	//Button addBusinessCase;
	HorizontalLayout iSpecDetailLayout;
	HorizontalLayout iSpecAddLayout;
	HorizontalLayout iSpecDelLayout;
	//VerticalLayout iSpecDisplayLayout;
	
	Button addSpec;
	TextField specName;
	TextField interestSpec;
	
	TextField specDetail1;
	TextField specDetail2;
	Button addSpecDetail;
	
	Button delISpec;
	Button addInterestSpec;
	
	ComboBox<String> iSpecsDropBox;
	Accordion iSpecs;

	public DomainExpert_BusinessCaseView() throws Exception{
			super(MainUI.DE_BUSINESSCASE_VIEW);
			super.setTitle("Domain Expert - Business Case View");
			super.initInterface();

			
			initView();
			
		}
	
	private void initView() {
		viewLayout = new VerticalLayout();
		iSpecDetailLayout = new HorizontalLayout();
		iSpecAddLayout = new HorizontalLayout();
		iSpecDelLayout = new HorizontalLayout();
		//iSpecDisplayLayout = new VerticalLayout();
		
		//businessCaseLayout = new HorizontalLayout();
		//interestSpecLayout = new VerticalLayout();
		//businessCaseName = new TextField();
		//addBusinessCase = new Button("add BusinessCase");
		

		
		
		//Adding ISpec Details
		iSpecDetailLayout = new HorizontalLayout();
		
		
		//viewLayout.addComponent(businessCaseLayout);
		viewLayout.addComponent(iSpecAddLayout);
		viewLayout.addComponent(iSpecDelLayout);
		viewLayout.addComponent(iSpecDetailLayout);
		//viewLayout.addComponent(iSpecDisplayLayout);
		//viewLayout.addComponent(interestSpecLayout);
		
		//initBusinessCaseLayout();
		initInterestSpecLayout();
		
		initISpecDetailLayout();
		
		super.setContent(viewLayout);		
	}
	
	/*
	private void initBusinessCaseLayout() {
		businessCaseName.setPlaceholder("Enter BusinessCaseName here");
		
		businessCaseLayout.addComponent(businessCaseName);
		businessCaseLayout.addComponent(addBusinessCase);
	}
	*/
	
	//In testModOps wird einem der Strings mit : ein Detail hinzugefügt. Dies wurde noch nicht eingebaut
	//Methode funktioniert, allerdings muss das Aktuallisieren der Seite manuell erfolgen
	private void addISpecDetail() {
		try {
			StringBuilder neoISpec = new StringBuilder();
			
			neoISpec.append(iSpecsDropBox.getSelectedItem().get().toString() + ":InterestSpec[");

			for(String[] s : fl.getISpecInfo(interestSpec.getValue())) {
				//System.out.println(s[0]);
				//System.out.println(s[1]);
				neoISpec.append(s[0] + "->" + s[1] + ",");
			}
			
			neoISpec.append(specDetail1.getValue() + "->" + specDetail2.getValue());
			neoISpec.append("].");
			String nIS = neoISpec.toString();
			System.out.println(nIS);
			fl.delInterestSpec(iSpecsDropBox.getSelectedItem().get().toString());
			
			//System.out.println("out with the old");
			fl.addInterestSpec(nIS);
			//System.out.println("in with the new");
			
			Page.getCurrent().reload();

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initISpecDetailLayout() {
		specDetail1 = new TextField();
		specDetail1.setPlaceholder("enter InterestSpecDetail here");
		specDetail2 = new TextField();
		specDetail2.setPlaceholder("enter InterestSpecDetail here");
		
		addSpecDetail = new Button("add Detail");
		addSpecDetail.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(iSpecsDropBox.getSelectedItem().get() == null || iSpecsDropBox.getSelectedItem().isPresent() || iSpecsDropBox.getSelectedItem().get().toString() == "") {
					Notification.show("Please select an interstSpec to add detail to");
				} else if(specDetail1.getValue() == null || specDetail1.getValue() == "" || 
						specDetail2.getValue() == null || specDetail2.getValue() == "") {
					Notification.show("Please enter details to add");
				} else {
					try {
						boolean exists = false;
						List<String> iSpecList = new ArrayList<String>();
						iSpecList = fl.getISpecs();
						for(String i : iSpecList) {

							if(i.equals(iSpecsDropBox.getSelectedItem().get().toString())) {
								exists = true;
							}
						}
						if(exists) {
							addISpecDetail();
						} else {
							Notification.show("Please select an interstSpec to add detail to");
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		
		iSpecDetailLayout.addComponent(specDetail1);
		iSpecDetailLayout.addComponent(specDetail2);
		iSpecDetailLayout.addComponent(addSpecDetail);
	}
	
	//DelISpec funktioniert, aktuallisierung der Seite muss manuell vorgenommen werden
	private void initInterestSpecLayout() {
		interestSpec = new TextField();
		interestSpec.setPlaceholder("enter InterestSpec here");
		addInterestSpec = new Button("add InterestSpec");
		addInterestSpec.addClickListener(new Button.ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				if(interestSpec.getValue() == null || interestSpec.getValue() == "") {
					Notification.show("Please enter an interstSpec name");
				} else {
					addISpec();
					
					
					
					/*viewLayout.removeComponent(iSpecs);
					iSpecDelLayout.removeComponent(iSpecsDropBox);
					viewLayout.addComponent(iSpecs);
					iSpecDelLayout.addComponent(iSpecsDropBox);*/
				}
			}
			
		});
		
		delISpec = new Button("delete InterestSpec");
		delISpec.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(iSpecsDropBox.getSelectedItem().get() == null || iSpecsDropBox.getSelectedItem().isPresent() || iSpecsDropBox.getSelectedItem().get().toString() == "") {
					Notification.show("Please select an interstSpec");
				} else {
					try {
						boolean exists = false;
						List<String> iSpecList = new ArrayList<String>();
						iSpecList = fl.getISpecs();
						for(String i : iSpecList) {

							if(i.equals(iSpecsDropBox.getSelectedItem().get().toString())) {
								exists = true;
							}
						}
						if(exists) {
							fl.delInterestSpec(iSpecsDropBox.getSelectedItem().get().toString());
							Page.getCurrent().reload();
							/*viewLayout.removeComponent(iSpecs);
							iSpecDelLayout.removeComponent(iSpecsDropBox);
							viewLayout.addComponent(iSpecs);
							iSpecDelLayout.addComponent(iSpecsDropBox);*/
							
						} else {
							Notification.show("Please enter an interstSpec to add detail to");
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
        
		
        
        initISpecAccordion();
        iSpecDelLayout.addComponent(iSpecsDropBox);
		iSpecDelLayout.addComponent(delISpec);
		
		iSpecAddLayout.addComponent(interestSpec);
		iSpecAddLayout.addComponent(addInterestSpec);
		
		
		
	}
	
	//Aktualisierung funktioniert noch nicht, muss noch manuel gemacht werden
	private void addISpec() {
		String sName = interestSpec.getValue().toString();
		System.out.println(sName);
		String[] arry = new String[0];
		
		try {
			fl.addInterestSpec(sName + ":InterestSpec" + Arrays.toString(arry) + ".");
			Page.getCurrent().reload();
			/*
			viewLayout.removeComponent(iSpecs);
			iSpecDelLayout.removeComponent(iSpecsDropBox);
			viewLayout.addComponent(iSpecs);
			iSpecDelLayout.addComponent(iSpecsDropBox);
*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//viewLayout.removeComponent(interestSpecLayout);
		//initView();
		
	}
	
	private void initISpecAccordion() {
		iSpecs = new Accordion();
		iSpecs.setHeight("500px");
		
		List<String> iSpecList = new ArrayList<String>();
		try {
			iSpecList = fl.getISpecs();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
		for(String i : iSpecList) {
			String infos = "";
			try {
				for(String[] s : fl.getISpecInfo(i)) {
					
					infos = infos + Arrays.toString(s) + "<br />";
					
					/*for(int j=0;j<s.length;j++) {
						infos = infos + s[j] + "<br />";
					}	*/

				}
				final Label label = new Label(infos, ContentMode.HTML);
				label.setWidth("500px");
				
				final VerticalLayout layout = new VerticalLayout(label);
				layout.setMargin(true);

				iSpecs.addTab(layout, i);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		viewLayout.addComponent(iSpecs);
		
		// Creates a new combobox using an existing container
        //Collection<CountryData> countriesData = fl.getISpecs();
 
		iSpecsDropBox = new ComboBox<>(null, iSpecList);
 
		iSpecsDropBox.setPlaceholder("Select iSpec");
 
        // Sets the combobox to show a certain property as the item caption
        //iSpecs.setItemCaptionGenerator(CountryData::getFullName);
 
        // Sets the icon to use with the items
		//iSpecsDropBox.setItemIconGenerator(CountryData::getFlag);
 
        // Disallow null selections
		iSpecsDropBox.setEmptySelectionAllowed(false);
 
        // Set full width
		iSpecsDropBox.setWidth(100.0f, Unit.PERCENTAGE);
 
        // Check if the caption for new item already exists in the list of item
        // captions before approving it as a new item.
        /*ComboBox.NewItemHandler itemHandler = newItemCaption -> {
            boolean newItem = countriesData.stream().noneMatch(data -> data.getFullName().equalsIgnoreCase(newItemCaption));
            if (newItem) {
                // Adds new option
                CountryData newCountryData = new CountryData(newItemCaption, null);
                countriesData.add(newCountryData);
                iSpecsDropBox.setItems(countriesData);
                iSpecsDropBox.setSelectedItem(newCountryData);
            }
        };
        iSpecsDropBox.setNewItemHandler(itemHandler);*/

        /*iSpecsDropBox.addValueChangeListener(event -> Notification.show("Value changed:",
                String.valueOf(event.getValue()),
                Type.TRAY_NOTIFICATION));*/
	}
	
}
