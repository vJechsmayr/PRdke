package ruleDeveloper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import dke.pr.cli.CBRInterface;
import g4.templates.RuleDeveloperDesign;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;

/**
 * @author Viktoria J.
 * 
 *         ContextView-Code copied to RepositoryAdmin_ContextView @Thomas
 * 
 */
public class RuleDev_ContextView extends RuleDeveloperViews implements View {

	private static final long serialVersionUID = 1L;

	VerticalLayout layout;
	Button showCtx = new Button("show Context");
	TextArea contextArea = new TextArea();

	public RuleDev_ContextView() throws Exception {
		super(MainUI.RD_CONTEXT_VIEW);
		super.setTitle("Rule Developer - Context View");

		showContexts();
	}


	/**
	 * @author Viktoria J.
	 * 
	 *         edited by @Marcel G.
	 */
	private void showContexts() {

		try {

			layout = new VerticalLayout();
			// drawTreeH(fl.getCtxs(),fl.getCtxHierarchy());

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
					
					String selectedContext = null;
					if(select.getSelectedItem().isPresent()) {
						selectedContext = select.getSelectedItem().get();
					}
					
					initCtxHierachy(selectedContext);
				}
			});
			layout.addComponent(select);
			super.setContent(layout);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Marcel G.
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

	/**
	 * @author Marcel G. Build list of children or parents of given context
	 */
	private List<Context> getContexts(String context, boolean isGetParents) {
		List<Context> list = new ArrayList<Context>();

		if (context != null) {
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
		}

		return list;
	}

	/**
	 * @author: Marcel G.
	 */
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
