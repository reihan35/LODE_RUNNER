package projet.decorators;

import projet.services.Cell;
import projet.services.EditableScreenService;

public class EditableScreenDecorator extends ScreenDecorator implements EditableScreenService {

		EditableScreenService delegates;

		public boolean isPlayable() {
			return delegates.isPlayable();
		}

		public void setNature(int i, int j, Cell c) {
			delegates.setNature(i, j, c);
		}

		public int getHeight() {
			return delegates.getHeight();
		}

		public int getWidth() {
			return delegates.getWidth();
		}

		public Cell getCellNature(int x, int y) {
			return delegates.getCellNature(x, y);
		}

		public void init(int h, int w) {
			delegates.init(h, w);
		}

		public void dig(int x, int y) {
			delegates.dig(x, y);
		}

		public void fill(int x, int y) {
			delegates.fill(x, y);
		}

}
