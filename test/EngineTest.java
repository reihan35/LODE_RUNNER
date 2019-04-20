package test;
import java.util.ArrayList;

import org.junit.Test;

import components.Engine;
import components.Environment;
import components.Player;
import contracts.EditableScreenContract;
import contracts.EngineContract;
import contracts.EnvironmentContract;
import contracts.PlayerContract;
import services.Command;
import services.Coordinates;
import services.EngineService;
import services.EnvironmentService;
import services.PlayerService;
import util.SetUtil;

public class EngineTest {
	@Test
	public void step() {
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
		enconrat.addCommand(Command.UP);
		PlayerService p = new Player();
		PlayerContract pcontrat = new PlayerContract(p);
		pcontrat.init(enconrat, 2, 2);
		pcontrat.step();
	}
	
	
}
