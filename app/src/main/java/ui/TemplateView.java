package ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import g4dke.app.MainUI;

/*
 * @author Viktoria J.
 * */
public class TemplateView extends Panel implements View{
	private static final long serialVersionUID = 1L;

	public static final String NAME = "thisView";
	
	private Panel menuPanel = new Panel("Menü");
	private Button dashboardBtn = new Button("Dashboard");
	private Button logoutBtn = new Button("Logout");
	
	private Button contextBtn = new Button("Context");
	private Button contextClassBtn = new Button("Context Class");
	private Button parameterBtn = new Button("Parameter");
	private Button parameterValueBtn = new Button("Parameter Value");
	private Button businessCaseClassBtn = new Button("Business Case Class");
	private Button businessCaseBtn = new Button("Business Case");
	private Button ruleBtn = new Button("Rule");
	private Button messageBtn = new Button("Messaging Service");
	private Button interestSpecBtn = new Button("Interest Spec.");
	
	private Label viewName = new Label("View Name");
	private Label contentLabel = new Label("Content here");
	
	private VerticalLayout menuLayout = new VerticalLayout();
	private HorizontalLayout content = new HorizontalLayout();
	private VerticalLayout leftCol = new VerticalLayout();
	private VerticalLayout rightCol = new VerticalLayout();
	private HorizontalLayout topLayout = new HorizontalLayout();

	
	@Override 
	public void enter(ViewChangeListener.ViewChangeEvent event) {

		menuLayout.addComponent(dashboardBtn);
		menuLayout.addComponent(contextBtn);
		menuLayout.addComponent(contextClassBtn);
		menuLayout.addComponent(parameterBtn);
		menuLayout.addComponent(parameterValueBtn);
		menuLayout.addComponent(businessCaseClassBtn);
		menuLayout.addComponent(businessCaseBtn);
		menuLayout.addComponent(ruleBtn);
		menuLayout.addComponent(interestSpecBtn);
		menuLayout.addComponent(messageBtn);
		menuLayout.addComponent(logoutBtn);
		
		//ToDo addComponent
		
		menuLayout.setSpacing(true);
		menuLayout.setMargin(true);
		menuLayout.setComponentAlignment(logoutBtn, Alignment.BOTTOM_LEFT);
		
		menuPanel.setContent(menuLayout);
		menuPanel.setWidth("250px");
		
		//topLayout
		topLayout.addComponent(viewName);
		topLayout.setSpacing(true);
		topLayout.setMargin(true);
		
		//leftCol
		leftCol.addComponent(menuPanel);
		leftCol.setSpacing(true);
		leftCol.setMargin(true);
		
		//rightCol
		rightCol.addComponent(contentLabel);
		rightCol.setSpacing(true);
		rightCol.setMargin(true);
		
		//content
		content.addComponent(leftCol);
		content.addComponent(rightCol);
		content.addComponent(topLayout);
		
		leftCol.setWidth("400px");
		
		content.setComponentAlignment(leftCol, Alignment.MIDDLE_LEFT);
		content.setComponentAlignment(topLayout, Alignment.TOP_CENTER);
		content.setComponentAlignment(rightCol, Alignment.MIDDLE_LEFT);
		
		
		setContent(content);
		
		contextBtn.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				//ToDO: Go To ContextView
				Notification.show("Context Button clicked");
				getUI().getNavigator().navigateTo(MainUI.CONTEXT_VIEW);
			}
		}); //end contextBtn ClickListener
		
		
		
	}
}
