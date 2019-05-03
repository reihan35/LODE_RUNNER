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

	/** post : willFall() implies goDown()
	 *  post : willDigRight() implies getCellNature(getWdt()+1,getHgt()-1) = HOL 
	 *  post : willDigLeft() implies getCellNature(getWdt()-1,getHgt()-1) = HOL
	 *  post : getEngine().getNextCommand() = UP implies goUp()
	 *  post : getEngine().getNextCommand() = DOWN implies goDown()
	 *  post : getEngine().getNextCommand() = RIGHT implies goRight()
	 *  post : getEngine().getNextCommand() = LEFT implies goLeft()
	 *  post : getEngine().getNextCommand() = NEUTRAL implies stay()
	 *  
	 **/
	@Override
	public void step() {
		EngineService engine_at_pre = getEngine();
		PlayerService p = new Player();
		boolean will_dig_right_at_pre = willDigRight();
		boolean will_dig_left_at_pre = willDigLeft();
		boolean will_fall = willFall();
		Command command_at_pre = getEngine().getNextCommand();
		p.init(engine_at_pre,getWdt(),getHgt());

		super.step();
		
		if (engine_at_pre != getEngine()) {
			throw new InvariantError("Le moteur de jeu ne doit pas changer ! ");
		}
		if(p.willFall()) {
			if(!will_fall) {
				throw new PostconditionError("Le joueur ne peut pas tomber");

			}
	
		}
		
		if(p.getEngine().getNextCommand() == Command.DIGL) {
			if(!will_dig_left_at_pre) {
				throw new PostconditionError("Le joueur ne peut pas creuser a gauche");

			}
			if(will_dig_left_at_pre && getEnvi().getCellNature(getWdt()-1,getHgt()-1) != Cell.HOL) {
				throw new PostconditionError("Le joueur n'a pas creusé à gauche alors qu'il le fallait ");
			}
		}
		
		if(p.getEngine().getNextCommand() == Command.DIGR) {
			if (!will_dig_right_at_pre) {
				throw new PostconditionError("le joueur ne peut pas creuser a doite ");
			}
			if(will_dig_right_at_pre && getEnvi().getCellNature(getWdt()+1,getHgt()-1) != Cell.HOL) {
				throw new PostconditionError("Le joueur n'a pas creuse a droite alors qu'il le fallait ");
			}
		}
		if(p.getEngine().getNextCommand() == Command.DOWN) {
			if(command_at_pre != Command.DOWN) {
				throw new PostconditionError("Le joueur n'est pas allee en bas alors qu'il le fallait ! ");
			}
		}
		
		if(p.getEngine().getNextCommand() == Command.UP) {
			if(command_at_pre != Command.UP) {
				throw new PostconditionError("Le joueur n'est pas allee en haut alors qu'il le fallait ! ");
			}
		}
		
		if(p.getEngine().getNextCommand() == Command.RIGHT) {
			
			if(command_at_pre != Command.RIGHT) {
				throw new PostconditionError("Le joueur n'est pas allee a droite alors qu'il le fallait ! ");
			}
		}
		
		if(p.getEngine().getNextCommand() == Command.LEFT) {
			
			if(command_at_pre != Command.LEFT) {
				throw new PostconditionError("Le joueur n'est pas allee a gauche alors qu'il le fallait ! ");
			}
		}
		
		if(p.getEngine().getNextCommand() == Command.NEUTRAL) {
			
			if(command_at_pre != Command.NEUTRAL) {
				throw new PostconditionError("Le joueur n'est pas reste a sa place alors qu'il le fallait ! ");
			}
			
		}
	}

}
