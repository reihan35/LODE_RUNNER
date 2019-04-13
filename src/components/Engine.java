package components;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
	private Command nextCommand;
	private int[][] holesTimes;

	
	
	public void init(EnvironmentService screen, Coordinates playerCoord, ArrayList<Coordinates> guardsCoord,
			ArrayList<Coordinates> treasuresCoord) {
		env = screen;
		int id = 0;
		
		guards = new ArrayList<GuardService>();
		treasures = new ArrayList<ItemService>();
		holesTimes = new int[screen.getWidth()][screen.getHeight()];
		
		for(Coordinates c : treasuresCoord) {
			ItemService i = new Item();
			i.init(id++,ItemType.TREASURE,c.getX(),c.getY());
			treasures.add(i);
			try {
				String s1 = c.getX() + " " + c.getY() + "\n";
				Files.write(Paths.get("error.txt"), s1.getBytes(), StandardOpenOption.APPEND);
			    
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		nextCommand = Command.NEUTRAL;

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

		return nextCommand;
	}
	
	public void containTreasure() {
		ArrayList<ItemService> items = getEnvi().getCellContentItem(player.getWdt(),player.getHgt());
		if(items.size() > 0){
			treasures.remove(items.get(0));
			getEnvi().removeCellContentItem(player.getWdt(),player.getHgt(),items.get(0));
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
						holesTimes[i][j] = -1;
						getEnvi().fill(i, j);
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
			getEnvi().removeCellContentChar(player.getWdt(), player.getHgt(), player);
			player.step();
			getEnvi().addCellContentChar(player.getWdt(), player.getHgt(), player);
			paceOfTime();
		}
	}

	@Override
	public void addCommand(Command c) {
		nextCommand = c;
		
	}


}
