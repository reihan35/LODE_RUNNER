package decorators;

import contracts.ScreenContract;
import services.Cell;
import services.EditableScreenService;

public class EditableScreenDecorator extends ScreenContract implements EditableScreenService {

		EditableScreenService delegates;

		public EditableScreenDecorator(EditableScreenService delegates) {
			super(delegates);
			this.delegates = delegates;
		}

		public boolean isPlayable() {
			return delegates.isPlayable();
		}

		@Override
		public void setNature(int i, int j, Cell c) {
			delegates.setNature(i, j, c);
			
		}

}
