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

	
	
	public void init(EnvironmentService screen, Coordinates playerCoord, ArrayList<Coordinates> guardsCoord,
			ArrayList<Coordinates> treasuresCoord) {
		env = screen;
		int id = 0;
		
		guards = new ArrayList<GuardService>();
		commands = new ArrayList<Command>();
		treasures = new ArrayList<ItemService>();
		holesTimes = new int[screen.getWidth()][screen.getHeight()];
		
		for(Coordinates c : treasuresCoord) {
			Item i = new Item();
			i.init(id++,ItemType.TREASURE,c.getX(),c.getY());
			treasures.add(i);
			env.addCellContentItem(c.getX(),c.getY(), i);
		}
		
		int h_play = playerCoord.getX();
		int w_play = playerCoord.getY();
		player = new Player();
		player.init(this, h_play, w_play);
		env.addCellContentChar(h_play,w_play,player);
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
		System.out.println("nb items dans commande : " + commands.size());

		if (commands.size() == 0) {
			commands.add(Command.NEUTRAL);
		}
		Command c = commands.get(0);
		commands.remove(0);
		return c;
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
		System.out.println("wdt vaut : " + player.getWdt());
		if(s != Stat.WIN) {
			containTreasure();
			if (treasures.size() == 0 ) {
				//s = Stat.WIN;
			}
			player.step();
		
			paceOfTime();
		}
	}

	@Override
	public void addCommand(Command c) {
		System.out.println("salut");
		commands.add(c);
		
	}


}
