package contracts;

import decorators.ScreenDecorator;
import services.Cell;
import services.ScreenService;

public class ScreenContract extends ScreenDecorator {

	

	public ScreenContract(ScreenService delegates) {
		super(delegates);
	}

	/**
	 * observators
	 */
	/**
	 * retourne l'etat de la cellule de coordonnees (x, y)
	 * pre: isInWindow(x, y)
	 */
	public Cell getCellNature(int x, int y) {
		if(!isInWindow(x, y)) {
			throw new PreconditionError("ScreenContract, getCellNature, isInWindow(x, y)");
		}
		return super.getCellNature(x, y);
	}
	
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
	public void init(int h, int w) {
		if(!(0<h)) {
			throw new PreconditionError("ScreenContract, init, 0<h");
		}
		if(!(0<w)) {
			throw new PreconditionError("ScreenContract, init, 0<w");
		}
	super.init(h, w);
	
	for (int x=0; x<getWidth()-1; x++)
		for(int y=0; y<getHeight()-1; y++)
			if(!(getCellNature(x, y) == Cell.EMP)) {
				throw new PostconditionError("ScreenContract, init, toutes les cases n'ont pas ete initialisees a vide");
			}
	}
	
	/**
	 * operators
	 */
	
	/**								
	 * creuse un trou dans la cellule de coordonnes (x, y)
	 * post: getCellNature(x, y) == HOL
	 * post: forall (x, y) in [0;getWidth()[ X [0;getHeight()[
 	 *			(x != u || y != v)  
 	 *			implies getCellNature(x, y) == getCellNature(x, y)@pre
	 */
	public void dig(int x, int y) {
		int getHeight_atPre = getHeight();
		int getWidth_atPre = getWidth();
		
		Cell[][] getCellNature_atPre = new Cell[getWidth()][getHeight()];
		for (int i=0; i<getWidth()-1; i++)
			for(int j=0; j<getHeight()-1; j++)
				getCellNature_atPre[i][j] = getCellNature(i, j);
		
		super.dig(x, y);
		if(getHeight_atPre != getHeight() || getWidth_atPre != getWidth()) {
			throw new InvariantError("ScreenContract, dig, les valeurs constantes ont changé");
		}
		
		if(!(getCellNature(x, y) == Cell.HOL)) {
			throw new PostconditionError("ScreenContract, dig, la case n'est pas un trou apres avoir creuse");
		}
		for (int i=0; i<getWidth()-1; i++)
			for(int j=0; j<getHeight()-1; j++)
				if(!(getCellNature_atPre[i][j] == getCellNature(i, j))) {
					throw new PostconditionError("ScreenContract, dig, d'autres cellules que la cellule du perso ont changé");
		}
	}
	
	/**								
	 * remplit un trou dans la cellule de coordonnes (x, y)
	 * post: getCellNature(x, y) == PLT
	 * post: forall (x, y) in [0;getWidth()[ X [0;getHeight()[
 	 *			(x != u || y != v)  
 	 *			implies getCellNature(x, y) == getCellNature(x, y)@pre
	 */
	public void fill(int x, int y) {
		int getHeight_atPre = getHeight();
		int getWidth_atPre = getWidth();
		
		Cell[][] getCellNature_atPre = new Cell[getWidth()][getHeight()];
		for (int i=0; i<getWidth()-1; i++)
			for(int j=0; j<getHeight()-1; j++)
				getCellNature_atPre[i][j] = getCellNature(i, j);
		
		super.fill(x, y);
		
		if(getHeight_atPre != getHeight() || getWidth_atPre != getWidth()) {
			throw new InvariantError("ScreenContract, fill, les valeurs constantes ont changé");
		}
		if(!(getCellNature(x, y) == Cell.PLT)) {
			throw new PostconditionError("ScreenContract, fill, la case n'est pas une plateforme apres remplissage");
		}
		
		for (int i=0; i<getWidth()-1; i++)
			for(int j=0; j<getHeight()-1; j++)
				if(!(getCellNature_atPre[i][j] == getCellNature(i, j))) {
					throw new PostconditionError("ScreenContract, dig, d'autres cellules que la cellule du perso ont changé");
		}
	}
	
}
