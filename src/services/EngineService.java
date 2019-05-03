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
	public Stat getStatus();
	public Command getNextCommand();
	public int getHoles(int x, int y);
	public void addCommand(Command c);
	
	/**
	 * invariants
	 * forall Guard g in getGuards()
	 * 		g in getEnvi().getCellContentChar(g.getWdt(),g.getHgt())
	 * forall Treasure t in getTreasures()
	 * 		g in getEnvi().getCellContentChar(t.getWdt(),t.getHgt())
	 * forall (Treasure t1, Treasure t2) in (getTreasures()*getTreasures), (t1.getWdt() == t2.getWdt && t1.getHgt() == t2.getHgt())
	 * 																			implies t1 == t2
	 * getTreasures().size() == 0 implies getStatus() = WIN
	 * Player p = getPlayer() in getEnvi().getCellContentChar(p.getWdt(), p.getHgt())
	 */

	/**
	 * constructors 
	 * post: getPlayer.getWdt() == playerCoord.getX() && getPlayer().getHgt() == playerCoord.getY()
	 * post: forall (int x, int y) in guardsCoord,
	 * 			exists Guard g in getGuards() with g.getWdt() == x && g.getHgt() == y
	 * post: forall (int x, int y) in treasuresCoord,
	 * 			exists Treasure t in getTreasures() with t.getWdt() == x && t.getHgt() == y
	 * post: getStatus() == PLAYING
	 */
	public void init(EnvironmentService screen,Coordinates playerCoord,ArrayList<Coordinates> guardsCoord,
				ArrayList<Coordinates> treasuresCoord,ArrayList<Coordinates> bombCoord);
	
	
	/**
	 * Operators
	 */
	
	/**
	 * post: exists Guard g in getEnvi().getCellContentChar(getPlayer().getWdt()@pre, getPlayer().getHgt()@pre)@pre
	 * 			implies getStatus() == LOSS
	 * post: exists Treasure t in getEnvi().getCellContentItem(getPlayer().getWdt()@pre, getPlayer().getHgt()@pre)@pre
	 * 			implies not exists t in getTreasures()
	 * post : forall (x, y) in [0;getWidth()[ X [0;getHeight()[ 
	 * 			&& getCellNature(x, y) == HOL implies getHoles(x,y) == Holes(x,y)@pre + 1
	 * 
	 * post : forall (x, y) in [0;getWidth()[ X [0;getHeight()[ 
	 * 			&& getCellNature(x, y) == HOL && getHoles(x,y) == 15 implies getCellNature(x, y) == PLT 
	 * 															 && getPlayer.getHgt() == x
	 * 															 &&	getPlayer.getWdt() == y
	 * 															 implies getStatus() = Status.LOSS
	 * 
	 * post:Player p = getPlayer() in p.willDigRight()@pre implies getHoles(p.getWdt()@pre+1,p.getHgt()@pre-1) = 0
	 * 
	 * post: Player p = getPlayer() in p.willDigLeft()@pre implies getHoles(p.getWdt()@pre-1,p.getHgt()@pre-1) = 0
	 */
	public void step();
	void addTreasure(int wdt, int hgt);
	void removeTreasure();
	int getScore();
	public ArrayList<ItemService> getBombs();
}
