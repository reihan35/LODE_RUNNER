package projet.services;

public interface EditableScreen extends ScreenService {
	
	/**
	 * observators
	 */
	

	public boolean isPlayable();
	
	/**
	 * invariants
	 * inv : Playable() = \forall (x, y) in [0;getWidth()[ X [0;getHeight()[
	 * 						getCellNature(x, y) != HOL 
	 * 						&& \forall x in [0;getWidth()[
	 * 							getCellNature(x,0) == MTL
	 *
	 **/
	
	/**
	 * operators
	 */
	
	/**
	 * pre : isInWindow(i, j)
	 * post : getCellNature(i, j) = c
	 * post : cellNatureHasNotChanged(int  x,int y)
	**/
	void SetNature(int i,int j,Cell c);
	
	
}
