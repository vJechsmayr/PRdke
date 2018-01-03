package messagingService;

import java.util.ArrayList;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.renderers.ButtonRenderer;

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
public class Messaging_OutboxView extends MessagingService implements View{
	private static final long serialVersionUID = 1L;

	ArrayList<Message> messages = null;
	ArrayList<SystemMessage> systemMessages = null;

	Grid<Message> messagesGrid = null;
	Grid<SystemMessage> systemMessagesGrid = null;

	public Messaging_OutboxView() {

		viewTitle.setValue("MessagingService - Outbox");
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
			messages = DBValidator.getOutboxMessagesOfUser(user);

			//Fil with data
			messagesGrid = new Grid<>();
			messagesGrid.setItems(messages);
			messagesGrid.addColumn(Message::getReceiver).setCaption("Receiver");
			messagesGrid.addColumn(Message::getText).setCaption("Text");
			messagesGrid.addColumn(Message::getTimestampAsString).setCaption("Timestamp");

			
			//Setting attributes
			messagesGrid.setSelectionMode(SelectionMode.MULTI);
			messagesGrid.setSizeFull();
			messagesPanel.setContent(messagesGrid);
			messagesPanel.setSizeFull();
		}
	}

	private void loadSystemMessages() {
		SystemUser user = SystemHelper.getCurrentUser();
		if (user != null) {
			systemMessages = DBValidator.getOutboxSystemMessagesOfUser(user);
			
			//Fill with data
			systemMessagesGrid = new Grid<>();
			systemMessagesGrid.setItems(systemMessages);
			systemMessagesGrid.addColumn(SystemMessage::getReceiver).setCaption("Receiver");
			systemMessagesGrid.addColumn(SystemMessage::getText).setCaption("Text");
			systemMessagesGrid.addColumn(SystemMessage::getTimestampAsString).setCaption("Timestamp");
			systemMessagesGrid.addColumn(SystemMessage::getAtomicOperation).setCaption("Atomic Operation");
			systemMessagesGrid.addColumn(SystemMessage::getConcernedRuleTerm).setCaption("Concerned Rule/Term");
			systemMessagesGrid.addColumn(SystemMessage::getContainingContext).setCaption("Containing Context");

			//Setting attributes
			systemMessagesGrid.setSizeFull();
			systemMessagesPanel.setContent(systemMessagesGrid);
			systemMessagesPanel.setSizeFull();

		}
	}

	private void loadAllMessages() {
		//load first messages then system messages
		loadMessages();
		loadSystemMessages();
	}

}
