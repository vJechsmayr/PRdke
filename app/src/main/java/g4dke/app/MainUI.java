package g4dke.app;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import messagingService.Messaging_InboxView;
import messagingService.Messaging_OutboxView;
import repositoryAdmin.RepositoryAdmin_ContextClassView;
import repositoryAdmin.RepositoryAdmin_ContextView;
import repositoryAdmin.RepositoryAdmin_ParameterValueView;
import repositoryAdmin.RepositoryAdmin_ParameterView;
import ruleDeveloper.RuleDev_ContextView;
import ruleDeveloper.RuleDev_ParameterValueView;
import ruleDeveloper.RuleDev_ParameterView;
import ruleDeveloper.RuleDev_RuleView;

/*
 * @author Viktoria J.
 * MainUI: Viktoria
 * Rule Developer: Viktoria
 * MessagingService: Marcel
 * RepositoryAdministrator: Thomas, Marcel
 * DomainExpert: Philip
 * User: ???
 * */
@Theme("mytheme")
public class MainUI extends UI {
	private static final long serialVersionUID = 1L;

	private Navigator nav;

	// Navigation Strings
	public static final String LOGIN_VIEW = "Login";

	// Navigation Strings RuleDeveloper
	public static final String RD_RULE_VIEW = "RuleDeveloper-Rules";
	public static final String RD_CONTEXT_VIEW = "RuleDeveloper-Context";
	public static final String RD_PARAMETER_VIEW = "RuleDeveloper-Parameter";
	public static final String RD_PARAMETERVALUE_VIEW = "RuleDeveloper-ParameterValue";

	// Navigation Strings RepositoryAdministrator
	public static final String RA_RULE_VIEW = "RepositoryAdministrator-Rules";
	public static final String RA_CONTEXT_VIEW = "RepositoryAdministrator-Context";
	public static final String RA_PARAMETER_VIEW = "RepositoryAdministrator-Parameter";
	public static final String RA_PARAMETERVALUE_VIEW = "RepositoryAdministrator-ParameterValue";
	public static final String RA_CONTEXTCLASS_VIEW = "RepositoryAdministrator-ContextClass";	


	// Navigation Strings Messaging Service
	public static final String MS_INBOX = "Messaging-Inbox";
	public static final String MS_OUTBOX = "Messaging-Outbox";

	// Navigation Strings DomainExpert
	public static final String DE_CONTEXT_VIEW = "DomainExpert-Context";
	public static final String DE_CONTEXTCLASS_VIEW = "DomainExpert-ContextClass";
	public static final String DE_PARAMETER_VIEW = "DomainExpert-Parameter";
	public static final String DE_PARAMETERVALUE_VIEW = "DomainExpert-ParameterValue";
	public static final String DE_BUSINESSCASE_VIEW = "DomainExpert-BusinessCase";
	public static final String DE_BUSINESSCASECLASS_VIEW = "DomainExpert-BusinessCaseClass";
	
	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Navigation");

		nav = new Navigator(this, this);

		nav.addView("", new LoginView()); // defaultView
		nav.addView(LOGIN_VIEW, new LoginView());

		// Rule Developer
		try {
			nav.addView(RD_RULE_VIEW, new RuleDev_RuleView());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			nav.addView(RD_CONTEXT_VIEW, new RuleDev_ContextView());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			nav.addView(RD_PARAMETER_VIEW, new RuleDev_ParameterView());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			nav.addView(RD_PARAMETERVALUE_VIEW, new RuleDev_ParameterValueView());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		// ---> @author Marcel G.
		// Code Marcel
		nav.addView(MS_INBOX, new Messaging_InboxView());
		nav.addView(MS_OUTBOX, new Messaging_OutboxView());
		
		nav.addView(RA_PARAMETER_VIEW, new RepositoryAdmin_ParameterView());
		nav.addView(RA_PARAMETERVALUE_VIEW, new RepositoryAdmin_ParameterValueView());
		// <---

		
		// ---> @author Thomas L.
		// Code Thomas
		try {
			nav.addView(RA_CONTEXT_VIEW, new RepositoryAdmin_ContextView());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			nav.addView(RA_CONTEXTCLASS_VIEW, new RepositoryAdmin_ContextClassView());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//nav.addView(RA_CONTEXTCLASS_VIEW, new RepositoryAdmin_ContextClassView());
		// <---

		// ---> @author Philip H.
		// Code Philip
		
		
		// <---
	}

	// TODO: Check what this does
	@WebServlet(urlPatterns = "/*", name = "LoginServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
	public static class LoginServlet extends VaadinServlet {
	}

}
