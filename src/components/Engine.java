package components;

import java.util.ArrayList;

import services.Command;
import services.Coordinates;
import services.EditableScreenService;
import services.EngineService;
import services.EnvironmentService;
import services.GuardService;
import services.ItemService;
import services.ItemType;
import services.PlayerService;
import services.Status;

public class Engine implements EngineService {
	
	private EnvironmentService env;
	private PlayerService player;
	public ArrayList<GuardService> guards;
	public ArrayList<ItemService> treasures;
	public Status s;
	public ArrayList<Command> commands;
	
	public void init(EditableScreenService screen, Coordinates playerCoord, ArrayList<Coordinates> guardsCoord,
			ArrayList<Coordinates> treasuresCoord) {
		env = (Environment) screen;
		int id = 0;
		for(Coordinates c : treasuresCoord) {
			Item i = new Item();
			i.init(id++,ItemType.TREASURE,c.getX(),c.getY());
			treasures.add(id,i);
			env.addCellContentItem(c.getX(),c.getY(), i);
		}
		
		int h_play = playerCoord.getX();
		int w_play = playerCoord.getY();
		Player p = new Player();
		p.init(env, h_play, w_play);
		env.addCellContentChar(h_play,w_play,p);
		s = Status.PLAYING;

	}
	
	@Override
	public EnvironmentService getEnvi() {
		return env;
	}

	@Override
	public PlayerService getPlayer() {
		return player;
	}

	@Override
	public ArrayList<GuardService> getGuards() {
		return guards;
	}

	@Override
	public ArrayList<ItemService> getTreasures() {
		// TODO Auto-generated method stub
		return treasures;
	}

	@Override
	public Status getStatus() {
		// TODO Auto-generated method stub
		return s;
	}

	@Override
	public Command getNextCommand() {
		// TODO Auto-generated method stub
		return commands.get(commands.size());
	}
	
	public void containTreasure() {
		ArrayList<ItemService> items = getEnvi().getCellContentItem(player.getHgt(),player.getWdt());
		if(items.size() > 0){
			treasures.remove(items.get(0).getId());
			getEnvi().removeCellContentItem(player.getHgt(),player.getWdt(),items.get(0));
		}
	}
	

	@Override
	public void step() {
		// TODO Auto-generated method stub
		while(s != Status.WIN) {
			containTreasure();
			if (treasures.size() == 0 ) {
				s = Status.WIN;
			}
			player.step();
		}
	}

}
