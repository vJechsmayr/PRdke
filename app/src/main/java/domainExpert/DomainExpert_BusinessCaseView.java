package domainExpert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.navigator.View;
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

import g4dke.app.MainUI;

/*
 * @author Philip H., Viktoria J.
 * 
 * */

public class DomainExpert_BusinessCaseView extends DomainExpertViews implements View {

	private static final long serialVersionUID = 1L;
	
	VerticalLayout viewLayout;
	//HorizontalLayout businessCaseLayout;
	VerticalLayout interestSpecLayout;
	//TextField businessCaseName;
	//Button addBusinessCase;
	HorizontalLayout iSpecDetailLayout;
	
	Button addSpec;
	TextField specName;
	TextField interestSpec;
	
	TextField specDetail1;
	TextField specDetail2;
	Button addSpecDetail;
	
	Button delISpec;
	Button addInterestSpec;

	public DomainExpert_BusinessCaseView() throws Exception{
			super(MainUI.DE_BUSINESSCASE_VIEW);
			super.setTitle("Domain Expert - Business Case View");
			super.initInterface();

			
			initView();
			
		}
	
	private void initView() {
		viewLayout = new VerticalLayout();
		//businessCaseLayout = new HorizontalLayout();
		interestSpecLayout = new VerticalLayout();
		//businessCaseName = new TextField();
		//addBusinessCase = new Button("add BusinessCase");
		

		//initBusinessCaseLayout();
		initInterestSpecLayout();
		
		//Adding ISpec Details
		iSpecDetailLayout = new HorizontalLayout();
		initISpecDetailLayout();
		
		//viewLayout.addComponent(businessCaseLayout);
		viewLayout.addComponent(iSpecDetailLayout);
		viewLayout.addComponent(interestSpecLayout);
		
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
			neoISpec.append(interestSpec.getValue() + ":InterestSpec[");

			for(String[] s : fl.getISpecInfo(interestSpec.getValue())) {
				//System.out.println(s[0]);
				//System.out.println(s[1]);
				neoISpec.append(s[0] + "->" + s[1] + ",");
			}
			
			neoISpec.append(specDetail1.getValue() + "->" + specDetail2.getValue());
			neoISpec.append("].");
			String nIS = neoISpec.toString();
			System.out.println(nIS);
			fl.delInterestSpec(interestSpec.getValue());
			System.out.println("out with the old");
			fl.addInterestSpec(nIS);
			System.out.println("in with the new");
			
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
				if(interestSpec.getValue() == null || interestSpec.getValue() == "") {
					Notification.show("Please enter an interstSpec to add detail to");
				} else if(specDetail1.getValue() == null || specDetail1.getValue() == "" || 
						specDetail2.getValue() == null || specDetail2.getValue() == "") {
					Notification.show("Please enter details to add");
				} else {
					try {
						boolean exists = false;
						List<String> iSpecList = new ArrayList<String>();
						iSpecList = fl.getISpecs();
						for(String i : iSpecList) {

							if(i.equals(interestSpec.getValue())) {
								exists = true;
							}
						}
						if(exists) {
							addISpecDetail();
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
				}
			}
			
		});
		
		delISpec = new Button("delete InterestSpec");
		delISpec.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(interestSpec.getValue() == null || interestSpec.getValue() == "") {
					Notification.show("Please enter an interstSpec name");
				} else {
					try {
						boolean exists = false;
						List<String> iSpecList = new ArrayList<String>();
						iSpecList = fl.getISpecs();
						for(String i : iSpecList) {

							if(i.equals(interestSpec.getValue())) {
								exists = true;
							}
						}
						if(exists) {
							fl.delInterestSpec(interestSpec.getValue());
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
		
		interestSpecLayout.addComponent(delISpec);
		interestSpecLayout.addComponent(addInterestSpec);
		interestSpecLayout.addComponent(interestSpec);
		
		initISpecAccordion();
		
	}
	
	//Aktualisierung funktioniert noch nicht, muss noch manuel gemacht werden
	private void addISpec() {
		String sName = interestSpec.getValue().toString();
		System.out.println(sName);
		String[] arry = new String[0];
		
		try {
			fl.addInterestSpec(sName + ":InterestSpec" + Arrays.toString(arry) + ".");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		viewLayout.removeComponent(interestSpecLayout);
		initView();
		
	}
	
	private void initISpecAccordion() {
		Accordion iSpecs = new Accordion();
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
		
		interestSpecLayout.addComponent(iSpecs);
	}
	
	

}
