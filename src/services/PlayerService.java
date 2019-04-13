package services;


public interface PlayerService extends CharacterService {
	/**
	 * observators const
	 */
	public EngineService getEngine();
	
	/**
	 * predicate definition:
	**/
	 
	/** def = getEnvi().getCellNature(getWdt(),getHgt()-1) in {HOL, EMP} 
	 * && not characterAt(getWdt(), getHgt()-1)
	 * && getEnvi().getCellNature(getWdt(),getHgt()) not in {LAD, HDR}
	**/
	public boolean willFall();
	
	/**
	 *  def = getEngine().getNextCommand() = DigR
	 *  not isFreeCell(getWdt(),getHgt()-1)  
	 *  || not characterAt(getWdt(), getHgt()-1)
	 *  && isFreeCell(getWdt()+1,getHgt()) 
	 *  && getEnvi().getCellNature(getWdt()+1,getHgt()-1) = PLT
	 */
	public boolean willDigRight();

	
	/**
	 *  def = getEngine().getNextCommand() = DigL
	 *  not isFreeCell(getWdt(),getHgt()-1)  
	 *  || not characterAt(getWdt(), getHgt()-1)
	 *  && isFreeCell(getWdt()-1,getHgt()) 
	 *  && getEnvi().getCellNature(getWdt()-1,getHgt()-1) = PLT
	 */
	public boolean willDigLeft();
	
	public void init(EngineService e, int w, int h);
	
	/**
	 * operators
	 */
	
	/** post : willFall() implies goDown()
	 *  post : willDigRight() implies getCellNature(getWdt()+1,getHgt()-1) = HOL 
	 *  post : willDigLeft() implies getCellNature(getWdt()-1,getHgt()-1) = HOL
	 *  post : getEngine().getNextCommand() = UP implies goUp()
	 *  post : getEngine().getNextCommand() = DOWN implies goDown()
	 *  post : getEngine().getNextCommand() = RIGHT implies goRight()
	 *  post : getEngine().getNextCommand() = LEFT implies goLeft()
	 *  post : getEngine().getNextCommand() = NEUTRAL implies stay()
	 *  
	 **/
	public void step();
}
