package services;

import java.util.List;

import util.SetUtil;

public interface PlayerService extends CharacterService {
	/**
	 * observators const
	 */
	public EngineService getEngine();
	
	/**
	 * predicate definition:
	**/
	 
	/** def = getEnvi().getCellNature(getWdt(),getHgt()-1) in {HOL, EMP, HDR,LAD} 
	 * && not characterAt(getWdt(), getHgt()-1)
	 * && getEnvi().getCellNature(getWdt(),getHgt()) not in {LAD, HDR}
	**/
	default public boolean willFall() {
		Cell downCell = getEnvi().getCellNature(getWdt(), getHgt()-1);
		Cell currCell = getEnvi().getCellNature(getWdt(), getHgt());
		Cell[] emp ={Cell.HOL,Cell.EMP, Cell.HDR};
		Cell[] lad ={Cell.LAD,Cell.HDR};
		if (downCell == Cell.HOL && characterAt(getWdt(), getHgt()-1)){
			return false; //pour que le player puisse marcher sur la tete du gardien quand ce dernier tombe dans un troue
		}
		return SetUtil.isIn(downCell,emp) && ! SetUtil.isIn(currCell,lad);
	}
	

	/**
	 *  def = 
	 *  getWdt() != getEnvi().getWidth()-1
	 *  && getEnvi().getCellContentItem(getWdt() + 1, getHgt()).size() == 0
	 *  && getEngine().getNextCommand() == DIGR
	 *  && (getEnvi().getCellNature(getWdt(),getHgt()-1) not in {EMP,HOL,HDR}  
	 *  	|| characterAt(getWdt(), getHgt()-1))
	 *  && isFreeCell(getWdt()+1,getHgt()) 
	 *  && getEnvi().getCellNature(getWdt()+1,getHgt()-1) == PLT
	 */
	default public boolean willDigRight() {
		if(getEnvi().isInWindow(getWdt()+1, getHgt()-1)) {
			Cell[] canDig ={Cell.LAD,Cell.PLT, Cell.MTL};
			Cell downCell = getEnvi().getCellNature(getWdt(), getHgt()-1);
			if(getEngine().getNextCommand() == Command.DIGR) {
				if(SetUtil.isIn(downCell, canDig) || characterAt(getWdt(), getHgt()-1)) {
					return isFreeCell(getWdt()+1,getHgt()) && getEnvi().getCellNature(getWdt()+1,  getHgt()-1)==Cell.PLT;
				}
			}
		}
		return false;
	}

	
	/**
	 *  def = 
	 *  getWdt() != 0
	 *  && getEnvi().getCellContentItem(getWdt() - 1, getHgt()).size() == 0
	 *  && getEngine().getNextCommand() == DIGR
	 *  && (getEnvi().getCellNature(getWdt(),getHgt()-1) not in {EMP,HOL,HDR}  
	 *  	|| characterAt(getWdt(), getHgt()-1))
	 *  && isFreeCell(getWdt()-1,getHgt()) 
	 *  && getEnvi().getCellNature(getWdt()-1,getHgt()-1) == PLT
	 */
	default public boolean willDigLeft() {

		if(getEnvi().isInWindow(getWdt()-1, getHgt()-1)) {
			
			Cell[] canDig ={Cell.LAD,Cell.PLT, Cell.MTL};
			Cell downCell = getEnvi().getCellNature(getWdt(), getHgt()-1);	
			if(getEngine().getNextCommand() == Command.DIGL) {
				if(getEnvi().getCellContentItem(getWdt() - 1, getHgt()).size() > 0) {
					return false;
				}
				if(SetUtil.isIn(downCell, canDig) || characterAt(getWdt(), getHgt()-1)) {
					return isFreeCell(getWdt()-1,getHgt()) && getEnvi().getCellNature(getWdt()-1,  getHgt()-1)==Cell.PLT;
				}
			}
		}
		return false;
	}
	
	
	default public boolean willFight() {
		Cell [] filledCell = {Cell.PLT, Cell.MTL};
		if(getEngine().getNextCommand() == Command.FIGHT && getBomb().size()>0) {
			if(getEnvi().getCellContentChar(getWdt()+1, getHgt()).size() > 0) {
				if(SetUtil.isIn(getEnvi().getCellNature(getWdt()+1, getHgt()-1), filledCell) && getEnvi().getCellNature(getWdt()+1, getHgt()) == Cell.EMP) {
					return true;
				}
			}
			if(getEnvi().getCellContentChar(getWdt()-1, getHgt()).size() > 0 ) {
				if(SetUtil.isIn(getEnvi().getCellNature(getWdt()-1, getHgt()-1), filledCell) && getEnvi().getCellNature(getWdt()-1, getHgt()) == Cell.EMP) {
					return true;
				}
			}
		}
			
		return false;
	}
	

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

	public List<ItemService> getBomb();
	
	public void addBomb(ItemService b);

}
