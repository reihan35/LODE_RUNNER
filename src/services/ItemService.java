package services;


public interface ItemService {
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
