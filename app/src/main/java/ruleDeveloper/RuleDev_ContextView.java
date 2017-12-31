package ruleDeveloper;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Tree;

import dke.pr.cli.CBRInterface;
import g4.templates.RuleDeveloperDesign;
import g4dke.app.MainUI;

/*
 * @author Viktoria J.
 * 
 * */
public class RuleDev_ContextView extends RuleDeveloperDesign implements View{
	private static final long serialVersionUID = 1L;
	
	// PFAD auf das Verzeichnis der Flora installation ändern
	//Viktoria C:/Users/vikto/Flora-2/flora2
	//
	final static String PFAD = "C:/Users/vikto/Flora-2/flora2";
	Button showCtx = new Button("show Context");
	TextArea contextArea = new TextArea();

	public RuleDev_ContextView() throws Exception {
		viewTitle.setValue("Rule Developer - Context View");
		
		initView();
		initContextView();
		
		
		

		
		
		
	}
	
	private void initView() {
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
		
		logout.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				logout();
				getUI().getNavigator().navigateTo(MainUI.LOGIN_VIEW);
			}
		});//end logout ClickListener
		
	}
	
	private void initContextView() throws Exception {
	showCtx.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					try {
						showContexts();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
	contentPanel.setContent(showCtx);
		

	
	
	}
	
	
	private void showContexts() throws Exception{
		
			
		
		CBRInterface fl = new CBRInterface(
				PFAD + "/ctxModelAIM.flr",
				PFAD + "/bc.flr", "AIMCtx",
				"SemNOTAMCase");

		fl.setDebug(false);

		System.out.println("Contexts: " + fl.getCtxs());
		System.out.print("\nCtx Hier: ");
		for (String[] strings : fl.getCtxHierarchy()) {
			System.out.print(Arrays.toString(strings) + "; ");
		}
		
		
		String value = new String();
			
		for (String x : fl.getCtxs()){
			value += x + "\t";	
		}

			contextArea.setValue(value);
			contextArea.setRows(25);
			
			//contentPanel.setContent(contextArea);
			//drawTree(fl.getCtxs());
			drawTreeH(fl.getCtxHierarchy());
		
		
			
			
		fl.close();	
	}
	private void drawTreeH(List<String[]> list) {
		
		Tree<String> tree = new Tree<>("Contexts");
		TreeData<String> data = new TreeData<>();
		
		for (String[] strings : list) {
			//System.out.print(Arrays.toString(strings) + "; ");
			data.addItems(null,Arrays.toString(strings));
			
		}
		
		
		
		tree.setDataProvider(new TreeDataProvider<>(data));
		contentPanel.setContent(tree);
		
	}
	
	
	private void drawTree(List<String> elem) {
		
		Tree<String> tree = new Tree<>("Contexts");
		TreeData<String> data = new TreeData<>();
		
		for(String s : elem) {
			data.addItems(null, s);
			
		}
		
		tree.setDataProvider(new TreeDataProvider<>(data));
		contentPanel.setContent(tree);
		
	}
	
	private void logout() {
		//toDO 
		
	}
}
