package implem_bugee;

import java.util.ArrayList;
import java.util.List;

import services.Cell;
import services.Command;
import services.Coordinates;
import services.Door;
import services.EngineService;
import services.EnvironmentService;
import services.GuardService;
import services.ItemService;
import services.ItemType;
import services.PlayerService;
import services.Stat;
import util.SetUtil;

public class Player extends Character implements PlayerService {
	
	private EngineService engine;
	private ArrayList<ItemService> bombs = new ArrayList<>() ;
	
	@Override
	public void init(EngineService e, int w, int h) {
		super.init(e.getEnvi(), w, h);
		engine = e;
		
	}
	@Override
	public void addBomb(ItemService b) {
		bombs.add(b);
	}
	
	@Override
	public EngineService getEngine() {
		// TODO Auto-generated method stub
		return engine;
	}
	

	@Override
	public void step() {
		System.out.println("je suis la nature");
		System.out.println(getEnvi().getCellNature(getWdt(), getHgt()));
		
		if(getEngine().getNextCommand() == Command.OPEND) {
			for (Door d: getEngine().getDoors()) {	
				if(d.isOnIn(new Coordinates(wdt, hgt))) {
					wdt = d.getOut().getX();
					hgt = d.getOut().getY();
					break;
				}
				else if(d.isOnOut(new Coordinates(wdt, hgt))) {
					wdt = d.getIn().getX();
					hgt = d.getIn().getY();
					break;
				}
			}
		}
		if(willFall()) {
			System.out.println("je fais goDown");
			goDown();
		}
		
		
		else {
			if(willDigRight()) {
				//bug rajoute
				//getEnvi().dig(getWdt() + 1, getHgt() - 1 );
				System.out.println("voila tout" + getEnvi().getCellNature(getWdt() - 1 , getHgt() - 1));
				
			}
			else {
				if(willDigLeft()) {
					//getEnvi().dig(getWdt() - 1, getHgt() - 1 );
					System.out.println("voila tout" + getEnvi().getCellNature(getWdt() - 1 , getHgt() - 1));
					System.out.println(getWdt() + 1 );
					System.out.println(getHgt() - 1 );
				}
				else {
					if (willFight()) {
						//determiner si le garde est à gauche ou à droite
						GuardService g = null;
						if(getEnvi().getCellContentChar(wdt-1, hgt).size() > 0) {
							g = (GuardService) getEnvi().getCellContentChar(wdt-1, hgt).get(0);
						}
						else {
							g = (GuardService) getEnvi().getCellContentChar(wdt+1, hgt).get(0);
						}
						ItemService b = bombs.get(0);
						bombs.remove(b);
						g.die();
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

	@Override
	public List<ItemService> getBomb() {
		
		return bombs;
	}
}
