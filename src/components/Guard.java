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
	private int move = 0;
	private int can_hold_t;
	
	@Override
	public void init(EngineService e, int w, int h, PlayerService p) {
		super.init(e.getEnvi(), w, h);
		this.p = p;
		engine = e;
		id = count++;
		first_x = w;
		first_y = h;
		
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
		if (SetUtil.isIn(currCell,emp) || (!isFreeCell(getWdt(), getHgt()-1)) || (isFreeCell(getWdt(), getHgt()-1))){

			 if(getTarget().getWdt() < getWdt()) {
				 return Move.LEFT;
			 }
			 if (getTarget().getWdt() > getWdt()) {
				 System.out.println("JE VAIS A DROITE");
				 return Move.RIGHT;
			 }
			 
			 else {
				 	System.out.println("JE VEUX SAVOIR");
					return Move.NEUTRAL;
				 
			 }
		 }
		 Cell[] f ={Cell.PLT, Cell.MTL};
		 if (getEnvi().getCellNature(getWdt(), getHgt()) == Cell.LAD && getHgt() < getTarget().getHgt() && (!isFreeCell(getWdt(), getHgt() - 1) 
				 																							|| isFreeCell(getWdt(), getHgt() - 1) && characterAt(getWdt(), getHgt()- 1))) {
			 if(getTarget().getHgt() - getHgt() < getTarget().getWdt()) {
				 return Move.UP;
			 }
		 }
		 
		 if (getEnvi().getCellNature(getWdt(), getHgt()) == Cell.LAD && getWdt() > getTarget().getWdt() && (!isFreeCell(getWdt(), getHgt() - 1) 
																											|| isFreeCell(getWdt(), getHgt() - 1) && characterAt(getWdt(), getHgt()- 1))) {
			 if(getTarget().getHgt() - getHgt() < getTarget().getWdt()) {
				 return Move.DOWN;
			 }
		 }
		 
		 if (getEnvi().getCellNature(getWdt(), getHgt()) == Cell.LAD && getHgt() > getTarget().getHgt() &&(!isFreeCell(getWdt(), getHgt() - 1) 
				 																								|| isFreeCell(getWdt(), getHgt() - 1) && characterAt(getWdt(), getHgt()- 1))) {
			 if(getTarget().getHgt() - getHgt() < getTarget().getWdt()) {
				 return Move.LEFT;
			 }
		 }
		 
		 System.out.println(!isFreeCell(getWdt(), getHgt() - 1));
		 System.out.println("HOHOHO");

		 if (getEnvi().getCellNature(getWdt(), getHgt()) == Cell.LAD && getWdt() < getTarget().getWdt() && (!isFreeCell(getWdt(), getHgt() - 1) 
																											|| isFreeCell(getWdt(), getHgt() - 1) && characterAt(getWdt(), getHgt()- 1))) {
			 System.out.println("HOHOHO");
			 if(getTarget().getHgt() - getHgt() > getTarget().getWdt()) {
				 return Move.RIGHT;
			 }
		 }
		 return Move.NEUTRAL;
	}


	@Override
	public CharacterService getTarget() {
		return p;
	}


	@Override
	public int getTimeInHole() {
		return engine.getHoles(getWdt(), getHgt());
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
	
	public void drop_off() {
		if(treasure != null) {
			getEngine().removeTreasure();
			getEngine().addTreasure( getWdt(), getHgt()+1);
			getEngine().getEnvi().addCellContentItem(getWdt(), getHgt()+1, treasure);
			treasure = null;
		}
	}
	
	@Override
	public void Reinitialize() {
		System.out.println("je suis dans reinitialize");
		System.out.println(first_x);
		System.out.println(first_y);
		transport(first_x,first_y);
	}
	
	public boolean willMove() {
		if (move++ > 2) {
			move = 0;
			return true;
		}
		return false;
	}

	@Override
	public void step() {
		
		
		System.out.println("je rentre dans step");
		if(willClimbLeft() || willClimbRight()) {
			if (willClimbLeft())
				climbLeft();
			else
				climbRight();
		}
		else {
			if(willAddTime() || willStay()) {
				stay();
			}
			else {
				if (willReinitialize()) {
					System.out.println("je suis pas venue ici");
					Reinitialize();
				}
				else {
					if(willFall()) {
						goDown();
						System.out.println("VAS-YYYYYY");
						if(getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HOL) {
							drop_off();
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
		}

}
