package g4dke.app;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import userDatabase.DBValidator;
import userDatabase.SystemUser;

/*
 * @author Viktoria J.
 * */
public class LoginView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
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
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				SystemUser user = DBValidator.getUser(username.getValue(), password.getValue());

				// TODO: check user navigate to
				if (user != null) {
					SystemHelper.setCurrentUser(user);
					switch (user.getRole()) {
					case SystemHelper.REPOSITORY_ADMINISTRATOR:
						getUI().getNavigator().navigateTo(MainUI.RA_CONTEXT_VIEW);
						break;
					case SystemHelper.RULE_DEVELOPER:
						getUI().getNavigator().navigateTo(MainUI.RD_CONTEXT_VIEW);
						break;
					case SystemHelper.DOMAIN_EXPERT:
						getUI().getNavigator().navigateTo(MainUI.DE_CONTEXT_VIEW);
						break;
					case SystemHelper.USER:
						// getUI().getNavigator().navigateTo(MainUI.u);
						break;
					}
				} else {
					Notification wrong = new Notification("WRONG", "Username or Password wrong!");
					wrong.show(Page.getCurrent());
				}

			}// end buttonClick
		});// end login ClickListener

		// Navigate to RuleDeveloper_Rules
		Button ruleDevRules = new Button("RuleDeveloper", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RD_RULE_VIEW);
			}
		});

		addComponent(ruleDevRules);

		Button userInterestSpec = new Button("User", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.USER_INTERESTSPEC);
			}
		});

		addComponent(userInterestSpec);

		// Navigate to Repository Administrator
		Button ra = new Button("Repository Administrator", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RA_CONTEXT_VIEW);
			}
		});

		addComponent(ra);

		// navigate to Domain Expert BusinessCase
		Button debusinesscase = new Button("Domain Expert", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.DE_BUSINESSCASE_VIEW);
			}
		});

		addComponent(debusinesscase);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome");

	}

}
