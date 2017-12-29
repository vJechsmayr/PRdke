package g4dke.app;

import com.vaadin.navigator.View;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/*
 * @author Viktoria J.
 * */
public class TestView extends VerticalLayout implements View{
	public TestView() {
		TextField name = new TextField();
	name.setCaption("Type your user-name here:");
	
	addComponent(name);
		
	}
	
	
}
