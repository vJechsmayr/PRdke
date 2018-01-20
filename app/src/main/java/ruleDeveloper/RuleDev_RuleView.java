package ruleDeveloper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;

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
	Grid<RulesForGrid> ruleGrid;
	CBRInterface fl = null;
	TextField ruleEditor = new TextField();
	
	Window addRuleWindow;
	FormLayout addRuleContent;
	
	
	public RuleDev_RuleView() throws Exception{
		
		viewTitle.setValue("Rule Developer - Rule View");
		initView();
		initInterface();
		loadRulesToList();
		setupRules();
		
		
	}
	
	private void initView() {
		initButtonsFromDesign();
		
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
				SystemHelper.lastPage = MainUI.RD_RULE_VIEW;
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
	
	private void loadRulesToList() throws Exception{
		contextList = fl.getCtxs();
	}
	
	private void initInterface() {
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
	
	private void loadRulesAndAddToList() throws Exception {
		ruleList = new ArrayList<>();
		
		fl = new CBRInterface(
				SystemHelper.PFAD + "/ctxModelAIM.flr",
				SystemHelper.PFAD + "/bc.flr", "AIMCtx",
				"SemNOTAMCase");
		
		fl.setDebug(false);
		
		for(String s: contextList){
			HashMap<String,String> rulesForContext = fl.getRules(s);
			for(String name: rulesForContext.keySet()) {
				String key = name.toString();
				String value = rulesForContext.get(key);//toString();
				ruleList.add(new RulesForGrid(s, key, value));
			}
		}
	}
	
	private void setGridItems() {
		ruleGrid = new Grid<>();
		ruleGrid.setItems(ruleList);
		ruleGrid.setSelectionMode(SelectionMode.MULTI);
		ruleGrid.addColumn(RulesForGrid::getContext).setEditorComponent(ruleEditor, RulesForGrid::setContext).setCaption("Context");
		ruleGrid.addColumn(RulesForGrid::getRuleKey).setEditorComponent(ruleEditor, RulesForGrid::setRuleKey).setCaption("Rule Key");
		ruleGrid.addColumn(RulesForGrid::getRuleValue).setEditorComponent(ruleEditor, RulesForGrid::setRuleValue).setCaption("Rule Value");
		ruleGrid.getEditor().setEnabled(true);
		ruleGrid.setSizeFull();
		
	}
	
	private void setupAddRuleWindow()
	{
		Collection<String> contextData = new ArrayList<String>();
		try {
			contextData = fl.getCtxs();
		} catch (IOException e1) {
		
			e1.printStackTrace();
		}
		
		
		ComboBox<String> contextCombo = new ComboBox<>("Select your Context", contextData);
		TextArea ruleText = new TextArea();
		Button saveRuleBtn = new Button("save Rule");
		
		addRuleWindow = new Window("Enter Data for a new Rule here");
		addRuleWindow.setWidth("800px");
		
		addRuleContent = new FormLayout();
		addRuleContent.setMargin(true);
		
		
		
		contextCombo.setPlaceholder("no Context selected");
		contextCombo.setEmptySelectionAllowed(false);
		contextCombo.setWidth("500px");
		
		ruleText.setCaption("Enter Rule here");
		ruleText.setWidth("500px");
		
		saveRuleBtn.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				
			
			try {
				//fl.addRule(context.getValue(), ruleText.getValue());
				//System.out.println(contextCombo.getSelectedItem().get());
				if(!contextCombo.getSelectedItem().isPresent())
				{
					Notification.show("No Context selected! Please select a Context!");
				}else {
					fl.addRule(contextCombo.getSelectedItem().get(), ruleText.getValue());
					Notification.show("Rule successfully saved!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				Notification.show("Error while saving Rule!");
			}
			
			}
		});
		
		addRuleContent.addComponent(contextCombo);
		
		addRuleContent.addComponent(ruleText);
		addRuleContent.addComponent(saveRuleBtn);
		
		addRuleWindow.setContent(addRuleContent);
		
	}
	
	private void setupRules() throws Exception
	{
		VerticalLayout layout = new VerticalLayout();
		HorizontalLayout buttonLayout = new HorizontalLayout();
		TextArea ruleField = new TextArea();
		
		Button addRuleBtn = new Button("add Rule");
		Button delRuleBtn = new Button("delete selected Rule(s)");
		
		initInterface();
		loadRulesAndAddToList();
		setGridItems();
		
		ruleField.setCaption("Enter Rule here");
		
		addRuleBtn.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				setupAddRuleWindow();
				getUI().addWindow(addRuleWindow);
			}
		});
		
		delRuleBtn.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				for( RulesForGrid rule : ruleGrid.getSelectedItems()) {
					try {
						if(!fl.delRule(rule.getContext(), rule.getRuleKey())) {
							Notification.show("An error occoured");
							fl.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}//end for
				
				layout.removeComponent(ruleGrid);
				initInterface();
				try {
					loadRulesAndAddToList();
				} catch (Exception e) {
					e.printStackTrace();
				}
				setGridItems();
				layout.addComponent(ruleGrid);
				
			}
		});
		
		
		buttonLayout.addComponent(addRuleBtn);
		buttonLayout.addComponent(delRuleBtn);
		
		layout.addComponent(buttonLayout);
		layout.addComponent(ruleGrid);
		contentPanel.setContent(layout);
		
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