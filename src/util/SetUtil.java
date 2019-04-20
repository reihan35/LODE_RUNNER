package util;

import components.EditableScreen;
import contracts.EditableScreenContract;
import services.Cell;
import services.EditableScreenService;

public class SetUtil {

	public static boolean isIn(Cell obj, Cell[] my_set) {
		for(Cell e: my_set) {
			if(e == obj) {
				return true;
			}
		}
		return false;
	}
	
	public static EditableScreenContract MakeEdiatableScreen(int x,int y) {
		EditableScreenService s = new EditableScreen();
		EditableScreenContract scontrat = new EditableScreenContract(s);
		scontrat.init(x, y);
		for(int i = 0; i < x; i++) {
			scontrat.setNature(i, 0,Cell.MTL);
		}
		
		return scontrat;
	}
		
}
