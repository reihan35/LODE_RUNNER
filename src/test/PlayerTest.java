package test;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.platform.engine.EngineExecutionListener;
import org.junit.platform.launcher.EngineFilter;

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
import main.EnvironmentMain;
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
	public void step_down() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		PlayerService p = new Player();
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2));
		en.addCommand(Command.DOWN);
		PlayerContract pcontrat = new PlayerContract(p);
		pcontrat.init(en, 2, 2);
		pcontrat.step();
	}
	
	@Test(expected = contracts.PreconditionError.class)
	public void step_down_2() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		PlayerService p = new Player();
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2));
		en.addCommand(Command.DOWN);
		PlayerContract pcontrat = new PlayerContract(p);
		pcontrat.init(en, 0, 0);
		pcontrat.step();
	}
	
	@Test
	public void step_up_lad() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(2, 3, Cell.LAD);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		PlayerService p = new Player();
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2));
		en.addCommand(Command.UP);
		PlayerContract pcontrat = new PlayerContract(p);
		pcontrat.init(en, 2, 2);
		pcontrat.step();
	}
	

	@Test
	public void step_up_hdr() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(2, 2, Cell.HDR);
		s.setNature(3, 2, Cell.HDR);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		PlayerService p = new Player();
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2));
		PlayerContract pcontrat = new PlayerContract(p);
		en.addCommand(Command.RIGHT);
		pcontrat.init(en, 2, 2);
		pcontrat.step();
	}
	@Test
	public void step_up_door() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(2, 2, Cell.DOR);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		PlayerService p = new Player();
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2));
		PlayerContract pcontrat = new PlayerContract(p);
		en.addCommand(Command.OPEND);
		pcontrat.init(en, 2, 2);
		pcontrat.step();
	}
	
	@Test(expected = contracts.PostconditionError.class)
	public void step_up_door_2() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(2, 2, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		PlayerService p = new Player();
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2));
		PlayerContract pcontrat = new PlayerContract(p);
		en.addCommand(Command.OPEND);
		pcontrat.init(en, 2, 2);
		pcontrat.step();
	}
	
	@Test
	public void step_fall() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(5, 5, Cell.HOL);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		PlayerService p = new Player();
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(5,5));
		PlayerContract pcontrat = new PlayerContract(p);
		pcontrat.init(en, 5, 5);
		pcontrat.step();
	}
	
	@Test
	public void step_dig() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(5, 4, Cell.PLT);
		s.setNature(4, 4, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		PlayerService p = new Player();
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(5,5));
		PlayerContract pcontrat = new PlayerContract(p);
		en.addCommand(Command.DIGL);
		pcontrat.init(en, 5, 5);
		pcontrat.step();
	}
	
	@Test(expected = contracts.PostconditionError.class)
	public void step_dig_2() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(5, 4, Cell.PLT);
		s.setNature(4, 4, Cell.MTL);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		PlayerService p = new Player();
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(5,5));
		PlayerContract pcontrat = new PlayerContract(p);
		en.addCommand(Command.DIGL);
		pcontrat.init(en, 5, 5);
		pcontrat.step();
	}
	
	// Paire de Transition
	@Test
	public void up_left() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(2, 3, Cell.LAD);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		PlayerService p = new Player();
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2));
		en.addCommand(Command.UP);
		PlayerContract pcontrat = new PlayerContract(p);
		pcontrat.init(en, 2, 2);
		pcontrat.step();
		en.addCommand(Command.LEFT);
		pcontrat.step();
		
	}
	
	@Test
	public void up_right() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(2, 3, Cell.LAD);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		PlayerService p = new Player();
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2));
		en.addCommand(Command.UP);
		PlayerContract pcontrat = new PlayerContract(p);
		pcontrat.init(en, 2, 2);
		pcontrat.step();
		en.addCommand(Command.LEFT);
		pcontrat.step();
		
	}
	
	@Test(expected = contracts.PostconditionError.class)
	public void up_dig_left() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(2, 3, Cell.LAD);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		PlayerService p = new Player();
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2));
		en.addCommand(Command.UP);
		PlayerContract pcontrat = new PlayerContract(p);
		pcontrat.init(en, 2, 2);
		pcontrat.step();
		en.addCommand(Command.DIGL);
		pcontrat.step();
		
	}
	
	//Scenario
	@Test
	public void scenario() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(4, 3, Cell.PLT);
		s.setNature(3, 4, Cell.PLT);
		s.setNature(2, 4, Cell.LAD);
		s.setNature(2, 3, Cell.LAD);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		PlayerService p = new Player();
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2));
		en.addCommand(Command.UP);
		PlayerContract pcontrat = new PlayerContract(p);
		pcontrat.init(en, 2, 2);
		pcontrat.step();
		en.addCommand(Command.UP);
		pcontrat.step();
		en.addCommand(Command.RIGHT);
		pcontrat.step();
		en.addCommand(Command.DIGR);
		pcontrat.step();
	}
}
