package g4dke.app;

import java.util.ArrayList;

import com.vaadin.navigator.View;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;

import g4.templates.MessagingService;
import userDatabase.DBValidator;
import userDatabase.Message;
import userDatabase.SystemMessage;
import userDatabase.SystemUser;

/*
 * @author Marcel G.
 * 
 * */
public class Messaging_InboxView extends MessagingService implements View {

	private static final long serialVersionUID = 1L;

	ArrayList<Message> messages = null;
	ArrayList<SystemMessage> systemMessages = null;
	public Messaging_InboxView() {

		viewTitle.setValue("MessagingService - Inbox");
		initView();
		showMessages();

	}

	private void initView() {
		inbox.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.MS_INBOX);

			}
		});// end rules ClickListener

		outbox.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.MS_OUTBOX);

			}
		});// end contexts ClickListener

		logout.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				SystemHelper.logout();
				getUI().getNavigator().navigateTo(MainUI.LOGIN_VIEW);
			}

			
		});// end logout ClickListener
		
		

	}

	
	private void showMessages()
	{
		SystemUser user = SystemHelper.getCurrentUser();
		if(user!=null) {
		messages = DBValidator.getInboxMessagesOfUser(user);
		systemMessages = DBValidator.getInboxSystemMessagesOfUser(user);
		
		Grid<Message> grid = new Grid<>();
		grid.setItems(messages);
		grid.addColumn(Message::getAuthor).setCaption("Author");
		grid.addColumn(Message::getText).setCaption("Text");
		grid.addColumn(Message::getTimestamp).setCaption("Timestamp");
		
		systemMessagesPanel.setContent(grid);
		}
	}

}
