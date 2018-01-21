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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import g4dke.app.MainUI;

/*
 * @author Philip H.
 * 
 * */

public class DomainExpert_BusinessCaseView extends DomainExpertViews implements View {

	private static final long serialVersionUID = 1L;
	
	VerticalLayout viewLayout;
	HorizontalLayout businessCaseLayout;
	HorizontalLayout interestSpecLayout;
	TextField businessCaseName;
	Button addBusinessCase;
	
	
	Button addSpec;
	TextField specName;

	public DomainExpert_BusinessCaseView() throws Exception{
			super(MainUI.DE_BUSINESSCASE_VIEW);
			super.setTitle("Domain Expert - Business Case View");
			super.initInterface();

			
			initView();
			
		}
	
	private void initView() {
		viewLayout = new VerticalLayout();
		businessCaseLayout = new HorizontalLayout();
		interestSpecLayout = new HorizontalLayout();
		businessCaseName = new TextField();
		addBusinessCase = new Button("add BusinessCase");
		
		businessCaseName.setPlaceholder("Enter BusinessCaseName here");
		
		businessCaseLayout.addComponent(businessCaseName);
		businessCaseLayout.addComponent(addBusinessCase);
		
		viewLayout.addComponent(businessCaseLayout);
		viewLayout.addComponent(interestSpecLayout);
		
		initISpecAccordion();
		
		super.setContent(viewLayout);
		
		
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
	
	

	
	
	
	class InterestSpecForGrid {
		String interestSpec;
		String[] interestInfos;

		public InterestSpecForGrid(String interestSpec, String[] interestInfos) {
			this.interestSpec = interestSpec;
			this.interestInfos = interestInfos;
			
		}
		
		public String getInterestSpec() {
			return this.interestSpec;
		}
		
		public String[] getInterestInfos() {
			return this.interestInfos;
		}
		
		public void setInterestSpec(String interestSpec) {
			this.interestSpec = interestSpec;
		}
		
		public void setInterestInfos(String[] interestInfos) {
			this.interestInfos = interestInfos;
		}
		
		
	}
	
	

}
