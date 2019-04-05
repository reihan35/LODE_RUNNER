package projet.components;

import projet.services.Cell;
import projet.services.ScreenService;

public class Screen implements ScreenService {

	private int height;
	private int width;
	private Cell[][] cellNature;
	
	public Screen(){
		height = -1;
		width = -1;
		cellNature = null;
	}
	
	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public Cell getCellNature(int x, int y) {
		return cellNature[x][y];
	}

	@Override
	public void init(int h, int w) {
		height = h;
		width = w;
		cellNature = new Cell[w][h];
		for(int i=0; i<w-1; i++)
			for(int j=0; j<h-1; j++)
				cellNature[i][j] = Cell.EMP;
	}

	@Override
	public void dig(int x, int y) {
		cellNature[x][y] = Cell.HOL;

	}

	@Override
	public void fill(int x, int y) {
		cellNature[x][y] = Cell.PLT;

	}

}
