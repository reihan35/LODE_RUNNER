package test_implem_bugee;
import org.junit.Test;

import implem_bugee.*;
import contracts.EditableScreenContract;
import contracts.PreconditionError;
import services.Cell;
import services.EditableScreenService;

public class EditableScreenTest {

	//Les preconditions
	
	@Test(expected = PreconditionError.class)
	public void setNature_pre() {
		EditableScreenService s = new EditableScreen();
		EditableScreenContract scontrat = new EditableScreenContract(s);
		scontrat.init(3,5);
		scontrat.setNature(4, 5, Cell.HDR);
		assert scontrat.getCellNature(4, 5) == Cell.HDR;
	}
	
	@Test
	public void setNature_pre2() {
		EditableScreenService s = new EditableScreen();
		EditableScreenContract scontrat = new EditableScreenContract(s);
		scontrat.init(3,5);
		scontrat.setNature(2, 1, Cell.HDR);
		assert scontrat.getCellNature(2, 1) == Cell.HDR;
	}
	
}
