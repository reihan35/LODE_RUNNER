package components;

import java.util.ArrayList;


import services.EnvironmentService;
import services.ItemService;

public class Environment extends Screen implements EnvironmentService {
	protected ArrayList<ItemService>[][] cellContent;
	
	@Override
	public ArrayList<ItemService> getCellContent(int x, int y) {
		// TODO Auto-generated method stub
		return cellContent[x][y];
	}
	
	@Override
	public void init(int h, int w) {
		super.init(h, w);
		int[] d = new int[10];
		cellContent = new ArrayList[h][w];
		
		
	}




}
