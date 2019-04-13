package decorators;

import java.util.ArrayList;

import services.Command;
import services.Coordinates;
import services.EditableScreenService;
import services.EngineService;
import services.EnvironmentService;
import services.GuardService;
import services.ItemService;
import services.PlayerService;
import services.Stat;
import services.Stat;

public class EngineDecorator implements EngineService {
	EngineService delegates;
	
	
	@Override
	public void init(EnvironmentService screen, Coordinates playerCoord, ArrayList<Coordinates> guardsCoord,
			ArrayList<Coordinates> treasuresCoord) {
		// TODO Auto-generated method stub
		delegates.init(screen, playerCoord, guardsCoord, treasuresCoord);

	}
	
	@Override
	public EnvironmentService getEnvi() {
		// TODO Auto-generated method stub
		return delegates.getEnvi();
	}

	@Override
	public PlayerService getPlayer() {
		// TODO Auto-generated method stub
		return delegates.getPlayer();
	}

	@Override
	public ArrayList<GuardService> getGuards() {
		// TODO Auto-generated method stub
		return delegates.getGuards();
	}

	@Override
	public ArrayList<ItemService> getTreasures() {
		// TODO Auto-generated method stub
		return delegates.getTreasures();
	}

	@Override
	public Stat getStatus() {
		// TODO Auto-generated method stub
		return delegates.getStatus();
	}

	@Override
	public Command getNextCommand() {
		// TODO Auto-generated method stub
		return delegates.getNextCommand();
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		delegates.step();
	}

	@Override
	public int getHoles(int x, int y) {
		// TODO Auto-generated method stub
		return delegates.getHoles(x, y);
	}

	@Override
	public void addCommand(Command c) {
		delegates.addCommand(c);
		
	}
	


}
