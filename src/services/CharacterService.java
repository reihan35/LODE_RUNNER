package services;

import util.SetUtil;

public interface CharacterService{
	/**
	 * observators const
	 */
	public EnvironmentService getEnvi();
	/**
	 * observators
	 */
	public int getHgt();
	public int getWdt();	
	/**
	 * invariants de minimisation
	 *
	 */
	
	/**
	 * definition:
	 * exists Character c in getEnvi().getCellContent(x, y)
	 */
	default public boolean characterAt(int x, int y) {
		return getEnvi().getCellContentChar(x, y).size() > 0;
	}
	
	/**
	 * getEnvi().getCellNature(x, y) in {EMP, LAD, HDR, HOL}
	 */
	default public boolean isFreeCell(int x, int y) {
		Cell currCell = getEnvi().getCellNature(x, y);
		Cell[] free = {Cell.LAD, Cell.EMP, Cell.HDR, Cell.HOL};
		if(SetUtil.isIn(currCell, free)) {
			return true;
		}
		return false;
	}
	
	/**
	 * invariants
	 * inv: isFreeCell(getWdt(), getHgt())
	 *  */
	
	
	/**
	 * constructors
	 */
	
	/**
	 * pre: getEnvi().getCellNature(x, y) = EMP
	 * post: getEnvi() == s
	 * post: getHgt() == y
	 * post: getWdt() == x
	 */
	public void init(EnvironmentService s, int x, int y);
	

	
	/**
	 * operators
	 */
	
	/**
	 * post: getHgt() == getHgt()@pre
	 * 
	 * 
	 * post: if (getWdt()@pre != 0 && isFreeCell(getWdt()@pre - 1, getHgt())
	 * 		&& (getEnvi().getCellNature(getWdt()@pre, getHgt()) in {LAD, HDR}
	 * 			|| getEnvi().getCellNature(getWdt()@pre, getHgt() - 1) in {PLT, MTL, LAD}
	 * 			|| characterAt(getWdt()@pre, getHgt() - 1))
	 *		&& not characterAt(getWdt()@pre - 1, getHgt()))
	 *			then getWdt() == getWdt()@pre - 1 
	 *			else getWdt() == getWdt()@pre
	 */			
	public void goLeft();
	
	
	/**
	 * post: getHgt() == getHgt()@pre
	 * 
	 * post: if getWdt()@pre != getEnvi().getWidth() - 1 && isFreeCell(getWdt()@pre + 1, getHgt())
	 * 		&& (getEnvi().getCellNature(getWdt()@pre, getHgt()) in {LAD, HDR}
	 * 			|| getEnvi().getCellNature(getWdt()@pre, getHgt() - 1) in {PLT, MTL, LAD}
	 * 			|| characterAt(getWdt()@pre, getHgt() - 1))
	 *		&& not characterAt(getWdt()@pre + 1, getHgt())
	 *			then getWdt() == getWdt()@pre + 1 
	 *			else getWdt() == getWdt()@pre
	 *			
	 */
	public void goRight();
	
	/**
	 * post: getWdt() == getWdt()@pre
	 * 
	 * post: if not getHgt()@pre == getEnvi().getHeight()-1
	 * 			&& getEnvi().getCellNature(getWdt(), getHgt()@pre) == LAD
	 * 			&& isFreeCell(getWdt(), getHgt()@pre + 1)
	 * 			&& not characterAt(getWdt(), getHgt()@pre + 1)
	 * 				then getHgt() == getHgt()@pre + 1
	 * 				else getHgt() == getHgt()@pre + 1
	 * 
	 */
	public void goUp();
	

	/**
	 * post: getWdt() == getWdt()@pre
	 * 
	 * 
	 * post: if not getHgt()@pre == 0
	 * 			&& isFreeCell(getWdt(), getHgt()@pre - 1)
	 * 			&& not characterAt(getWdt()@pre, getHgt()@pre - 1)
	 * 				then getHgt() == getHgt()@pre - 1
	 * 				else getHgt() == getHgt()@pre
	 * 
	 */
	public void goDown();
	
	/**
	 * 	getHgt() == getHgt()@pre && getWdt() == getWdt()@pre
	 * 
	 **/
	public void stay();
}
