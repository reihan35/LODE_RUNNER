package test;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.platform.engine.EngineExecutionListener;
import org.junit.platform.launcher.EngineFilter;

import components.Character;
import components.Engine;
import components.Environment;
import components.Guard;
import components.Player;
import contracts.CharacterContract;
import contracts.EditableScreenContract;
import contracts.EngineContract;
import contracts.EnvironmentContract;
import contracts.GuardContrtact;
import contracts.PlayerContract;
import contracts.PreconditionError;
import main.EnvironmentMain;
import services.Cell;
import services.CharacterService;
import services.Command;
import services.Coordinates;
import services.EngineService;
import services.EnvironmentService;
import services.GuardService;
import services.PlayerService;
import util.SetUtil;


public class GuardTest {
	
	@Test
	public void will_fall() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2),new Coordinates(3,3));
		PlayerService pi = new Player();
		PlayerContract p = new PlayerContract(pi);
		pi.init(en, 2, 2);
		GuardService gi = new Guard();
		GuardContrtact g = new GuardContrtact(gi);
		g.init(env, 3, 3);
		gi.step();
	}
	
	@Test
	public void behav_right() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2),new Coordinates(3,2));
		PlayerService pi = new Player();
		PlayerContract p = new PlayerContract(pi);
		pi.init(en, 2, 2);
		GuardService gi = new Guard();
		GuardContrtact g = new GuardContrtact(gi);
		g.init(env, 3, 2);
		gi.step();
	}
	
	@Test
	public void behav_left() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2),new Coordinates(1,2));
		PlayerService pi = new Player();
		PlayerContract p = new PlayerContract(pi);
		pi.init(en, 2, 2);
		GuardService gi = new Guard();
		GuardContrtact g = new GuardContrtact(gi);
		g.init(env, 1, 2);
		gi.step();
	}
	
	@Test
	public void behav_down() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(2, 1, Cell.LAD);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,1),new Coordinates(2,2));
		PlayerService pi = new Player();
		PlayerContract p = new PlayerContract(pi);
		pi.init(en, 2, 1);
		GuardService gi = new Guard();
		GuardContrtact g = new GuardContrtact(gi);
		g.init(env, 2, 2);
		gi.step();
	}
	
	//Paire de Transitions
	
	@Test
	public void climb_left() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(2, 2, Cell.PLT);
		s.setNature(3, 1, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,1),new Coordinates(2,2));
		PlayerService pi = new Player();
		PlayerContract p = new PlayerContract(pi);
		p.init(en, 2, 2);
		en.addCommand(Command.DIGR);
		p.step();
		GuardService gi = new Guard();
		GuardContrtact g = new GuardContrtact(gi);
		g.init(env, 4, 1);
		gi.step();
		gi.step();
	}
	
}
