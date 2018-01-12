package domainExpert;

import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextArea;

import dke.pr.cli.CBRInterface;
import g4.templates.DomainExpertDesign;
import g4dke.app.MainUI;
import g4dke.app.SystemHelper;

/*
 * @author Philip H.
 * 
 * */


public class DomainExpert_BusinessCaseClassView extends DomainExpertDesign implements View {
	
	final static String PFAD = SystemHelper.PFAD;
	
	Button showBCCs = new Button("show Business Case Classes");
	TextArea BCCsArea = new TextArea();
	
	public DomainExpert_BusinessCaseClassView() throws Exception {
		viewTitle.setValue("Domain Expert - Business Case Class View");
		
		initView();
		//Old Code:
		//initBusinessCaseClassView();
		
	}
	
	private void initView() {
		
		initButtonsFromDesign();
		
		showBCCs.addClickListener(new Button.ClickListener() {
			
			
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
		contentPanel.setContent(showBCCs);
		
	}
	
	/*
	 * initButtonsFromDesign()
	 * author: Viktoria
	 */
	private void initButtonsFromDesign() {
		
		businessCase.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.DE_BUSINESSCASE_VIEW);
			}
		});
		
		businessCaseClass.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(MainUI.DE_BUSINESSCASECLASS_VIEW);
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
/*
	private void initBusinessCaseClassView() throws Exception {
		showBCCs.addClickListener(new Button.ClickListener() {
					
		
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
		contentPanel.setContent(showBCCs);
		
		}
	
		*/
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

			BCCsArea.setValue(value);
			BCCsArea.setRows(25);
				
			contentPanel.setContent(BCCsArea);
				
			fl.close();	
			
		}
		

}
