package services;

import java.util.ArrayList;

import components.Player;

public interface EnvironmentService extends EditableScreenService {
	/**
	 * observators
	 */
	
	
	/**
	 * pre : isInWindow(int x, int y) 
	 */
	
	ArrayList<ItemService> getCellContentItem(int x, int y);
	
	/**
	 * pre : isInWindow(int x, int y) 
	 */
	
	ArrayList<CharacterService> getCellContentChar(int x, int y);
	
	/*
	 * DES PRES ET POST A RAJOUTE !
	 * */

	void addCellContentItem(int x, int y,ItemService i);
	
	/*
	 * DES PRES ET POST A RAJOUTE !
	 * */
	
	void addCellContentChar(int x, int y, CharacterService c);
	
	/*
	 * DES PRES ET POST A RAJOUTE !
	 * */
	
	void removeCellContentItem(int x , int y ,ItemService i);
	
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
	
}

