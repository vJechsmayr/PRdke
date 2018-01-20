package userViews;

import java.io.IOException;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import g4dke.app.MainUI;

public class User_BusinessCaseView extends UserViews implements View{
	private static final long serialVersionUID = 1L;
	VerticalLayout businessCaseLayout = new VerticalLayout();

	public User_BusinessCaseView() {
		super(MainUI.USER_BUSINESSCASE);
		super.initInterface();
		super.setTitle("User - BusinessCase View");

		initRun();
		initNOTAMAccordion();
		
		super.setContent(businessCaseLayout);
	}
	
	private void initRun() {
		TextField interestSpec = new TextField();
		interestSpec.setPlaceholder("enter InterestSpec here");
		interestSpec.setWidth("300px");
		Button run = new Button("Run");
		run.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
			Notification.show("Running NOTAMs! :) "); 	
			}
		});
		
		HorizontalLayout runLayout = new HorizontalLayout();
		
		runLayout.addComponent(interestSpec);
		runLayout.addComponent(run);
		businessCaseLayout.addComponent(runLayout);
		
	}
	
	private void initNOTAMAccordion() {
		Accordion notams = new Accordion();
		notams.setHeight("500px");
		
		try {
			for(String f : fl.getNOTAMS()) {
				String infos = "";
				for(String[] n : fl.getNOTAMInfo(f)) {
					for(int i=0; i<n.length; i++)
					{
						infos = infos + n[i] + "<br />";
					}
				}
				final Label label = new Label(infos, ContentMode.HTML);
				label.setWidth("500px");
				
				final VerticalLayout layout = new VerticalLayout(label);
				layout.setMargin(true);

				notams.addTab(layout, f);
				
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	
	businessCaseLayout.addComponent(notams);	
	}
	
}
