package userViews;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;

import g4dke.app.MainUI;

public class User_InterestSpecView extends UserViews implements View{
	private static final long serialVersionUID = 1L;
	List<InterestSpecForGrid> interestList;
	Grid<InterestSpecForGrid> interestGrid;

	public User_InterestSpecView() {
		super(MainUI.USER_INTERESTSPEC);
		super.setTitle("User - InterestSpec View");
		
		initGrid();
	}
	
	private void initGrid() {
		loadInterestSpecAndAddToList();
		setGridItems();
		
		super.setContent(interestGrid);
	}
	
	
	private void loadInterestSpecAndAddToList(){
		interestList = new ArrayList<>();


		try {
			for (String[] f: fl.getInterestSpecClass()) {
				interestList.add(new InterestSpecForGrid(f[0], f[1]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void setGridItems() {
		interestGrid = new Grid<>();
		interestGrid.setItems(interestList);
		interestGrid.setSelectionMode(SelectionMode.NONE);
		interestGrid.addColumn(InterestSpecForGrid::getInterest).setCaption("Interest");
		interestGrid.addColumn(InterestSpecForGrid::getObj).setCaption("Object");
		
		interestGrid.getEditor().setEnabled(false);
		interestGrid.setSizeFull();

	}
	
	
	class InterestSpecForGrid {
		String interest;
		String obj;

		public InterestSpecForGrid(String interest, String obj) {
			this.interest = interest;
			this.obj = obj;
			
		}
		
		public String getInterest() {
			return this.interest;
		}
		
		public String getObj() {
			return this.obj;
		}
		
		public void setInterest(String interest) {
			this.interest = interest;
		}
		
		public void setObj(String obj) {
			this.obj = obj;
		}
		
		
	}
	

}
