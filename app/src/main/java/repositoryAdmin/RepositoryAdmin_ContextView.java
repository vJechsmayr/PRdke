package repositoryAdmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.naming.Context;

import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;

import dke.pr.cli.CBRInterface;
import g4.templates.RepositoryAdminDesign;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;
import userDatabase.DBValidator;
import userDatabase.Message;
import userDatabase.SystemUser;

/*
 * @author Thomas
 * 
 * */
public class RepositoryAdmin_ContextView extends RepositoryAdminViews implements View {

	private static final long serialVersionUID = 1L;

	//Button showCtx = new Button("show Context");
	TextArea contextArea = new TextArea();
	VerticalLayout layout;
	
	public RepositoryAdmin_ContextView() throws Exception {
		super(MainUI.RA_CONTEXT_VIEW);
		super.setTitle("RepositoryAdmin - ContextView");
	
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
			
			//drawTreeH(fl.getCtxs(),fl.getCtxHierarchy());
			Button deleteCtx = new Button("Delete");
			Button newCtx = new Button("New Context");
			Button splitCtx = new Button("Split Context");
			ComboBox<String> select = new ComboBox<>();
			List<String> contexts = fl.getCtxs();
			select.setItems(contexts);
			select.setSizeFull();
			select.addSelectionListener(new SingleSelectionListener<String>() {

				private static final long serialVersionUID = 1L;

				@Override
				public void selectionChange(SingleSelectionEvent<String> event) {
					layout.removeAllComponents();
					layout.addComponent(select);
					layout.addComponent(deleteCtx);
					layout.addComponent(newCtx);
					layout.addComponent(splitCtx);
					initCtxHierachy(select.getSelectedItem().get().toString());
				}

				
			});
			
			
			
			deleteCtx.addClickListener(new Button.ClickListener() {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					if(select.getSelectedItem()!=null)
					{
						String ctx = select.getSelectedItem().get();
						
						try {
							//TODO: check if really true...
							if(!fl.delCtx(ctx, true))
							{
								Notification.show("An Error occoured");
							}
							else
							{
								layout.removeAllComponents();
								showContexts();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}
			});
			
			newCtx.addClickListener(new Button.ClickListener() {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					Window window = new Window("New Context");
					window.setWidth(500.0f, Unit.PIXELS);
					FormLayout content = new FormLayout();
					content.setMargin(true);


					TextField field = new TextField();
					field.setCaption("");
					field.setWidth(200.0f, Unit.PIXELS);
					field.setHeight(200.0f, Unit.PIXELS);

					Button addBtn = new Button("Add", new Button.ClickListener() {
					
						private static final long serialVersionUID = 1L;

						@Override
						public void buttonClick(ClickEvent event) {
							//TODO
							window.close();
						}
					});

					content.addComponent(field);
					content.addComponent(addBtn);
					window.setContent(content);
					getUI().getUI().addWindow(window);
					
				}
			});
			
			splitCtx.addClickListener(new Button.ClickListener() {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					
					Window window = new Window("Split Context");
					window.setWidth(500.0f, Unit.PIXELS);
					FormLayout content = new FormLayout();
					content.setMargin(true);


					TextField field = new TextField();
					field.setCaption("New Name");
					field.setWidth(200.0f, Unit.PIXELS);
					field.setHeight(200.0f, Unit.PIXELS);

					Button split = new Button("Split", new Button.ClickListener() {
						
						private static final long serialVersionUID = 1L;

						@Override
						public void buttonClick(ClickEvent event) {
							String ctx = select.getSelectedItem().get();
							List<Context> parents = getContexts(ctx, true);
							//TODO
							SystemHelper.SplitContext(field.getValue());
							window.close();
						}
					});

					content.addComponent(field);
					content.addComponent(split);
					window.setContent(content);
					getUI().getUI().addWindow(window);
				}
			});
			
			layout.addComponent(select);
			layout.addComponent(deleteCtx);
			layout.addComponent(newCtx);
			layout.addComponent(splitCtx);
			//contentPanel.setContent(layout);
			super.setContent(layout);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	 
	 /*
	  * @author Marcel G.
	  * */
	 private void initCtxHierachy(String selectedContext) {
			
		 Grid<Context> parents = new Grid<>();
		 parents.setItems(getContexts(selectedContext, true));
		 parents.addColumn(Context::getValue).setCaption("Parent");
		 parents.setSizeFull();
		 layout.addComponent(parents);
		 
		 Grid<Context> children = new Grid<>();
		 children.setItems(getContexts(selectedContext, false));
		 children.addColumn(Context::getValue).setCaption("Children");
		 children.setSizeFull();
		 layout.addComponent(children);
			
		 
		}
	 
	 /*
	  * @author Marcel G.
	  * Build list of children or parents of given context
	  * */
	 private List<Context> getContexts(String context, boolean isGetParents)
	 {
		 List<Context> list = new ArrayList<Context>();
		 try {
			for(String[] pair : fl.getCtxHierarchy())
			 {
				 if(isGetParents && pair[0].equals(context))
					 list.add(new Context(pair[1]));
				 else if(!isGetParents && pair[1].equals(context))
				 {
					 list.add(new Context(pair[0]));
				 }
			 }
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		 
		 return list;
	 }
	 
	 /*
	  * @author Marcel G.
	  * */
	 private void addContext()
	 {
		 
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
