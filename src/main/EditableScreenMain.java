package main;

import components.EditableScreen;
import components.Screen;
import contracts.EditableScreenContract;
import contracts.ScreenContract;
import services.Cell;
import services.EditableScreenService;
import services.ScreenService;

public class EditableScreenMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EditableScreenService e = new EditableScreen();
		EditableScreenService econtrat = new EditableScreenContract(e);
		econtrat.init(2, 2);
		econtrat.setNature(0, 0, Cell.MTL);
		econtrat.setNature(1, 0, Cell.MTL);
		System.out.println(econtrat.isPlayable());

	}

}
