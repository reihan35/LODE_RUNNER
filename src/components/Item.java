package components;

import services.ItemService;
import services.ItemType;

public class Item implements ItemService {
	protected int id;
	protected ItemType type;
	protected int hgt;
	protected int wdt;
	
	public void init(int id, ItemType type,int wdt, int hgt) {
		this.id = id;
		this.type = type;
		this.hgt = hgt;
		this.wdt = wdt;

	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public ItemType getNature() {
		return type;
	}

	@Override
	public void printItem() {
		System.out.println("Item d'id " + id);
	}

	@Override
	public int getHgt() {
		return hgt;
	}

	@Override
	public int getWdt() {
		// TODO Auto-generated method stub
		return wdt;
	}
	
}
