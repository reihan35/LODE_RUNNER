package contracts;

import java.util.ArrayList;

import components.Guard;
import components.Player;
import decorators.GuardDecorator;
import services.Cell;
import services.CharacterService;
import services.Command;
import services.EngineService;
import services.EnvironmentService;
import services.GuardService;
import services.ItemService;
import services.Move;
import services.PlayerService;
import util.SetUtil;

public class GuardContrtact extends GuardDecorator implements GuardService {

	public GuardContrtact(GuardService delegates) {
		super(delegates);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * invariants
	 * inv: getEnvi().getCellNature(getWdt(),getHgt()) = LAD && getHgt() < getTarget().getHgt() 
	 * 		implies getBehaviour()=Up
	 * 
	 * inv: getEnvi().getCellNature(getWdt(),getHgt()) = LAD && getHgt() > getTarget().getHgt() 
	 * 		implies getBehaviour()=DOWN
	 *
	 * * inv: getEnvi().getCellNature(getWdt(),getHgt()) = LAD && getHgt() = getTarget().getHgt() 
	 * 		implies getBehaviour()=NEUTRAL
	 *
	 * inv: getEnvi().getCellNature(getWdt(),getHgt()) in {LAD, HDR} 
	 * 		|| not isFreeCell(getWdt()@pre, getHgt() - 1)
	 * 		|| isFreeCell(getWdt()@pre, getHgt() - 1) &&  characterAt(getWdt(), getHgt()-1)
	 * 		implies getTarget().getWdt() < getWdt() implies getBehaviour()=LEFT
	 * 
	 * inv: getEnvi().getCellNature(getWdt(),getHgt()) in {LAD, HDR} 
	 * 		|| not isFreeCell(getWdt()@pre, getHgt() - 1)
	 * 		|| isFreeCell(getWdt()@pre, getHgt() - 1) &&  characterAt(getWdt(), getHgt()-1)
	 * 		implies getTarget().getWdt() > getWdt() implies getBehaviour()=RIGHT
	 * 
	 * inv: getEnvi().getCellNature(getWdt(),getHgt()) in {LAD, HDR} 
	 * 		|| not isFreeCell(getWdt()@pre, getHgt() - 1)
	 * 		|| isFreeCell(getWdt()@pre, getHgt() - 1) &&  characterAt(getWdt(), getHgt()-1)
	 * 		implies getTarget().getWdt() = getWdt() implies getBehaviour()=NEUTRAL
	 * 
	 *  inv: getEnvi().getCellNature(getWdt(),getHgt()) = LAD && getHgt()< getTarget().getHgt() 
	 * 		&& getEnvi().getCellNature(getWdt(),getHgt()-1) not in {PLT,MTL} 
	 * 		|| characterAt(getWdt(), getHgt()-1) 
	 * 		implies getTarget().getHgt()-getHgt() < getTarget().getWdt() implies getBehaviour()=Up
	 * 
	 *  inv: getEnvi().getCellNature(getWdt(),getHgt()) = LAD && getHgt()>getTarget().getHgt() 
	 * 		&& getEnvi().getCellNature(getWdt(),getHgt()-1) not in {PLT,MTL} 
	 * 		|| characterAt(getWdt(), getHgt()-1) 
	 * 		implies getTarget().getHgt()-getHgt() < getTarget().getWdt() implies getBehaviour()=Down
	 *  
	 *  inv: getEnvi().getCellNature(getWdt(),getHgt()) = LAD && getWdt()>getTarget().getWdt() 
	 * 		&& getEnvi().getCellNature(getWdt(),getHgt()-1) not in {PLT,MTL} 
	 * 		|| characterAt(getWdt(), getHgt()-1) 
	 * 		implies getTarget().getHgt()-getHgt() > getTarget().getWdt() implies getBehaviour()=RIGHT
	 * 
	 *  inv: getEnvi().getCellNature(getWdt(),getHgt()) = LAD && getWdt()<getTarget().getWdt() 
	 * 		&& getEnvi().getCellNature(getWdt(),getHgt()-1) not in {PLT,MTL} 
	 * 		|| characterAt(getWdt(), getHgt()-1) 
	 * 		implies getTarget().getHgt()-getHgt() > getTarget().getWdt() implies getBehaviour()=LEFT
	**/

	
	public void checkInvariants() {
	}
	
	@Override
	public void climbRight() {
		if (getEnvi().getCellNature(getWdt(), getHgt()) != Cell.HOL){
			throw new PreconditionError("La case du gardien n'est pas un trou");
		}
		
		int wdt_at_pre = getWdt();
		int hgt_at_pre = getHgt();
		
		super.climbRight();
		
		if(GuardAt(wdt_at_pre+1, hgt_at_pre+1) && (getWdt() != wdt_at_pre || getHgt() != hgt_at_pre )){
			throw new PostconditionError("Le gardien ne peut pas remonter car la case en haut a droite contien un autre gardien");
		}
		
		Cell cell = getEnvi().getCellNature(wdt_at_pre + 1,getHgt() + 1); 
		Cell[] emp ={Cell.MTL, Cell.PLT};

		
		if(wdt_at_pre != 0 && SetUtil.isIn(cell, emp) && (GuardAt(wdt_at_pre+1, hgt_at_pre+1) && (getWdt() != wdt_at_pre + 1 || getHgt() != hgt_at_pre + 1))){
			throw new PostconditionError("Le gardien n'a pas pu remonte alors qu'il aurait du");
		}
	}

	@Override
	public void climbLeft() {
		if (getEnvi().getCellNature(getWdt(), getHgt()) != Cell.HOL){
			throw new PreconditionError("La case du gardien n'est pas un trou");
		}
		
		int wdt_at_pre = getWdt();
		int hgt_at_pre = getHgt();
		
		
		super.climbLeft();
		
		if(GuardAt(wdt_at_pre-1, hgt_at_pre+1) && (getWdt() != wdt_at_pre || getHgt() != hgt_at_pre )){
			throw new PostconditionError("Le gardien ne peut pas remonter car la case en haut a droite contien un autre gardien");
		}
		
		Cell cell = getEnvi().getCellNature(wdt_at_pre + 1,getHgt() + 1); 
		Cell[] emp ={Cell.MTL, Cell.PLT};

		
		if(wdt_at_pre != 0 && SetUtil.isIn(cell, emp) && (GuardAt(wdt_at_pre-1, hgt_at_pre+1) && (getWdt() != wdt_at_pre - 1 || getHgt() != hgt_at_pre + 1))){
			throw new PostconditionError("Le gardien n'a pas pu remonte alors qu'il aurait du");
		}
	}
	

	
	@Override
	public void goDown() {
		
		boolean g_atPre = false;
		int wdt_atPre = getWdt();
		int hgt_atPre = getHgt();
		if(getHgt()>0)
			g_atPre = GuardAt(getWdt(), getHgt()-1);
		super.goDown();
		
		if(getHgt()> 0 && g_atPre && (getWdt() == wdt_atPre && getHgt() == hgt_atPre)) {
			throw new PostconditionError("La position du garde a change alors qu'il y a un autre garde en dessous de lui");
		}
	}
	
	@Override
	public void goUp() {
		
		boolean g_atPre = false;
		int wdt_atPre = getWdt();
		int hgt_atPre = getHgt();
		if(getHgt() != getEnvi().getHeight()-1)
			g_atPre = GuardAt(getWdt(), getHgt()+1);
		super.goUp();
		
		if(getHgt() != getEnvi().getHeight()-1 && g_atPre && (getWdt() == wdt_atPre && getHgt() == hgt_atPre)) {
			throw new PostconditionError("La position du garde a change alors qu'il y a un autre garde au dessus de lui");
		}
	}
	
	@Override
	public void goLeft() {
		
		boolean g_atPre = false;
		int wdt_atPre = getWdt();
		int hgt_atPre = getHgt();
		if(getWdt()>0)
			g_atPre = GuardAt(getWdt()-1, getHgt());
		super.goLeft();
		
		if(getWdt() != 0 && g_atPre && (getWdt() == wdt_atPre && getHgt() == hgt_atPre)) {
			throw new PostconditionError("La position du garde a change alors qu'il y a un autre garde � sa gauche");
		}
	}
	
	@Override
	public void goRight() {
		
		boolean g_atPre = false;
		int wdt_atPre = getWdt();
		int hgt_atPre = getHgt();
		if(getWdt()!= getEnvi().getWidth()-1)
			g_atPre = GuardAt(getWdt()+1, getHgt());
		super.goRight();
		
		if(getWdt() != getEnvi().getWidth()-1 && g_atPre && (getWdt() == wdt_atPre && getHgt() == hgt_atPre)) {
			throw new PostconditionError("La position du garde a change alors qu'il y a un autre garde � sa droite");
		}
	}
	@Override
	public void step() {

		EngineService engine_at_pre = getEngine();
		PlayerService p_at_pre = getPlayer();
		GuardService g = new Guard();
		g.init(engine_at_pre,getWdt(),getHgt(),p_at_pre);
		int t_at_pre = -10000;
		if (getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HOL){
			t_at_pre = getEngine().getHoles(getWdt(), getHgt());
		}
		boolean will_reinitialize_atPre = willReinitialize();
		boolean will_grab_treasure_atPre = willGrabTreasure();
		ArrayList<ItemService> treasure_atPre = (ArrayList<ItemService>) getEnvi().getCellContentItem(getWdt(), getHgt()).clone();
		int wdt_atPre = getWdt();
		int hgt_atPre = getHgt();
		int nbTreasures_on_map_atPre = getEngine().getTreasures().size();
		Move behaviour = getBehaviour();
		super.step();
		

		if (engine_at_pre != getEngine()) {
			throw new InvariantError("Le moteur de jeu ne doit pas changer ! ");
		}
		
		
		if(will_grab_treasure_atPre) {
			if(treasure_atPre.size() != getEnvi().getCellContentChar(wdt_atPre, hgt_atPre).size() + 1 ) {
				throw new PostconditionError("Tresor non enlev� de l'environnement par le garde");
			}
			if(nbTreasures_on_map_atPre != getEngine().getTreasures().size()+1) {
				throw new PostconditionError("le tresor n'a pas �t� enlev� de l'engine");
			}
		}
		if(will_reinitialize_atPre) {
			

			if (getFirst_y() != getHgt())  {
				throw new PostconditionError("Le gardien ne s'est pas reinitialise ");
			}
			
			
			if (getFirst_x() != getWdt())  {
				throw new PostconditionError("Le gardien ne s'est pas reinitialise ");
			}
			return;
		}
		
		if (!willMove()) return;
		if(g.willClimbLeft()) {
			
			g.climbLeft();
			
			if (g.getHgt() != getHgt())  {
				throw new PostconditionError("Le gardien n'est pas sorti du troue ! ");
			}
			
			
			if (g.getWdt() != getWdt())  {
				throw new PostconditionError("Le gardien n'est pas sorti du troue ! ");
			}
		
		}
		
		if(g.willClimbRight()) {
			
			g.climbRight();;
			
			if (g.getHgt() != getHgt())  {
				throw new PostconditionError("Le gardien n'est pas sorti du troue ! ");
			}
			
			
			if (g.getWdt() != getWdt())  {
				throw new PostconditionError("Le gardien n'est pas sorti du troue ! ");
			}
		
		}
		
		
		
		
		if(g.willStay()) {
			
			g.stay();
			
			if (g.getHgt() != getHgt())  {
				throw new PostconditionError("Le gardien n'est pas resté a sa place alors qu'il le fallait ! ");
			}
			
			
			if (g.getWdt() != getWdt())  {
				throw new PostconditionError("Le gardien n'est pas resté a sa plca alors qu'il le fallait ! ");
			}
			return;
		
		}
		

		
		System.out.println("WILLL FALLLLLL");
		System.out.println(g.willFall());
		if(g.willFall()) {
			
			g.goDown();
			
			if (g.getHgt() != getHgt())  {
				throw new PostconditionError("Le gardien n'est pas tombé alors qu'il le fallait ! ");
			}
			
			
			if (g.getWdt() != getWdt())  {
				throw new PostconditionError("Le gardien n'est pas tombé alors qu'il le fallait ! ");
			}
			//beware not to execute the rest...
			return;
			
		}
		
		
		if(behaviour == Move.UP) {
			
			g.goUp();
			
			if (g.getHgt() != getHgt())  {
				throw new PostconditionError("Le gardien ne s'est pas deplace vers le haut ! ");
			}
			
			
			if (g.getWdt() != getWdt())  {
				throw new PostconditionError("Le gardien ne s'est pas deplace vers le haut ! ");
			}
		
		
		}
		
		if(behaviour== Move.DOWN) {
			

			g.goDown();
			
			
			if (g.getHgt() != getHgt())  {
				throw new PostconditionError("Le gardien ne s'est pas deplace vers le bas ! ");
			}
			

			if (g.getWdt() != getWdt())  {
				throw new PostconditionError("Le gadien ne s'est pas deplace vers le bas ! ");
			}
		
		
		}
		
		if(behaviour == Move.RIGHT) {
			System.out.println("le joueur a comme coordonnees " + g.getWdt() + ", " + g.getHgt() + " actual " +getWdt() + "," + getHgt() );
			
			g.goRight();
			if (g.getHgt() != getHgt())  {
				throw new PostconditionError("Le gardien ne s'est pas deplace vers la droite ! ");
			}
			

			if (g.getWdt() != getWdt())  {
				throw new PostconditionError("Le gardien ne s'est pas deplace vers la drpite ! ");
			}
		
		
		}
		if(behaviour == Move.LEFT) {
			g.goLeft();


			if (g.getHgt() != getHgt())  {
				throw new PostconditionError("Le gardien ne s'est pas deplace vers la gauche ! ");
			}
			
			
			if (g.getWdt() != getWdt())  {
				throw new PostconditionError("Le gardien ne s'est pas deplace vers la gauche ! ");
			}
		
		}
		
		if(behaviour == Move.NEUTRAL) {
			
			g.stay();
			
			if (g.getHgt() != getHgt())  {
				throw new PostconditionError("Le gardien n'est pas resté a sa place alors qu'il le fallait ! ");
			}
			
			
			if (g.getWdt() != getWdt())  {
				throw new PostconditionError("Le gardien n'est pas resté a sa place alors qu'il le fallait ! ");
			}
		
		}
		
	}
	
	@Override
	public void die() {
		checkInvariants();
		ItemService t = get_treasure();
		boolean has_treasure_atPre = has_treasure();
		int items_size_atPre = getEnvi().getCellContentItem(getWdt(), getHgt()).size();
		int treasure_size_atPre = getEngine().getTreasures().size();
		int guards_size_atPre = getEngine().getGuards().size();
		super.die();
		checkInvariants();
		if(has_treasure_atPre) {
			int items_size_after = getEnvi().getCellContentItem(getWdt(), getHgt()).size();
			int treasure_size_after = getEngine().getTreasures().size();
			int guards_size_after = getEngine().getGuards().size();
			if(items_size_atPre +1 != items_size_after) {
				throw new PostconditionError("Erreur le garde est mort sans relacher son tresor 1");
			}
			if(treasure_size_atPre + 1 != treasure_size_after) {
				throw new PostconditionError("Erreur le garde est mort sans relacher son tresor 2");
			}
			if(get_treasure() != null) {
				throw new PostconditionError("Erreur le garde est mort sans relacher son tresor 3");
			}
			
			if(guards_size_atPre-1 != guards_size_after) {
				throw new PostconditionError("Erreur le garde n'a pas �t� enlev� de l'environnement");
			}
		}
	}
	
	@Override
	public void dropTreasure() {
		if(getEnvi().getCellNature(getWdt(), getHgt()) != Cell.HOL) {
			throw new PreconditionError("Le garde lache son tresor quand il est dans une case vide seulement");
		}
		checkInvariants();
		ItemService t_atPre = get_treasure();
		boolean hasTreasure_atPre = has_treasure();
		int size_Treasures_atPre = getEngine().getTreasures().size();
		int size_Item_atPre = getEnvi().getCellContentItem(getWdt(), getHgt()).size();
		super.dropTreasure();
		
		if(hasTreasure_atPre) {
			int size_Treasures_after = getEngine().getTreasures().size();
			int size_Item_after = getEnvi().getCellContentItem(getWdt(), getHgt()).size();
			
			if(size_Item_after != size_Item_atPre) {
				throw new PostconditionError("le tresor n'a pas ete rajoute dans l'environnement");
			}
			if (size_Treasures_after != size_Treasures_atPre + 1) {
				throw new PostconditionError("Le tresor n'a pas ete remis dans le moteur");
			}
			if(get_treasure() != null) {
				throw new PostconditionError("Le garde doit avoir lach� son tr�sor");
			}
		}
		checkInvariants();
	}
}
