package components;


import services.Cell;
import services.CharacterService;
import services.EnvironmentService;
import services.GuardService;
import util.SetUtil;

public class Character implements CharacterService {

	private EnvironmentService envi;
	protected int hgt;
	protected int wdt;
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
	public void init(EnvironmentService s, int x, int y) {
		this.envi = s;
		this.wdt = x;
		this.hgt = y;

	}

	@Override
	public void goLeft() {
		Cell currCell = getEnvi().getCellNature(wdt, hgt);
		Cell downCell = getEnvi().getCellNature(wdt, hgt-1);
		if(wdt != 0
			&& isFreeCell(wdt-1, hgt)
			&& (((currCell == Cell.LAD || currCell == Cell.HDR)
				|| (downCell == Cell.PLT || downCell == Cell.MTL || downCell == Cell.LAD)
				|| characterAt(wdt, hgt-1)))){
			
				wdt = wdt - 1;
		}
		

	}

	@Override
	public void goRight() {
		Cell currCell = getEnvi().getCellNature(wdt, hgt);
		Cell downCell = getEnvi().getCellNature(wdt, hgt-1);

		if(wdt != getEnvi().getWidth()-1
			&& isFreeCell(wdt+1, hgt)
			&& ((currCell == Cell.LAD || currCell == Cell.HDR)
				||(downCell == Cell.PLT || downCell == Cell.MTL || downCell == Cell.LAD)
				||characterAt(wdt, hgt-1))) {


				wdt = wdt + 1;
			
		}

	}

	@Override
	public void goUp() {
		Cell currCell = getEnvi().getCellNature(wdt, hgt);

		if(hgt != getEnvi().getHeight()-1 
			&& isFreeCell(wdt, hgt+1)
			&& currCell == Cell.LAD) {

				hgt = hgt + 1;
			
			
		}

	}

	
	@Override
	public void goDown() {
		if(hgt != 0 && isFreeCell(wdt, hgt-1)) {

				hgt = hgt - 1;

			
		}

	}


	@Override
	public void stay() {

	}


}
