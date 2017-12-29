package g4dke.app;



import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;

/*
 * @author Viktoria J.
 * */
public class LoginView extends VerticalLayout implements View{
	
	private TextField name = new TextField("LoginView");
	
	
	public LoginView() {
		setSizeFull();
		
	
		final TextField name = new TextField();
		name.setCaption("Test");
		
		addComponent(name);
		setComponentAlignment(name, Alignment.TOP_LEFT);
				
		Button next = new Button("Go to Test View", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.TEST_VIEW);
			}
		});
    	
    	addComponent(next);
    	setComponentAlignment(next, Alignment.TOP_CENTER);
    	
    	Button templateBtn = new Button("Go to Template View", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.CONTEXT_VIEW);
				
			}
		});
    	
    	addComponent(templateBtn);
    	setComponentAlignment(templateBtn, Alignment.TOP_CENTER);
    	
	}
	
	@Override
    public void enter(ViewChangeEvent event) {
        Notification.show("Welcome");
    }

}
