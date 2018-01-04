package repositoryAdmin;

import java.io.IOException;
import java.util.List;

import com.vaadin.data.TreeData;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Tree;

import dke.pr.cli.CBRInterface;
import g4.templates.RepositoryAdminDesign;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;

/*
 * @author Marcel G.
 * 
 * */
public class RepositoryAdmin_ParameterValueView extends RepositoryAdminDesign implements View {
	
	
	public RepositoryAdmin_ParameterValueView()
	{
		viewTitle.setValue("Repository Administrator - Parameter Value View");
		initView();
	}

	private void initView()
	{
		//TODO:
		//protected Button contextsClass;
		
		parameterValue.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.RA_PARAMETERVALUE_VIEW);
				
			}
		});//end  ClickListener
		
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
		
		Button loadParameterValues = new Button();
		loadParameterValues.addClickListener( new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				loadParameterValues();
			}
		});
		contentPanel.setContent(loadParameterValues);
	}
	
	private void loadParameterValues()
	{
		
		CBRInterface fl;
		try {
			fl = new CBRInterface(
					SystemHelper.PFAD + "/ctxModelAIM.flr",
					SystemHelper.PFAD + "/bc.flr", "AIMCtx",
					"SemNOTAMCase");
			fl.setDebug(false);
			List<String> parameters = fl.getParameters();
			
			Tree<String> tree = new Tree<>("Parameter Values");
			TreeData<String> data = new TreeData<>();
			for(String param : parameters){
				data.addItems(null, param);
				List<String[]> values = fl.getParameterValuesHiearchy(param);
				//TODO: WHAT THE FUCK????
				String s ="";
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
