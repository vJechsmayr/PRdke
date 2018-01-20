package domainExpert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;
import userDatabase.OperationPosition;


/**
 * @author Viktoria J.
 * 
 * Liste Context 										OK
 * Anfrage new Context -> RepAdmin						OK
 * Anfrage update Context -> RepAdmin (nice to have)    -
 * Anfrage delete Context -> RepAdmin    				OK
 * 
 * Test - SystemMessages sent?!
 * 
 * */

public class DomainExpert_ContextView extends DomainExpertViews implements View {
	private static final long serialVersionUID = 1L;
	
	VerticalLayout layout;
	HorizontalLayout buttonLayout;
	TextField contextName = new TextField();
	
	Button deleteCtx;
	Button newCtx;
	ComboBox<String> select;
	List<String> contextStringList;
	List<Context> contextList;
	
	Grid<Context> parents;
	Grid<Context> children;
	
	public DomainExpert_ContextView(){
		super(MainUI.DE_CONTEXT_VIEW);
		super.setTitle("Domain Expert - Context View");
		super.initInterface();
		
		showContexts();
	}
	
	
	/*
	 * @author Viktoria J.
	 * 
	 * edited by @Marcel G.
	 * */
	 private void showContexts()  {
	
		try {
			
			layout = new VerticalLayout();
			buttonLayout = new HorizontalLayout();
			initInterface();
			
			deleteCtx = new Button("Delete selected Context");
			newCtx = new Button("New Child Context");
			select = new ComboBox<>();
			contextStringList = fl.getCtxs();
			
			select.setItems(contextStringList);
			select.setSizeFull();
			select.addSelectionListener(new SingleSelectionListener<String>() {
				private static final long serialVersionUID = 1L;

				@Override
				public void selectionChange(SingleSelectionEvent<String> event) {
					layout.removeAllComponents();
					layout.addComponent(select);
					layout.addComponent(buttonLayout);
					
					//layout.addComponent(deleteCtx);
					//layout.addComponent(newCtx);

					initCtxHierachy(select.getSelectedItem().get().toString());
				}

				
			});
			
			
			
			deleteCtx.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
										
					if(select.getSelectedItem().isPresent()) {
						OperationPosition op = SystemHelper.isComposedOperationsStarted(SystemHelper.COM_DELETE_CONTEXT, "", "", select.getSelectedItem().get());
						
						if(op == null) {
							op = SystemHelper.DeleteContext(select.getSelectedItem().get());
						}else {
							Notification.show("Delete Context already running - Please wait for Response!");
							
						}
					}else {
						Notification.show("Please select a Context to delete!");
						
					}
					

				}//end ButtonClick deleteCtx
			});
			
			newCtx.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					
					if(select.getSelectedItem().isPresent()) {
						if(contextName.getValue() != null && contextName.getValue() != "") {
							
							OperationPosition op = SystemHelper.isComposedOperationsStarted(SystemHelper.COM_NEW_CONTEXT, "", "", contextName.getValue());
							
							if(op == null) {
								op = SystemHelper.NewContext(contextName.getValue(), select.getSelectedItem().get());
								Notification.show("SystemMessage sent - Please wait for Response!");
							}else {
								Notification.show("New Context already running - Please wait for Response!");
							}
						}else {
							Notification.show("Please enter a Context Name!");
						}
					}else {
						Notification.show("Please select a Parent Context!");
					}
				}
			});
			
			contextName.setPlaceholder("Enter new Context Name here");
			contextName.setWidth("300px");
			
			layout.addComponent(select);
			buttonLayout.addComponent(deleteCtx);
			
			buttonLayout.addComponent(contextName);
			buttonLayout.addComponent(newCtx);
			
			//layout.addComponent(deleteCtx);
			//layout.addComponent(newCtx);
			//layout.addComponent(contextName);
			layout.addComponent(buttonLayout);
			super.setContent(layout);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	 
	 /*
	  * @author Marcel G.
	  * 
	  * edit by Viktoria J.
	  * */
	 private void initCtxHierachy(String selectedContext) {
			
		 parents = new Grid<>();
		 parents.setItems(getContexts(selectedContext, true));
		 parents.addColumn(Context::getValue).setCaption("Parent");
		 parents.setSizeFull();
		 layout.addComponent(parents);
		 
		 children = new Grid<>();
		 children.setItems(getContexts(selectedContext, false));
		 children.addColumn(Context::getValue).setCaption("Children");
		 children.setSizeFull();
		 layout.addComponent(children);
			
		}
	 
	 /*
	  * @author Marcel G.
	  * 
	  * edit by Viktoria J.
	  * Build list of children or parents of given context
	  * */
	 private List<Context> getContexts(String context, boolean isGetParents)
	 {
		 contextList = new ArrayList<Context>();
		 try {
			for(String[] pair : fl.getCtxHierarchy())
			 {
				 if(isGetParents && pair[0].equals(context))
					 contextList.add(new Context(pair[1]));
				 else if(!isGetParents && pair[1].equals(context))
				 {
					 contextList.add(new Context(pair[0]));
				 }
			 }
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		 
		 return contextList;
	 }
	 
	 /*
	  * @author Marcel G.
	  * 
	  * edit by Viktoria J.
	  * */
	 private void addContext()
	 {
		 //ToDo
	 }

	 class Context{
		 private String value;
		 
		 public Context(String v)
		 {
			 this.value=v;
		 }

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		 
		 @Override
		public String toString() {
			return this.getValue();
		}
	 }
	
	
	
}
