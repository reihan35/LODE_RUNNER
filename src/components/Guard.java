package components;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


import services.Cell;
import services.CharacterService;
import services.Command;
import services.EngineService;
import services.EnvironmentService;
import services.GuardService;
import services.ItemService;
import services.ItemType;
import services.Move;
import services.PlayerService;
import util.SetUtil;

public class Guard extends Character implements GuardService {
	
	private static int count = 0;
	private EngineService engine;
	private int id;
	private PlayerService p;
	private int first_x;
	private int first_y;
	private ItemService treasure;
	private boolean hasTreasure;
	private int timeStayed;
	private int timeInHole;
	private int nbStay = 3;
	private int nbStayInHole = 5;
	
	private int can_hold_t;
	
	@Override
	public int getFirst_x() {
		return first_x;
	}
	@Override
	public int getFirst_y() {
		return first_y;
	}

	public ItemService getTreasure() {
		return treasure;
	}


	@Override
	public void init(EngineService e, int w, int h, PlayerService p) {
		super.init(e.getEnvi(), w, h);
		this.p = p;
		engine = e;
		id = count++;
		first_x = w;
		first_y = h;
		timeInHole = 0;
		timeStayed = 0;
		treasure = null;
		
	}
	
	@Override
	public boolean has_treasure() {
		return treasure != null;
	}
	
	@Override 
	public ItemService get_treasure() {
		return treasure;
	}
	
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return  id;

	}
	
	@Override
	public PlayerService getPlayer() {
		return p;
	}
	
	@Override
	public EngineService getEngine() {
		return engine;
	}
	
	@Override
	public void setTreasure(ItemService i ) {
		treasure = i;
		
	}
	
	/**
	 * invariants
	 * inv: getEnvi().getCellNature(getWdt(),getHgt()) = LAD && getHgt() < getTarget().getHgt() 
	 * 		implies getBehaviour()=Up
	 * 
	 * inv: getEnvi().getCellNature(getWdt(),getHgt()) = LAD && getHgt() > getTarget().getHgt() 
	 * 		implies getBehaviour()=DOWN
	 *
	 * * inv: getEnvi().getCellNature(getWdt(),getHgt()) = LAD && getHgt() = getTarget().getHgt() 
	 * 		implies getBehaviour()=NEUTRAL
	 *
	 * inv: getEnvi().getCellNature(getWdt(),getHgt()) in {HOL, HDR} 
	 * 		|| not isFreeCell(getWdt()@pre, getHgt() - 1)
	 * 		|| isFreeCell(getWdt()@pre, getHgt() - 1) &&  characterAt(getWdt(), getHgt()-1)
	 * 		implies getTarget().getWdt() < getWdt() implies getBehaviour()=LEFT
	 * 
	 * inv: getEnvi().getCellNature(getWdt(),getHgt()) in {LAD, HDR} 
	 * 		|| not isFreeCell(getWdt()@pre, getHgt() - 1)
	 * 		|| isFreeCell(getWdt()@pre, getHgt() - 1) &&  characterAt(getWdt(), getHgt()-1)
	 * 		implies getTarget().getWdt() > getWdt() implies getBehaviour()=RIGHT
	 * 
	 * inv: getEnvi().getCellNature(getWdt(),getHgt()) in {LAD, HDR} 
	 * 		|| not isFreeCell(getWdt()@pre, getHgt() - 1)
	 * 		|| isFreeCell(getWdt()@pre, getHgt() - 1) &&  characterAt(getWdt(), getHgt()-1)
	 * 		implies getTarget().getWdt() = getWdt() implies getBehaviour()=NEUTRAL
	 * 
	 *  inv: getEnvi().getCellNature(getWdt(),getHgt()) = LAD && getHgt()< getTarget().getHgt() 
	 * 		&& getEnvi().getCellNature(getWdt(),getHgt()-1) not in {PLT,MTL} 
	 * 		|| characterAt(getWdt(), getHgt()-1) 
	 * 		implies getTarget().getHgt()-getHgt() < getTarget().getWdt() implies getBehaviour()=Up
	 * 
	 *  inv: getEnvi().getCellNature(getWdt(),getHgt()) = LAD && getHgt()>getTarget().getHgt() 
	 * 		&& getEnvi().getCellNature(getWdt(),getHgt()-1) not in {PLT,MTL} 
	 * 		|| characterAt(getWdt(), getHgt()-1) 
	 * 		implies getTarget().getHgt()-getHgt() < getTarget().getWdt() implies getBehaviour()=Down
	 *  
	 *  inv: getEnvi().getCellNature(getWdt(),getHgt()) = LAD && getWdt()>getTarget().getWdt() 
	 * 		&& getEnvi().getCellNature(getWdt(),getHgt()-1) not in {PLT,MTL} 
	 * 		|| characterAt(getWdt(), getHgt()-1) 
	 * 		implies getTarget().getHgt()-getHgt() > getTarget().getWdt() implies getBehaviour()=RIGHT
	 * 
	 *  inv: getEnvi().getCellNature(getWdt(),getHgt()) = LAD && getWdt()<getTarget().getWdt() 
	 * 		&& getEnvi().getCellNature(getWdt(),getHgt()-1) not in {PLT,MTL} 
	 * 		|| characterAt(getWdt(), getHgt()-1) 
	 * 		implies getTarget().getHgt()-getHgt() > getTarget().getWdt() implies getBehaviour()=LEFT
	**/

	@Override
	public Move getBehaviour() {
		
		Cell[] full = {Cell.MTL, Cell.PLT};
		
		if(getEnvi().getCellNature(getWdt(),getHgt()) == Cell.HOL) {
			if(getTarget().getWdt() < getWdt())
				return Move.LEFT;
			return Move.RIGHT;
		}
		
		 if (getEnvi().getCellNature(getWdt(),getHgt()) == Cell.LAD && getHgt() < getTarget().getHgt()) {
			 return Move.UP;
		 }
		 if ((!SetUtil.isIn(getEnvi().getCellNature(getWdt(), getHgt()-1), full)) && getHgt() > getTarget().getHgt()) {
			 return Move.DOWN;
		 }
		 Cell downCell = getEnvi().getCellNature(getWdt(), getHgt()-1);
		 Cell currCell = getEnvi().getCellNature(getWdt(), getHgt());
		 Cell[] emp ={Cell.HOL, Cell.HDR};
		 
		// if (SetUtil.isIn(currCell,emp) || (!isFreeCell(getWdt(), getHgt()-1)) || (isFreeCell(getWdt(), getHgt()-1) && characterAt(getWdt(), getHgt()-1))){
		//if (SetUtil.isIn(currCell,emp) || (!isFreeCell(getWdt(), getHgt()-1))){

			 if(getTarget().getWdt() < getWdt()) {
				 return Move.LEFT;
			 }
			 if (getTarget().getWdt() > getWdt()) {
				 System.out.println("JE VAIS A DROITE");
				 return Move.RIGHT;
			 }
		 //}
		return Move.NEUTRAL;
	}


	@Override
	public CharacterService getTarget() {
		return p;
	}


	@Override
	public int getTimeInHole() {
		return timeInHole;
	}
	
	public void Climb_Left() {
		boolean pas_gardien = true;
		int wdt_b = wdt;
		int hgt_b = hgt;
		if(this instanceof GuardService ) {
			for(CharacterService c : getEnvi().getCellContentChar(wdt-1,hgt+1)) {
				if(c instanceof GuardService) {
					pas_gardien = false;
				}
			}
		}
		if (pas_gardien) {
			hgt = hgt + 1;
			wdt = wdt - 1;

		}
	}
	
	public void Climb_Right() {
		int wdt_b = wdt;
		int hgt_b = hgt;
		boolean pas_gardien = true;
		if(this instanceof GuardService ) {
			for(CharacterService c : getEnvi().getCellContentChar(wdt+1,hgt+1)) {
				if(c instanceof GuardService) {
					pas_gardien = false;
				}
			}
		}
		if (pas_gardien) {
			hgt = hgt + 1;
			wdt = wdt + 1;

		}
	}
	
	@Override
	public void climbRight() {
		 Cell UpCell = getEnvi().getCellNature(getWdt() + 1, getHgt() + 1);
		 Cell currCell = getEnvi().getCellNature(getWdt(), getHgt());
		 Cell[] fill ={Cell.MTL, Cell.PLT};
		 System.out.println("je rentre dans climbRight");
		 if (getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HOL){
			System.out.println("je rentre meme la");
			if (getWdt() != 0 && !SetUtil.isIn(UpCell, fill)) {
				System.out.println("j'arrive jusque la");
				Climb_Right();
			}
		}
	}


	@Override
	public void climbLeft() {
		 Cell UpCell = getEnvi().getCellNature(getWdt() - 1, getHgt() + 1);
		 Cell currCell = getEnvi().getCellNature(getWdt(), getHgt());
		 Cell[] fill ={Cell.MTL, Cell.PLT};
		 if (getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HOL){
			if (getWdt() != 0 && !SetUtil.isIn(UpCell, fill)) {
				Climb_Left();
			}
		}
		
	}
	
	@Override
	public void dropTreasure() {
		if(treasure != null) {
			getEngine().addTreasure( getWdt(), getHgt()+1);
			treasure = null;
		}
	}
	
	@Override
	public void Reinitialize() {
		wdt = first_x;
		hgt = first_y;
	}
	
	/*
	public boolean willMove() {
		if (move > 2) {
			move = 0;
			return true;
		}
		return false;
	}*/
	
	@Override
	public void grabTreasure() {
		ItemService t = getEnvi().getCellContentItem(wdt, hgt).get(0); 
		getEnvi().getCellContentItem(wdt, hgt).remove(0);
		getEngine().getTreasures().remove(t);
		treasure = t;
			
	}

	@Override
	public void step() {

 		timeStayed++;
 		
 		if(willGrabTreasure()) {
 			grabTreasure();
 		}
 		
		if (willReinitialize()) {
			Reinitialize();
			return;
		}
		if(!willMove()) return;
		timeStayed = 0;
		if(getEnvi().getCellNature(getWdt(),getHgt()) == Cell.HOL)
			timeInHole++;
		System.out.println("je rentre dans step");
		if(willClimbLeft() || willClimbRight()) {
			if (willClimbLeft())
				climbLeft();
			else
				climbRight();
			timeInHole = 0;
		}
		else {
			if(/*willAddTime()*/ willStay()) {
				stay();
			}
			else {
					if(willFall()) {
						goDown();
						System.out.println("VAS-YYYYYY");
						if(getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HOL) {
							dropTreasure();
						}
					}
					else {
							switch(getBehaviour()) {
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

	@Override
	public void die() {
		if(treasure != null) {
			getEngine().addTreasure( getWdt(), getHgt());
			getEngine().getEnvi().addCellContentItem(getWdt(), getHgt()+1, treasure);
			treasure = null;
		}

		getEngine().removeGuard(this);
		
	}
	@Override
	public int getNbStayInHole() {
		return nbStayInHole;
	}
	@Override
	public int getNbStay() {
		// TODO Auto-generated method stub
		return nbStay;
	}
	@Override
	public int getTimeStayed() {
		// TODO Auto-generated method stub
		return timeStayed;
	}

}
