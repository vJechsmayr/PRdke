package repositoryAdmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

import composedOperations.DeleteParameter;
import composedOperations.SplitContext;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import g4dke.app.MainUI;
import g4dke.app.SystemHelper;
import userDatabase.DBValidator;
import userDatabase.OperationPosition;

/*
 * @author Thomas
 * 
 * */
public class RepositoryAdmin_ContextView extends RepositoryAdminViews implements View {

	private static final long serialVersionUID = 1L;

	// Button showCtx = new Button("show Context");
	TextArea contextArea = new TextArea();
	VerticalLayout layout;
	List<ComboBox<String>> boxes = new ArrayList<>();
	public RepositoryAdmin_ContextView() throws Exception {
		super(MainUI.RA_CONTEXT_VIEW);
		super.setTitle("RepositoryAdmin - ContextView");

		showContexts();

	}

	/*
	 * @author Viktoria J.
	 * 
	 * edited by @Marcel G. & @Thomas L.
	 */
	private void showContexts() {

		try {

			layout = new VerticalLayout();

			// drawTreeH(fl.getCtxs(),fl.getCtxHierarchy());
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
					if (select.getSelectedItem() != null) {
						String ctx = select.getSelectedItem().get();

						try {

							if (!fl.delCtx(ctx, true)) {
								Notification.show("An Error occoured");
							} else {
								fl.restart();
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
					content.addComponent(field);
					
					try {
						for(String param : fl.getParameters())
						{
							
							List<String[]> values = fl.getParameterValuesHiearchy(param);
							ComboBox<String> cb = new ComboBox<>(param);
							List<String> list = new ArrayList<>();
							for(String[] value : values)
							{
								if(!list.contains(value[0]))
								{
									list.add(value[0]);
								}
								if(!list.contains(value[1]))
								{
									list.add(value[1]);
								}
							}
							cb.setItems(list);
							content.addComponent(cb);
							boxes.add(cb);
					
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					
					Button addBtn = new Button("Add", new Button.ClickListener() {

						private static final long serialVersionUID = 1L;

						@Override
						public void buttonClick(ClickEvent event) {
							try {
								String user = System.getProperty("user.name");
								if(!fl.addCtx(
										field.getValue() + ":AIMCtx[" + params() +
										"file->'C:/Users/" + user + "/Flora-2/flora2/Contexts/" +
												field.getValue()+".flr'].",
												"C:/Users/"+user+ "/Flora-2/flora2/Contexts/" + field.getValue() + ".flr"
										))
								{
									
									Notification.show("An Error occoured");
								}
								else
									fl.restart();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							window.close();
						}
					});

					content.addComponent(addBtn);
					window.setContent(content);
					getUI().getUI().addWindow(window);

				}
			});

			splitCtx.addClickListener(new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					
						
						Window window = new Window("New Context");
						window.setWidth(500.0f, Unit.PIXELS);
						FormLayout content = new FormLayout();
						content.setMargin(true);
						ComboBox<String> oldctx = new ComboBox<>("old ctx");
						ComboBox<String> newctx = new ComboBox<>("new ctx");
						oldctx.setItems(contexts);
						newctx.setItems(contexts);
						content.addComponent(oldctx);
						content.addComponent(newctx);
						Button split = new Button("Split now");
						split.addClickListener(new Button.ClickListener() {
							
							@Override
							public void buttonClick(ClickEvent event) {
								String octx = oldctx.getSelectedItem().get();
								String nctx = newctx.getSelectedItem().get();
								OperationPosition op = SystemHelper.isComposedOperationsStarted(
										SystemHelper.COM_SPLIT_CONTEXT, "", "", octx);
								// SystemMessage
								if (op == null) {

									op = SystemHelper.SplitContext(octx,nctx);
									Notification.show("Rule Developer has been messaged");

								} else {
									
								}
							
								
								window.close();
							}
						});
						content.addComponent(split);
						window.setContent(content);
						getUI().getUI().addWindow(window);
						

				}	
			});

			layout.addComponent(select);
			layout.addComponent(deleteCtx);
			layout.addComponent(newCtx);
			layout.addComponent(splitCtx);
			// contentPanel.setContent(layout);
			super.setContent(layout);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * @author& @Thomas L.
	 * edited by @Marcel G.
	 */
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
	 * @author Thomas L. Build list of children or parents of given context
	 * edited by @Marcel G.
	 */
	private List<Context> getContexts(String context, boolean isGetParents) {
		List<Context> list = new ArrayList<Context>();
		try {
			for (String[] pair : fl.getCtxHierarchy()) {
				if (isGetParents && pair[0].equals(context))
					list.add(new Context(pair[1]));
				else if (!isGetParents && pair[1].equals(context)) {
					list.add(new Context(pair[0]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return list;
	}
	
	private String params()
	{
		String s ="";
		for(ComboBox cbBox : boxes)
		{
			s = s+ cbBox.getCaption() + "->" + cbBox.getSelectedItem().get().toString() +",";
		}
		
		return s;
	}

	/*
	 * @author Thomas L.
	 */
	private void addContext() {

	}

	class Context {
		private String value;

		public Context(String v) {
			this.value = v;
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
