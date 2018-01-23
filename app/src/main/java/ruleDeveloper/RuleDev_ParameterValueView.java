package ruleDeveloper;

import java.io.IOException;
import java.util.List;

import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

import dke.pr.cli.CBRInterface;
import g4.templates.RuleDeveloperDesign;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;

/**
 * @author Viktoria J.
 * 
 */
public class RuleDev_ParameterValueView extends RuleDeveloperViews implements View {
	private static final long serialVersionUID = 1L;

	VerticalLayout layout;
	ComboBox<String> select;
	Tree<String> tree;
	TreeData<String> data;

	boolean treeLoadedFirst;

	public RuleDev_ParameterValueView() throws Exception {
		super(MainUI.RD_PARAMETERVALUE_VIEW);
		super.setTitle("Rule Developer - ParameterValue View");

		treeLoadedFirst = false;
		loadParameterValues();
	}

	/**
	 * @author Marcel G.
	 * 
	 *         edited by Viktoria
	 * 
	 */
	private void initTree() {
		try {
			if (treeLoadedFirst) {
				layout.removeComponent(tree);
			} else
				treeLoadedFirst = true;

			List<String[]> values = fl.getParameterValuesHiearchy(select.getSelectedItem().get().toString());
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

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Marcel G.
	 * 
	 *         edited by Viktoria
	 * 
	 */
	private void loadParameterValues() {
		try {
			layout = new VerticalLayout();
			List<String> parameters = fl.getParameters();
			select = new ComboBox<>("Select a parameter");
			select.setItems(parameters);
			select.addSelectionListener(new SingleSelectionListener<String>() {
				private static final long serialVersionUID = 1L;

				@Override
				public void selectionChange(SingleSelectionEvent<String> event) {
					initTree();
				}
			});
			select.setWidth("300px");
			layout.addComponent(select);
			layout.setWidth("800px");
			super.setContent(layout);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
