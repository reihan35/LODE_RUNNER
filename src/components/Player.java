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
	public void init(EngineService e, int w, int h) {
		super.init(e.getEnvi(), w, h);
		engine = e;
		
	}
	
	@Override
	public EngineService getEngine() {
		// TODO Auto-generated method stub
		return engine;
	}

	@Override
	public void step() {
		if(getEnvi().getCellNature(getWdt(), getHgt()) == Cell.DOR && getEngine().getNextCommand() == Command.OPEND) {
			System.out.println("je rentre avant trans");
			if (getWdt()==15 && getHgt()==13) {
				System.out.println("je rentre dans la porte");
				transport(13, 7);
			}
			else {
				
				transport(15, 13);
			}
			
		}
		if(willFall()) {
			System.out.println("je fais goDown");
			goDown();
		}
		else {
			if(willDigRight()) {
				getEnvi().dig(getWdt() + 1, getHgt() - 1 );
				System.out.println("voila tout" + getEnvi().getCellNature(getWdt() - 1 , getHgt() - 1));
				
			}
			else {
				if(willDigLeft()) {
					getEnvi().dig(getWdt() - 1, getHgt() - 1 );
					System.out.println("voila tout" + getEnvi().getCellNature(getWdt() - 1 , getHgt() - 1));
					System.out.println(getWdt() + 1 );
					System.out.println(getHgt() - 1 );
				}
				else {
		
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
			}
		}

	}

}
