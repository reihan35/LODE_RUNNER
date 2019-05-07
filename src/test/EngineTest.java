package test;
import java.util.ArrayList;

import org.junit.Test;

import components.Engine;
import components.Environment;
import components.Player;
import contracts.EditableScreenContract;
import contracts.EngineContract;
import contracts.EnvironmentContract;
import contracts.InvariantError;
import contracts.PlayerContract;
import services.Cell;
import services.Command;
import services.Coordinates;
import services.Door;
import services.EngineService;
import services.EnvironmentService;
import services.PlayerService;
import services.Stat;
import util.SetUtil;

import contracts.InvariantError;
import contracts.PreconditionError;

public class EngineTest {
	
	@Test
	public void init_pred() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineService e = new Engine();
		EngineContract enconrat = new EngineContract(e);
		ArrayList<Coordinates> t = new ArrayList<>();
		ArrayList<Coordinates> g = new ArrayList<>();
		ArrayList<Coordinates> b = new ArrayList<>();
		ArrayList<Door> d = new ArrayList<>();
		enconrat.init(env, new Coordinates(2, 3), g, t, b, d);
	}
	
	@Test(expected = PreconditionError.class)
	public void init_2() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(3, 2, Cell.PLT);
		s.setNature(2, 2, Cell.PLT);
		s.setNature(4, 4, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineService e = new Engine();
		EngineContract enconrat = new EngineContract(e);
		ArrayList<Coordinates> t = new ArrayList<>();
		t.add(new Coordinates(2, 2));
		ArrayList<Coordinates> g = new ArrayList<>();
		g.add(new Coordinates(2, 3));
		ArrayList<Coordinates> b = new ArrayList<>();
		ArrayList<Door> d = new ArrayList<>();
		enconrat.init(env, new Coordinates(3, 3), g, t, b, d);
	}
	
	@Test(expected = PreconditionError.class)
	public void init_3() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(3, 2, Cell.PLT);
		s.setNature(2, 2, Cell.PLT);
		s.setNature(4, 4, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineService e = new Engine();
		EngineContract enconrat = new EngineContract(e);
		ArrayList<Coordinates> t = new ArrayList<>();
		t.add(new Coordinates(2, 2));
		ArrayList<Coordinates> g = new ArrayList<>();
		g.add(new Coordinates(2, 3));
		ArrayList<Coordinates> b = new ArrayList<>();
		Coordinates pcoord = new Coordinates(3, 3);
		b.add(pcoord);
		ArrayList<Door> d = new ArrayList<>();
		enconrat.init(env, pcoord, g, t, b, d);
	}
	
	@Test(expected = PreconditionError.class)
	public void init_4() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(3, 2, Cell.PLT);
		s.setNature(2, 2, Cell.PLT);
		s.setNature(4, 4, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineService e = new Engine();
		EngineContract enconrat = new EngineContract(e);
		ArrayList<Coordinates> t = new ArrayList<>();
		t.add(new Coordinates(2, 2));
		ArrayList<Coordinates> g = new ArrayList<>();
		g.add(new Coordinates(2, 3));
		ArrayList<Coordinates> b = new ArrayList<>();
		ArrayList<Door> dlist = new ArrayList<>();
		Door d = new Door(new Coordinates(3, 3), new Coordinates(5, 5));
		dlist.add(d);
		enconrat.init(env, new Coordinates(3, 3), g, t, b, dlist);
	}
	
	
	@Test
	public void step_1() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(2, 2, Cell.PLT);
		s.setNature(3, 2, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineService e = new Engine();
		EngineContract enconrat = new EngineContract(e);
		ArrayList<Coordinates> t = new ArrayList<>();
		t.add(new Coordinates(3, 3));
		ArrayList<Coordinates> g = new ArrayList<>();
		g.add(new Coordinates(3, 3));
		ArrayList<Coordinates> b = new ArrayList<>();
		ArrayList<Door> d = new ArrayList<>();
		enconrat.init(env, new Coordinates(2, 3), g, t, b, d);
		e.addCommand(Command.LEFT);
		enconrat.step();
		assert enconrat.getPlayer().getWdt() == 1;
	}
	
	@Test
	public void step_3() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(3, 2, Cell.PLT);
		s.setNature(2, 2, Cell.PLT);
		s.setNature(4, 4, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineService e = new Engine();
		EngineContract enconrat = new EngineContract(e);
		ArrayList<Coordinates> t = new ArrayList<>();
		t.add(new Coordinates(3, 3));
		ArrayList<Coordinates> g = new ArrayList<>();
		ArrayList<Coordinates> b = new ArrayList<>();
		ArrayList<Door> d = new ArrayList<>();
		enconrat.init(env, new Coordinates(2, 3), g, t, b, d);
		e.addCommand(Command.RIGHT);
		enconrat.step();
		e.addCommand(Command.LEFT);
		enconrat.step();
		assert(enconrat.getScore() == 10);
	}
	
	@Test
	public void step_4() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(3, 2, Cell.PLT);
		s.setNature(2, 2, Cell.PLT);
		s.setNature(4, 4, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineService e = new Engine();
		EngineContract enconrat = new EngineContract(e);
		ArrayList<Coordinates> t = new ArrayList<>();
		t.add(new Coordinates(2, 3));
		ArrayList<Coordinates> g = new ArrayList<>();
		g.add(new Coordinates(2, 3));
		ArrayList<Coordinates> b = new ArrayList<>();
		ArrayList<Door> d = new ArrayList<>();
		enconrat.init(env, new Coordinates(3, 3), g, t, b, d);
		e.addCommand(Command.DIGL);
		enconrat.step();
		assert(enconrat.getEnvi().getCellNature(2, 2) == Cell.PLT);
	}
	
	@Test
	public void step_5() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(3, 2, Cell.PLT);
		s.setNature(2, 2, Cell.PLT);
		s.setNature(4, 4, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineService e = new Engine();
		EngineContract enconrat = new EngineContract(e);
		ArrayList<Coordinates> t = new ArrayList<>();
		ArrayList<Coordinates> g = new ArrayList<>();
		g.add(new Coordinates(2, 3));
		ArrayList<Coordinates> b = new ArrayList<>();
		ArrayList<Door> d = new ArrayList<>();
		enconrat.init(env, new Coordinates(3, 3), g, t, b, d);
		e.addCommand(Command.DIGL);
		enconrat.step();
		assert(enconrat.getEnvi().getCellNature(2, 2) == Cell.HOL);
	}
	

	

	
}
