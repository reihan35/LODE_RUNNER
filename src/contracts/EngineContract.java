package contracts;

import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;

import components.Item;
import decorators.EngineDecorator;
import services.Cell;
import services.CharacterService;
import services.Command;
import services.Coordinates;
import services.Door;
import services.EditableScreenService;
import services.EngineService;
import services.EnvironmentService;
import services.GuardService;
import services.ItemService;
import services.ItemType;
import services.Stat;
import util.SetUtil;

public class EngineContract extends EngineDecorator implements EngineService{
	
	
	public EngineContract(EngineService delegates) {
		super(delegates);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(EnvironmentService screen,Coordinates playerCoord,ArrayList<Coordinates> guardsCoord,
			ArrayList<Coordinates> treasuresCoord,ArrayList<Coordinates> bombCoord, ArrayList<Door> doorCoord) {
		
		
		for(Door d:doorCoord) {
			Coordinates d1 = d.getIn();
			Coordinates d2 = d.getOut();
			Cell[] filledCell = {Cell.PLT, Cell.MTL};
			if(!SetUtil.isIn(screen.getCellNature(d1.getX(), d1.getY()-1), filledCell)){
				throw new PreconditionError("Erreur de porte 1");
			}
			if(! (screen.getCellNature(d1.getX(), d1.getY()) == Cell.EMP)){
				throw new PreconditionError("Erreur de porte 2");
			}
			
			if(!SetUtil.isIn(screen.getCellNature(d2.getX(), d2.getY()-1), filledCell)){
				throw new PreconditionError("Erreur de porte 3");
			}
			if(! (screen.getCellNature(d2.getX(), d2.getY()) == Cell.EMP)){
				throw new PreconditionError("Erreur de porte 4");
			}

		}
		
		for(Coordinates t: treasuresCoord) {
			Cell[] filledCell = {Cell.PLT, Cell.MTL};
			if(!SetUtil.isIn(screen.getCellNature(t.getX(), t.getY()-1), filledCell)){
				throw new PreconditionError("Erreur de tresor 1");
			}
			if(! (screen.getCellNature(t.getX(), t.getY()) == Cell.EMP)){
				throw new PreconditionError("Erreur de tresor 2");
			}
			if(t.getX() == playerCoord.getX() && t.getY() == playerCoord.getY()) {
				throw new PreconditionError("Erreur de tresor 3");
			}
		}
		
		for(Coordinates t: bombCoord) {
			Cell[] filledCell = {Cell.PLT, Cell.MTL};
			if(!SetUtil.isIn(screen.getCellNature(t.getX(), t.getY()-1), filledCell)){
				throw new PreconditionError("Erreur d'arme 1");
			}
			if(! (screen.getCellNature(t.getX(), t.getY()) == Cell.EMP)){
				throw new PreconditionError("Erreur d'arme 2");
			}
			if(t.getX() == playerCoord.getX() && t.getY() == playerCoord.getY()) {
				throw new PreconditionError("Erreur d arme 3");
			}
		}
		
		super.init(screen, playerCoord, guardsCoord, treasuresCoord,bombCoord, doorCoord);
		
		System.out.println("On est la !!!");
		System.out.println(getPlayer().getWdt());
		System.out.println(playerCoord.getX() );
		System.out.println(getPlayer().getWdt() != playerCoord.getX());
		if((getPlayer().getWdt() != playerCoord.getX() )||( getPlayer().getHgt() != playerCoord.getY())) {
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
		
		if ((getScore() != 0) && (getScore() == get_nb_first_tres()*10) && (getStatus() != Stat.WIN)) {
			 throw new InvariantError("La partie n'a pas ete gagnee alors que il y a plus de tresors");

		}
		
		//Player p = getPlayer() in getEnvi().getCellContentChar(p.getWdt(), p.getHgt())
		
		int x_play = getPlayer().getWdt();
		int y_play = getPlayer().getHgt();
		if (!getEnvi().getCellContentChar(x_play,y_play).contains(getPlayer())) {
			 throw new InvariantError("Le joueur n'est pas a la bonne place !");
		}
		
		if(getEnvi().getCellNature(getPlayer().getWdt(), getPlayer().getHgt()) == Cell.PLT && getStatus() != Stat.LOSS) {
				throw new InvariantError("Le joueur a perdu normalement");
		}

		
	}
	
	
	public void step() {
		
		int hgt_player_at_pre = getPlayer().getHgt();
		int wdt_player_at_pre = getPlayer().getWdt();
		boolean will_dig_right_atPre = getPlayer().willDigRight();
		boolean will_dig_left_atPre = getPlayer().willDigLeft();
		GuardService can_fight_at_pre = can_fight();
		ArrayList<CharacterService> cellContentChar_atPre = (ArrayList<CharacterService>) getEnvi().getCellContentChar(wdt_player_at_pre, hgt_player_at_pre).clone(); 
		ArrayList<ItemService> cellContentItem_atPre = (ArrayList<ItemService>) getEnvi().getCellContentItem(wdt_player_at_pre, hgt_player_at_pre).clone(); 
		int treasure_size_at_pre = getTreasures().size();
		int[][] holes_time_at_pre;
		holes_time_at_pre = new int[getEnvi().getWidth()][getEnvi().getHeight()];
		ArrayList<Coordinates> holes_at_pre = new ArrayList<Coordinates>();
		int bombs_size_at_pre = getBombs().size();
		for(int x = 0; x < getEnvi().getWidth(); x++) {
			for(int y = 0; y < getEnvi().getHeight(); y++) {
				if(getEnvi().getCellNature(x, y) == Cell.HOL) {
					holes_time_at_pre[x][y] = getHoles(x, y);
					holes_at_pre.add(new Coordinates(x, y));
				}
			}
		}
		checkInvariants();		
		super.step();
		checkInvariants();
		
		ArrayList<CharacterService> a = getEnvi().getCellContentChar(getPlayer().getWdt(), getPlayer().getHgt());
		for (CharacterService c : cellContentChar_atPre) {
			if (c instanceof GuardService && getStatus() != Stat.LOSS) {
				throw new PostconditionError("Le statu n'a pas mis a loos alors que le player a perdu");
			}
		}
		if (can_fight_at_pre != null) {
			for(ItemService it: cellContentItem_atPre) {
				System.out.println(getTreasures().size());
				System.out.println(treasure_size_at_pre - 1);
				if (it.getNature() == ItemType.TREASURE && getTreasures().size() != treasure_size_at_pre - 1 ) {
					throw new PostconditionError("Le tresor n'a pas ete enleve de l'environnement");
				}
				if (it.getNature() == ItemType.BOMB && getBombs().size() != bombs_size_at_pre - 1) {
					throw new PostconditionError("L'arme n'a pas ete enleve de l'environnement");
				}
			}
		}
		
		for(Coordinates c : holes_at_pre) {
			System.out.println( getHoles(c.getX(), c.getY()));
			System.out.println(holes_time_at_pre[c.getX()][c.getY()]);
			if (holes_time_at_pre[c.getX()][c.getY()] == -1 && getHoles(c.getX(), c.getY()) != 1 ) {
				throw new PostconditionError("blablabla Un des trous est rebouche alors qu'il ne le fallait pas !");
			}
			else {
				System.out.println( getHoles(c.getX(), c.getY()));
				System.out.println(holes_time_at_pre[c.getX()][c.getY()]);
				if (holes_time_at_pre[c.getX()][c.getY()] < 48 && holes_time_at_pre[c.getX()][c.getY()] != -1 && holes_time_at_pre[c.getX()][c.getY()] != getHoles(c.getX(), c.getY()) -1 ) {
					throw new PostconditionError("Un des trous est rebouche alors qu'il ne le fallait pas !");
				}
				else {
					if(holes_time_at_pre[c.getX()][c.getY()] == 49 && getEnvi().getCellNature(c.getX(), c.getY()) == Cell.HOL){
						throw new PostconditionError("Un des trous n'a pas ete rebouche alors qu'il le fallait");
					}
				}
			}
		}
	}
	
}
