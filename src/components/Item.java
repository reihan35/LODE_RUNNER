package components;

import services.ItemService;
import services.ItemType;

public class Item implements ItemService {
	protected int id;
	protected ItemType type;
	protected int hgt;
	protected int col;
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public ItemType getNature() {
		return type;
	}

	@Override
	public void init(int id, ItemType type, int hgt, int col) {
		this.id = id;
		this.type = type;
		this.hgt = hgt;
		this.col = col;

	}

}
