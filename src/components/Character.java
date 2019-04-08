package components;


import services.Cell;
import services.CharacterService;
import services.EnvironmentService;
import util.SetUtil;

public class Character implements CharacterService {

	private EnvironmentService envi;
	private int hgt, wdt;
	@Override
	public EnvironmentService getEnvi() {
		return envi;
	}

	@Override
	public int getHgt() {
		return hgt;
	}

	@Override
	public int getWdt() {
		return wdt;
	}
	

	@Override
	public boolean characterAt(int x, int y) {
		// TODO Auto-generated method stub
		return getEnvi().getCellContentChar(x, y).size() > 0;
	}

	@Override
	public boolean isFreeCell(int x, int y) {
		Cell currCell = getEnvi().getCellNature(x, y);
		Cell[] free = {Cell.LAD, Cell.EMP, Cell.HDR, Cell.HOL};
		if(SetUtil.isIn(currCell, free)) {
			return true;
		}
		return false;
	}

	@Override
	public void init(EnvironmentService s, int x, int y) {
		this.envi = s;
		this.hgt = y;
		this.wdt = x;

	}

	@Override
	public void goLeft() {
		Cell currCell = getEnvi().getCellNature(wdt, hgt);
		Cell downCell = getEnvi().getCellNature(wdt, hgt-1);
		if(wdt != 0
			&& isFreeCell(wdt-1, hgt)
			&& ((currCell == Cell.LAD || currCell == Cell.HDR)
				|| (downCell == Cell.PLT || downCell == Cell.MTL || downCell == Cell.LAD)
				|| characterAt(wdt, hgt-1))
			&& !characterAt(wdt-1, hgt)) {
			wdt--;
		}

	}

	@Override
	public void goRight() {
		Cell currCell = getEnvi().getCellNature(wdt, hgt);
		Cell downCell = getEnvi().getCellNature(wdt, hgt-1);
		if(wdt != getEnvi().getWidth()-1
			&& isFreeCell(wdt+1, hgt)
			&& ((currCell == Cell.LAD || currCell == Cell.HDR)
				|| (downCell == Cell.PLT || downCell == Cell.MTL || downCell == Cell.LAD)
				|| characterAt(wdt, hgt-1))
			&& !characterAt(wdt+1, hgt)) {
			wdt++;
		}

	}

	@Override
	public void goUp() {
		Cell currCell = getEnvi().getCellNature(wdt, hgt);
		if(hgt != getEnvi().getHeight()-1 
			&& isFreeCell(wdt, hgt+1)
			&& currCell == Cell.LAD)
			hgt++;

	}

	@Override
	public void goDown() {
		if(hgt != 0 && isFreeCell(wdt, hgt-1)) {
			hgt --;
		}

	}

	@Override
	public void stay() {

	}


}
