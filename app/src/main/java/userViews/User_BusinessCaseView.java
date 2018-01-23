package userViews;

import java.io.IOException;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import g4dke.app.MainUI;

/**
 * @author Viktoria J.
 * 
 */
public class User_BusinessCaseView extends UserViews implements View {
	private static final long serialVersionUID = 1L;
	VerticalLayout businessCaseLayout = new VerticalLayout();
	Accordion notams;

	public User_BusinessCaseView() {
		super(MainUI.USER_BUSINESSCASE);
		super.setTitle("User - BusinessCase - NOTAMS");

		initRun();
		initNOTAMAccordion();

		super.setContent(businessCaseLayout);
	}

	private void initRun() {
		HorizontalLayout runLayout = new HorizontalLayout();

		businessCaseLayout.addComponent(runLayout);
	}

	private void initNOTAMAccordion() {
		notams = new Accordion();
		notams.setHeight("500px");

		try {
			for (String f : fl.getNOTAMS()) {
				String infos = "";
				for (String[] n : fl.getNOTAMInfo(f)) {
					for (int i = 0; i < n.length; i++) {
						infos = infos + n[i] + "<br />";
					}
				}
				final Label label = new Label(infos, ContentMode.HTML);
				label.setWidth("500px");

				final VerticalLayout layout = new VerticalLayout(label);
				layout.setMargin(true);

				notams.addTab(layout, f);

			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		businessCaseLayout.addComponent(notams);
	}

}
