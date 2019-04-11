package components;

import java.util.ArrayList;

import services.Cell;
import services.Command;
import services.Coordinates;
import services.EditableScreenService;
import services.EngineService;
import services.EnvironmentService;
import services.GuardService;
import services.ItemService;
import services.ItemType;
import services.PlayerService;
import services.Stat;
import services.Stat;

public class Engine implements EngineService {
	
	private EnvironmentService env;
	private PlayerService player;
	private ArrayList<GuardService> guards;
	private ArrayList<ItemService> treasures;
	private Stat s;
	private ArrayList<Command> commands;
	private int[][] holesTimes;

	
	
	public void init(EditableScreenService screen, Coordinates playerCoord, ArrayList<Coordinates> guardsCoord,
			ArrayList<Coordinates> treasuresCoord) {
		env = (Environment) screen;
		int id = 0;
		for(Coordinates c : treasuresCoord) {
			Item i = new Item();
			i.init(id++,ItemType.TREASURE,c.getX(),c.getY());
			treasures.add(i);
			env.addCellContentItem(c.getX(),c.getY(), i);
		}
		
		int h_play = playerCoord.getX();
		int w_play = playerCoord.getY();
		Player p = new Player();
		p.init(env, h_play, w_play);
		env.addCellContentChar(h_play,w_play,p);
		s = Stat.PLAYING;
		for(int i = 0 ; i < getEnvi().getWidth() ; i++) {
			for(int j = 0 ; j < getEnvi().getHeight() ; j++) {
				holesTimes[i][j] = -1;
			}
		}

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
	public Stat getStatus() {
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
	public int getHoles(int x , int y) {
		return holesTimes[x][y];
	}
	
	
	public void paceOfTime() {
		for (int i = 0; i < getEnvi().getWidth(); i++) {
			for (int j = 0 ; j < getEnvi().getHeight(); j++) {
				if (getEnvi().getCellNature(i, j) == Cell.HOL) {
					if(holesTimes[i][j] + 1 != 15) {
						holesTimes[i][j] = holesTimes[i][j] + 1;
					}
					else {
						holesTimes[i][j] = 0;
					}
				}
			}
		}
	}
	
	@Override
	public void step() {
		// TODO Auto-generated method stub
		while(s != Stat.WIN) {
			containTreasure();
			if (treasures.size() == 0 ) {
				s = Stat.WIN;
			}
			player.step();
		}
		paceOfTime();
	}


}
