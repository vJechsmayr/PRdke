package userViews;

import com.vaadin.navigator.View;

import g4dke.app.MainUI;

public class User_BusinessCaseView extends UserViews implements View{
	private static final long serialVersionUID = 1L;

	public User_BusinessCaseView() {
		super(MainUI.USER_BUSINESSCASE);
		super.initInterface();
		super.setTitle("User - BusinessCase View");

		
	}

}
