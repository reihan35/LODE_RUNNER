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
import services.Door;
import services.EngineService;
import services.EnvironmentService;
import services.GuardService;
import services.PlayerService;
import services.Stat;
import util.SetUtil;


public class GuardTest {
	
	@Test
	public void will_fall() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(1,1),new Coordinates(3,3));
		PlayerService pi = new Player();
		PlayerContract p = new PlayerContract(pi);
		p.init(en, 1, 1);
		GuardService gi = new Guard();
		GuardContrtact g = new GuardContrtact(gi);
		g.init(en, 3, 3, p);
		g.step();
		g.step();
		g.step();
		assert(g.getHgt() == 2);
	}
	
	@Test
	public void behav_right() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(2, 1, Cell.PLT);
		s.setNature(3, 1, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2),new Coordinates(3,2));
		PlayerService pi = new Player();
		PlayerContract p = new PlayerContract(pi);
		pi.init(en, 2, 2);
		GuardService gi = new Guard();
		GuardContrtact g = new GuardContrtact(gi);
		g.init(en, 3, 2, p);
		g.step();
		g.step();
		g.step();
		assert(g.getHgt() == 2 && g.getWdt() == 2);
	}
	
	@Test
	public void behav_left() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(3, 1, Cell.PLT);
		s.setNature(4, 1, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2),new Coordinates(3,2));
		PlayerService pi = new Player();
		PlayerContract p = new PlayerContract(pi);
		pi.init(en, 4, 2);
		GuardService gi = new Guard();
		GuardContrtact g = new GuardContrtact(gi);
		g.init(en, 3, 2, p);
		g.step();
		g.step();
		g.step();
		assert(g.getHgt() == 2 && g.getWdt() == 4);
	}
	
	@Test
	public void behav_down() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2),new Coordinates(3,2));
		PlayerService pi = new Player();
		PlayerContract p = new PlayerContract(pi);
		pi.init(en, 2, 2);
		GuardService gi = new Guard();
		GuardContrtact g = new GuardContrtact(gi);
		g.init(en, 2, 3, p);
		g.step();
		g.step();
		g.step();
		assert(g.getHgt() == 2 && g.getWdt() == 2);
	}
	
	//Paire de Transitions
	
	@Test
	public void climb_left() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(2, 1, Cell.PLT);
		s.setNature(3, 1, Cell.PLT);
		s.setNature(4, 1, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,1),new Coordinates(2,2));
		PlayerService pi = new Player();
		PlayerContract p = new PlayerContract(pi);
		p.init(en, 2, 2);
		en.addCommand(Command.DIGR);
		p.step();	
		assert env.getCellNature(3, 1) == Cell.HOL;
		GuardService gi = new Guard();
		GuardContrtact g = new GuardContrtact(gi);
		g.init(en, 4, 2, p);
		g.step();
		g.step();
		g.step();
		assert(g.getHgt() == 2 && g.getWdt() == 3);
		g.step();
		g.step();
		g.step();
		assert(g.getHgt() == 1 && g.getWdt() == 3);

	}
	
	
	//recuperation d'un tresor
	@Test
	public void trasure_guard() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(11,10);
		s.setNature(3, 1, Cell.PLT);
		s.setNature(4, 1, Cell.PLT);
		s.setNature(5, 1, Cell.PLT);
		s.setNature(6, 1, Cell.PLT);
		s.setNature(7, 1, Cell.PLT);
		s.setNature(8, 1, Cell.PLT);
		s.setNature(9, 1, Cell.PLT);
		s.setNature(10, 1, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineService e = new Engine();
		EngineContract enconrat = new EngineContract(e);
		ArrayList<Coordinates> t = new ArrayList<>();
		t.add(new Coordinates(8, 2));
		ArrayList<Coordinates> g = new ArrayList<>();
		g.add(new Coordinates(9, 2));
		ArrayList<Coordinates> b = new ArrayList<>();
		ArrayList<Door> d = new ArrayList<>();
		Coordinates pcoord = new Coordinates(4, 2);
		enconrat.init(env, pcoord, g, t, b, d);
		GuardService guard = enconrat.getGuards().get(0);
		enconrat.step();
		enconrat.step();
		enconrat.step();
		enconrat.step();
		enconrat.step();
		assert(guard.has_treasure() == true && enconrat.getTreasures().size() == 0);
	}
	
	//drop d'un tresor
	@Test
	public void trasure_guard_2() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(11,10);
		s.setNature(3, 1, Cell.PLT);
		s.setNature(4, 1, Cell.PLT);
		s.setNature(5, 1, Cell.PLT);
		s.setNature(6, 1, Cell.PLT);
		s.setNature(7, 1, Cell.PLT);
		s.setNature(8, 1, Cell.PLT);
		s.setNature(9, 1, Cell.PLT);
		s.setNature(10, 1, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineService e = new Engine();
		EngineContract enconrat = new EngineContract(e);
		ArrayList<Coordinates> t = new ArrayList<>();
		t.add(new Coordinates(9, 2));
		ArrayList<Coordinates> g = new ArrayList<>();
		g.add(new Coordinates(9, 2));
		ArrayList<Coordinates> b = new ArrayList<>();
		ArrayList<Door> d = new ArrayList<>();
		Coordinates pcoord = new Coordinates(7, 2);
		enconrat.init(env, pcoord, g, t, b, d);
		enconrat.addCommand(Command.DIGR);
		GuardService guard = enconrat.getGuards().get(0);
		enconrat.step();
		assert(guard.has_treasure() == true && enconrat.getTreasures().size() == 0);
		
		while(enconrat.getEnvi().getCellNature(guard.getWdt(), guard.getHgt()) != Cell.HOL) {
			enconrat.step();
		}
		assert(guard.has_treasure() == false && enconrat.getTreasures().size() == 1);

	}
	
	//die
	@Test
	public void die_guard() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(11,10);
		s.setNature(3, 1, Cell.PLT);
		s.setNature(4, 1, Cell.PLT);
		s.setNature(5, 1, Cell.PLT);
		s.setNature(6, 1, Cell.PLT);
		s.setNature(7, 1, Cell.PLT);
		s.setNature(8, 1, Cell.PLT);
		s.setNature(9, 1, Cell.PLT);
		s.setNature(10, 1, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineService e = new Engine();
		EngineContract enconrat = new EngineContract(e);
		ArrayList<Coordinates> t = new ArrayList<>();
		ArrayList<Coordinates> g = new ArrayList<>();
		g.add(new Coordinates(8, 2));
		ArrayList<Coordinates> b = new ArrayList<>();
		b.add(new Coordinates(7, 2));
		ArrayList<Door> d = new ArrayList<>();
		Coordinates pcoord = new Coordinates(6, 2);
		enconrat.init(env, pcoord, g, t, b, d);
		enconrat.addCommand(Command.RIGHT);
		enconrat.step();
		enconrat.addCommand(Command.FIGHT);
		enconrat.step();
		assert(enconrat.getGuards().size() == 0);
	}
	
	//die with treasure
	@Test
	public void die_guard_with_treasure() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(11,10);
		s.setNature(3, 1, Cell.PLT);
		s.setNature(4, 1, Cell.PLT);
		s.setNature(5, 1, Cell.PLT);
		s.setNature(6, 1, Cell.PLT);
		s.setNature(7, 1, Cell.PLT);
		s.setNature(8, 1, Cell.PLT);
		s.setNature(9, 1, Cell.PLT);
		s.setNature(10, 1, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineService e = new Engine();
		EngineContract enconrat = new EngineContract(e);
		ArrayList<Coordinates> t = new ArrayList<>();
		t.add(new Coordinates(8, 2));
		ArrayList<Coordinates> g = new ArrayList<>();
		g.add(new Coordinates(8, 2));
		ArrayList<Coordinates> b = new ArrayList<>();
		b.add(new Coordinates(7, 2));
		ArrayList<Door> d = new ArrayList<>();
		Coordinates pcoord = new Coordinates(6, 2);
		enconrat.init(env, pcoord, g, t, b, d);
		enconrat.addCommand(Command.RIGHT);
		enconrat.step();
		enconrat.addCommand(Command.FIGHT);
		enconrat.step();
		assert(enconrat.getGuards().size() == 0);
		assert(enconrat.getTreasures().size() == 1);
		assert(enconrat.getEnvi().getCellContentItem(8, 2).size() == 1);
	}
	
	//etat remarquable
	@Test
	public void reinit_guard() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(11,10);
		s.setNature(3, 1, Cell.PLT);
		s.setNature(4, 1, Cell.PLT);
		s.setNature(5, 1, Cell.PLT);
		s.setNature(6, 1, Cell.PLT);
		s.setNature(7, 1, Cell.PLT);
		s.setNature(8, 1, Cell.PLT);
		s.setNature(9, 1, Cell.PLT);
		s.setNature(10, 1, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineService e = new Engine();
		EngineContract enconrat = new EngineContract(e);
		ArrayList<Coordinates> t = new ArrayList<>();
		ArrayList<Coordinates> g = new ArrayList<>();
		g.add(new Coordinates(10, 2));
		ArrayList<Coordinates> b = new ArrayList<>();
		ArrayList<Door> d = new ArrayList<>();
		Coordinates pcoord = new Coordinates(7, 2);
		enconrat.init(env, pcoord, g, t, b, d);
		GuardService guard = enconrat.getGuards().get(0);
		e.addCommand(Command.DIGR);
		enconrat.step();
		assert(enconrat.getEnvi().getCellNature(8, 1) == Cell.HOL);
		e.addCommand(Command.LEFT);
		enconrat.step();
		e.addCommand(Command.DIGR);
		enconrat.step();
		assert(enconrat.getEnvi().getCellNature(7, 1) == Cell.HOL);
		e.addCommand(Command.LEFT);
		enconrat.step();
		e.addCommand(Command.DIGR);
		enconrat.step();
		assert(enconrat.getEnvi().getCellNature(6, 1) == Cell.HOL);
		e.addCommand(Command.LEFT);
		enconrat.step();
		e.addCommand(Command.DIGR);
		enconrat.step();
		assert(enconrat.getEnvi().getCellNature(5, 1) == Cell.HOL);
		e.addCommand(Command.LEFT);
		enconrat.step();
		e.addCommand(Command.DIGR);
		enconrat.step();
		assert(enconrat.getEnvi().getCellNature(4, 1) == Cell.HOL);
		
		assert(enconrat.getPlayer().getWdt() == 3 && enconrat.getPlayer().getHgt() == 2);
		while(enconrat.getEnvi().getCellNature(guard.getWdt(), guard.getHgt()) != Cell.PLT) {
			enconrat.step();
		}
		enconrat.step();
		assert(guard.getWdt() == 10 && guard.getHgt() == 2);
		
	}
	
}
