package projet.services;

public interface Environment extends ScreenService {
	/**
	 * observators
	 */
	
	/**
	 * pre : isInWindow(int x, int y)
	 */
	Cell getCellContent(int x, int y);
	
	/**
	 * invariants :
	 * inv : forall (x, y) in [0;getWidth()[ X [0;getHeight()[
	 *			forall (c1, c2) in (getCellContent(E,x,y), getCellContent(E,x,y)
	 *				c1 = c2
	 *
	 * inv: forall (x, y) in [0;getWidth()[ X [0;getHeight()[
	 * 			getCellNature(E,x,y) in {MTL, PLR} implies getCellContent(x,y)=0
	 * 
	 * inv: forall (x, y) in [0;getWidth()[ X [0;getHeight()[
	 * 			exists Treasure t in getCellContent(E,x,y) 
	 * 				implies (getCellNature(E,x,y) == EMP && getCellNature(E,x,y-1) in {PLT,MTL})
	 * 
	 **/
	
}

