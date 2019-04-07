package Projet.src.services;


public interface ItemService extends CharacterService {
	/**
	 * observators const
	 */
	
	public int getId();
	public ItemType getNature();
	
	/**
	 * Constuctor
	 **/
	
	public void init(int id,ItemType type,int hgt,int col);
}
