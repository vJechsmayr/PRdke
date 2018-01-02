package g4dke.app;

import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextArea;

import dke.pr.cli.CBRInterface;
import g4.templates.DomainExpertDesign;

public class DomainExpert_BusinessCaseView extends DomainExpertDesign implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// PFAD auf das Verzeichnis der Flora installation ändern
		// Philip D:\Users\Philip\Flora-2\flora2
		
		final static String PFAD = "D:/Users/Philip/Flora-2/flora2";
		
		Button showBCs = new Button("show Business Cases");
		TextArea BusinessCasesArea = new TextArea();
		
		public DomainExpert_BusinessCaseView() throws Exception {
			viewTitle.setValue("Domain Expert - Business Case View");
			
			initView();
			initBusinessCaseView();
			
		}
		
		private void initView() {
			
			contexts.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;
				
				@Override
				public void buttonClick(ClickEvent event) {
					getUI().getNavigator().navigateTo(MainUI.DE_CONTEXT_VIEW);
					
				}
			});//end contexts ClickListener
			
			contexts.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;
				
				@Override
				public void buttonClick(ClickEvent event) {
					getUI().getNavigator().navigateTo(MainUI.DE_BUSINESSCASE_VIEW);
					
				}
			});//end businessCase ClickListener
			
			logout.addClickListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					logout();
					getUI().getNavigator().navigateTo(MainUI.LOGIN_VIEW);
				}
			});//end logout ClickListener
			
		}
		
		private void initBusinessCaseView() throws Exception {
		showBCs.addClickListener(new Button.ClickListener() {
					
					/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							showBusinessCases();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
		contentPanel.setContent(showBCs);
			

		
		
		}
		
		
		private void showBusinessCases() throws Exception{
			
				
			
			CBRInterface fl = new CBRInterface(
					PFAD + "/ctxModelAIM.flr",
					PFAD + "/bc.flr", "AIMCtx",
					"SemNOTAMCase");

			fl.setDebug(false);

			System.out.println("Business Cases: " + fl.getCtxs());

			
			
			String value = new String();
				
			for (String x : fl.getCtxs()){
				value += x + "\t";	
			}

				BusinessCasesArea.setValue(value);
				BusinessCasesArea.setRows(25);
				
				contentPanel.setContent(BusinessCasesArea);
			
				
				
			fl.close();	
		}
		
		private void logout() {
			//toDO 
			
		}

}
