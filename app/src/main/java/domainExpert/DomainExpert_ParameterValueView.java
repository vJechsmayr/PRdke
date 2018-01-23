package domainExpert;

import java.io.IOException;
import java.util.List;

import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid.SelectionMode;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;
import userDatabase.OperationPosition;

/**
 * @author Viktoria J.
 * 
 * 
 *         Liste ParameterValues OK Anfrage add ParameterValue -> RepAdmin OK
 *         Anfrage delete ParameterValue ->RepAdmin OK Anfrage update
 *         ParameterValue -> RepAdmin (nice to have)
 * 
 *         test SystemMessages sent? --> nach Neustart?
 * 
 */
public class DomainExpert_ParameterValueView extends DomainExpertViews implements View {
	private static final long serialVersionUID = 1L;

	VerticalLayout layout;
	ComboBox<String> selectParameter;
	Tree<String> tree;
	TreeData<String> data;
	Button delParamValue;
	boolean treeLoadedFirst;
	boolean addComponentsLoadedFirst;
	// TextField parentValue;
	TextField paramValueName;
	Button addParamValue;

	public DomainExpert_ParameterValueView() {
		super(MainUI.DE_PARAMETERVALUE_VIEW);
		super.setTitle("Domain Expert - ParameterValue View");

		treeLoadedFirst = false;
		addComponentsLoadedFirst = false;

		loadParameterValues();
	}

	private void initTree() {
		try {
			if (treeLoadedFirst) {
				layout.removeComponent(tree);
				layout.removeComponent(delParamValue);
			} else
				treeLoadedFirst = true;

			List<String[]> values = fl.getParameterValuesHiearchy(selectParameter.getSelectedItem().get().toString());
			tree = new Tree<>();
			data = new TreeData<>();
			for (String[] array : values) {

				if (!data.contains(array[0])) {
					data.addItem(null, array[0]);
				}
				if (data.contains(array[1])) {
					// copy tree if parent gets parent
					TreeData<String> help = new TreeData<>();
					help.addItem(null, array[0]);
					help.addItem(array[0], array[1]);
					List<String> listHelp = data.getChildren(array[1]);
					help.addItems(array[1], listHelp);
					data.clear();
					data = help;
				} else
					data.addItem(array[0], array[1]);
			}
			tree.setDataProvider(new TreeDataProvider<>(data));
			tree.setSelectionMode(SelectionMode.SINGLE);
			layout.addComponent(tree);

			delParamValue = new Button("delete selected paramValue");
			delParamValue.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {

					if (!tree.getSelectedItems().isEmpty()) {
						String selectedParameter = selectParameter.getSelectedItem().get();

						for (String s : tree.getSelectedItems()) {
							OperationPosition op = SystemHelper.isComposedOperationsStarted(
									SystemHelper.COM_DELETE_PARAMETERVALUE, selectedParameter, "", "");

							if (op == null) {
								op = SystemHelper.DeleteParameterValue(selectedParameter, s);
							}
						}
						Notification.show("SystemMessage sent - Please wait for Response!");
					} else {
						Notification.show("Please select a ParameterValue to delete");
					}

				}
			});

			layout.addComponent(delParamValue);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initAddComponents() {
		if (addComponentsLoadedFirst) {
			layout.removeComponent(paramValueName);
			layout.removeComponent(addParamValue);
		} else
			addComponentsLoadedFirst = true;

		paramValueName = new TextField();
		paramValueName.setCaption("Enter here new param value");
		layout.addComponent(paramValueName);

		addParamValue = new Button("Add");
		addParamValue.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				if (paramValueName.getValue() == null || paramValueName.getValue().equals("")) {
					Notification.show("Please enter a ParameterValue Name!");
				} else if (tree.getSelectedItems().isEmpty()) {
					Notification.show("Please select a ParameterValue in tree!");
				} else {
					String selectedParameter = selectParameter.getSelectedItem().get();

					OperationPosition op = SystemHelper.isComposedOperationsStarted(SystemHelper.COM_NEW_PARAMETERVALUE,
							selectedParameter, "", "");

					if (op == null) {
						op = SystemHelper.AddParameterValue(selectedParameter, paramValueName.getValue(),
								tree.getSelectedItems().toArray()[0].toString());
					}

					Notification.show("SystemMessage sent - Please wait for Response!");
				}

			}// end addParamValue ButtonClick
		});
		layout.addComponent(addParamValue);
	}

	private void loadParameterValues() {

		// CBRInterface fl;
		try {
			layout = new VerticalLayout();

			List<String> parameters = fl.getParameters();
			selectParameter = new ComboBox<>("Select a parameter");
			selectParameter.setItems(parameters);
			selectParameter.addSelectionListener(new SingleSelectionListener<String>() {
				private static final long serialVersionUID = 1L;

				@Override
				public void selectionChange(SingleSelectionEvent<String> event) {
					initTree();
					initAddComponents();
				}
			});
			layout.addComponent(selectParameter);
			// contentPanel.setContent(layout);
			super.setContent(layout);

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
