package components;

import services.Cell;
import services.Command;
import services.EngineService;
import services.EnvironmentService;
import services.PlayerService;
import util.SetUtil;

public class Player extends Character implements PlayerService {
	
	private EngineService engine;
	@Override
	public EngineService getEngine() {
		// TODO Auto-generated method stub
		return engine;
	}
	

	@Override
	
	public boolean willFall() {
		Cell downCell = getEnvi().getCellNature(getWdt(), getHgt()-1);
		Cell currCell = getEnvi().getCellNature(getWdt(), getHgt());
		Cell[] emp ={Cell.HOL,Cell.EMP};
		Cell[] lad ={Cell.LAD,Cell.HDR};
		System.out.println("laa");
		System.out.println(SetUtil.isIn(currCell,emp) && ! characterAt(getWdt(),getHgt()-1) && ! SetUtil.isIn(currCell,lad));
		return SetUtil.isIn(downCell,emp) && ! characterAt(getWdt(),getHgt()-1) && ! SetUtil.isIn(currCell,lad);
	}

	
	@Override
	public boolean willDigRight() {
		return false;
		/*
		Cell[] plt = {Cell.PLT};
		getEngine().getNextCommand();
		return ((getEngine().getNextCommand() == Command.DigR && ! isFreeCell(getWdt(), getHgt()-1)) || ! characterAt(getWdt(), getHgt()-1)) &&
		isFreeCell(getWdt()+1, getHgt()) && SetUtil.isIn(getEnvi().getCellNature(getWdt()+1, getHgt() - 1 ),plt);
		*/
	}

	@Override
	public boolean willDigLeft() {
		return false;
		/*
		Cell[] plt = {Cell.PLT};
		return ((getEngine().getNextCommand() == Command.DigL && ! isFreeCell(getWdt(), getHgt()-1)) || ! characterAt(getWdt(), getHgt()-1)) &&
		isFreeCell(getWdt()-1, getHgt()) && SetUtil.isIn(getEnvi().getCellNature(getWdt()-1, getHgt() - 1 ),plt);
		*/
	}

	@Override
	public void step() {
		System.out.println("hello");
		if(willFall()) {
			goDown();
		}
		if(willDigRight()) {
			getEnvi().dig(getWdt() + 1, getHgt() - 1 );
		}
		
		if(willDigLeft()) {
			
			getEnvi().dig(getWdt() - 1, getHgt() - 1 );
		}
		
		switch(getEngine().getNextCommand()) {
		  case UP:
			  goUp();
			  break;
		  case DOWN:
			  goDown();
			  break;
		  case RIGHT:
			  goRight();
			  break;
		  case LEFT:
			  goLeft();
			  break;
		  case NEUTRAL:
			  stay();
			  break;
		}

	}


	@Override
	public void init(EngineService e, int w, int h) {
		super.init(e.getEnvi(), w, h);
		engine = e;
		
	}

}
