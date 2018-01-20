package userViews;

import java.io.IOException;
import com.vaadin.navigator.View;
import g4dke.app.MainUI;

public class User_NotamView extends UserViews implements View{
	private static final long serialVersionUID = 1L;
		
	public User_NotamView() {
		super(MainUI.USER_NOTAMS);
		super.initInterface();
		super.setTitle("User - NOTAM View");
		
		
		
		//printNOTAMS();
		//printInterestSpec();
	}
	
	private void printInterestSpec() {
		try {
			for (String[] f: fl.getInterestSpecClass()) {
				for(int i=0; i<f.length;i++)
				{
					System.out.println("- " + f[i]);
				}
				System.out.println("\n  ");
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void printNOTAMS() {
		
		try {
			for(String f : fl.getNOTAMS()) {
				System.out.println("Notam: " + f);
				for(String[] n : fl.getNOTAMInfo(f)) {
					for(int i=0; i<n.length; i++)
					{
						System.out.println("-- Info: " + n[i]);
					}
					
					
				}
				
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}//end printNOTAMS
	
}
