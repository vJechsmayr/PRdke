package repositoryAdmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import dke.pr.cli.CBRInterface;
import g4.templates.RepositoryAdminDesign;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;
import userDatabase.DBValidator;
import userDatabase.SystemMessage;
/*
 * @author Marcel G.
 * 
 * ParameterView-Code copied to RuleDev_ParameterView @Viktoria
 * */
public class RepositoryAdmin_ParameterView extends RepositoryAdminDesign implements View {
	
	List<ParameterForGrid> parameterList;
	Grid<ParameterForGrid> parameterGrid;
	CBRInterface fl =null;
	public RepositoryAdmin_ParameterView()
	{
		viewTitle.setValue("Repository Administrator - Parameter View");
		initView();
	}
	
	private void initView()
	{
		initButtonsFromDesign();
		//TODO:
	
		Button loadParameters = new Button("Load Parameters");
		loadParameters.addClickListener( new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				loadParameters();
			}
		});
		contentPanel.setContent(loadParameters);
	}
	
	/*
	 * initButtonsFromDesign()
	 * author: Viktoria
	 */
	private void initButtonsFromDesign() {
		// Contexts
				contexts.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						getUI().getNavigator().navigateTo(MainUI.RA_CONTEXT_VIEW);

					}
				});// end ClickListener

				// ContextClass
				contextsClass.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 1L;
					
					@Override
					public void buttonClick(ClickEvent event) {
						getUI().getNavigator().navigateTo(MainUI.RA_CONTEXTCLASS_VIEW);
						
					}
				});
				// end ClickListener

				// Parameter
				parameter.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 1L;
					
					@Override
					public void buttonClick(ClickEvent event) {
						getUI().getNavigator().navigateTo(MainUI.RA_PARAMETER_VIEW);
						
					}
				});
				// end ClickListener

				// ParameterVal
				parameterValue.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 1L;
					
					@Override
					public void buttonClick(ClickEvent event) {
						getUI().getNavigator().navigateTo(MainUI.RA_PARAMETERVALUE_VIEW);
						
					}
				});
				// end ClickListener

				// MessagingService
				messagingService.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						getUI().getNavigator().navigateTo(MainUI.MS_INBOX);

					}
				}); // end ClickListener

				// Logout
				logout.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						SystemHelper.logout();
						getUI().getNavigator().navigateTo(MainUI.LOGIN_VIEW);
					}
				});// end logout ClickListener
		
	}
	
	private void loadAndBuildListForGrid()
	{
		//Grid needs Bean Class. Cannot use only String
		List<String> parameters;
		try {
			parameters = fl.getParameters();
			parameterList = new ArrayList<>();
			for(String p : parameters)
			{
				parameterList.add(new ParameterForGrid(p));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void setGridItems()
	{
		parameterGrid = new Grid<>();
		parameterGrid.setItems(parameterList);
		parameterGrid.setSelectionMode(SelectionMode.MULTI);
		parameterGrid.addColumn(ParameterForGrid::getValue).setCaption("Parameters");
		//parameterGrid.addColumn(ParameterForGrid::getValue).setEditorComponent(paramEditor, ParameterForGrid::setValue).setCaption("Parameter");
		//parameterGrid.getEditor().setEnabled(true);
	}
	
	private void initInterface()
	{
		try {
			fl = new CBRInterface(
					SystemHelper.PFAD + "/ctxModelAIM.flr",
					SystemHelper.PFAD + "/bc.flr", "AIMCtx",
					"SemNOTAMCase");
			fl.setDebug(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void loadParameters()
	{
		
			VerticalLayout layout = new VerticalLayout();
			initInterface();
			loadAndBuildListForGrid();
			setGridItems();

			TextField nameField = new TextField();
			nameField.setCaption("Enter Parameter here");
			
			Button addParam = new Button("Add Parameter");
			addParam.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					if(nameField.getValue()== null || nameField.getValue().equals(""))
						Notification.show("FIELD MUST NOT BE EMPTY");
					else
					{
					
						try {
							
							if(fl.addParameter(nameField.getValue(), "", ""))
							{
								//TODO: NOT WORKING
								fl.close();
								initInterface();
								loadAndBuildListForGrid();
								setGridItems();
							}
							else
								Notification.show("An error occoured");
							
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
				}
			});
			
			Button delParam = new Button("delete marked Parameters");
			delParam.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					for(ParameterForGrid param : parameterGrid.getSelectedItems())
					{
						try {
							if(!fl.delParameter(param.getValue()))
								Notification.show("An error occoured");
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
					
				}
			});
			
			
			layout.addComponent(nameField);
			layout.addComponent(addParam);
			layout.addComponent(parameterGrid);
			layout.addComponent(delParam);
			contentPanel.setContent(layout);
			
		
	}
	class ParameterForGrid
	{
		String value;
		
		public ParameterForGrid(String p)
		{
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


