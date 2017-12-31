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
import ruleDeveloper.RuleDev_ContextView;
import ruleDeveloper.RuleDev_ParameterView;
import ruleDeveloper.RuleDev_RuleView;
import ui.TemplateView;

/*
 * @author Viktoria J.
 * MainDev: Viktoria
 * MessagingService: Marcel
 * 
 * */
@Theme("mytheme")
public class MainUI extends UI{
	Navigator nav;
	public static final String TEST_VIEW = "TestView"; //doNOTuse
	public static final String TEMPLATE_VIEW = "TemplateView"; //doNOTuse
	public static final String CONTEXT_VIEW = "ContextView"; //doNOTuse
	
	//Navigation Strings
	public static final String LOGIN_VIEW = "Login";
	
	//Navigation Strings RuleDeveloper
	public static final String RD_RULE_VIEW = "Rules";
	public static final String RD_CONTEXT_VIEW = "Context";
	public static final String RD_PARAMETER_VIEW = "Parameter";
	
	//Navigation Strings Messaging Service
	public static final String MS_INBOX = "Inbox";
	public static final String MS_OUTBOX = "Outbox";
	
	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Navigation");
		
		nav = new Navigator(this, this);
		
		nav.addView("", new LoginView()); //defaultView
		nav.addView(LOGIN_VIEW, new LoginView());
		
		//Rule Developer
		nav.addView(RD_RULE_VIEW, new RuleDev_RuleView());
		try {
			nav.addView(RD_CONTEXT_VIEW, new RuleDev_ContextView());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nav.addView(RD_PARAMETER_VIEW, new RuleDev_ParameterView());
		
		
		
		//---> @author Marcel G. 
		nav.addView(MS_INBOX, new Messaging_InboxView());
		nav.addView(MS_OUTBOX, new Messaging_OutboxView());
		//<---
		
		
		//--->
			//Code Thomas here
		//<---
		
		
		//--->
			//Code Philip here
		//<---
		
		
		
		
		
		/*
		 * nav.addView(TEST_VIEW, new TestView());
		 * nav.addView(TEMPLATE_VIEW, new TemplateView());
		 * nav.addView(CONTEXT_VIEW, new ContextView());
		 * 
		 * */
		
	}
	
	//TODO: Check what this does
	  @WebServlet(urlPatterns = "/*", name = "LoginServlet", asyncSupported = true)
	    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
	    public static class LoginServlet extends VaadinServlet {
	    }

}
