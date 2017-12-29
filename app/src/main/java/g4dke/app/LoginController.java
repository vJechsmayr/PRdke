package g4dke.app;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import userDatabase.DBValidator;
import userDatabase.SystemUser;


/*
 * @author Marcel G.
 * */
public class LoginController extends UI {

	@Override
	protected void init(VaadinRequest request) {
		// TODO Auto-generated method stub
		final VerticalLayout layout = new VerticalLayout();

		final TextField name = new TextField();
		name.setCaption("Type your user-name here:");
		final TextField password = new TextField();
		password.setCaption("Type your password here:");
		
		CheckBox rememberCB = new CheckBox("Remember Me");
		rememberCB.setValue(false);
				
		Button button = new Button("Login");
        button.addClickListener(e -> {
        	SystemUser user = DBValidator.getUser(name.getValue(), password.getValue());
            if(user!=null)
            {
            	if(rememberCB.getValue())
            	{
            		//TODO: Check if this is right, 30 minutes or more?
            		//TODO: Method to  update cookie? Method to check if cookie exists??
            		// Create a new cookie
            		Cookie usernameCookie = new Cookie("username", name.getValue());
            		// Make cookie expire in 30 minutes
            		usernameCookie.setMaxAge(1800);
            		// Set the cookie path.
            		usernameCookie.setPath(VaadinService.getCurrentRequest().getContextPath());
            		// Save cookie
            		VaadinService.getCurrentResponse().addCookie(usernameCookie);
            	}
            	//TODO: Get User to the next window
            }
            else
            {
            	layout.addComponent(new Label("Username or password wrong"));
            }
        });
        
        layout.addComponents(name, password, button);
        
        setContent(layout);
	}

}
