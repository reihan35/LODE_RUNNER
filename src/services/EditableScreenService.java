package services;


public interface EditableScreenService extends ScreenService {
	
	/**
	 * observators
	 */

	/**
	 * predicates
	 */
	
	/**
	 * def : Playable() = \forall (x, y) in [0;getWidth()[ X [0;getHeight()[
	 * 						getCellNature(x, y) != HOL 
	 * 					&& \forall x in [0;getWidth()[
	 * 						getCellNature(x,0) == MTL
	 *
	 **/
	
	default public boolean isPlayable() {
		System.out.println("cc");
		/*for(int i = 0; i < getWidth(); i++)
			for(int j = 0; j < getHeight(); j++)
				if(getCellNature(i, j) == Cell.HOL)
					return false;*/
		for(int i = 0; i < getWidth(); i++)
			if(getCellNature(i, 0) != Cell.MTL)
				return false;
		return true;
	}
	
	/**
	 * operators
	 */
	
	/**
	 * pre : isInWindow(i, j)
	 * post : getCellNature(i, j) = c
	 * post: forall (x, y) in [0;getWidth()[ X [0;getHeight()[
 	 *			(x != u || y != v)  
 	 *			implies getCellNature(x, y) == getCellNature(x, y)@pre
	**/
	void setNature(int i,int j,Cell c);
	
	
}
