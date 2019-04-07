package services;


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
	public int getTimeInHole();	

	/**
	 * predicate definition:
	 * getEnvi().getCellNature(getWdt(),getHgt()-1) in {HOL, EMP} 
	 * && not characterAt(getWdt(), getHgt()-1)
	 * && getEnvi().getCellNature(getWdt(),getHgt()) not in {LAD, HDR}
	 */
	public boolean willFall();
	
	/**
	 * getEnvi().getCellNature(getWdt(),getHgt()) = HOL && getTimeInHole = 5 && getBehaviour = Left
	 */
	public boolean willClimbLeft();
	
	/**
	 * getEnvi().getCellNature(getWdt(),getHgt()) = HOL && getTimeInHole = 5 && getBehaviour = Right 
	 */
	public boolean willClimbRight();
	
	/**
	 * getEnvi().getCellNature(getWdt(),getHgt()) = HOL && getTimeInHole = 5 && getBehaviour = Neutral
	 */
	public boolean willStay();
	
	/**
	 * getEnvi().getCellNature(getWdt(),getHgt()) = HOL && getTimeInHole() < 5 * 5
	 */
	public boolean willAddTime();
	
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
	 *post: getWdt()@pre != 0 && getEnvi().getCellNature(getWdt()@pre-1, getHgt()+1) not in {MLT, PLT}
	 *		&& not characterAt(getWdt()@pre-1, getHgt()+1) 
	 *			implies getWdt() == getWdt()@pre - 1 && etHgt() == getHgt()@pre + 1
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
	 *			implies getWdt() == getWdt()@pre - 1 && etHgt() == getHgt()@pre + 1
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

}
