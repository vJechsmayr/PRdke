package ruleDeveloper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;

import dke.pr.cli.CBRInterface;
import g4.templates.RuleDeveloperDesign;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;

/*
 * @author Viktoria J.
 * 
 * */
public class RuleDev_ParameterView extends RuleDeveloperDesign implements View{
	private static final long serialVersionUID = 1L;

	List<ParameterForGrid> parameterList;
	Grid<ParameterForGrid> parameterGrid;
	CBRInterface fl =null;
	
	public RuleDev_ParameterView() {
		viewTitle.setValue("Rule Developer - Parameter View");
		initView();
		loadParameters();
	}
	
	private void initView() {
		initButtonsFromDesign();
		
		/*Button loadParameters = new Button("Load Parameters");
		loadParameters.addClickListener( new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				loadParameters();
			}
		});
		contentPanel.setContent(loadParameters);*/
		
	}
	
	private void initButtonsFromDesign() {
			rules.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;
				
				@Override
				public void buttonClick(ClickEvent event) {
					getUI().getNavigator().navigateTo(MainUI.RD_RULE_VIEW);
					
				}
			});//end rules ClickListener
			
			contexts.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;
				
				@Override
				public void buttonClick(ClickEvent event) {
					getUI().getNavigator().navigateTo(MainUI.RD_CONTEXT_VIEW);
					
				}
			});//end contexts ClickListener
			
			parameter.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;
				@Override
				public void buttonClick(ClickEvent event) {
					getUI().getNavigator().navigateTo(MainUI.RD_PARAMETER_VIEW);
					
				}
			});
			
			parameterValue.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;
				@Override
				public void buttonClick(ClickEvent event) {
					getUI().getNavigator().navigateTo(MainUI.RD_PARAMETERVALUE_VIEW);
					
				}
			});
			
			messagingService.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;
				@Override
				public void buttonClick(ClickEvent event) {
					getUI().getNavigator().navigateTo(MainUI.MS_INBOX);
				}
			});
		
		logout.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				SystemHelper.logout();
				getUI().getNavigator().navigateTo(MainUI.LOGIN_VIEW);
			}
		});//end logout ClickListener
			
			
		}
	
	/*
	 * @author Marcel G.
	 * 
	 * */
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
	
	/*
	 * @author Marcel G.
	 * 
	 * */
	private void setGridItems()
	{
		parameterGrid = new Grid<>();
		parameterGrid.setItems(parameterList);
		parameterGrid.setSelectionMode(SelectionMode.NONE);
		parameterGrid.addColumn(ParameterForGrid::getValue).setCaption("Parameters");
		//parameterGrid.addColumn(ParameterForGrid::getValue).setEditorComponent(paramEditor, ParameterForGrid::setValue).setCaption("Parameter");
		//parameterGrid.getEditor().setEnabled(true);
	}
	
	/*
	 * @author Marcel G.
	 * 
	 * */
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
	
	/*
	 * @author Marcel G.
	 * 
	 * edit by Viktoria
	 * 
	 * */
	private void loadParameters()
	{
			VerticalLayout layout = new VerticalLayout();
			initInterface();
			loadAndBuildListForGrid();
			setGridItems();

			
			layout.addComponent(parameterGrid);
			contentPanel.setContent(layout);
	}
	
	/*
	 * @author Marcel G.
	 * 
	 * */
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
