package Projet.src.services;


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
	 * forall (x, y) in getGuards() implies ItemService(x,y) in getEnvi().getCellContent(x,y)
	 * getTreasures().size() = 0 implies Status = Win
	 */
	
	/**
	 * constructors //JE SAIS PAS CMT DIRE CHAQUE XY DOIT ETRE UNIQUE 
	 * pre : screen.isPlayble() &&  forall (x, y) in guards 
 	 *								implies getCellNature(x, y) == EMP
 	 *								&& forall (x, y) in treasures 
 	 *								implies getCellNature(x, y-1) in {PLT, MTL}
 	 * post : forall (x, y) in Treasures 
 	 * 			(x==player.getX() && y==player.getY()) implies treasures = treasures@pre\(x,y) 
	 */
	public void init(EditableScreenService screen,Coordinates player,ArrayList<Coordinates> guards,
				ArrayList<Coordinates> Treasures);
	
	
	/**
	 * Operators
	 */
	
	public void step();
}
