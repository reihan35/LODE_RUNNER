package services;

import java.util.ArrayList;

public interface EnvironmentService extends ScreenService {
	/**
	 * observators
	 */
	
	/**
	 * predicate definition:
	 * exists Treasure t in getEnvi().getCellContent(x, y)
	 */
	 
	default public boolean isTreasureAt(int x, int y) {
	}
	
	/**
	 * pre : isInWindow(int x, int y) 
	 */
	
	ArrayList<ItemService> getCellContent(int x, int y);
	
	/**
	 * invariants :
	 * inv : forall (x, y) in [0;getWidth()[ X [0;getHeight()[
	 *			forall (c1, c2) in (getCellContent(x,y), getCellContent(x,y)
	 *				c1 = c2
	 *
	 * inv:�forall (x, y) in [0;getWidth()[ X [0;getHeight()[
	 * 			getCellNature(x,y) in {MTL, PLR} implies getCellContent(x,y)={}
	 * 
	 * inv: forall (x, y) in [0;getWidth()[ X [0;getHeight()[
	 * 			exists Treasure t in getCellContent(x,y) 
	 * 				implies getCellNature(x,y)) = EMP && getCellNature(x,y-1) in {PLT,MTL}
	 * 
	 **/
	
}

