package components;

import java.util.ArrayList;

import services.CharacterService;
import services.EnvironmentService;
import services.ItemService;

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
	
	@Override
	public void init(int h, int w) {
		super.init(h, w);
		cellContentItems = new ArrayList[h][w];
		cellContentChar = new ArrayList[h][w];
		for(int x = 0; x < getWidth(); x++) {
			for(int y = 0; y < getHeight(); y++) {
				cellContentItems[x][y] = new ArrayList<ItemService>();
				cellContentChar[x][y] = new ArrayList<CharacterService>();
			}
		}
	}





}
