package test_implem_bugee;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;

import org.junit.Test;


import implem_bugee.*;
import contracts.EditableScreenContract;
import contracts.EngineContract;
import contracts.EnvironmentContract;
import contracts.PlayerContract;
import services.Cell;
import services.Command;
import services.Coordinates;
import services.Door;
import services.EngineService;
import services.PlayerService;
import services.Stat;
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
		assert(pcontrat.getHgt() == 1);
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
		s.setNature(2, 2, Cell.LAD);
		s.setNature(2, 3, Cell.LAD);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		PlayerService p = new Player();
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2));
		en.addCommand(Command.UP);
		PlayerContract pcontrat = new PlayerContract(p);
		pcontrat.init(en, 2, 2);
		pcontrat.step();
		assert(pcontrat.getHgt() == 3);
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
		assert(pcontrat.getWdt()==3);
	}
	
	@Test
	public void step_door() {
			EditableScreenContract s = SetUtil.MakeEdiatableScreen(11,10);
			s.setNature(3, 1, Cell.PLT);
			s.setNature(4, 1, Cell.PLT);
			EnvironmentContract env = SetUtil.EnviMaker(s);
			EngineService e = new Engine();
			EngineContract enconrat = new EngineContract(e);
			ArrayList<Coordinates> t = new ArrayList<>();
			ArrayList<Coordinates> g = new ArrayList<>();
			g.add(new Coordinates(10, 2));
			ArrayList<Coordinates> b = new ArrayList<>();
			ArrayList<Door> d = new ArrayList<>();
			d.add(new Door(new Coordinates(3, 2), new Coordinates(4, 2)));
			Coordinates pcoord = new Coordinates(3, 2);
			enconrat.init(env, pcoord, g, t, b, d);
			enconrat.addCommand(Command.OPEND);
			enconrat.step();
			assert(enconrat.getPlayer().getWdt() == 4 && enconrat.getPlayer().getHgt() == 2);
	}
	
	@Test
	public void step_up_door_2() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(2, 1, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		PlayerService p = new Player();
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2));
		PlayerContract pcontrat = new PlayerContract(p);
		en.addCommand(Command.OPEND);
		pcontrat.init(en, 2, 2);
		pcontrat.step();
		assert(pcontrat.getWdt() == 2 && pcontrat.getHgt() == 2);
	}
	
	@Test
	public void step_fall() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(5, 4, Cell.HOL);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		PlayerService p = new Player();
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(5,5));
		PlayerContract pcontrat = new PlayerContract(p);
		pcontrat.init(en, 5, 5);
		pcontrat.step();
		assert(pcontrat.getHgt() == 4);
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
		assert(env.getCellNature(4, 4) == Cell.HOL);
	}
	
	@Test
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
		assert en.getEnvi().getCellNature(4, 4) == Cell.MTL;
	}
	
	// Paire de Transition
	@Test
	public void up_left() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(1, 2, Cell.PLT);
		s.setNature(2, 3, Cell.LAD);
		s.setNature(2, 2, Cell.LAD);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		PlayerService p = new Player();
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2));
		en.addCommand(Command.UP);
		PlayerContract pcontrat = new PlayerContract(p);
		pcontrat.init(en, 2, 2);
		pcontrat.step();
		en.addCommand(Command.LEFT);
		assert(pcontrat.getWdt() == 2 && pcontrat.getHgt() == 3);
		pcontrat.step();
		assert(pcontrat.getHgt() == 3 && pcontrat.getWdt() == 1);
	}
	
	@Test
	public void up_right() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(3, 2, Cell.PLT);
		s.setNature(2, 3, Cell.LAD);
		s.setNature(2, 2, Cell.LAD);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		PlayerService p = new Player();
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2));
		en.addCommand(Command.UP);
		PlayerContract pcontrat = new PlayerContract(p);
		pcontrat.init(en, 2, 2);
		pcontrat.step();
		en.addCommand(Command.RIGHT);
		pcontrat.step();
		assert(pcontrat.getHgt() == 3 && pcontrat.getWdt() == 3);
		
	}
	
	
	@Test
	public void up_dig_left() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(1, 2, Cell.PLT);
		s.setNature(2, 3, Cell.LAD);
		s.setNature(2, 2, Cell.LAD);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		PlayerService p = new Player();
		EngineContract en = SetUtil.Engine_maker(env,new Coordinates(2,2));
		en.addCommand(Command.UP);
		PlayerContract pcontrat = new PlayerContract(p);
		pcontrat.init(en, 2, 2);
		pcontrat.step();
		en.addCommand(Command.DIGL);
		pcontrat.step();
		assert(env.getCellNature(1, 2) == Cell.HOL);
	}
	
	
	@Test
	public void get_bomb() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(11,10);
		s.setNature(3, 1, Cell.PLT);
		s.setNature(4, 1, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineService e = new Engine();
		EngineContract enconrat = new EngineContract(e);
		ArrayList<Coordinates> t = new ArrayList<>();
		ArrayList<Coordinates> g = new ArrayList<>();
		g.add(new Coordinates(10, 2));
		ArrayList<Coordinates> b = new ArrayList<>();
		b.add(new Coordinates(4, 2));
		ArrayList<Door> d = new ArrayList<>();
		Coordinates pcoord = new Coordinates(3, 2);
		enconrat.init(env, pcoord, g, t, b, d);
		enconrat.addCommand(Command.RIGHT);
		enconrat.step();
		enconrat.step();
		assert(enconrat.getPlayer().getBomb() != null);
		assert(enconrat.getBombs().size() == 0);
	}
	
	//etat remarquable
	@Test
	public void game_loss() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(3, 1, Cell.PLT);
		s.setNature(4, 1, Cell.PLT);
		s.setNature(5, 1, Cell.PLT);
		EnvironmentContract env = SetUtil.EnviMaker(s);
		EngineService e = new Engine();
		EngineContract enconrat = new EngineContract(e);
		ArrayList<Coordinates> t = new ArrayList<>();
		ArrayList<Coordinates> g = new ArrayList<>();
		ArrayList<Coordinates> b = new ArrayList<>();
		ArrayList<Door> d = new ArrayList<>();
		Coordinates pcoord = new Coordinates(3, 2);
		enconrat.init(env, pcoord, g, t, b, d);
		e.addCommand(Command.DIGR);
		enconrat.step();
		assert(enconrat.getEnvi().getCellNature(4, 1) == Cell.HOL);
		assert(enconrat.getHoles(4, 1) == 0);
		e.addCommand(Command.RIGHT);
		enconrat.step();

		assert(enconrat.getPlayer().getWdt() == 4 && enconrat.getPlayer().getHgt() == 2);
		enconrat.step();
		assert(enconrat.getPlayer().getWdt() == 4 && enconrat.getPlayer().getHgt() == 1);
		while(enconrat.getEnvi().getCellNature(4, 1) == Cell.HOL) {
			enconrat.step();
		}
		assert(enconrat.getStatus() == Stat.LOSS);
		
	}
	
	//Scenario
	@Test
	public void scenario() {
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(10,10);
		s.setNature(4, 3, Cell.PLT);
		s.setNature(3, 3, Cell.PLT);
		s.setNature(2, 4, Cell.LAD);
		s.setNature(2, 3, Cell.LAD);
		s.setNature(2, 2, Cell.LAD);
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
		assert(pcontrat.getWdt() == 3 && pcontrat.getHgt() == 4);
		en.addCommand(Command.DIGR);
		pcontrat.step();
		assert(env.getCellNature(4,3 ) == Cell.HOL);
	}
}
