package userViews;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import g4dke.app.MainUI;

/**
 * @author Viktoria J.
 * 
 */
public class User_InterestSpecView extends UserViews implements View {
	private static final long serialVersionUID = 1L;

	Accordion iSpecs;
	List<String> iSpecList;

	public User_InterestSpecView() {
		super(MainUI.USER_INTERESTSPEC);
		super.setTitle("User - InterestSpec View");

		initISpecAccordion();
		super.setContent(iSpecs);
	}

	private void initISpecAccordion() {
		iSpecs = new Accordion();
		iSpecs.setHeight("500px");

		iSpecList = new ArrayList<String>();
		try {
			iSpecList = fl.getISpecs();
		} catch (IOException e) {

			e.printStackTrace();
		}

		for (String i : iSpecList) {
			String infos = "";
			try {
				for (String[] s : fl.getISpecInfo(i)) {

					infos = infos + Arrays.toString(s) + "<br />";

				}
				final Label label = new Label(infos, ContentMode.HTML);
				label.setWidth("500px");

				final VerticalLayout layout = new VerticalLayout(label);
				layout.setMargin(true);

				iSpecs.addTab(layout, i);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

}
