package util;

import java.util.ArrayList;

import components.EditableScreen;
import components.Engine;
import components.Environment;
import contracts.EditableScreenContract;
import contracts.EngineContract;
import contracts.EnvironmentContract;
import contracts.PlayerContract;
import services.Cell;
import services.Coordinates;
import services.EditableScreenService;
import services.EngineService;
import services.EnvironmentService;

public class SetUtil {

	public static boolean isIn(Cell obj, Cell[] my_set) {
		for(Cell e: my_set) {
			if(e == obj) {
				return true;
			}
		}
		return false;
	}
	
	public static EditableScreenContract MakeEdiatableScreen(int x,int y) {
		EditableScreenService s = new EditableScreen();
		EditableScreenContract scontrat = new EditableScreenContract(s);
		scontrat.init(x, y);
		for(int i = 0; i < x; i++) {
			scontrat.setNature(i, 0,Cell.MTL);
		}
		
		return scontrat;
	}
	
	public static EnvironmentContract EnviMaker(EditableScreenContract s) {
		EnvironmentService e = new Environment();
		EnvironmentContract econtrat = new EnvironmentContract(e);
		econtrat.init(s);
		return econtrat;
	}
	
	public static EngineContract Engine_maker(EnvironmentContract econtrat, Coordinates player) {
		EngineService en = new Engine();
		EngineContract enconrat = new EngineContract(en);
		ArrayList<Coordinates> t = new ArrayList<>();
		ArrayList<Coordinates> g = new ArrayList<>();
		ArrayList<Coordinates> b = new ArrayList<>();
		enconrat.init(econtrat, player, g, t, b);
		return enconrat;
	}
	
	public static EngineContract Engine_maker(EnvironmentContract econtrat, Coordinates player, Coordinates guard) {
		EngineService en = new Engine();
		EngineContract enconrat = new EngineContract(en);
		ArrayList<Coordinates> t = new ArrayList<>();
		ArrayList<Coordinates> g = new ArrayList<>();
		g.add(guard);
		ArrayList<Coordinates> b = new ArrayList<>();
		enconrat.init(econtrat, player, g, t, b);
		return enconrat;
	}
}
