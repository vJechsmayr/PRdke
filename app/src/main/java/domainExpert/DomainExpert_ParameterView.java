package domainExpert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import g4dke.app.MainUI;

/**
 * @author Viktoria J.
 * 
 * Anfrage new Parameter -> RepAdmin (Fall2)
 * Anfrage delete Parameter -> RepAdmin
 * 
 * */
public class DomainExpert_ParameterView extends DomainExpertViews implements View {

private static final long serialVersionUID = 1L;
List<ParameterForGrid> parameterList;
Grid<ParameterForGrid> parameterGrid;
List<String> parameters;

VerticalLayout layout;
TextField nameField;
TextField rootValue;
TextField detParam;

Button addParam;
Button delParam;
	
	public DomainExpert_ParameterView(){
		super(MainUI.DE_PARAMETER_VIEW);
		super.setTitle("Domain Expert - Parameter View");
		super.initInterface();
		
		loadParameters();
	}
	
	/*
	 * @author Marcel G.
	 * 
	 * edit by Viktoria J.
	 * 
	 * */
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
	 * */
	private void setGridItems() {
		parameterGrid = new Grid<>();
		parameterGrid.setItems(parameterList);
		parameterGrid.setSelectionMode(SelectionMode.MULTI);
		parameterGrid.addColumn(ParameterForGrid::getValue).setCaption("Parameters");
	}

	
	/*
	 * @author Marcel G.
	 * 
	 * edit by Viktoria J.
	 * 
	 * */
	private void loadParameters() {

		layout = new VerticalLayout();
		initInterface();
		loadAndBuildListForGrid();
		setGridItems();

		nameField = new TextField();
		nameField.setCaption("Enter Parameter here");

		rootValue = new TextField();
		rootValue.setCaption("Enter root value here");
		
		detParam = new TextField();
		detParam.setCaption("Enter detParam here");
		
		addParam = new Button("Add Parameter");
		addParam.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				/*
				if (rootValue.getValue() == null || rootValue.getValue().equals("") && detParam.getValue() == null || detParam.getValue().equals("") && nameField.getValue() == null || nameField.getValue().equals(""))
					Notification.show("FIELDS MUST NOT BE EMPTY");
				else {

					try {

						if (fl.addParameter(nameField.getValue(), rootValue.getValue(), detParam.getValue())) {
							// TODO: NOT WORKING
							fl.close();
							initInterface();
							loadAndBuildListForGrid();
							setGridItems();
						} else
							Notification.show("An error occoured");

					} catch (IOException e) {
						e.printStackTrace();
					}
				}*/

			}//end addParam ButtonClick
		});

		delParam = new Button("delete marked Parameters");
		delParam.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				
				
				
				/*
				for (ParameterForGrid param : parameterGrid.getSelectedItems()) {
					try {
						OperationPosition op = SystemHelper.isComposedOperationsStarted(
								SystemHelper.COM_DELETE_Parameter, param.getValue(), "", "");
						// SystemMessage
						if (op == null) {

							op = SystemHelper.DeleteParameter(param.getValue());
							
						} else {
							DeleteParameter help = new DeleteParameter();
							if (help.isFinished(op.getCurrentPosition())) {
								if (!fl.delParameter(param.getValue())) {
									Notification.show("An error occoured");
									}
								else {
									op.setCurrentPosition(op.getCurrentPosition()+1);
									DBValidator.updateOperationPosition(op);
								}
							} else
								Notification.show("The composed operation is not ready");
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				try {
					fl.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				layout.removeComponent(parameterGrid);
				layout.removeComponent(delParam);
				initInterface();
				loadAndBuildListForGrid();
				setGridItems();
				layout.addComponent(parameterGrid);
				layout.addComponent(delParam);
*/
			}//end delParam ButtonClick
		});

		layout.addComponent(nameField);
		layout.addComponent(rootValue);
		layout.addComponent(detParam);
		layout.addComponent(addParam);
		layout.addComponent(parameterGrid);
		layout.addComponent(delParam);
		//contentPanel.setContent(layout);
		super.setContent(layout);
	}

	/*
	 * @author Marcel G.
	 * 
	 * edit by Viktoria J.
	 * 
	 * */
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
