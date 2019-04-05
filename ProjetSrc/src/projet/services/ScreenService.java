package projet.services;

public interface ScreenService {
	/**
	 * predicates
	 */
	 /** verifie si les coordonnes sont dans la fenetre
	 * pre: 0<=y 
	 * pre: y<=getHeight()
	 * pre: 0<=x 
	 * pre: x<=getWidth()
	 */
	public boolean isInWindow(int x, int y);
	

	/**								
	 * creuse un trou dans la cellule de coordonnes (x, y)
	 * post: forall (x, y) in [0;getWidth()[ X [0;getHeight()[
	 * 			(x != u || y != v)  implies
	 * 				getCellNature(x, y) == getCellNature(x, y)@pre
	 */
	public boolean cellNatureHasNotChanged(int  x,int y);

	/**
	 * const observators
	 */
	public int getHeight();
	public int getWidth();
	/**
	 * observators
	 */
	/**
	 * retourne l'etat de la cellule de coordonnees (x, y)
	 * pre: isInWindow(x, y)
	 */
	public Cell getCellNature(int x, int y);
	
	/**
	 * invariants
	 */
	
	

	/**
	 * constructors
	 */
	
	/**
	 * pre: 0<h
	 * pre: 0<w
	 * post: getHeight() = h
	 * post: getWidth() = w
	 * post: forall (x, y) in [0;getWidth()[ X [0;getHeight()[
	 * 			getCellNature(x, y) == EMP
	 */
	public void init(int h, int w);
	
	/**
	 * operators
	 */
	
	/**								
	 * creuse un trou dans la cellule de coordonnes (x, y)
	 * pre: getCellNature(x, y) == HOL
	 * post: forall (x, y) in [0;getWidth()[ X [0;getHeight()[
	 * 			(x != u || y != v)  implies getCellNature(x, y) == PLT 
	 */
	public void dig(int x, int y);
	
	/**								
	 * creuse un trou dans la cellule de coordonnes (x, y)
	 * pre: getCellNature(x, y) == PLT
	 * post: forall (x, y) in [0;getWidth()[ X [0;getHeight()[
	 * 			(x != u || y != v)  implies
	 * 				getCellNature(x, y) == PLT 
	 */
	public void fill(int x, int y);
	

}
