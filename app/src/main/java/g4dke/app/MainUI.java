package g4dke.app;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

public class MainUI extends UI{
	Navigator nav;
	static final String MAINVIEW = "MainView";
	
	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Navigation");
		
		nav = new Navigator(this, this);
		
		nav.addView("", new LoginView(nav));
		nav.addView(MAINVIEW, new TestView());
		
	}
	
	//TODO: Check what this does
	  @WebServlet(urlPatterns = "/*", name = "LoginServlet", asyncSupported = true)
	    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
	    public static class LoginServlet extends VaadinServlet {
	    }

}
