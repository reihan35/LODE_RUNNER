package contracts;

import components.Guard;
import components.Player;
import decorators.GuardDecorator;
import services.Cell;
import services.CharacterService;
import services.EngineService;
import services.EnvironmentService;
import services.GuardService;
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
		if (getEnvi().getCellNature(getWdt(), getHgt()) == Cell.LAD && getHgt() < getTarget().getHgt() && getBehaviour() != Move.UP) {
			throw new InvariantError("Behaviour ne return pas UP alors qu'il le faut !");
		}
		
		if (getEnvi().getCellNature(getWdt(), getHgt()) == Cell.LAD && getHgt() > getTarget().getHgt() && getBehaviour() != Move.DOWN) {
			throw new InvariantError("Behaviour ne return pas DOWN alors qu'il le faut !");
		}
		
		if (getEnvi().getCellNature(getWdt(), getHgt()) == Cell.LAD && getHgt() > getTarget().getHgt() && getBehaviour() != Move.NEUTRAL) {
			throw new InvariantError("Behaviour ne return pas NEUTRAL alors qu'il le faut !");
		}
		
		 Cell downCell = getEnvi().getCellNature(getWdt(), getHgt()-1);
		 Cell currCell = getEnvi().getCellNature(getWdt(), getHgt());
		 Cell[] emp ={Cell.HOL, Cell.HDR};
		
		if (SetUtil.isIn(currCell,emp) || (!isFreeCell(getWdt(), getHgt()-1)) || (isFreeCell(getWdt(), getHgt()-1))){

			 if(getTarget().getWdt() < getWdt() && getBehaviour() != Move.LEFT) {
				 throw new InvariantError("Behaviour ne return pas LEFT alors qu'il le faut !");
			 }
			 if (getTarget().getWdt() > getWdt() && getBehaviour() != Move.RIGHT) {
				 throw new InvariantError("Behaviour ne return pas RIGHT alors qu'il le faut !");
			 }
			 
			 else {
				 throw new InvariantError("Behaviour ne return pas NEUTRAL alors qu'il le faut !");
				 
			 }
		 }
		
		 Cell[] f ={Cell.PLT, Cell.MTL};
		 if (getEnvi().getCellNature(getWdt(), getHgt()) == Cell.LAD && getHgt() < getTarget().getHgt() && (!isFreeCell(getWdt(), getHgt() - 1) 
				 																							|| isFreeCell(getWdt(), getHgt() - 1) && characterAt(getWdt(), getHgt()- 1))) {
			 if(getTarget().getHgt() - getHgt() < getTarget().getWdt() && getBehaviour() != Move.UP) {
					throw new InvariantError("Behaviour ne return pas UP alors qu'il le faut !");
			 }
		 }
		 
		 if (getEnvi().getCellNature(getWdt(), getHgt()) == Cell.LAD && getWdt() > getTarget().getWdt() && (!isFreeCell(getWdt(), getHgt() - 1) 
																											|| isFreeCell(getWdt(), getHgt() - 1) && characterAt(getWdt(), getHgt()- 1))) {
			 if(getTarget().getHgt() - getHgt() < getTarget().getWdt() && getBehaviour() != Move.DOWN) {
					throw new InvariantError("Behaviour ne return pas DOWN alors qu'il le faut !");
			 }
		 }
		 
		 if (getEnvi().getCellNature(getWdt(), getHgt()) == Cell.LAD && getHgt() > getTarget().getHgt() &&(!isFreeCell(getWdt(), getHgt() - 1) 
				 																								|| isFreeCell(getWdt(), getHgt() - 1) && characterAt(getWdt(), getHgt()- 1))) {
			 if(getTarget().getHgt() - getHgt() < getTarget().getWdt() && getBehaviour() != Move.LEFT) {
				 throw new InvariantError("Behaviour ne return pas LEFT alors qu'il le faut !");
			 }
		 }

		 if (getEnvi().getCellNature(getWdt(), getHgt()) == Cell.LAD && getWdt() < getTarget().getWdt() && (!isFreeCell(getWdt(), getHgt() - 1) 
																											|| isFreeCell(getWdt(), getHgt() - 1) && characterAt(getWdt(), getHgt()- 1))) {
			 if(getTarget().getHgt() - getHgt() > getTarget().getWdt() && getBehaviour() != Move.RIGHT) {
				 throw new InvariantError("Behaviour ne return pas RIGHT alors qu'il le faut !");
			 }
		 }
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
	public void step() {
		
		EngineService engine_at_pre = getEngine();
		PlayerService p_at_pre = getPlayer();
		GuardService g = new Guard();
		g.init(engine_at_pre,getWdt(),getHgt(),p_at_pre);
		int t_at_pre = -10000;
		if (getEnvi().getCellNature(getWdt(), getHgt()) == Cell.HOL){
			t_at_pre = getEngine().getHoles(getWdt(), getHgt());
		}

		super.step();
		

		if (engine_at_pre != getEngine()) {
			throw new InvariantError("Le moteur de jeu ne doit pas changer ! ");
		}
		
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
		
		if(g.willAddTime()) {
			if(t_at_pre + 1 !=  getEngine().getHoles(getWdt(), getHgt())) {
				throw new PostconditionError("Le gardien n'est pas resté dans le trou");
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
		
		}
		
		if(g.willReinitialize()) {
			g.Reinitialize();
			

			if (g.getHgt() != getHgt())  {
				throw new PostconditionError("Le gardien ne s'est pas reinitialise ");
			}
			
			
			if (g.getWdt() != getWdt())  {
				throw new PostconditionError("Le gardien ne s'est pas reinitialise ");
			}
			
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
			
		}
		
		
		if(g.getBehaviour() == Move.UP) {
			
			g.goUp();
			
			if (g.getHgt() != getHgt())  {
				throw new PostconditionError("Le gardien ne s'est pas deplace vers le haut ! ");
			}
			
			
			if (g.getWdt() != getWdt())  {
				throw new PostconditionError("Le gardien ne s'est pas deplace vers le haut ! ");
			}
		
		
		}
		
		if(g.getBehaviour() == Move.DOWN) {
			
			g.goDown();
			
			if (g.getHgt() != getHgt())  {
				throw new PostconditionError("Le gardien ne s'est pas deplace vers le bas ! ");
			}
			
			
			if (g.getWdt() != getWdt())  {
				throw new PostconditionError("Le gardien ne s'est pas deplace vers le bas ! ");
			}
		
		
		}
		
		if(g.getBehaviour() == Move.RIGHT) {
			
			g.goRight();
			
			if (g.getHgt() != getHgt())  {
				throw new PostconditionError("Le gardien ne s'est pas deplace vers la droite ! ");
			}
			
			
			if (g.getWdt() != getWdt())  {
				throw new PostconditionError("Le gardien ne s'est pas deplace vers la drpite ! ");
			}
		
		
		}
		if(g.getBehaviour() == Move.LEFT) {
			System.out.println("JE VEUX MOVE LEFT");
			System.out.println(g.getWdt());
			g.goLeft();
			System.out.println(g.getWdt());
			System.out.println(getWdt());

			if (g.getHgt() != getHgt())  {
				throw new PostconditionError("Le gardien ne s'est pas deplace vers la gauche ! ");
			}
			
			
			if (g.getWdt() != getWdt())  {
				throw new PostconditionError("Le gardien ne s'est pas deplace vers la gauche ! ");
			}
		
		}
		
		if(g.getBehaviour() == Move.NEUTRAL) {
			
			g.stay();
			
			if (g.getHgt() != getHgt())  {
				throw new PostconditionError("Le gardien n'est pas resté a sa place alors qu'il le fallait ! ");
			}
			
			
			if (g.getWdt() != getWdt())  {
				throw new PostconditionError("Le gardien n'est pas resté a sa place alors qu'il le fallait ! ");
			}
		
		}
	}
}
