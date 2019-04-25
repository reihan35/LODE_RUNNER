package contracts;

import java.util.ArrayList;

import com.sun.org.apache.xerces.internal.util.Status;

import components.Item;
import decorators.EngineDecorator;
import services.Cell;
import services.CharacterService;
import services.Command;
import services.Coordinates;
import services.EditableScreenService;
import services.EngineService;
import services.EnvironmentService;
import services.GuardService;
import services.ItemService;
import services.ItemType;
import services.Stat;

public class EngineContract extends EngineDecorator implements EngineService{
	
	
	public EngineContract(EngineService delegates) {
		super(delegates);
		// TODO Auto-generated constructor stub
	}


	public void init(EnvironmentService screen,Coordinates playerCoord,ArrayList<Coordinates> guardsCoord,
			ArrayList<Coordinates> treasuresCoord) {
		
		super.init(screen, playerCoord, guardsCoord, treasuresCoord);
		
		
		if(getPlayer().getWdt() != playerCoord.getX() || getPlayer().getHgt() != playerCoord.getY()) {
			System.out.println(playerCoord.getX()+ " " + playerCoord.getY());
			System.out.println(playerCoord.getX()+ " " + playerCoord.getY());
			throw new PostconditionError("La position initiale du joueur ne correspond pas a celle passee en parametre ! ");
		}
		
		for(int i = 0 ; i < getTreasures().size() ; i++) {
			int hgt = getTreasures().get(i).getHgt();
			int wdt = getTreasures().get(i).getWdt();
			if (!getEnvi().getCellContentItem(wdt,hgt).contains(getTreasures().get(i))){
				throw new PostconditionError("L'un des tresors n'est pas a la bonne place !");
			}
		}
		
		if(getStatus() != Stat.PLAYING) {
			throw new PostconditionError("Le jeu n'est pas en mode playing apres l'initialisation !");
		}
	
	}
	
	
	/**
	 * invariants
	 * forall Guard g in getGuards()
	 * 		g in getEnvi().getCellContentChar(g.getWdt(),g.getHgt())
	 * forall Treasure t in getTreasures()
	 * 		t in getEnvi().getCellContentChar(t.getWdt(),t.getHgt())
	 * forall (Treasure t1, Treasure t2) in (getTreasures()*getTreasures), (t1.getWdt() == t2.getWdt && t1.getHgt() == t2.getHgt())
	 * 																			implies t1 == t2
	 * getTreasures().size() == 0 implies getStatus() = WIN
	 * Player p = getPlayer() in getEnvi().getCellContentChar(p.getWdt(), p.getHgt())
	 * Player p = getPlayer in p.willDigRight() implies Holes(p.getWdt()+1,p.getHgt()-1) = 0
	 * Player p = getPlayer in p.willDigLeft() implies Holes(p.getWdt()-1,p.getHgt()-1) = 0
	 */
	public void checkInvariants() {
		
		
		 /* forall Guard g in getGuards()
		 * 		g in getEnvi().getCellContentChar(g.getWdt(),g.getHgt())
		 */
		for(GuardService g: getGuards()) {
			int hgt = g.getHgt();
			int wdt = g.getWdt();
			 if (!getEnvi().getCellContentChar(wdt,hgt).contains(g)) {
				 throw new InvariantError("Il n'y a pas le garde dans l'environnement");
			 }
			
		}
		
		 
		/*forall Treasure t in getTreasures()
	 	*	t in getEnvi().getCellContentChar(t.getWdt(),t.getHgt())
	 	*/
		for(ItemService t: getTreasures()) {
			int hgt = t.getHgt();
			int wdt = t.getWdt();
			 if (!getEnvi().getCellContentItem(wdt,hgt).contains(t)) {
				 throw new InvariantError("Le tresor n'est pas present dans l'environnement !");
			 }
			
		}
		
		 /* forall (Treasure t1, Treasure t2) in (getTreasures()*getTreasures), (t1.getWdt() == t2.getWdt && t1.getHgt() == t2.getHgt())
		 * 																			implies t1 == t2
		*/
		for(ItemService t1: getTreasures()) {
			for(ItemService t2: getTreasures()) {
				if(t1.getWdt() == t2.getWdt() && t1.getHgt() == t2.getHgt()) {
					if(!(t1==t2)) {
						throw new InvariantError("2 tresors sur la meme case");
					}
				}
			}

		}
		//getTreasures().size() == 0 implies getStatus() = WIN
		if ((getTreasures().size() == 0) && (getStatus() != Stat.WIN)) {
			 throw new InvariantError("La partie n'a pas ete gagnee alors que il y a plus de tresors");
		}
		
		//Player p = getPlayer() in getEnvi().getCellContentChar(p.getWdt(), p.getHgt())
		
		int x_play = getPlayer().getWdt();
		int y_play = getPlayer().getHgt();
		if (!getEnvi().getCellContentChar(x_play,y_play).contains(getPlayer())) {
			 throw new InvariantError("Le joueur n'est pas � la bonne place !");
		}
		
		
	}
	
	
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
	 * post:Player p = getPlayer() in p.willDigRight()@pre implies Holes(p.getWdt()@pre+1,p.getHgt()-1) = 0
	 * 
	 * post: Player p = getPlayer() in p.willDigLeft()@pre implies Holes(p.getWdt()@pre-1,p.getHgt()@pre-1) = 0
	 */
	public void step() {
		
		int hgt_player_at_pre = getPlayer().getHgt();
		int wdt_player_at_pre = getPlayer().getWdt();
		boolean will_dig_right_atPre = getPlayer().willDigRight();
		boolean will_dig_left_atPre = getPlayer().willDigLeft();
		
		ArrayList<CharacterService> cellContentChar_atPre = (ArrayList<CharacterService>) getEnvi().getCellContentChar(wdt_player_at_pre, hgt_player_at_pre).clone(); 
		ArrayList<ItemService> cellContentItem_atPre = (ArrayList<ItemService>) getEnvi().getCellContentItem(wdt_player_at_pre, hgt_player_at_pre).clone(); 

		super.step();
		
		/* post: exists Guard g in getEnvi().getCellContentChar(getPlayer().getWdt()@pre, getPlayer().getHgt()@pre)@pre
		 * 			implies getStatus() == LOSS
		 */
		
		//only one player so 2 at the same place means at least one is guard
		if(cellContentChar_atPre.size()>1) {
			if(getStatus() != Stat.LOSS) {
				throw new PostconditionError("La partie devrait etre perdue");
			}
		}
		/* post: exists Treasure t in getEnvi().getCellContentItem(getPlayer().getWdt()@pre, getPlayer().getHgt()@pre)@pre
		 * 			implies not exists t in getTreasures()
		 */
		
		for(ItemService it: cellContentItem_atPre) {
			if (it.getNature() == ItemType.TREASURE) {
				for(ItemService treas:getTreasures()) {
					if(it.getWdt() == treas.getWdt() && it.getHgt() == treas.getWdt()) {
						throw new PostconditionError("Le tresor n'a pas ete enleve de la liste des tresors");
					}
				}
				for(ItemService obj:getEnvi().getCellContentItem(wdt_player_at_pre, hgt_player_at_pre)) {
					if(obj == it) {
						throw new PostconditionError("Le tresor n'a pas ete enleve de l'environnement");
					}
				}
				break;
			}
		}
		
		/* post : forall (x, y) in [0;getWidth()[ X [0;getHeight()[ 
		 * 			&& getCellNature(x, y) == HOL implies getHoles(x,y) == Holes(x,y)@pre + 1
		 */
		
		
		/*	  post : forall (x, y) in [0;getWidth()[ X [0;getHeight()[ 
		 * 			&& getCellNature(x, y) == HOL && getHoles(x,y) == 15 implies getCellNature(x, y) == PLT 
		 * 															 && getPlayer.getHgt() == x
		 * 															 &&	getPlayer.getWdt() == y
		 * 															 implies getStatus() = Status.LOSS
		 */
		
		
		 //post:Player p = getPlayer() in p.willDigRight()@pre implies getHoles(p.getWdt()@pre+1,p.getHgt()-1) = 0
		if(will_dig_right_atPre) {
			if(getHoles(wdt_player_at_pre+1, hgt_player_at_pre-1) != 0) {
				throw new PostconditionError("Le timelapse du trou n'a pas été mis à 0 alors que le joueur a du creuser a droite");
			}
		}
		//post: Player p = getPlayer() in p.willDigLeft()@pre implies getHoles(p.getWdt()@pre-1,p.getHgt()@pre-1) = 0
		if(will_dig_left_atPre) {
			if(getHoles(wdt_player_at_pre-1, hgt_player_at_pre-1) != 0) {
				throw new PostconditionError("Le timelapse du trou n'a pas été mis à 0 alors que le joueur a du creuser a gauche");
			}
		}
	}
	
	
}
