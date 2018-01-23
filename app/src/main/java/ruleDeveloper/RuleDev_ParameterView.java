package ruleDeveloper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;

import g4dke.app.MainUI;

/*
 * @author Viktoria J.
 * 
 * */
public class RuleDev_ParameterView extends RuleDeveloperViews implements View {
	private static final long serialVersionUID = 1L;

	List<ParameterForGrid> parameterList;
	Grid<ParameterForGrid> parameterGrid;

	public RuleDev_ParameterView() {
		super(MainUI.RD_PARAMETER_VIEW);
		super.setTitle("Rule Developer - Parameter View");
		loadParameters();
	}

	/*
	 * @author Marcel G.
	 * 
	 */
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

	/*
	 * @author Marcel G.
	 * 
	 */
	private void setGridItems() {
		parameterGrid = new Grid<>();
		parameterGrid.setItems(parameterList);
		parameterGrid.setSelectionMode(SelectionMode.NONE);
		parameterGrid.addColumn(ParameterForGrid::getValue).setCaption("Parameters");
	}
	
	/*
	 * @author Marcel G.
	 * 
	 * edit by Viktoria
	 * 
	 */
	private void loadParameters() {
		VerticalLayout layout = new VerticalLayout();
		loadAndBuildListForGrid();
		setGridItems();

		layout.addComponent(parameterGrid);
		super.setContent(layout);
	}

	/*
	 * @author Marcel G.
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
