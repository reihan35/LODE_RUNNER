package projet.contracts;

import projet.decorators.EditableScreenDecorator;
import projet.services.Cell;
import projet.services.EditableScreenService;

public class EditableScreenContract extends EditableScreenDecorator implements EditableScreenService {

	//probleme: il faut heriter du contract screen par la relation include entre les deux specifs
	// il faut egalement heriter du decorator

	/**
	 * invariants
	 * inv : Playable() = \forall (x, y) in [0;getWidth()[ X [0;getHeight()[
	 * 						getCellNature(x, y) != HOL 
	 * 					&& \forall x in [0;getWidth()[
	 * 						getCellNature(x,0) == MTL
	 *
	 **/
	public void checkInvariants() {
		for (int i=0; i<getWidth()-1; i++)
			for(int j=0; j<getHeight()-1; j++) 
				if(!(getCellNature(i, j) !=Cell.HOL)) {
					throw new InvariantError("cellNature ne doit pas etre un trou");
				}
		
		for (int i=0; i<getWidth()-1; i++)
			if(!(getCellNature(i, 0) !=Cell.MTL)) {
				throw new InvariantError("le niveau 0 de l'ecran doit comporter uniquement des plateformes en metal");
			}
		
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
	@Override
	public void setNature(int x, int y, Cell c) {
		if(!isInWindow(x, y)) {
			throw new PreconditionError("isInWindow(x, y)");
		}
		checkInvariants();
		
		Cell[][] getCellNature_atPre = new Cell[getWidth()][getHeight()];
		for (int i=0; i<getWidth()-1; i++)
			for(int j=0; j<getHeight()-1; j++)
				getCellNature_atPre[i][j] = getCellNature(i, j);
		
		super.setNature(x, y, c);
		checkInvariants();
		
		if(!(getCellNature(x, y) == c)) {
			throw new PostconditionError("l'etat de la cellule n'a pas la valeur voulue");
		}
		for (int i=0; i<getWidth()-1; i++)
			for(int j=0; j<getHeight()-1; j++)
				if(!(getCellNature_atPre[i][j] == getCellNature(i, j))) {
					throw new PostconditionError("ScreenContract, dig, d'autres cellules que la cellule du perso ont changé");
		}
		

	}

}
