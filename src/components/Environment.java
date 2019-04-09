package components;

import java.util.ArrayList;

import services.CharacterService;
import services.EnvironmentService;
import services.ItemService;

public class Environment extends EditableScreen implements EnvironmentService {
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
	public void init(int w, int h) {
		super.init(w, h);
		cellContentItems = new ArrayList[w][h];
		cellContentChar = new ArrayList[w][h];
		for(int x = 0; x < getWidth(); x++) {
			for(int y = 0; y < getHeight(); y++) {
				cellContentItems[x][y] = new ArrayList<ItemService>();
				cellContentChar[x][y] = new ArrayList<CharacterService>();
			}
		}
	}





}
