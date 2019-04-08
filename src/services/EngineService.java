package services;


import java.util.ArrayList;

public interface EngineService {
	
	/**
	 * predicate definition:
	**/
	 
	
	/**
	 * observators const
	 */
	public EnvironmentService getEnvi();
	public PlayerService getPlayer();
	
	/**
	 * observators
	 */
	public ArrayList<GuardService> getGuards();
	public ArrayList<ItemService> getTreasures();
	public Status getStatus();
	public Command getNextCommand();
	
	/**
	 * invariants
	 * forall Guard g in getGuards()
	 * 		g in getEnvi().getCellContentChar(g.getWdt(),g.getHgt())
	 * forall Treasure t in getTreasures()
	 * 		g in getEnvi().getCellContentChar(t.getWdt(),t.getHgt())
	 * getTreasures().size() == 0 implies getStatus() = WIN
	 * Player p = getPlayer() in getEnvi().getCellContentChar(p.getWdt(), p.getHgt())
	 */

	/**
	 * constructors 
	 * pre : screen.isPlayable() 
	 * post: getEnvi().getScreen() == screen
	 * post: getPlayer.getWdt() == playerCoord.getX() && getPlayer().getHgt() == playerCoord.getY()
	 * post: forall (int x, int y) in guardsCoord,
	 * 			exists Guard g in getGuards() with g.getWdt() == x && g.getHgt() == y
	 * post: forall (int x, int y) in treasuresCoord,
	 * 			exists Treasure t in getTreasures() with t.getWdt() == x && t.getHgt() == y
	 * post: getStatus() == PLAYING
	 */
	public void init(EditableScreenService screen,Coordinates playerCoord,ArrayList<Coordinates> guardsCoord,
				ArrayList<Coordinates> treasuresCoord);
	
	
	/**
	 * Operators
	 */
	
	/**
	 * post: exists Guard g in getEnvi().getCellContentChar(getPlayer().getWdt()@pre, getPlayer().getHgt()@pre)@pre
	 * 			implies getStatus() == LOSS
	 * post: exists Treasure t in getEnvi().getCellContentItem(getPlayer().getWdt()@pre, getPlayer().getHgt()@pre)@pre
	 * 			implies not exists t in getTreasures()
	 */
	public void step();
}
