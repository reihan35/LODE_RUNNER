package contracts;

import java.util.ArrayList;

import decorators.EnvironmentDecorator;
import services.Cell;
import services.CharacterService;
import services.EditableScreenService;
import services.EnvironmentService;
import services.GuardService;
import services.ItemService;
import services.ItemType;
import services.ScreenService;

public class EnvironmentContract extends EnvironmentDecorator implements EnvironmentService {

	
	public EnvironmentContract(EnvironmentService delegates) {
		super(delegates);
		System.out.println(("yayra"));
	}

	/**
	 * pre : isInWindow(int x, int y) 
	 */

	@Override
	public ArrayList<ItemService> getCellContentItem(int x, int y) {
		if(!isInWindow(x, y)) {
			throw new PreconditionError("EnvironmentContract, getCellContentItem, isInWindow(x, y)");
		}
		return super.getCellContentItem(x, y);
	}

	/**
	 * pre : isInWindow(int x, int y) 
	 */
	@Override
	public ArrayList<CharacterService> getCellContentChar(int x, int y) {
		if(!isInWindow(x, y)) {
			throw new PreconditionError("EnvironmentContract, getCellContentChar, isInWindow(x, y)");
		}
		return super.getCellContentChar(x, y);
	}
	
	/*
	 * constructors
	 */
	
	/**
	 * 
	 * pre: s.isPlayable()
	 * 
	 * post: getWidth() == s.getWidth()
	 * post: getHeight() == s.getHeight()
	 * post: forall (x, y) in [0;getWidth()[,[0:getHeight()[,
	 * 			getCellNature(x, y) == s.getCellNature(x, y)
	 */
	public void init(EditableScreenService s) {
		System.out.println("dfsfds");
		if(!s.isPlayable()) {
			throw new PreconditionError("L'ecran n'est pas jouable");
		}
		super.init(s);
		if(s.getWidth() != getWidth()) {
			throw new PostconditionError("La largeur de l'environnement n'a pas été bien initialisée");
		}
		if(s.getHeight() != getHeight()) {
			throw new PostconditionError("La hauteur de l'environnement n'a pas été bien initialisée");
		}
		for(int x = 0; x < getWidth(); x++) {
			for(int y = 0; y < getHeight(); y++) {
				if(getCellNature(x, y) != s.getCellNature(x, y)) {
					throw new PostconditionError("L'état de l'environnement n'a pas été bien initialisé");
				}
			}
		}
	}
	
	
	/**
	 * invariants :
	 * inv : forall (int x, int y) in [0;getWidth()[ X [0;getHeight()[
	 *			forall (Character c1, Character c2) in (getCellContent(x,y), getCellContent(x,y)
	 *				c1 = c2
	 *
	 * inv: forall (int x, int y) in [0;getWidth()[ X [0;getHeight()[
	 * 			getCellNature(x,y) in {MTL, PLT} implies getCellContent(x,y)={}
	 * 
	 * inv: forall (int x, int y) in [0;getWidth()[ X [0;getHeight()[
	 * 			exists Treasure t in getCellContent(x,y) 
	 * 				implies getCellNature(x,y)) = EMP && getCellNature(x,y-1) in {PLT,MTL}
	 * 
	 **/
	public void checkInvariants() {
		for(int x = 0; x < getWidth(); x++) {
			for(int y = 0; y < getHeight(); y++) {
				for(CharacterService c1: getCellContentChar(x, y)) {
					for(CharacterService c2: getCellContentChar(x, y)) {
						if(c1 != c2 && (c1 instanceof GuardService) && (c2 instanceof GuardService)) {
							throw new InvariantError("2 personnages differents sur la même case");
						}
					}
				}
			}
		}
		for(int x = 0; x < getWidth(); x++) {
			for(int y = 0; y < getHeight(); y++) {
				Cell currCell = getCellNature(x, y);
				if(currCell == Cell.MTL || currCell == Cell.PLT){
					if(getCellContentItem(x, y).size() >  0) {
						throw new InvariantError("Une case MTL ou PLT ne peut pas contenir d'objet ou de personnage\n");
					}
				}
			}
		}
				
		for(int x = 0; x < getWidth(); x++) {
			for(int y = 0; y < getHeight(); y++) {
				boolean existTreasure = false;
				for(ItemService item:getCellContentItem(x, y)) {
					if(item.getNature() == ItemType.TREASURE) {
						existTreasure = true;
						break;
					}
				}
				if(existTreasure) {
					if(!(getCellNature(x, y) == Cell.EMP || (getCellNature(x, y-1) == Cell.PLT || getCellNature(x, y-1) == Cell.MTL))) {
						throw new InvariantError("Un tresor n'est pas situe sur une case vide ou n'est pas au dessus d'une plateforme alors qu'il le fallait ");
					}
				}
			}
		}
	
	}
	
	
	/*
	 * pre: isInWindow(int x, int y)
	 * 
	 * post: i in getCellContentChar(x, y)
	 * */

	public void addCellContentItem(int x, int y,ItemService i) {
		if(!isInWindow(x, y)) {
			throw new PreconditionError("La position specifiee n'est pas dans la fenetre");
		}
		checkInvariants();
		super.addCellContentItem(x, y, i);
		checkInvariants();
		if(!getCellContentItem(x, y).contains(i)) {
			throw new PostconditionError("L'objet n'a pas été correctement ajouté");
		}
	}
	
	/*
	 * pre: isInWindow(int x, int y)
	 * 
	 * c in getCellContentChar(x, y)
	 * */
	
	public void addCellContentChar(int x, int y, CharacterService c) {
		if(!isInWindow(x, y)) {
			throw new PreconditionError("La position specifiee n'est pas dans la fenetre");
		}
		checkInvariants();
		super.addCellContentChar(x, y, c);
		checkInvariants();
		if(!getCellContentChar(x, y).contains(c)) {
			throw new PostconditionError("Le personnage n'a pas été correctement ajouté dans sa case");
		}
	}
	
	/*
	 * pre: isInWindow(int x, int y)
	 * pre: i in getCellContentItem(x, y)
	 * pre : size(getCellContentItem(x, y)) > 0
	 * post: i not in getCellContentItem(x, y)
	 * */
	
	public void removeCellContentItem(int x , int y ,ItemService i) {
		if(!isInWindow(x, y)) {
			throw new PreconditionError("La position specifiee n'est pas dans la fenetre");
		}
		if(!getCellContentItem(x, y).contains(i)) {
			throw new PreconditionError("L'item voulu n'est pas dans la case");
		}
		if (getCellContentItem(x, y).size() == 0) {
			throw new PreconditionError("La case est vide !");
		}
		checkInvariants();
		super.removeCellContentItem(x, y, i);
		checkInvariants();
		if(getCellContentItem(x, y).contains(i)) {
			throw new PostconditionError("L'item n'a pas été enlevé de la case");
		}
	}
	
	
	/*
	 * pre: isInWindow(int x, int y)
	 * pre: i in getCellContentChar(x, y)
	 * pre : size(getCellContentChar(x, y)) > 0
	 * 
	 * post: i not in getCellContentChar(x, y)
	 * */
	public void removeCellContentChar(int x, int y, CharacterService c) {
		if(!isInWindow(x, y)) {
			throw new PreconditionError("La position specifiee n'est pas dans la fenetre");
		}
		/*if(!getCellContentChar(x, y).contains(c)) {
			throw new PreconditionError("Le personnage voulu n'est pas dans la case");
		}
		if (getCellContentChar(x, y).size() == 0) {
			throw new PreconditionError("La case ne contient pas de perso !");
		}*/
		checkInvariants();
		super.removeCellContentChar(x, y, c);
		checkInvariants();
		if(getCellContentChar(x, y).contains(c)) {
			throw new PostconditionError("Le personnage n'a pas été enlevé de la case");
		}
	}

}
