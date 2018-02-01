package repositoryAdmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import composedOperations.DeleteParameter;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;
import userDatabase.DBValidator;
import userDatabase.OperationPosition;

/*
 * @author Marcel G.
 * 
 * ParameterView-Code copied to RuleDev_ParameterView @Viktoria
 * */
public class RepositoryAdmin_ParameterView extends RepositoryAdminViews implements View {

	private static final long serialVersionUID = 1L;
	List<ParameterForGrid> parameterList;
	Grid<ParameterForGrid> parameterGrid;

	public RepositoryAdmin_ParameterView() {
		super(MainUI.RA_PARAMETER_VIEW);
		super.setTitle("Repository Administrator - Parameter View");

		loadParameters();
	}

	private void loadAndBuildListForGrid() {
		// Grid needs Bean Class. Cannot use only String
		List<String> parameters;
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

	private void setGridItems() {
		parameterGrid = new Grid<>();
		parameterGrid.setItems(parameterList);
		parameterGrid.setSelectionMode(SelectionMode.MULTI);
		parameterGrid.addColumn(ParameterForGrid::getValue).setCaption("Parameters");
		// parameterGrid.addColumn(ParameterForGrid::getValue).setEditorComponent(paramEditor,
		// ParameterForGrid::setValue).setCaption("Parameter");
		// parameterGrid.getEditor().setEnabled(true);
	}

	private void loadParameters() {

		VerticalLayout layout = new VerticalLayout();
		loadAndBuildListForGrid();
		setGridItems();

		TextField nameField = new TextField();
		nameField.setCaption("Enter Parameter here");

		TextField rootValue = new TextField();
		rootValue.setCaption("Enter root value here");

		TextField detParam = new TextField();
		detParam.setCaption("Enter detParam here");

		Button addParam = new Button("Add Parameter");
		addParam.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (rootValue.getValue() == null || rootValue.getValue().equals("") && detParam.getValue() == null
						|| detParam.getValue().equals("") && nameField.getValue() == null
						|| nameField.getValue().equals(""))
					Notification.show("FIELDS MUST NOT BE EMPTY");
				else {

					try {

						if (fl.addParameter(nameField.getValue(), rootValue.getValue(), detParam.getValue())) {
							fl.restart();
							loadParameters();
						} else
							Notification.show("An error occoured");

					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		});

		Button delParam = new Button("delete marked Parameters");
		delParam.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				for (ParameterForGrid param : parameterGrid.getSelectedItems()) {
					try {
						OperationPosition op = SystemHelper.isComposedOperationsStarted(
								SystemHelper.COM_DELETE_PARAMETER, param.getValue(), "", "");
						// SystemMessage
						if (op == null) {

							op = SystemHelper.DeleteParameter(param.getValue());

						} else {
							DeleteParameter help = new DeleteParameter();
							if (help.isFinished(op.getCurrentPosition())) {
								if (!fl.delParameter(param.getValue())) {
									Notification.show("An error occoured");
								} else {
									fl.restart();
									op.setCurrentPosition(op.getCurrentPosition() + 1);
									DBValidator.updateOperationPosition(op);
								}
							} else
								Notification.show("The composed operation is not ready");
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

				}

				layout.removeComponent(parameterGrid);
				layout.removeComponent(delParam);

				loadAndBuildListForGrid();
				setGridItems();
				layout.addComponent(parameterGrid);
				layout.addComponent(delParam);

			}
		});

		layout.addComponent(nameField);
		layout.addComponent(rootValue);
		layout.addComponent(detParam);
		layout.addComponent(addParam);
		layout.addComponent(parameterGrid);
		layout.addComponent(delParam);
		// contentPanel.setContent(layout);
		super.setContent(layout);
	}

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
