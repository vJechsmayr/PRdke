package g4dke.app;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

import g4.templates.MessagingService;

/*
 * @author Marcel G.
 * 
 * */
public class Messaging_OutboxView extends MessagingService implements View{

	public Messaging_OutboxView() {

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
		
	}
}
