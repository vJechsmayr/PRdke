package ruleDeveloper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid.SelectionMode;

import dke.pr.cli.CBRInterface;
import g4.templates.RuleDeveloperDesign;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;

/*
 * @author Viktoria J.
 * 
 * */
public class RuleDev_RuleView extends RuleDeveloperDesign implements View{
	private static final long serialVersionUID = 1L;
	
	List<String> contextList = new ArrayList<String>(); 
	List<RulesForGrid> ruleList;
	
	public RuleDev_RuleView() throws Exception{
		
		viewTitle.setValue("Rule Developer - Rule View");
		initView();
		
		showRules();
		
		
	}
	
	private void initView() {
		initButtonsFromDesign();
		
		
		Button loadRules = new Button("load Rules");
		loadRules.addClickListener( new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					loadRules();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		contentPanel.setContent(loadRules);
		
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
	
	private void showRules() throws Exception{
		CBRInterface fl = new CBRInterface(
				SystemHelper.PFAD + "/ctxModelAIM.flr",
				SystemHelper.PFAD + "/bc.flr", "AIMCtx",
				"SemNOTAMCase");

		fl.setDebug(false);
		contextList = fl.getCtxs();
		
		rules2Console();
		fl.close();
		
		
	}
	
	//wird nicht benötigt - nur zur ausgabe der Daten in der Konsole!
	private void rules2Console() throws Exception{
		CBRInterface fl = new CBRInterface(
				SystemHelper.PFAD + "/ctxModelAIM.flr",
				SystemHelper.PFAD + "/bc.flr", "AIMCtx",
				"SemNOTAMCase");

		fl.setDebug(false);

		//--------------
		//System.out.println("Context: ");
		for(String s: contextList){
			HashMap<String,String> rulesForContext = fl.getRules(s);
			//System.out.println("\n ---Rules für " + s + ": \n" + rulesForContext);
			
			for(String name: rulesForContext.keySet()) {
				String key = name.toString();
				String value = rulesForContext.get(key);//toString();
				System.out.println("\n Context: " + s + "\n Key: " + key + "\n Value:" + value);
				System.out.println("\n------");
				
				
			}
		}
		//--------------------------
		
		//ToDO: Grid
		
		
		
		
		
		
		
		
		fl.close();
	}
	
	private void loadRules() throws Exception
	{
		try {
			VerticalLayout layout = new VerticalLayout();
			CBRInterface fl = new CBRInterface(
					SystemHelper.PFAD + "/ctxModelAIM.flr",
					SystemHelper.PFAD + "/bc.flr", "AIMCtx",
					"SemNOTAMCase");
			
			fl.setDebug(false);
			//Grid needs Bean Class. Cannot use only String
			//List<String> parameters = fl.getParameters();
			ruleList = new ArrayList<>();
			
			for(String s: contextList){
				HashMap<String,String> rulesForContext = fl.getRules(s);
				//System.out.println("\n ---Rules für " + s + ": \n" + rulesForContext);
				
				for(String name: rulesForContext.keySet()) {
					String key = name.toString();
					String value = rulesForContext.get(key);//toString();
					System.out.println("\n Context: " + s + "\n Key: " + key + "\n Value:" + value);
					System.out.println("\n------");
					
					ruleList.add(new RulesForGrid(s, key, value));
				}
			}
			
			
			TextField paramEditor = new TextField();
			Grid<RulesForGrid> ruleGrid = new Grid<>();
			ruleGrid.setItems(ruleList);
			ruleGrid.setSelectionMode(SelectionMode.NONE);
			ruleGrid.addColumn(RulesForGrid::getContext).setEditorComponent(paramEditor, RulesForGrid::setContext).setCaption("Context");
			ruleGrid.addColumn(RulesForGrid::getRuleKey).setEditorComponent(paramEditor, RulesForGrid::setRuleKey).setCaption("Rule Key");
			ruleGrid.addColumn(RulesForGrid::getRuleValue).setEditorComponent(paramEditor, RulesForGrid::setRuleValue).setCaption("Rule Value");
			ruleGrid.getEditor().setEnabled(true);
			ruleGrid.setSizeFull();
			
			Button saveBtn = new Button("save");
			saveBtn.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					//TODO: save changes
				}
			});
			layout.addComponent(saveBtn);
			layout.addComponent(ruleGrid);
			contentPanel.setContent(layout);
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class RulesForGrid
	{
		String ruleKey;
		String ruleValue;
		String context;
		
		public RulesForGrid(String c, String rK, String rV)
		{
			this.ruleKey = rK;
			this.context = c;
			this.ruleValue = rV;
		}

		public String getRuleKey() {
			return ruleKey;
		}

		public void setRuleKey(String ruleKey) {
			this.ruleKey = ruleKey;
		}
		
		public String getRuleValue() {
			return ruleValue;
		}

		public void setRuleValue(String ruleValue) {
			this.ruleValue = ruleValue;
		}
		
		public String getContext() {
			return context;
		}
		
		public void setContext(String context) {
			this.context = context;			
		}
		
		
	}

}