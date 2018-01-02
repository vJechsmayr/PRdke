package messagingService;

import java.util.ArrayList;

import com.vaadin.navigator.View;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.Grid;

import g4.templates.MessagingService;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;
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

	Grid<Message> messagesGrid = null;
	Grid<SystemMessage> systemMessagesGrid = null;

	public Messaging_InboxView() {

		viewTitle.setValue("MessagingService - Inbox");
		initView();
		loadAllMessages();

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

	private void loadMessages() {
		SystemUser user = SystemHelper.getCurrentUser();
		if (user != null) {
			messages = DBValidator.getInboxMessagesOfUser(user);

			//Fil with data
			messagesGrid = new Grid<>();
			messagesGrid.setItems(messages);
			messagesGrid.addColumn(Message::getAuthor).setCaption("Author");
			messagesGrid.addColumn(Message::getText).setCaption("Text");
			messagesGrid.addColumn(Message::getTimestamp).setCaption("Timestamp");

			
			//Setting attributes
			messagesGrid.setSelectionMode(SelectionMode.MULTI);
			messagesGrid.setSizeFull();
			messagesPanel.setContent(messagesGrid);
			messagesPanel.setSizeFull();
			
			
			
			//TODO: bind button in panel
			Button deleteBtn = new Button("Delete", new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					if(messagesGrid.getSelectedItems()!=null && messagesGrid.getSelectedItems().size()>0) {
						
						for(Message m : messagesGrid.getSelectedItems())
						{
							DBValidator.RemoveMessage(m);
						}
						messages = DBValidator.getInboxMessagesOfUser(user);
					}
				}
			});
			
			
		
		}
	}

	private void loadSystemMessages() {
		SystemUser user = SystemHelper.getCurrentUser();
		if (user != null) {
			systemMessages = DBValidator.getInboxSystemMessagesOfUser(user);
			
			//Fill with data
			systemMessagesGrid = new Grid<>();
			systemMessagesGrid.setItems(systemMessages);
			systemMessagesGrid.addColumn(SystemMessage::getAuthor).setCaption("Author");
			systemMessagesGrid.addColumn(SystemMessage::getText).setCaption("Text");
			systemMessagesGrid.addColumn(SystemMessage::getTimestamp).setCaption("Timestamp");
			systemMessagesGrid.addColumn(SystemMessage::getAtomicOperation).setCaption("Atomic Operation");
			systemMessagesGrid.addColumn(SystemMessage::getConcernedRuleTerm).setCaption("Concerned Rule/Term");
			systemMessagesGrid.addColumn(SystemMessage::getContainingContext).setCaption("Containing Context");
			systemMessagesGrid.addColumn(message -> "Acknowledge",
				      new ButtonRenderer(clickEvent -> {
				         //TODO: handle event
				    	 SystemMessage m = (SystemMessage) clickEvent.getItem();
				    	 m.setAcknowledged(true);
				    	 DBValidator.saveSystemMessages(systemMessages);
				    }));
			//Setting attributes
			systemMessagesGrid.setSizeFull();
			systemMessagesPanel.setContent(systemMessagesGrid);
			systemMessagesPanel.setSizeFull();

		}
	}

	private void loadAllMessages() {
		loadMessages();
		loadSystemMessages();
	}

}
