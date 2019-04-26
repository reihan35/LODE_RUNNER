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
	public void init(EnvironmentService s, int x, int y) {
		this.envi = s;
		this.wdt = x;
		this.hgt = y;

	}

	@Override
	public void goLeft() {
		Cell currCell = getEnvi().getCellNature(wdt, hgt);
		Cell downCell = getEnvi().getCellNature(wdt, hgt-1);
		int wdt_b = wdt;
		int hgt_b = hgt;
		System.out.println((currCell == Cell.LAD || currCell == Cell.HDR));
		if(wdt != 0
			&& isFreeCell(wdt-1, hgt)
			&& (!(currCell == Cell.LAD || currCell == Cell.HDR)
				|| !(downCell == Cell.PLT || downCell == Cell.MTL || downCell == Cell.LAD)
				|| !characterAt(wdt, hgt-1))){
			wdt = wdt - 1;
			getEnvi().removeCellContentChar(wdt_b, hgt_b, this);
			getEnvi().addCellContentChar(wdt, hgt, this);
			
		}
		

	}

	@Override
	public void goRight() {
		Cell currCell = getEnvi().getCellNature(wdt, hgt);
		Cell downCell = getEnvi().getCellNature(wdt, hgt-1);
		int wdt_b = wdt;
		int hgt_b = hgt;
		if(wdt != getEnvi().getWidth()-1
			&& isFreeCell(wdt+1, hgt)
			&& (!(currCell == Cell.LAD || currCell == Cell.HDR)
				|| !(downCell == Cell.PLT || downCell == Cell.MTL || downCell == Cell.LAD)
				||! characterAt(wdt, hgt-1))) {
			wdt = wdt + 1;
			getEnvi().removeCellContentChar(wdt_b, hgt_b, this);
			getEnvi().addCellContentChar(wdt, hgt, this);
		}

	}

	@Override
	public void goUp() {
		Cell currCell = getEnvi().getCellNature(wdt, hgt);
		int wdt_b = wdt;
		int hgt_b = hgt;
		if(hgt != getEnvi().getHeight()-1 
			&& isFreeCell(wdt, hgt+1)
			&& currCell == Cell.LAD
			&& !characterAt(wdt, hgt+1))
			hgt = hgt + 1;
		getEnvi().removeCellContentChar(wdt_b, hgt_b, this);
		getEnvi().addCellContentChar(wdt, hgt, this);

	}
	public void Climb_Left() {
		int wdt_b = wdt;
		int hgt_b = hgt;
		hgt = hgt + 1;
		wdt = wdt - 1;
		getEnvi().removeCellContentChar(wdt_b, hgt_b, this);
		getEnvi().addCellContentChar(wdt, hgt, this);
	}
	
	public void Climb_Right() {
		int wdt_b = wdt;
		int hgt_b = hgt;
		hgt = hgt + 1;
		wdt = wdt + 1;
		getEnvi().removeCellContentChar(wdt_b, hgt_b, this);
		getEnvi().addCellContentChar(wdt, hgt, this);
	}
	
	public void transport(int w,int h) {
		int wdt_b = wdt;
		int hgt_b = hgt;
		wdt = w;
		hgt = h;
		getEnvi().removeCellContentChar(wdt_b, hgt_b, this);
		getEnvi().addCellContentChar(wdt, hgt, this);
	}

	@Override
	public void goDown() {
		int wdt_b = wdt;
		int hgt_b = hgt;
		if(hgt != 0 && isFreeCell(wdt, hgt-1) && !characterAt(wdt, hgt-1)) {
			System.out.println("avant" + hgt);
			hgt = hgt - 1;
			getEnvi().removeCellContentChar(wdt_b, hgt_b, this);
			getEnvi().addCellContentChar(wdt, hgt, this);
		}

	}


	@Override
	public void stay() {

	}


}
