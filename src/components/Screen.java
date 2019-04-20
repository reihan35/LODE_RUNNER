package components;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import services.Cell;
import services.ScreenService;

public class Screen implements ScreenService {

	protected int height;
	protected int width;
	protected Cell[][] cellNature;
	
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
		System.out.println();
		return cellNature[x][y];
	}

	@Override
	public void init(int w, int h) {
		height = h;
		width = w;
		cellNature = new Cell[w][h]; 
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				cellNature[i][j] = Cell.EMP;
			}
		}
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
