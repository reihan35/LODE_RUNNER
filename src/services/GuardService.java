package services;


import components.Player;
import util.SetUtil;

public interface GuardService extends CharacterService {
	/**
	 * observators const
	 */
	public int getId();
	/**
	 * observators
	 */
	public Move getBehaviour();
	public CharacterService getTarget();
	public int getNbStayInHole();
	public int getNbStay();
	public int getFirst_x();
	public int getFirst_y();
	public int getTimeStayed();
	public int getTimeInHole();

	/**
	 * predicate definition:
	 * getEnvi().getCellNature(getWdt(),getHgt()-1) in {HOL, EMP} 
	 * && not characterAt(getWdt(), getHgt()-1)
	 * && getEnvi().getCellNature(getWdt(),getHgt()) not in {LAD, HDR}
	 */
	default public boolean willFall() {
		Cell downCell = getEnvi().getCellNature(getWdt(), getHgt()-1);
		Cell currCell = getEnvi().getCellNature(getWdt(), getHgt());
		Cell[] emp ={Cell.HOL,Cell.EMP};
		Cell[] lad ={Cell.LAD,Cell.HDR, Cell.HOL};
		return SetUtil.isIn(downCell,emp) && ! characterAt(getWdt(),getHgt()-1) && ! SetUtil.isIn(currCell,lad);
	}
	
	
	/**
	 * def = getEnvi().getCellNature(getWdt(),getHgt()) = HOL && getTimeInHole = 5 && getBehaviour = Left
	 */
	default public boolean willClimbLeft() {
		System.out.println("on est dans willClimbLeft : "  + getEnvi().getCellNature(getWdt(), getHgt()) + getBehaviour() );
		 return getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HOL && getTimeInHole() >= 5 && getBehaviour() == Move.LEFT;	
	}
	
	
	/**
	 * def = getEnvi().getCellNature(getWdt(),getHgt()) = HOL && getTimeInHole = 5 && getBehaviour = Right 
	 */
	default public boolean willClimbRight() {
		 System.out.println("on est dans willClimbRight : "  + getEnvi().getCellNature(getWdt(), getHgt()) + getBehaviour() );
		 return getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HOL && getTimeInHole() >= getNbStayInHole() && getBehaviour() == Move.RIGHT;
	}
	
	/**
	 * def = getEnvi().getCellNature(getWdt(),getHgt()) = HOL && getTimeInHole = 5 && getBehaviour = Neutral
	 */
	default public boolean willStay() {
		 if (getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HOL) {
			if(getEnvi().getCellNature(getWdt()-1, getHgt()) == Cell.HOL || getEnvi().getCellNature(getWdt()+1, getHgt()) == Cell.HOL) {
				return true;
			}
		 }
		 return getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HOL && getTimeInHole() == 5 && getBehaviour() == Move.NEUTRAL;
	}
	
	/**
	 * def = getEnvi().getCellNature(getWdt(),getHgt()) = HOL && getTimeInHole() < 5 * 5
	 */
	default public boolean willAddTime() {
		return getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HOL && getTimeInHole() < 45 ;
	}
	
	default public boolean willGrabTreasure() {
		return !has_treasure() && getEnvi().getCellContentItem(getWdt(), getHgt()).size()>0 && 
				getEnvi().getCellContentItem(getWdt(), getHgt()).get(0).getNature() == ItemType.TREASURE;
	}
	default public boolean willReinitialize() {
		System.out.println(getEnvi().getCellNature(getWdt(), getHgt()));
		return getEnvi().getCellNature(getWdt(), getHgt()) == Cell.PLT;
	}
	
	default public boolean willMove() {
		return getTimeStayed() >= getNbStay();
	}

	default boolean has_treasure() {
		return get_treasure() !=null;
	}
	
	default public boolean GuardAt(int x, int y) {
		if (!characterAt(x, y)) {
			return false;
		}
		for(CharacterService c : getEnvi().getCellContentChar(x, y)) {
			if (c instanceof GuardService) {
				return true;
			}
		}
		return false;
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
	 * inv: getEnvi().getCellNature(getWdt(),getHgt()) in {LAD, HDR} 
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

	
	/**
	 * operators
	 */
	
	/** 
	 * //AJOUT DE LIBERTE DE CASE ETC A VOIR
	 *pre : getEnvi().getCellNature(getWdt(),getHgt()) = HOL 
	 *post: getEnvi().getCellNature(getWdt()@pre-1, getHgt()@pre+1) in {MTL, PLT}
	 * 		implies getWdt() == getWdt()@pre and getHgt() == getHgt()@pre
	 * 
	 *post: characterAt(getWdt()@pre-1, getHgt()+1)
	 * 			implies getWdt() == getWdt()@pre && getHgt() == getHgt()@pre
	 * 
	 *post: getWdt()@pre != 0 && getEnvi().getCellNature(getWdt()@pre+1, getHgt()+1) not in {MLT, PLT}
	 *		&& not characterAt(getWdt()@pre-1, getHgt()+1) 
	 *			implies getWdt() == getWdt()@pre - 1 && getHgt() == getHgt()@pre + 1
	**/
	public void climbRight();
	
	/** 
	 *pre : getEnvi.getCellNature(getWdt(),getHgt()) = HOL 
	 *post: getEnvi().getCellNature(getWdt()@pre+1, getHgt()@pre+1) in {MTL, PLT}
	 * 		implies getWdt() == getWdt()@pre and getHgt() == getHgt()@pre
	 * 
	 *post: characterAt(getWdt()@pre+1, getHgt()+1)
	 * 			implies getWdt() == getWdt()@pre && getHgt() == getHgt()@pre
	 * 
	 *post: getWdt()@pre != 0 && getEnvi().getCellNature(getWdt()@pre-1, getHgt()+1) not in {MLT, PLT}
	 *		&& not characterAt(getWdt()@pre+1, getHgt()+1) //AJOUT DE LIBERTE DE CASE A VOIR
	 *			implies getWdt() == getWdt()@pre - 1 && getHgt() == getHgt()@pre + 1
	**/
	public void climbLeft();
	
	/**
	 * post : willFall() implies goDown()
	 * post : willAddTime() implies getTimeInHole() = getTimeInHole@pre + 1 
	 * post : willClimbLeft() implies climbLeft()
	 * post : willClimbRight() implies climRight()
	 * post : willStay() implies stay()
	 * post : getBehaviour() = UP implies goUp()
	 * post : getBehaviour() = DOWN implies goDown()
	 * post : getBehaviour() = RIGHT implies goRight()
	 * post : getBehaviour() = LEFT implies goLeft()
	 * post : getBehaviour() = Neutral implies stay()
	 */
	public void step();
	void Reinitialize();
	PlayerService getPlayer();
	EngineService getEngine();
	void init(EngineService e, int w, int h, PlayerService p);
	void setTreasure(ItemService i);
	

	void drop_off();
	ItemService get_treasure();
	public void die();
	public void grabTreasure();

}
