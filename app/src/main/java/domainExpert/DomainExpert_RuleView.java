package domainExpert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid.SelectionMode;

import g4dke.app.MainUI;
import g4dke.app.SystemHelper;
import userDatabase.OperationPosition;

/**
 * @author Viktoria J.
 * 
 * Liste aller Rules
 * Anfrage update Rule -> RuleDev (nice to have)
 * Anfrage neue Rule -> RuleDev
 * Anfrage delete Rule -> RuleDev
 * Anfrage contextualise Rule -> RuleDev
 * 
 * */
public class DomainExpert_RuleView extends DomainExpertViews implements View {
	private static final long serialVersionUID = 1L;
	
	List<String> contextList = new ArrayList<String>();
	List<RulesForGrid> ruleList;
	Grid<RulesForGrid> ruleGrid;
	TextField ruleEditor = new TextField();

	Window addRuleWindow;
	FormLayout addRuleContent;
	
	public DomainExpert_RuleView(){
		super(MainUI.DE_RULE_VIEW);
		super.setTitle("Domain Expert - Rule View");
		super.initInterface();
		
		try {
			loadRulesToList();
			setupRules();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadRulesToList() throws Exception {
		contextList = fl.getCtxs();
	}

	private void loadRulesAndAddToList() throws Exception {
		ruleList = new ArrayList<>();


		super.initInterface();
		
		for (String s : contextList) {
			HashMap<String, String> rulesForContext = fl.getRules(s);
			for (String name : rulesForContext.keySet()) {
				String key = name.toString();
				String value = rulesForContext.get(key);
				ruleList.add(new RulesForGrid(s, key, value));
			}
		}
	}

	private void setGridItems() {
		ruleGrid = new Grid<>();
		ruleGrid.setItems(ruleList);
		ruleGrid.setSelectionMode(SelectionMode.MULTI);
		ruleGrid.addColumn(RulesForGrid::getContext).setEditorComponent(ruleEditor, RulesForGrid::setContext)
				.setCaption("Context");
		ruleGrid.addColumn(RulesForGrid::getRuleKey).setEditorComponent(ruleEditor, RulesForGrid::setRuleKey)
				.setCaption("Rule Key");
		ruleGrid.addColumn(RulesForGrid::getRuleValue).setEditorComponent(ruleEditor, RulesForGrid::setRuleValue)
				.setCaption("Rule Value");
		ruleGrid.getEditor().setEnabled(true);
		ruleGrid.setSizeFull();

	}

	private void setupAddRuleWindow() {
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
					if (!contextCombo.getSelectedItem().isPresent()) {
						Notification.show("No Context selected! Please select a Context!");
					} else {
						
						if(ruleText.getValue() == null || ruleText.getValue().equals("")) {
							Notification.show("Please enter a Rule Text!");
						}else {
							
							OperationPosition op = SystemHelper.isComposedOperationsStarted(SystemHelper.COM_NEW_RULE, "", ruleText.getValue(), contextCombo.getSelectedItem().get());
							
							if(op == null) {
								op = SystemHelper.AddRule(contextCombo.getSelectedItem().get(), ruleText.getValue());
								Notification.show("SystemMessage sent - Please wait for Response!");
							}else {
								Notification.show("Add Rule already running - Please wait for Response!");
								
							}
						}
						
						
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

	private void setupRules() throws Exception {
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
				
				
						if(ruleGrid.getSelectedItems().isEmpty()) {
							Notification.show("Please select a Rule to delete!");
							
						}else {
							for(RulesForGrid r : ruleGrid.getSelectedItems()){
								
								OperationPosition op = SystemHelper.isComposedOperationsStarted(SystemHelper.COM_DELETE_RULE, "", r.getRuleKey(), r.getContext());

								if(op == null) {
									op = SystemHelper.DeleteRule(r.getContext(), r.getRuleKey());
								}
							}
							Notification.show("SystemMessage sent - Please wait for Response!");
							
						}

			}
		});

		buttonLayout.addComponent(addRuleBtn);
		buttonLayout.addComponent(delRuleBtn);

		layout.addComponent(buttonLayout);
		layout.addComponent(ruleGrid);
		super.setContent(layout);

	}

	class RulesForGrid {
		String ruleKey;
		String ruleValue;
		String context;

		public RulesForGrid(String c, String rK, String rV) {
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
