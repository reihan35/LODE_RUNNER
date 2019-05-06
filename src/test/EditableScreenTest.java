package test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.EditableScreen;
import components.Screen;
import contracts.EditableScreenContract;
import contracts.PreconditionError;
import contracts.ScreenContract;
import services.Cell;
import services.EditableScreenService;
import services.ScreenService;

public class EditableScreenTest {

	//Les preconditions
	
	@Test(expected = PreconditionError.class)
	public void setNature_pre() {
		EditableScreenService s = new EditableScreen();
		EditableScreenContract scontrat = new EditableScreenContract(s);
		scontrat.init(3,5);
		scontrat.setNature(4, 5, Cell.HDR);
	}
	
	@Test
	public void setNature_pre2() {
		EditableScreenService s = new EditableScreen();
		EditableScreenContract scontrat = new EditableScreenContract(s);
		scontrat.init(3,5);
		scontrat.setNature(2, 1, Cell.HDR);
	}
	
}
