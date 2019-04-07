package services;


public interface PlayerService extends CharacterService {
	/**
	 * observators const
	 */
	public EngineService getEngine();
	
	/**
	 * predicate definition:
	**/
	 
	/** getEnvi().getCellNature(getWdt(),getHgt()-1) in {HOL, EMP} 
	 * && not characterAt(getWdt(), getHgt()-1)
	 * && getEnvi().getCellNature(getWdt(),getHgt()) not in {LAD, HDR}
	**/
	public boolean willFall();
	
	/**
	 *  getEngine().getNextCommand() = DigR
	 *  not isFreeCell(getWdt(),getHgt()-1)  
	 *  || not characterAt(getWdt(), getHgt()-1)
	 *  && isFreeCell(getWdt()+1,getHgt()) 
	 *  && getEnvi().getCellNature(getWdt()+1,getHgt()-1) = PLT
	 */
	public boolean willDigRight();

	
	/**
	 *  getEngine().getNextCommand() = DigL
	 *  not isFreeCell(getWdt(),getHgt()-1)  
	 *  || not characterAt(getWdt(), getHgt()-1)
	 *  && isFreeCell(getWdt()-1,getHgt()) 
	 *  && getEnvi().getCellNature(getWdt()-1,getHgt()-1) = PLT
	 */
	public boolean willDigLeft();
	
	/**
	 * operators
	 */
	
	/** willFall() implies goDown()
	 *  willDigRight() implies getCellNature(getWdt()+1,getHgt()-1) = HOL 
	 *  willDigLeft() implies getCellNature(getWdt()-1,getHgt()-1) = HOL
	 *  getEngine().getNextCommand() = UP implies goUp()
	 *  getEngine().getNextCommand() = DOWN implies goDown()
	 *  getEngine().getNextCommand() = RIGHT implies goRight()
	 *  getEngine().getNextCommand() = LEFT implies goLeft()
	 *  getEngine().getNextCommand() = NEUTRAL implies stay()
	 *  
	 **/
	public void step();
}
