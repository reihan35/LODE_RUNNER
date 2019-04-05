package projet.services;

public interface CharacterService {
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
	 * predicates
	 *
	 */
	
	/**
	 * predicate definition:
	 * exists Character c in getEnvi().getCellContent(x, y)
	 */
	public boolean characterAt(int x, int y);
	
	/**
	 * getEnvi().getCellNature(x, y) in {EMP, LAD, HDR, HOL}
	 */
	public boolean isFreeCell(int x, int y);
	
	/**
	 * invariants
	 * inv: isFreeCell(getWdt(), getHgt()
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
	 * post: getWdt()@pre == 0 implies getWdt() == getWdt()@pre
	 * 
	 * post: getEnvi().getCellNature(getWdt()@pre-1, getHgt()@pre) in {MTL, PLT, LAD}
	 * 		implies getWdt() == getWdt()@pre
	 * 
	 * post: getEnvi().getCellNature(getWdt()@pre, getHgt()@pre ) not in {LAD, HDR}	
	 * 			&& getWdt()@pre != 0 && isFreeCell(getWdt()@pre, getHgt()@pre - 1)
	 * 			&& not characterAt(getWdt()@pre, getHgt()@pre-1)
	 * 				implies getWdt() == getWdt()@pre
	 * 
	 * post: characterAt(getWdt()@pre - 1, getHgt()@pre)
	 * 			implies getWdt() == getWdt()@pre
	 * 
	 * post: getWdt()@pre != 0 && isFreeCell(getWdt()@pre - 1, getHgt()@pre)
	 * 		&& (getEnvi().getCellNature(getWdt()@pre, getHgt()@pre) in {LAD, HDR}
	 * 			|| getEnvi().getCellNature(getWdt()@pre, getHgt()@pre - 1) in {PLT, MTL, LAD}
	 * 			|| characterAt(getWdt()@pre, getHgt()@pre - 1))
	 *		&& not characterAt(getWdt()@pre - 1, getHgt()@pre)
	 *			implies getWdt() == getWdt()@pre - 1 
	 */
	public void goLeft();
	
	
	/**
	 * post: getHgt() == getHgt()@pre
	 * 
	 * post: getWdt()@pre == getEnvi().getWidth() - 1 implies getWdt() == getWdt()@pre
	 * 
	 * post: getEnvi().getCellNature(getWdt()@pre + 1, getHgt()@pre) in {MTL, PLT, LAD}
	 * 		implies getWdt() == getWdt()@pre
	 * 
	 * post: getEnvi().getCellNature(getWdt()@pre, getHgt()@pre) not in {LAD, HDR}	
	 * 			&& getWdt()@pre != 0 && isFreeCell(getWdt()@pre, getHgt()@pre - 1)
	 * 			&& not characterAt(getWdt()@pre, getHgt()@pre - 1)
	 * 				implies getWdt() == getWdt()@pre
	 * 
	 * post: characterAt(getWdt()@pre + 1, getHgt()@pre)
	 * 			implies getWdt() == getWdt()@pre
	 * 
	 * post: getWdt()@pre != 0 && isFreeCell(getWdt()@pre + 1, getHgt()@pre)
	 * 		&& (getEnvi().getCellNature(getWdt()@pre, getHgt()@pre) in {LAD, HDR}
	 * 			|| getEnvi().getCellNature(getWdt()@pre, getHgt()@pre - 1) in {PLT, MTL, LAD}
	 * 			|| characterAt(getWdt()@pre, getHgt()@pre - 1))
	 *		&& not characterAt(getWdt()@pre + 1, getHgt()@pre)
	 *			implies getWdt() == getWdt()@pre + 1 
	 */
	public void goRight();
	
	/**
	 * post: getWdt() == getWdt()@pre
	 * 
	 * post: getHgt()@pre == getEnvi().getHeight()-1 implies getHgt() == getHgt()@pre
	 * 
	 * post: getEnvi().getCellNature(getWdt()@pre + 1, getHgt()@pre) in {MTL, PLT, LAD}
	 * 		implies getWdt() == getWdt()@pre
	 * 
	 * post: getEnvi().getCellNature(getWdt()@pre, getHgt()@pre) not in {LAD, HDR}	
	 * 			&& getWdt()@pre != 0 && isFreeCell(getWdt()@pre, getHgt()@pre - 1)
	 * 			&& not characterAt(getWdt()@pre, getHgt()@pre - 1)
	 * 				implies getWdt() == getWdt()@pre
	 * 
	 * post: characterAt(getWdt()@pre + 1, getHgt()@pre)
	 * 			implies getWdt() == getWdt()@pre
	 * 
	 * post: if getWdt()@pre != 0 && isFreeCell(getWdt()@pre + 1, getHgt()@pre)
	 * 			&& (getEnvi().getCellNature(getWdt()@pre, getHgt()@pre) in {LAD, HDR}
	 * 				|| getEnvi().getCellNature(getWdt()@pre, getHgt()@pre - 1) in {PLT, MTL, LAD}
	 * 				|| characterAt(getWdt()@pre, getHgt()@pre - 1))
	 *			&& not characterAt(getWdt()@pre + 1, getHgt()@pre)
	 *		then
	 *			 getHgt() == getHgt()@pre + 1
	 *		else
	 *			getHgt == getHgt()@pre
	 */
	public void goUp();
	public void goDown();
}
