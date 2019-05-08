package implem_bugee;

import services.Cell;
import services.EditableScreenService;

public class EditableScreen extends Screen implements EditableScreenService {

	@Override
	//bug rajouté 
	public void setNature(int i, int j, Cell c) {
		cellNature[i][i] = c;
	}
	


}
