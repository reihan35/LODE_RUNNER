package contracts;

import java.util.ArrayList;

import decorators.EnvironmentDecorator;
import services.Cell;
import services.CharacterService;
import services.EnvironmentService;
import services.ItemService;
import services.ItemType;
import services.ScreenService;

public class EnvironmentContract extends EnvironmentDecorator implements EnvironmentService {

	
	public EnvironmentContract(EnvironmentService delegates) {
		super(delegates);
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
	
	
	/**
	 * invariants :
	 * inv : forall (int x, int y) in [0;getWidth()[ X [0;getHeight()[
	 *			forall (Character c1, Character c2) in (getCellContent(x,y), getCellContent(x,y)
	 *				c1 = c2
	 *
	 * inv:�forall (int x, int y) in [0;getWidth()[ X [0;getHeight()[
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
						if(c1 != c2) {
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
					if(!(getCellNature(x, y) == Cell.EMP && (getCellNature(x, y-1) == Cell.PLT || getCellNature(x, y-1) == Cell.MTL))) {
						throw new InvariantError("Un tresor n'est pas situe sur une case vide ou n'est pas au dessus d'une plateforme");
					}
				}
			}
		}
	
	}

}
