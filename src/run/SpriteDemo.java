package run;


import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.plaf.basic.BasicTreeUI.KeyHandler;


import components.EditableScreen;
import components.Engine;
import components.Environment;
import contracts.EditableScreenContract;
import contracts.EngineContract;
import contracts.EnvironmentContract;
import services.Cell;
import services.Command;
import services.Coordinates;
import services.EditableScreenService;
import services.EngineService;
import services.EnvironmentService;
import services.GuardService;
import services.ItemService;
import services.PlayerService;
import services.Stat;

import java.util.ArrayList;
import java.util.Random;


//Problems: Agents stay at their position in the window, not in the world --> once out of the window, they disappear!
public class SpriteDemo extends JPanel implements KeyListener{

	Image emp, mtl, lad, hdr, treas, player, plt, hol, guard,player2, lost,une,deux,trois,won, door, bomb;
	
	public static final int spriteLength = 32;
	
	int sizeWindow_x;
	int sizeWindow_y;
	
	
	private JFrame frame;
	//world seen
	private EditableScreenService level;
	private EngineService moteur;
	private ArrayList<Coordinates> treasures;
	private ArrayList<Coordinates> guards;
	private ArrayList<Coordinates> bombs;
	//whole world
	private EnvironmentService envi;
	int vie = 3;
	Random rand = new Random();
	int n = 0;
	boolean p_c;
	
	public int get_vie() {
		return vie;
	}

	public SpriteDemo()
	{
		try
		{
			emp = ImageIO.read(new File("../Sprites/EMP2.png"));
			hol = ImageIO.read(new File("../Sprites/EMP2.png"));
			mtl = ImageIO.read(new File("../Sprites/MTL2.png"));
			treas = ImageIO.read(new File("../Sprites/TREASURE3.png"));
			player = ImageIO.read(new File("../Sprites/PERSO2modif.png"));
			hdr = ImageIO.read(new File("../Sprites/HDR2.png"));
			plt = ImageIO.read(new File("../Sprites/PLT2.png"));
			lad = ImageIO.read(new File("../Sprites/LADDER2.png"));
			guard =  ImageIO.read(new File("../Sprites/GUARD2.png"));
			lost = ImageIO.read(new File("../Sprites/buttonX.png"));
			une = ImageIO.read(new File("../Sprites/gamepad1.png"));
			deux = ImageIO.read(new File("../Sprites/gamepad2.png"));
			trois = ImageIO.read(new File("../Sprites/gamepad3.png"));
			won = ImageIO.read(new File("../Sprites/trophy.png"));
			door = ImageIO.read(new File("../Sprites/DOOR.png"));
			bomb = ImageIO.read(new File("../Sprites/sword.png"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}

		moteur = new EngineContract(new Engine());
		level = new EditableScreenContract(new EditableScreen());
		envi = new EnvironmentContract(new Environment());
		treasures = new ArrayList<>();
		guards = new ArrayList<>();
		bombs = new ArrayList<>();
		level.init(28, 16);
		parseLevel("../Levels/Level1.txt");
		envi.init(level);
		moteur.init(envi, new Coordinates(5, 7), guards, treasures,bombs);
		frame = new JFrame("LODE RUNNER");
		frame.add(this);
		frame.addKeyListener(this);
		sizeWindow_x = spriteLength * moteur.getEnvi().getWidth() ;
		sizeWindow_y = spriteLength * moteur.getEnvi().getHeight() ;
		frame.setSize(sizeWindow_x, sizeWindow_y);
		frame.setVisible(true);

		
	}
	
	public void parseLevel(String filename) {
		try (FileReader reader = new FileReader(filename);
	             BufferedReader br = new BufferedReader(reader)) {

	            // read line by line
	            String line;
	            for(int i = 0; i < level.getWidth();i++) {
	            	level.setNature(i, 0, Cell.MTL);
	            }
	            while ((line = br.readLine()) != null) {
	                String[] tokens = line.split(" ");
	                System.out.println(tokens[0]);
	                Cell c = Cell.EMP;
	                boolean cellNature = false;
	                boolean treasure = false;
	                switch(tokens[0]) {
	                case "EMP":
	                	c = Cell.EMP;
	                	cellNature = true;
	                	break;
	                case "DOR":
	                	c = Cell.DOR;
	                	System.out.println("DOOOOOOR");
	                	cellNature = true;
	                	break;
	                case "HDR":
	                	c = Cell.HDR;
	                	cellNature = true;
	                	break;
	                case "MTL":
	                	c = Cell.MTL;
	                	cellNature = true;
	                	break;
	                case "LAD":
	                	c = Cell.LAD;
	                	cellNature = true;
	                	break;
	                case "PLT":
	                	c = Cell.PLT;
	                	cellNature = true;
	                	break;
	                case "TRE":
	                	treasures.add(new Coordinates(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])));
	                	break;
	                case "GUA":
	                	guards.add(new Coordinates(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])));
	                	break;
	                case "BOM":
	                	bombs.add(new Coordinates(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])));
	                	break;
	                }
	                if(cellNature)
	                	level.setNature(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), c);
	            }

	        } catch (IOException e) {
	            System.err.format("IOException: %s%n", e);
	        }
	}
	
	public void keyPressed(KeyEvent e){
		System.out.println("le joueur se trouve en position " + moteur.getPlayer().getWdt() + " " + moteur.getPlayer().getHgt());
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			moteur.addCommand(Command.UP);
			break;
		case KeyEvent.VK_DOWN:
			moteur.addCommand(Command.DOWN);
			break;
		case KeyEvent.VK_LEFT:
			moteur.addCommand(Command.LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			moteur.addCommand(Command.RIGHT);
			break;
		case KeyEvent.VK_X:
			moteur.addCommand(Command.DIGR);
			break;
		case KeyEvent.VK_W:
			moteur.addCommand(Command.DIGL);
			break;
		case KeyEvent.VK_ENTER:
			moteur.addCommand(Command.OPEND);
		default:
			System.out.println("Other key");
			break;
		}
	}
	
	public void keyTyped(KeyEvent e){
		//System.out.println("Key typed: "+e.getKeyChar());
		return;
	}
	
	public void keyReleased(KeyEvent e){
		moteur.addCommand(Command.NEUTRAL);
	}
	
	public void paint(Graphics g)
	{
		//g.drawImage(emp,spriteLength*0,(moteur.getEnvi().getHeight()-(0 + 2))*spriteLength,spriteLength,spriteLength, frame);
		//Graphics2D g2 = (Graphics2D)g;
		//paints the world (and adds the WeatherTime)
		
		
		
		for(int i = 0; i < moteur.getEnvi().getWidth(); i++) {
			for(int j = 0; j < moteur.getEnvi().getHeight(); j++) {
				int pos_x = spriteLength * i;
				int pos_y = (moteur.getEnvi().getHeight()-(j+2))*spriteLength;
				Image img = emp;
				switch(moteur.getEnvi().getCellNature(i, j)) {
					case EMP:
						img = emp;
						break;
					case HDR:
						img = hdr;
						break;
					case HOL:
						img = hol;
						break;
					case LAD:
						img = lad;
						break;
					case MTL:
						img = mtl;
						break;
					case PLT:
						img = plt;
						break;
					case DOR:
						img = door;
					default:
						break;
				
				};
				g.drawImage(img,pos_x,pos_y,spriteLength,spriteLength, frame);
			}
		}
		
		Image img = emp;
		
		switch(vie) {
			case 0:
				g.drawImage(lost, 20, 15, 200, 200, frame);
				System.exit(0);

			case 1:
				img = une;
				break;
			case 2:
				img = deux;
				break;
			case 3:
				img = trois;
				break;
	
		};
		
		g.drawImage(img,22,22,spriteLength,spriteLength, frame);
		
		for(ItemService item: moteur.getTreasures()) {
			int pos_x = item.getWdt()*spriteLength;
			int pos_y = (moteur.getEnvi().getHeight()-(item.getHgt()+2))*spriteLength;
		
			g.drawImage(treas,pos_x,pos_y,spriteLength,spriteLength, frame);
		}
		
		for(ItemService item: moteur.getBombs()) {
			System.out.println("je rentre pas dans le bombes");
			int pos_x = item.getWdt()*spriteLength;
			int pos_y = (moteur.getEnvi().getHeight()-(item.getHgt()+2))*spriteLength;
		
			g.drawImage(bomb,pos_x,pos_y,spriteLength,spriteLength, frame);
		}
		
		for(GuardService gi: moteur.getGuards()) {
			int pos_x = gi.getWdt()*spriteLength;
			int pos_y = (moteur.getEnvi().getHeight()-(gi.getHgt()+2))*spriteLength;
		
			g.drawImage(guard,pos_x,pos_y,spriteLength,spriteLength, frame);
		}
		
		PlayerService p = moteur.getPlayer();
		if (moteur.getPlayer().willFall() && moteur.getEnvi().getCellNature(p.getWdt(), p.getHgt()) == Cell.EMP) {
			try {
				player2 = ImageIO.read(new File("../Sprites/player_fall.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
		else {
		
			if (moteur.getEnvi().getCellNature(p.getWdt(), p.getHgt()) == Cell.LAD && (moteur.getNextCommand()==Command.UP || moteur.getNextCommand()==Command.DOWN)) {
					try {
						player2 = ImageIO.read(new File("../Sprites/player_climb2.png"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}else {
					if(moteur.getEnvi().getCellNature(p.getWdt(), p.getHgt()) == Cell.HDR) {
						try {
							player2 = ImageIO.read(new File("../Sprites/player_cheer1.png"));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
						if(moteur.getNextCommand()==Command.LEFT) {
							try {
								player2 = ImageIO.read(new File("../Sprites/left_perso2.png"));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else {
								player2 = player;
							}
						}
					}
				}
			
				
		
		int pos_x = p.getWdt()*spriteLength;
		int pos_y = (moteur.getEnvi().getHeight()-(p.getHgt()+2))*spriteLength;
		g.drawImage(player2,pos_x,pos_y,spriteLength,spriteLength, frame);
		moteur.step();
		g.drawString("Score", 18, 15);
		g.drawString(Integer.toString(moteur.getScore()), 50, 15);
		if (moteur.getStatus() == Stat.WIN) {
			g.drawImage(won, 20, 20, 200, 200, frame);
		}
		
		if (moteur.getStatus() == Stat.LOSS) {
				moteur = new EngineContract(new Engine());
				level = new EditableScreenContract(new EditableScreen());
				envi = new EnvironmentContract(new Environment());
				treasures = new ArrayList<>();
				guards = new ArrayList<>();
				level.init(28, 16);
				parseLevel("../Levels/Level1.txt");
				envi.init(level);
				moteur.init(envi, new Coordinates(18, 7), guards, treasures,bombs);
				frame = new JFrame("LODE RUNNER");
				frame.add(this);
				frame.addKeyListener(this);
				sizeWindow_x = spriteLength * moteur.getEnvi().getWidth();
				sizeWindow_y = spriteLength * moteur.getEnvi().getHeight();
				frame.setSize(sizeWindow_x, sizeWindow_y);
				frame.setVisible(true);
				vie--;
				
		}
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		
		frame.repaint();
	}

	

	
	public static void main(String[] args) {
		int nombreDePasMaximum = 10000;
		int it = 0;
		
		SpriteDemo sd = new SpriteDemo();
		sd.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//sd.map.initEnv();
		/*try {
			Thread.sleep(500);
		} catch (InterruptedException e) */
		while ( it != nombreDePasMaximum && sd.get_vie()>=1 )
 		{	
			//System.out.println("Nous sommes le "+sd.map.wt.getDay()+"/"+sd.map.wt.getMonth()+", il est "+sd.map.wt.getHour()+" heures");

 			it++;
 		}
	}
}

