package g4dke.app;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import ui.TemplateView;

/*
 * @author Viktoria J.
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
		
		nav.addView(RD_RULE_VIEW, new RuleDev_RuleView());
		nav.addView(RD_CONTEXT_VIEW, new RuleDev_ContextView());
		nav.addView(RD_PARAMETER_VIEW, new RuleDev_ParameterView());
		
		
		
		
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
