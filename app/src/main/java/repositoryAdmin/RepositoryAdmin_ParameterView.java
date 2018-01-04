package repositoryAdmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import dke.pr.cli.CBRInterface;
import g4.templates.RepositoryAdminDesign;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;
import userDatabase.Message;

/*
 * @author Marcel G.
 * 
 * */
public class RepositoryAdmin_ParameterView extends RepositoryAdminDesign implements View {
	
	List<ParameterForGrid> parameterList;
	public RepositoryAdmin_ParameterView()
	{
		viewTitle.setValue("Repository Administrator - Parameter View");
		initView();
	}
	
	private void initView()
	{
		//TODO:
		//protected Button contextsClass;
		//protected Button parameterValue;
		
		contexts.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RA_CONTEXT_VIEW);
				
			}
		});//end  ClickListener
		
		parameter.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RA_PARAMETER_VIEW);
				
			}
		});//end  ClickListener
		
		messagingService.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.MS_INBOX);
				
			}
		});//end  ClickListener
		
		logout.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				SystemHelper.logout();
				getUI().getNavigator().navigateTo(MainUI.LOGIN_VIEW);
				
			}
		});//end  ClickListener
		
		Button loadParameters = new Button();
		loadParameters.addClickListener( new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				loadParameters();
			}
		});
		contentPanel.setContent(loadParameters);
	}
	
	private void loadParameters()
	{
		try {
			CBRInterface fl = new CBRInterface(
					SystemHelper.PFAD + "/ctxModelAIM.flr",
					SystemHelper.PFAD + "/bc.flr", "AIMCtx",
					"SemNOTAMCase");
			
			fl.setDebug(false);
			
			//Grid needs Bean Class. Cannot use only String
			List<String> parameters = fl.getParameters();
			parameterList = new ArrayList<>();
			for(String p : parameters)
			{
				parameterList.add(new ParameterForGrid(p));
			}
			Grid<ParameterForGrid> parameterGrid = new Grid<>();
			parameterGrid.setItems(parameterList);
			parameterGrid.addColumn(ParameterForGrid::getValue).setCaption("Parameter");
			parameterGrid.setSelectionMode(SelectionMode.NONE);
			
			contentPanel.setContent(parameterGrid);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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


