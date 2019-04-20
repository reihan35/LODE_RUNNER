package services;

import java.util.ArrayList;

import components.Player;

public interface EnvironmentService extends ScreenService {
	/**
	 * observators
	 */
	
	
	/**
	 * 
	 * pre: s.isPlayable()
	 * post: getWidth() == s.getWidth()
	 * post: getHeight() == s.getHeight()
	 * post: forall (x, y) in [0;getWidth()[,[0:getHeight()[,
	 * 			getCellNature(x, y) == s.getCellNature(x, y)
	 */
	public void init(EditableScreenService s);
	
	/**
	 * pre : isInWindow(int x, int y) 
	 */
	
	public ArrayList<ItemService> getCellContentItem(int x, int y);
	
	/**
	 * pre : isInWindow(int x, int y) 
	 */
	
	public ArrayList<CharacterService> getCellContentChar(int x, int y);
	
	/*
	 * pre: isInWindow(int x, int y)
	 * 
	 * post: i in getCellContentChar(x, y)
	 * */

	void addCellContentItem(int x, int y,ItemService i);
	
	/*
	 * pre: isInWindow(int x, int y)
	 * 
	 * c in getCellContentChar(x, y)
	 * */
	
	void addCellContentChar(int x, int y, CharacterService c);
	
	/*
	 * pre: isInWindow(int x, int y)
	 * pre: i in getCellContentItem(x, y)
	 * pre : size(getCellContentItem(x, y)) > 0
	 * post: i not in getCellContentItem(x, y)
	 * */
	
	public void removeCellContentItem(int x , int y ,ItemService i);
	
	
	/*
	 * pre: isInWindow(int x, int y)
	 * pre: i in getCellContentChar(x, y)
	 * pre : size(getCellContentChar(x, y)) > 0
	 * 
	 * post: i not in getCellContentChar(x, y)
	 * */
	public void removeCellContentChar(int x, int y, CharacterService c);

	
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

