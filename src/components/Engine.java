package components;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import services.Cell;
import services.CharacterService;
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
		//Initialisaiton des gardes 
		
		for(Coordinates c : guardsCoord) {
			GuardService g = new Guard();
			g.init(this, c.getX(), c.getY(), player);
			guards.add(g);
			env.addCellContentChar(c.getX(), c.getY(), g);			
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
		System.out.println("je rentre dans pace");
		for (int i = 0; i < getEnvi().getWidth(); i++) {
			for (int j = 0 ; j < getEnvi().getHeight(); j++) {
				if (getEnvi().getCellNature(i, j) == Cell.HOL) {
					if(holesTimes[i][j] + 1 != 50) {
						holesTimes[i][j] = holesTimes[i][j] + 1;
					}
					else {
						if(holesTimes[i][j] + 1 == 50) {
							if(getEnvi().getCellContentChar(i, j).size() != 0) {
								System.out.println("il s'agit de" + getEnvi().getCellContentChar(i, j));
								for (CharacterService c : getEnvi().getCellContentChar(i, j)) {
									if (c instanceof GuardService) {
										((GuardService) c).Reinitialize();
									}
								}
							holesTimes[i][j] = -1;
							getEnvi().fill(i, j);
							}
							
						}
					}
				}
			}
		}
	}
	
	public boolean haschased() {
		for(GuardService g : guards) {
			if (g.getWdt() == getPlayer().getWdt() && g.getHgt() == getPlayer().getHgt()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void step() {
		// TODO Auto-generated method stub
		System.out.println("wdt vaut : " + player.getWdt());
	    
		if(s != Stat.WIN || s!= Stat.LOSS) {
			containTreasure();
			if (treasures.size() == 0 ) {
				s = Stat.WIN;
			}
			if(haschased()) {
				s = Stat.LOSS;
			}
			//getEnvi().removeCellContentChar(player.getWdt(), player.getHgt(), player);
			player.step();
			for (GuardService g : guards ) {
				System.out.println("on est la !");
				g.step();
			}
			//getEnvi().addCellContentChar(player.getWdt(), player.getHgt(), player);
			paceOfTime();
		}
	}

	@Override
	public void addCommand(Command c) {
		nextCommand = c;
		
	}


}
