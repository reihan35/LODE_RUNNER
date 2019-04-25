package components;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import services.Cell;
import services.CharacterService;
import services.Command;
import services.EngineService;
import services.EnvironmentService;
import services.GuardService;
import services.Move;
import services.PlayerService;
import util.SetUtil;

public class Guard extends Character implements GuardService {
	
	private static int count = 0;
	private EngineService engine;
	private int id;
	private PlayerService p;
	
	@Override
	public void init(EngineService e, int w, int h, PlayerService p) {
		super.init(e.getEnvi(), w, h);
		this.p = p;
		engine = e;
		id = count++;
		
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return  id;

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
		
		 if (getEnvi().getCellNature(getWdt(),getHgt()) == Cell.LAD && getHgt() < getTarget().getHgt()) {
			 return Move.UP;
		 }
		 if ((((getEnvi().getCellNature(getWdt(),getHgt()-1) == Cell.LAD) && (getEnvi().getCellNature(getWdt(),getHgt()-1) == Cell.LAD) )|| getEnvi().getCellNature(getWdt(),getHgt()-1) == Cell.LAD )&& getHgt() > getTarget().getHgt()) {
			 return Move.DOWN;
		 }
		 if (getEnvi().getCellNature(getWdt(),getHgt()) == Cell.LAD && getHgt() == getTarget().getHgt()) {
			 return Move.NEUTRAL;
		 }
		 Cell downCell = getEnvi().getCellNature(getWdt(), getHgt()-1);
		 Cell currCell = getEnvi().getCellNature(getWdt(), getHgt());
		 Cell[] emp ={Cell.HOL, Cell.HDR};
		 System.out.println("j'essaeye de travailler");
		 System.out.println(!isFreeCell(getWdt(), getHgt()-1));
		 if (SetUtil.isIn(currCell,emp) || (!isFreeCell(getWdt(), getHgt()-1)) || (isFreeCell(getWdt(), getHgt()-1) && characterAt(getWdt(), getHgt()-1))){
			
			 if(getTarget().getWdt() < getWdt()) {
				 return Move.LEFT;
			 }
			 if (getTarget().getWdt() > getWdt()) {
				 return Move.RIGHT;
			 }
			 
			 else {
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
		 if (getEnvi().getCellNature(getWdt(), getHgt()) == Cell.LAD && getWdt() < getTarget().getWdt() && (!isFreeCell(getWdt(), getHgt() - 1) 
																											|| isFreeCell(getWdt(), getHgt() - 1) && characterAt(getWdt(), getHgt()- 1))) {
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
		 if (getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HOL){
			if (getWdt() != 0 && !SetUtil.isIn(UpCell, fill)) {
				goUp();
				goRight();
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
				goUp();
				goLeft();
			}
		}
		
	}


	@Override
	public void step() {
		System.out.println("je rentre dans step");
		if(willFall()) {
			System.out.println("je fais goDown");
			goDown();
		}
		else {
			if(willAddTime() || willStay()) {
				stay();
			}
			else {
				if(willClimbLeft() || willClimbRight()) {
					if (willClimbLeft())
						climbLeft();
					else
						climbRight();
				}
				else {
					System.out.println("je suis ici sometimes");
					 System.out.println("coucou");
					 System.out.println(getTarget().getWdt());
					 System.out.println(getWdt());
					 System.out.println(getEnvi().getCellNature(getWdt(),getHgt()));
					 System.out.println(getBehaviour());
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
