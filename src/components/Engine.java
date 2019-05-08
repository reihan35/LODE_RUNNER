package components;

import java.awt.event.ItemListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Random;

import contracts.GuardContrtact;
import contracts.PlayerContract;
import services.Cell;
import services.CharacterService;
import services.Command;
import services.Coordinates;
import services.Door;
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
	private ArrayList<ItemService> bombs;
	private ArrayList<Door> doors;
	private Stat s;
	private Command nextCommand;
	private int[][] holesTimes;
	Random rand = new Random();
	private int score = 0;
	private int first_t_n = 0;


	
	
	public void init(EnvironmentService screen, Coordinates playerCoord, ArrayList<Coordinates> guardsCoord,
			ArrayList<Coordinates> treasuresCoord, ArrayList<Coordinates> bombCoord, ArrayList<Door> doorCoord) {
		env = screen;
		int id = 0;
		
		guards = new ArrayList<GuardService>();
		treasures = new ArrayList<ItemService>();
		bombs = new ArrayList<ItemService>();
		holesTimes = new int[screen.getWidth()][screen.getHeight()];
		this.doors = doorCoord;
		
		for(Coordinates c : treasuresCoord) {
			ItemService i = new Item();
			i.init(id++,ItemType.TREASURE,c.getX(),c.getY());
			treasures.add(i);
			/*try {
				String s1 = c.getX() + " " + c.getY() + "\n";
				Files.write(Paths.get("error.txt"), s1.getBytes(), StandardOpenOption.APPEND);
			    
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			env.addCellContentItem(c.getX(),c.getY(), i);
		}
		

		for(Coordinates c : bombCoord) {
			ItemService i = new Item();
			i.init(id++,ItemType.BOMB,c.getX(),c.getY());
			bombs.add(i);
			env.addCellContentItem(c.getX(),c.getY(), i);
		}
		
		System.out.println("au moment");
		System.out.println(env.getCellContentItem(6, 7).size());
		first_t_n = treasures.size();
		
		int h_play = playerCoord.getX();
		int w_play = playerCoord.getY();
		player = new PlayerContract(new Player());
		//player = new Player();
		player.init(this, h_play, w_play);
		env.addCellContentChar(h_play,w_play,player);
		System.out.println("la size est : ");
		System.out.println(env.getCellContentChar(h_play,w_play).size());
		s = Stat.PLAYING;
		for(int i = 0 ; i < getEnvi().getWidth() ; i++) {
			for(int j = 0 ; j < getEnvi().getHeight() ; j++) {
				holesTimes[i][j] = -1;
			}
		}		
		//Initialisaiton des gardes 
		
		for(Coordinates c : guardsCoord) {
			GuardService g = new GuardContrtact(new Guard());
			g.init(this, c.getX(), c.getY(), player);
			guards.add(g);
			env.addCellContentChar(c.getX(), c.getY(), g);			
		}

		nextCommand = Command.NEUTRAL;

	}
	
	@Override
	public void removeTreasure() {
		treasures.remove(0);
	}
	
	@Override
	public EnvironmentService getEnvi() {
		return env;
	}
	
	@Override
	public void addTreasure(int wdt, int hgt) {
		System.out.println("j'ajoute un treasure");
		ItemService i = new Item();
		i.init(treasures.size()+1, ItemType.TREASURE, wdt, hgt);
		treasures.add(i);
		getEnvi().addCellContentItem(wdt, hgt,i);
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
	
	public void containTreasure(CharacterService c ) {
		ArrayList<ItemService> items = getEnvi().getCellContentItem(c.getWdt(),c.getHgt());
		System.out.println("je passe dans containTreasure");
		if(items.size() > 0 && items.get(0).getNature()!=ItemType.BOMB){
			if (c instanceof PlayerService) {
				treasures.remove(items.get(0));
				getEnvi().removeCellContentItem(c.getWdt(),c.getHgt(),items.get(0));
				score = score + 10;
				System.out.println("la taille des tresors" + treasures.size());
			}
		}
	}
	
	public void containBomb() {
		ArrayList<ItemService> items = getEnvi().getCellContentItem(getPlayer().getWdt(),getPlayer().getHgt());

		if(items.size() > 0 && items.get(0).getNature()==ItemType.BOMB){
			getPlayer().addBomb(items.get(0));
			getEnvi().removeCellContentItem(getPlayer().getWdt(),getPlayer().getHgt(),items.get(0));
			bombs.remove(0);
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
					if(holesTimes[i][j] + 1 != 50) {
						holesTimes[i][j] = holesTimes[i][j] + 1;
					}
					else {
						if(holesTimes[i][j] + 1 == 50) {
							holesTimes[i][j] = -1;
							getEnvi().fill(i, j);
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
	public int getScore () {
		return score;
	}

	@Override
	public int get_nb_first_tres() {
		return first_t_n;
	}
	
	@Override
	public void step() {
		// TODO Auto-generated method stub
		//if (player.getWdt() > 5) {
			//throw new Error("je peux augmenter");
		//}
	    
		if(s != Stat.WIN || s!= Stat.LOSS) {
			containTreasure(player);
			containBomb();
			if (score == first_t_n*10) {
				s = Stat.WIN;
			}
			
			getEnvi().removeCellContentChar(player.getWdt(), player.getHgt(), player);
			player.step();
			getEnvi().addCellContentChar(player.getWdt(), player.getHgt(), player);
			
			for (GuardService g : guards ) {
				getEnvi().removeCellContentChar(g.getWdt(), g.getHgt(), g);
				//containTreasure(g);
				g.step();
				getEnvi().addCellContentChar(g.getWdt(), g.getHgt(), g);
			}

			
			
			if(haschased()) {
				s = Stat.LOSS;
			}
			//getEnvi().addCellContentChar(player.getWdt(), player.getHgt(), player);
			paceOfTime();
			if(getEnvi().getCellNature(getPlayer().getWdt(), getPlayer().getHgt()) == Cell.PLT) {
				s = Stat.LOSS;
			}
		}
	}

	public void setS(Stat s) {
		this.s = s;
	}

	@Override
	public void addCommand(Command c) {
		nextCommand = c;
		
	}

	@Override
	public ArrayList<ItemService> getBombs() {
		// TODO Auto-generated method stub
		return bombs;
	}
	
	@Override
	public ArrayList<Door> getDoors() {
		// TODO Auto-generated method stub
		return doors;
	}


	public void removeGuard(GuardService g) {
		getEnvi().getCellContentChar(g.getWdt(), g.getHgt()).remove(0);
		for(GuardService g2: guards) {
			if(g2.getWdt() == g.getWdt() && g2.getHgt() == g.getHgt()) {
				guards.remove(g2);
				break;
			}
		}
		
	}


}
