package test;
import java.util.ArrayList;

import org.junit.Test;

import components.Character;
import components.Engine;
import components.Environment;
import components.Player;
import contracts.CharacterContract;
import contracts.EditableScreenContract;
import contracts.EngineContract;
import contracts.EnvironmentContract;
import contracts.PlayerContract;
import contracts.PreconditionError;
import services.Cell;
import services.CharacterService;
import services.Command;
import services.Coordinates;
import services.EngineService;
import services.EnvironmentService;
import services.PlayerService;
import util.SetUtil;


public class PlayerTest {
	
	@Test
	public void step_1() {
		EnvironmentService e = new Environment();
		EditableScreenContract scontrat = SetUtil.MakeEdiatableScreen(10,10);
		EnvironmentContract econtrat = new EnvironmentContract(e);
		econtrat.init(scontrat);
		EngineService en = new Engine();
		EngineContract enconrat = new EngineContract(en);
		ArrayList<Coordinates>  treasuresCoord = new ArrayList<>();
		treasuresCoord.add(new Coordinates(4,4));
		Coordinates player  = new Coordinates(2, 2);
		ArrayList<Coordinates> h = new ArrayList<>();
		enconrat.init(econtrat,player,h,treasuresCoord);
		enconrat.addCommand(Command.DOWN);
		PlayerService p = new Player();
		PlayerContract pcontrat = new PlayerContract(p);
		pcontrat.init(enconrat, 2, 2);
		pcontrat.step();
	}
	
	@Test
	public void step_up_lad() {
		EnvironmentService e = new Environment();
		EditableScreenContract scontrat = SetUtil.MakeEdiatableScreen(10,10);
		scontrat.setNature(2, 2, Cell.LAD);
		EnvironmentContract econtrat = new EnvironmentContract(e);
		econtrat.init(scontrat);
		EngineService en = new Engine();
		EngineContract enconrat = new EngineContract(en);
		ArrayList<Coordinates>  treasuresCoord = new ArrayList<>();
		treasuresCoord.add(new Coordinates(4,4));
		Coordinates player  = new Coordinates(2, 2);
		ArrayList<Coordinates> h = new ArrayList<>();
		enconrat.init(econtrat,player,h,treasuresCoord);
		enconrat.addCommand(Command.UP);
		PlayerService p = new Player();
		PlayerContract pcontrat = new PlayerContract(p);
		pcontrat.init(enconrat, 2, 2);
		pcontrat.step();
	}
	
	@Test
	public void step_dig_right() {
		EnvironmentService e = new Environment();
		EditableScreenContract scontrat = SetUtil.MakeEdiatableScreen(10,10);
		EnvironmentContract econtrat = new EnvironmentContract(e);
		scontrat.setNature(2, 1,Cell.PLT );
		scontrat.setNature(3, 1,Cell.PLT );
		econtrat.init(scontrat);
		EngineService en = new Engine();
		EngineContract enconrat = new EngineContract(en);
		ArrayList<Coordinates>  treasuresCoord = new ArrayList<>();
		treasuresCoord.add(new Coordinates(4,4));
		Coordinates player  = new Coordinates(1, 2);
		ArrayList<Coordinates> h = new ArrayList<>();
		enconrat.init(econtrat,player,h,treasuresCoord);
		enconrat.addCommand(Command.DIGR);
		PlayerService p = new Player();
		PlayerContract pcontrat = new PlayerContract(p);
		pcontrat.init(enconrat, 2, 2);
		System.out.println("on est la " + pcontrat.getHgt());
		pcontrat.step();
	}
	
	@Test(expected = contracts.PostconditionError.class)
	public void step_dig_left() {
		EnvironmentService e = new Environment();
		EditableScreenContract scontrat = SetUtil.MakeEdiatableScreen(10,10);
		EnvironmentContract econtrat = new EnvironmentContract(e);
		scontrat.setNature(2, 1,Cell.PLT );
		scontrat.setNature(3, 1,Cell.PLT );
		econtrat.init(scontrat);
		EngineService en = new Engine();
		EngineContract enconrat = new EngineContract(en);
		ArrayList<Coordinates>  treasuresCoord = new ArrayList<>();
		treasuresCoord.add(new Coordinates(4,4));
		Coordinates player  = new Coordinates(1, 2);
		ArrayList<Coordinates> h = new ArrayList<>();
		enconrat.init(econtrat,player,h,treasuresCoord);
		enconrat.addCommand(Command.DIGL);
		PlayerService p = new Player();
		PlayerContract pcontrat = new PlayerContract(p);
		pcontrat.init(enconrat, 2, 2);
		System.out.println("on est la " + pcontrat.getHgt());
		pcontrat.step();
	}
	
	@Test
	public void stay() {
		EnvironmentService e = new Environment();
		EditableScreenContract scontrat = SetUtil.MakeEdiatableScreen(10,10);
		EnvironmentContract econtrat = new EnvironmentContract(e);
		econtrat.init(scontrat);
		EngineService en = new Engine();
		EngineContract enconrat = new EngineContract(en);
		ArrayList<Coordinates>  treasuresCoord = new ArrayList<>();
		treasuresCoord.add(new Coordinates(4,4));
		Coordinates player  = new Coordinates(2, 2);
		ArrayList<Coordinates> h = new ArrayList<>();
		enconrat.init(econtrat,player,h,treasuresCoord);
		enconrat.addCommand(Command.NEUTRAL);
		PlayerService p = new Player();
		PlayerContract pcontrat = new PlayerContract(p);
		pcontrat.init(enconrat, 2, 2);
		pcontrat.step();
	}
	
}
