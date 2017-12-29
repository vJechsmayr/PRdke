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
	public static final String TEST_VIEW = "TestView";
	public static final String TEMPLATE_VIEW = "TemplateView";
	public static final String CONTEXT_VIEW = "ContextView";
	
	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Navigation");
		
		nav = new Navigator(this, this);
		
		nav.addView("", new LoginView()); //defaultView
		nav.addView(TEST_VIEW, new TestView());
		nav.addView(TEMPLATE_VIEW, new TemplateView());
		nav.addView(CONTEXT_VIEW, new ContextView());
		
	}
	
	//TODO: Check what this does
	  @WebServlet(urlPatterns = "/*", name = "LoginServlet", asyncSupported = true)
	    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
	    public static class LoginServlet extends VaadinServlet {
	    }

}
