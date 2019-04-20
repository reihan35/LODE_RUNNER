package components;

import services.Cell;
import services.EditableScreenService;

public class EditableScreen extends Screen implements EditableScreenService {

	@Override
	public void setNature(int i, int j, Cell c) {
		cellNature[i][j] = c;
	}
	


}
