package contracts;

import components.Player;
import decorators.PlayerDecorator;
import services.Cell;
import services.Command;
import services.EngineService;
import services.EnvironmentService;
import services.PlayerService;

public class PlayerContract extends PlayerDecorator implements PlayerService {

	public PlayerContract(PlayerService delegates) {
		super(delegates);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void step() {
		EngineService engine_at_pre = getEngine();
		PlayerService p = new Player();
		p.init(getEnvi(),getWdt(),getHgt());
		
		super.step();
		
		if (engine_at_pre != getEngine()) {
			throw new InvariantError("Le moteur de jeu ne doit pas changer ! ");
		}
		
		if(willFall()) {
			
			p.goDown();
			
			if (p.getHgt() != getHgt())  {
				throw new PostconditionError("Le joueur n'est pas tombé alors qu'il le fallait ! ");
			}
			
			if (p.getWdt() != getWdt())  {
				throw new PostconditionError("Le joueur n'est pas tombé alors qu'il le fallait ! ");
			}
			
		}
		
		if(willDigLeft() && getEnvi().getCellNature(getWdt()-1,getHgt()-1) == Cell.HOL) {
			throw new PostconditionError("Le joueur n'a pas creusé à gauche alors qu'il le fallait ");
		}
		
		if(willDigRight() && getEnvi().getCellNature(getWdt()+1,getHgt()-1) == Cell.HOL) {
			throw new PostconditionError("Le joueur n'a pas creusé à droite alors qu'il le fallait ");
		}
		
		if(getEngine().getNextCommand() == Command.DOWN) {
			
			p.goDown();
			
			if (p.getHgt() != getHgt())  {
				throw new PostconditionError("Le joueur n'est pas allé en bas alors qu'il le fallait ! ");
			}
			
			if (p.getWdt() != getWdt())  {
				throw new PostconditionError("Le joueur n'est pas allé en bas qu'il le fallait ! ");
			}
			
		}
		
		if(getEngine().getNextCommand() == Command.UP) {
			
			p.goUp();
			
			if (p.getHgt() != getHgt())  {
				throw new PostconditionError("Le joueur n'est pas allé en haut alors qu'il le fallait ! ");
			}
			
			if (p.getWdt() != getWdt())  {
				throw new PostconditionError("Le joueur n'est pas allé en haut qu'il le fallait ! ");
			}
			
		}
		
		if(getEngine().getNextCommand() == Command.RIGHT) {
			
			p.goRight();
			
			if (p.getHgt() != getHgt())  {
				throw new PostconditionError("Le joueur n'est pas allé à droite alors qu'il le fallait ! ");
			}
			
			if (p.getWdt() != getWdt())  {
				throw new PostconditionError("Le joueur n'est pas allé à droite qu'il le fallait ! ");
			}
			
		}
		
		if(getEngine().getNextCommand() == Command.LEFT) {
			
			p.goLeft();
			
			if (p.getHgt() != getHgt())  {
				throw new PostconditionError("Le joueur n'est pas allé à gauche alors qu'il le fallait ! ");
			}
			
			if (p.getWdt() != getWdt())  {
				throw new PostconditionError("Le joueur n'est pas allé à gauche qu'il le fallait ! ");
			}
			
		}
		
		if(getEngine().getNextCommand() == Command.NEUTRAL) {
			
			p.stay();
			
			if (p.getHgt() != getHgt())  {
				throw new PostconditionError("Le joueur n'est pas resté à sa place alors qu'il le fallait ! ");
			}
			
			if (p.getWdt() != getWdt())  {
				throw new PostconditionError("Le joueur n'est pas resté à sa place alors qu'il le fallait ! ");
			}
			
		}
	}

}
