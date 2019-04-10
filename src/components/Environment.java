package components;

import java.util.ArrayList;
import java.util.Arrays;

import services.Cell;
import services.CharacterService;
import services.EnvironmentService;
import services.ItemService;
import services.ItemType;

public class Environment extends Screen implements EnvironmentService {
	protected ArrayList<ItemService>[][] cellContentItems;
	protected ArrayList<CharacterService>[][] cellContentChar;
	@Override
	public ArrayList<ItemService> getCellContentItem(int x, int y) {
		return cellContentItems[x][y];
	}
	

	@Override
	public ArrayList<CharacterService> getCellContentChar(int x, int y) {
		return cellContentChar[x][y];
	}
	
	public void addCellContentChar(int x, int y, CharacterService c) {
		cellContentChar[x][y].add(c);
	}
	
	public void addCellContentItem(int x, int y, ItemService i) {
		cellContentItems[x][y].add(i);
	}
	
	@Override
	public void removeCellContentItem(int x, int y, ItemService i) {
		cellContentItems[x][y].remove(i.getId());
		
	}
	
	@Override
	public void printCellContentItem(int x, int y) {
		
		ArrayList<ItemService> items = cellContentItems[x][y];
		
		if (items.size() == 0 ) {
			System.out.println("0");
			return;
		}
		
		for (int i = 0 ; i < items.size() ; i++ ) {
			System.out.println(items.get(i));
		}
	}

	
	@Override
	public void init(int w, int h) {
		super.init(w, h);
		cellContentItems = new ArrayList[w][h];
		cellContentChar = new ArrayList[w][h];
		for(int x = 0; x < getWidth(); x++) {
			for(int y = 0; y < getHeight(); y++) {
				cellContentItems[x][y] = new ArrayList<ItemService>();
				ItemService i = new Item();
				i.init(1,ItemType.TREASURE,x,y);
				addCellContentItem(x,y,i);
				printCellContentItem(x,y);
				cellContentChar[x][y] = new ArrayList<CharacterService>();
			}
		}
	}

}
