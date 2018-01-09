package g4dke.app;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import userDatabase.DBValidator;
import userDatabase.SystemUser;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;

/*
 * @author Viktoria J.
 * */
public class LoginView extends VerticalLayout implements View {

	private VerticalLayout layout = new VerticalLayout();
	private TextField username = new TextField();
	private PasswordField password = new PasswordField();
	Button login = new Button("Login");

	public LoginView() {
		setSizeFull();

		username.setCaption("Username");
		password.setCaption("Password");

		layout.addComponent(username);
		layout.addComponent(password);
		layout.addComponent(login);

		layout.setComponentAlignment(username, Alignment.TOP_CENTER);
		layout.setComponentAlignment(password, Alignment.TOP_CENTER);
		layout.setComponentAlignment(login, Alignment.TOP_CENTER);

		this.addComponent(layout);

		login.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				/*
				 * SystemUser user = DBValidator.getUser(username.getValue(),
				 * password.getValue());
				 * 
				 * if(user != null) { //get to next Window }else { Notification wrong = new
				 * Notification("WRONG", "Username or Password wrong!");
				 * wrong.show(Page.getCurrent()); }
				 */

			}// end buttonClick
		});// end login ClickListener

		// Navigate to RuleDeveloper_Rules
		Button ruleDevRules = new Button("RuleDeveloper Views", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RD_RULE_VIEW);
			}
		});

		addComponent(ruleDevRules);
		setComponentAlignment(ruleDevRules, Alignment.TOP_LEFT);

		// Navigate to RuleDeveloper_Rules
		Button d = new Button("Messaging", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.MS_INBOX);
			}
		});

		addComponent(d);
		setComponentAlignment(d, Alignment.TOP_LEFT);

		// Navigate to Repository Administrator
		Button ra = new Button("Repository Administrator", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RA_CONTEXT_VIEW);
			}
		});

	
		
		addComponent(ra);
		setComponentAlignment(ra, Alignment.TOP_LEFT);

		//navigate to RA: Parameter View
		Button raparameter = new Button("Repository Administrator Parameter", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RA_PARAMETER_VIEW);
			}
		});

		addComponent(raparameter);

		
		//navigate to RA: ParameterValue View
		Button raparametervalue = new Button("Repository Administrator Parameter Values", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RA_PARAMETERVALUE_VIEW);
			}
		});

		addComponent(raparametervalue);
		
		//Philip H.
		
		//navigate to Domain Expert BusinessCase
		Button debusinesscase = new Button("Domain Expert BusinessCase", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.DE_BUSINESSCASE_VIEW);
			}
		});

		addComponent(debusinesscase);
		
		//Philip H.
		
		//navigate to Domain Expert BusinessCaseClass
		Button debccview = new Button("Domain Expert BusinessCaseClass View", new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.DE_BUSINESSCASECLASS_VIEW);
			}
		});

		addComponent(debccview);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome");
	}

}
