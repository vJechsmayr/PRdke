package domainExpert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import g4dke.app.MainUI;
import g4dke.app.SystemHelper;
import userDatabase.OperationPosition;

/**
 * @author Viktoria J.
 * 
 *         Anfrage new Parameter -> RepAdmin (Fall2) OK Anfrage delete Parameter
 *         -> RepAdmin OK
 * 
 *         Test SystemMessages sent?!
 * 
 */
public class DomainExpert_ParameterView extends DomainExpertViews implements View {

	private static final long serialVersionUID = 1L;
	List<ParameterForGrid> parameterList;
	Grid<ParameterForGrid> parameterGrid;
	List<String> parameters;

	VerticalLayout layout;
	HorizontalLayout addLayout;
	TextField nameField;
	TextField rootValue;
	TextField detParam;

	Button addParam;
	Button delParam;

	public DomainExpert_ParameterView() {
		super(MainUI.DE_PARAMETER_VIEW);
		super.setTitle("Domain Expert - Parameter View");

		loadParameters();
	}

	/*
	 * @author Marcel G.
	 * 
	 * edit by Viktoria J.
	 * 
	 */
	private void loadAndBuildListForGrid() {
		try {
			parameters = fl.getParameters();
			parameterList = new ArrayList<>();
			for (String p : parameters) {
				parameterList.add(new ParameterForGrid(p));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * @author Marcel G.
	 * 
	 * edit by Viktoria J.
	 * 
	 */
	private void setGridItems() {
		parameterGrid = new Grid<>();
		parameterGrid.setItems(parameterList);
		parameterGrid.setSelectionMode(SelectionMode.MULTI);
		parameterGrid.addColumn(ParameterForGrid::getValue).setCaption("Parameters");
	}

	/*
	 * @author Viktoria J.
	 * 
	 */
	private void loadParameters() {

		layout = new VerticalLayout();
		addLayout = new HorizontalLayout();
		nameField = new TextField();
		rootValue = new TextField();
		detParam = new TextField();
		addParam = new Button("Add Parameter");
		delParam = new Button("delete selected Parameters");

		nameField.setPlaceholder("Enter Parameter here");
		rootValue.setPlaceholder("Enter root value here");
		detParam.setPlaceholder("Enter detParam here");

		loadAndBuildListForGrid();
		setGridItems();

		addParam.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				if (rootValue.getValue() == null || rootValue.getValue() == "") {
					Notification.show("Please insert a root Value!");
				} else if (detParam.getValue() == null || detParam.getValue() == "") {
					Notification.show("Please insert a detParam Value!");
				} else if (nameField.getValue() == null || nameField.getValue() == "") {
					Notification.show("Please insert new Parameter Name!");
				} else {
					OperationPosition op = SystemHelper.isComposedOperationsStarted(SystemHelper.COM_NEW_PARAMETER,
							nameField.getValue(), "", "");

					if (op == null) {
						op = SystemHelper.NewParameter(rootValue.getValue(), detParam.getValue(), nameField.getValue());
						Notification.show("SystemMessage sent - Please wait for Response!");
						rootValue.setValue("");
						detParam.setValue("");
						nameField.setValue("");
					} else {
						Notification.show("New Parameter already running - Please wait for Response!");
					}
				}
			}// end addParam ButtonClick
		});

		delParam.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				if (!parameterGrid.getSelectedItems().isEmpty()) {
					for (ParameterForGrid p : parameterGrid.getSelectedItems()) {
						OperationPosition op = SystemHelper
								.isComposedOperationsStarted(SystemHelper.COM_DELETE_PARAMETER, p.getValue(), "", "");

						if (op == null) {
							op = SystemHelper.DeleteParameter(p.getValue());
						}
					}
					Notification.show("SystemMessage sent - Please wait for Response!");
				} else {
					Notification.show("Please select a Parameter to delete");
				}
			}// end delParam ButtonClick
		});

		addLayout.addComponent(nameField);
		addLayout.addComponent(rootValue);
		addLayout.addComponent(detParam);
		addLayout.addComponent(addParam);

		layout.addComponent(addLayout);
		layout.addComponent(delParam);
		layout.addComponent(parameterGrid);

		super.setContent(layout);
	}

	/*
	 * @author Marcel G.
	 * 
	 * edit by Viktoria J.
	 * 
	 */
	class ParameterForGrid {
		String value;

		public ParameterForGrid(String p) {
			this.value = p;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

}
