package contracts;

import java.util.ArrayList;

import com.sun.org.apache.xerces.internal.util.Status;

import components.Item;
import decorators.EngineDecorator;
import services.Cell;
import services.Command;
import services.Coordinates;
import services.EditableScreenService;
import services.EngineService;
import services.ItemService;
import services.Stat;
import sun.awt.windows.WToolkit;

public class EngineContract extends EngineDecorator implements EngineService{
	
	
	public void init(EditableScreenService screen,Coordinates playerCoord,ArrayList<Coordinates> guardsCoord,
			ArrayList<Coordinates> treasuresCoord) {
		if (!screen.isPlayable()) {
			throw new PreconditionError("l'écran n'est pas jouable !");
		}
		
		super.init(screen, playerCoord, guardsCoord, treasuresCoord);
		
		if(getEnvi().getScreen() != screen) {
			throw new PostconditionError("L'ecran de l'environnement ne corespond pas à celui passé en parametre !");
		}
		
		if(getPlayer().getHgt() != playerCoord.getX() || getPlayer().getWdt() != playerCoord.getY()) {
			throw new PostconditionError("La position initiale du joueur ne correspond pas à celle passée en parametre ! ");
		}
		
		for(int i = 0 ; i < getTreasures().size() ; i++) {
			int hgt = getTreasures().get(i).getHgt();
			int wdt = getTreasures().get(i).getWdt();
			if (!getEnvi().getCellContentItem(wdt,hgt).contains(getTreasures().get(i))){
				throw new PostconditionError("L'un des tresors n'est pas à la bonne place !");
			}
		}
		
		if(getStatus() != Stat.PLAYING) {
			throw new PostconditionError("Le jeu n'est pas en mode playing apres l'initialisation !");
		}
	
	}
	
	public void checkInvariants() {
		for(int i = 0; i<getTreasures().size(); i++) {
			int hgt = getTreasures().get(i).getHgt();
			int wdt = getTreasures().get(i).getWdt();
			 if (!getEnvi().getCellContentItem(wdt,hgt).contains(getTreasures().get(i))) {
				 throw new InvariantError("Il n'y a pas de tresor dans cette cas !");
			 }
			
		}
		
		if ((getTreasures().size() == 0) && (getStatus() != Stat.WIN)) {
			 throw new InvariantError("La partie n'a pas été gagnée alors que il y a plus de tresors");
		}
		
		int x_play = getPlayer().getWdt();
		int y_play = getPlayer().getHgt();
		
		if (!getEnvi().getCellContentChar(x_play,y_play).contains(getPlayer())) {
			 throw new InvariantError("Le joueur n'est pas à la bonne place !");
		}
		
	}
	
	public void step() {
		
		int hgt_player_at_pre = getPlayer().getHgt();
		int wdt_player_at_pre = getPlayer().getWdt();
		ArrayList<ItemService> treasors_at_pre = getTreasures();
		ArrayList<ArrayList<ItemService>> CellContent_Item_at_pre ;
		for(int i = 0 ; i<getEnvi().getWidth(); i++) {
			for(int j = 0; j< getEnvi().getHeight() ; j++) {
				CellContent_Item_at_pre.add(getEnvi().getCellContentItem(i, j));
			}
		}
		
		super.step();
		
		if(CellContent_Item_at_pre(hgt_player_at_pre, wdt_player_at_pre).size() !=0 )
			
		}

	}
	
	
}
