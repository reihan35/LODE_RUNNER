package run;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.plaf.basic.BasicTreeUI.KeyHandler;

import components.EditableScreen;
import components.Engine;
import components.Environment;
import services.Cell;
import services.Command;
import services.Coordinates;
import services.EditableScreenService;
import services.EngineService;
import services.EnvironmentService;
import services.PlayerService;

import java.util.ArrayList;


//Problems: Agents stay at their position in the window, not in the world --> once out of the window, they disappear!
public class SpriteDemo2 extends JPanel implements KeyListener{

	Image emp, mtl, lad, hdr, treas, player, plt, hol;

	public static final int spriteLength = 32;
	
	final int sizeWindow_x;
	final int sizeWindow_y;
	
	
	private JFrame frame;
	//world seen
	private EditableScreenService level;
	private EngineService moteur;
	
	//whole world
	private EnvironmentService envi;
	

	public SpriteDemo2()
	{
		try
		{
			emp = ImageIO.read(new File("Sprites/EMP.png"));
			hol = ImageIO.read(new File("Sprites/EMP.png"));
			mtl = ImageIO.read(new File("Sprites/MTL.png"));
			treas = ImageIO.read(new File("Sprites/TREASURE.png"));
			player = ImageIO.read(new File("Sprites/PERSO.png"));
			hdr = ImageIO.read(new File("Sprites/HDR.png"));
			plt = ImageIO.read(new File("Sprites/PLT.png"));
			lad = ImageIO.read(new File("Sprites/LADDER.png"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}

		moteur = new Engine();
		level = new EditableScreen();
		envi = new Environment();
		level.init(16, 16);
		parseLevel("Levels/Level1.txt");
		envi.init(level);
		ArrayList<Coordinates> guards = new ArrayList<>();
		ArrayList<Coordinates> treasures = new ArrayList<>();
		moteur.init(envi, new Coordinates(0, 1), guards, treasures);
		frame = new JFrame("World of Sprite");
		frame.add(this);
		frame.addKeyListener(this);
		sizeWindow_x = spriteLength * moteur.getEnvi().getWidth();
		sizeWindow_y = spriteLength * moteur.getEnvi().getHeight();
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
	                switch(tokens[0]) {
	                case "EMP":
	                	c = Cell.EMP;
	                	break;
	                case "HDR":
	                	c = Cell.HDR;
	                	break;
	                case "MTL":
	                	c = Cell.MTL;
	                	break;
	                case "LAD":
	                	c = Cell.LAD;
	                	break;
	                case "PLT":
	                	c = Cell.PLT;
	                	break;
	                }
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
				default:
					break;
				
				};
				g.drawImage(img,pos_x,pos_y,spriteLength,spriteLength, frame);
			}
		}
		
		PlayerService p = moteur.getPlayer();
		int pos_x = p.getWdt()*spriteLength;
		int pos_y = (moteur.getEnvi().getHeight()-(p.getHgt()+2))*spriteLength;
		g.drawImage(player,pos_x,pos_y,spriteLength,spriteLength, frame);
		moteur.step();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		frame.repaint();
	}

	

	
	public static void main(String[] args) {
		int nombreDePasMaximum = 10000;
		int it = 0;
		
		SpriteDemo2 sd = new SpriteDemo2();
		sd.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//sd.map.initEnv();
		/*try {
			Thread.sleep(500);
		} catch (InterruptedException e) */
		while ( it != nombreDePasMaximum )
 		{
			//System.out.println("Nous sommes le "+sd.map.wt.getDay()+"/"+sd.map.wt.getMonth()+", il est "+sd.map.wt.getHour()+" heures");

 			it++;
 		}
	}
}

