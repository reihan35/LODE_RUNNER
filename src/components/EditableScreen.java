package components;

import services.Cell;
import services.EditableScreenService;

public class EditableScreen extends Screen implements EditableScreenService {

	@Override
	public boolean isPlayable() {
		for(int i = 0; i < getWidth(); i++)
			for(int j = 0; j < getHeight(); j++)
				if(getCellNature(i, j) == Cell.HOL)
					return false;
		for(int i = 0; i < getWidth(); i++)
			if(getCellNature(i, 0) != Cell.MTL)
				return false;
		return true;
	}

	@Override
	public void setNature(int i, int j, Cell c) {
		cellNature[i][j] = c;
	}
	


}
