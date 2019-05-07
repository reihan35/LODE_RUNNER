package contracts;

import components.Character;
import components.Player;
import decorators.PlayerDecorator;
import services.Cell;
import services.Command;
import services.Coordinates;
import services.Door;
import services.EngineService;
import services.EnvironmentService;
import services.PlayerService;
import services.Stat;

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
		p.init(engine_at_pre,getWdt(),getHgt());
		Command c = getEngine().getNextCommand();
				
		super.step();
		
		
		if (engine_at_pre != getEngine()) {
			throw new InvariantError("Le moteur de jeu ne doit pas changer ! ");
		}
	

		
		if(p.willFall()) {
			
			p.goDown();
			
			if (p.getHgt() != getHgt())  {
				throw new PostconditionError("Le joueur n'est pas tombé alors qu'il le fallait ! ");
			}
			
			
			if (p.getWdt() != getWdt())  {
				throw new PostconditionError("Le joueur n'est pas tombé alors qu'il le fallait ! ");
			}
			return;
		}
		
		if(p.getEngine().getNextCommand() == Command.DIGL) {
			System.out.println("salut toi");
			System.out.println(will_dig_left_at_pre);
			/*
			if (!will_dig_left_at_pre && getEnvi().getCellNature(getWdt()-1,getHgt()-1) == Cell.HOL) {
				throw new PostconditionError("Le joueur ne peut pas creuser a gauche ");
			}*/
			if(will_dig_left_at_pre && getEnvi().getCellNature(getWdt()-1,getHgt()-1) != Cell.HOL) {
				throw new PostconditionError("Le joueur n'a pas creusé à gauche alors qu'il le fallait ");
			}
			
		}
		
		if(p.getEngine().getNextCommand() == Command.DIGR) {
			/*
			if (!will_dig_right_at_pre && getEnvi().getCellNature(getWdt()+1,getHgt()-1) == Cell.HOL) {
				throw new PostconditionError("Le joueur ne peut pas creuser a droite ");
			}*/
			
			if(will_dig_right_at_pre && getEnvi().getCellNature(getWdt()+1,getHgt()-1) != Cell.HOL) {
				throw new PostconditionError("Le joueur n'a pas creuse a droite alors qu'il le fallait ");
			}
		}
		if(p.getEngine().getNextCommand() == Command.DOWN) {
			p.goDown();
			System.out.println(p.getHgt() + " " + getHgt());

			if (p.getHgt() != getHgt())  {

				throw new PostconditionError("Le joueur n'est pas allé en bas alors qu'il le fallait ! ");
			}
			
			if (p.getWdt() != getWdt())  {
				throw new PostconditionError("Le joueur n'est pas allé en bas qu'il le fallait ! ");
			}
			
		}
		
		if(p.getEngine().getNextCommand() == Command.UP) {
			
			p.goUp();
			
			System.out.println("BONJOUE A TOUS");
			System.out.println(p.getHgt());
			System.out.println(getHgt());
			if (p.getHgt() != getHgt())  {
				throw new PostconditionError("Le joueur n'est pas allé en haut alors qu'il le fallait ! ");
			}
			
			if (p.getWdt() != getWdt())  {
				throw new PostconditionError("Le joueur n'est pas allé en haut qu'il le fallait ! ");
			}
			
		}
		
		if(p.getEngine().getNextCommand() == Command.RIGHT) {
			
			p.goRight();
			
			if (p.getHgt() != getHgt())  {
				throw new PostconditionError("Le joueur n'est pas allé à droite alors qu'il le fallait ! ");
			}
			
			if (p.getWdt() != getWdt())  {
				throw new PostconditionError("Le joueur n'est pas allé à droite qu'il le fallait ! ");
			}
			
		}
		
		if(p.getEngine().getNextCommand() == Command.LEFT) {
			
			p.goLeft();
			
			if (p.getHgt() != getHgt())  {
				throw new PostconditionError("Le joueur n'est pas allé à gauche alors qu'il le fallait ! ");
			}
			
			if (p.getWdt() != getWdt())  {
				throw new PostconditionError("Le joueur n'est pas allé à gauche qu'il le fallait ! ");
			}
			
		}
		
		if(p.getEngine().getNextCommand() == Command.NEUTRAL) {
			
			p.stay();
			
			if (p.getHgt() != getHgt())  {
				throw new PostconditionError("Le joueur n'est pas resté à sa place alors qu'il le fallait ! ");
			}
			
			if (p.getWdt() != getWdt())  {
				throw new PostconditionError("Le joueur n'est pas resté à sa place alors qu'il le fallait ! ");
			}
		}
		
		if(getEngine().getNextCommand() == Command.OPEND) {
			int new_pos_x = p.getWdt();
			int new_pos_y = p.getHgt();
			for (Door d: getEngine().getDoors()) {	
				if(d.isOnIn(new Coordinates(p.getWdt(), p.getHgt()))) {
					new_pos_x = d.getOut().getX();
					new_pos_y = d.getOut().getY();
					break;
				}
				else if(d.isOnOut(new Coordinates(p.getWdt(), p.getHgt()))) {
					new_pos_x = d.getIn().getX();
					new_pos_y = d.getIn().getY();;
					break;
				}
			}
			System.out.println(new_pos_y + " " + getHgt());
			if (new_pos_y!= getHgt())  {
				throw new PostconditionError("Probleme de porte");
			}
				
			if (new_pos_x != getWdt())  {
				throw new PostconditionError("Probleme de porte ");				
			
			}
			
		}
	}
			

}
