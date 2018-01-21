package messagingService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.vaadin.navigator.View;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.MultiSelectionModel;
import com.vaadin.ui.renderers.ButtonRenderer;

import composedOperations.ContexualizeRule;
import composedOperations.DeContextualizeRule;
import composedOperations.DeleteContext;
import composedOperations.DeleteParameter;
import composedOperations.MergeContext;
import composedOperations.ModifyRule;
import composedOperations.NewContext;
import composedOperations.NewParameter;
import composedOperations.Operation;
import composedOperations.StandardComposedOperation;

import com.vaadin.ui.Grid;

import g4.templates.MessagingService;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;
import userDatabase.DBValidator;
import userDatabase.Message;
import userDatabase.OperationPosition;
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
		newMessage.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Window window = new Window("New Message");
				window.setWidth(500.0f, Unit.PIXELS);
				FormLayout content = new FormLayout();
				content.setMargin(true);

				ComboBox<SystemUser> select = new ComboBox<>("Select a SystemUser");
				select.setItems(DBValidator.getAllSystemUsers());

				TextField field = new TextField();
				field.setWidth(200.0f, Unit.PIXELS);
				field.setHeight(200.0f, Unit.PIXELS);

				Button sendBtn = new Button("Send", new Button.ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						SystemHelper.WriteMessage(select.getSelectedItem().get().getName(),
								field.getValue().toString());
						window.close();
					}
				});

				content.addComponent(select);
				content.addComponent(field);
				content.addComponent(sendBtn);
				window.setContent(content);
				getUI().getUI().addWindow(window);
			}
		});// end rules ClickListener

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

		back.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (SystemHelper.lastPage.equals("")) {
					getUI().getNavigator().navigateTo(MainUI.LOGIN_VIEW);
				} else
					getUI().getNavigator().navigateTo(SystemHelper.lastPage);
			}

		});// end logout ClickListener
	}

	private void loadMessages() {
		SystemUser user = SystemHelper.getCurrentUser();
		if (user != null) {
			VerticalLayout layout = new VerticalLayout();
			messages = DBValidator.getInboxMessagesOfUser(user);

			// Fil with data
			messagesGrid = new Grid<>();
			messagesGrid.setItems(messages);
			messagesGrid.addColumn(Message::getAuthor).setCaption("Author");
			messagesGrid.addColumn(Message::getText).setCaption("Text");

			messagesGrid.addColumn(Message::getTimestampAsString).setCaption("Timestamp");

			// Setting attributes
			messagesGrid.setSelectionMode(SelectionMode.MULTI);
			messagesGrid.setSizeFull();

			layout.addComponent(messagesGrid);
			Button deleteBtn = new Button("Delete", new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					if (messagesGrid.getSelectedItems() != null && messagesGrid.getSelectedItems().size() > 0) {

						for (Message m : messagesGrid.getSelectedItems()) {
							messages.remove(m);

						}
						messagesGrid.setItems(messages);
						DBValidator.saveMessages(messages);
					}
				}
			});

			layout.addComponent(deleteBtn);
			messagesPanel.setContent(layout);
			messagesPanel.setSizeFull();
		}
	}

	private void loadSystemMessages() {
		SystemUser user = SystemHelper.getCurrentUser();
		if (user != null) {
			VerticalLayout layout = new VerticalLayout();
			systemMessages = DBValidator.getInboxSystemMessagesOfUser(user);

			// Fill with data
			systemMessagesGrid = new Grid<>();
			systemMessagesGrid.setItems(systemMessages);
			systemMessagesGrid.addColumn(SystemMessage::getAuthor).setCaption("Author");
			systemMessagesGrid.addColumn(SystemMessage::getText).setCaption("Text");
			systemMessagesGrid.addColumn(SystemMessage::getTimestampAsString).setCaption("Timestamp");
			systemMessagesGrid.addColumn(SystemMessage::getAtomicOperation).setCaption("Atomic Operation");
			systemMessagesGrid.addColumn(SystemMessage::getConcernedRuleTerm).setCaption("Concerned Rule/Term");
			systemMessagesGrid.addColumn(SystemMessage::getContainingContext).setCaption("Containing Context");
			systemMessagesGrid.addColumn(SystemMessage::getConcernedParameter).setCaption("Concerned Parameter");
			// Setting attributes
			systemMessagesGrid.setSelectionMode(SelectionMode.MULTI);
			systemMessagesGrid.setSizeFull();
			layout.addComponent(systemMessagesGrid);
			Button ackButton = new Button("Acknowledge", new Button.ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					if (systemMessagesGrid.getSelectedItems() != null
							&& systemMessagesGrid.getSelectedItems().size() > 0) {

						for (SystemMessage m : systemMessagesGrid.getSelectedItems()) {
							if (!m.isAcknowledged()) {
								m.setAcknowledged(true);
								StandardComposedOperation com = null;
								OperationPosition op = null;
								String operation = "";
								switch (m.getText()) {
								case SystemHelper.COM_DELETE_PARAMETER:
									com = new DeleteParameter();
									operation = SystemHelper.COM_DELETE_PARAMETER;
									break;
								case SystemHelper.COM_CONTEXTUALIZE_RULE:
									com = new ContexualizeRule();
									operation = SystemHelper.COM_CONTEXTUALIZE_RULE;
									break;
								case SystemHelper.COM_DECONTEXTUALIZE_RULE:
									com = new DeContextualizeRule();
									operation = SystemHelper.COM_DECONTEXTUALIZE_RULE;
									break;
								case SystemHelper.COM_DELETE_CONTEXT:
									com = new DeleteContext();
									operation = SystemHelper.COM_DELETE_CONTEXT;
									break;
								case SystemHelper.COM_MERGE_CONTEXT:
									com = new MergeContext();
									operation = SystemHelper.COM_MERGE_CONTEXT;
									break;
								case SystemHelper.COM_MODIFY_RULE:
									com = new ModifyRule();
									operation = SystemHelper.COM_MODIFY_RULE;
									break;
								case SystemHelper.COM_NEW_CONTEXT:
									com = new NewContext();
									operation = SystemHelper.COM_NEW_CONTEXT;
									break;
								case SystemHelper.COM_NEW_PARAMETER:
									com = new NewParameter();
									operation = SystemHelper.COM_NEW_PARAMETER;
									break;
								}
								op = SystemHelper.isComposedOperationsStarted(operation, m.getConcernedParameter(),
										m.getConcernedRuleTerm(), m.getContainingContext());
								if (op != null) {
									op.setCurrentPosition(op.getCurrentPosition() + 1);
									if (!com.isFinished(op.getCurrentPosition()-1)) {
										Operation nextOP = com.getOperation(op.getCurrentPosition());
										SystemUser u = SystemHelper.getSpecificUser(nextOP.getRole());
										SystemHelper.WriteSystemMessage(u.getName(), operation,
												nextOP.getNameOfOperation(), m.getConcernedRuleTerm(),
												m.getContainingContext(), m.getConcernedParameter(), m.getAdditionalData());
										
										
									}
									DBValidator.updateOperationPosition(op);

								}

							}

						}

					}
				}
			});
			layout.addComponent(ackButton);
			systemMessagesPanel.setContent(layout);
			// systemMessagesPanel.setContent(systemMessagesGrid);
			systemMessagesPanel.setSizeFull();

		}
	}

	private void loadAllMessages() {
		loadMessages();
		loadSystemMessages();
	}

}
