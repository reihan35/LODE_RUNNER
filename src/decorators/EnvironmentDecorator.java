package decorators;

import java.util.ArrayList;

import contracts.EditableScreenContract;
import contracts.ScreenContract;
import services.Cell;
import services.CharacterService;
import services.EditableScreenService;
import services.EnvironmentService;
import services.ItemService;

public class EnvironmentDecorator extends ScreenContract implements EnvironmentService{
	
	
	EnvironmentService delegates;

	public EnvironmentDecorator(EnvironmentService delegates) {
		super(delegates);
		this.delegates = delegates;
	}
	
	@Override
	public void init(EditableScreenService s) {
		System.out.println(delegates);
		delegates.init(s);
	}
	@Override
	public ArrayList<ItemService> getCellContentItem(int x, int y) {
		return delegates.getCellContentItem(x, y);
	}

	@Override
	public ArrayList<CharacterService> getCellContentChar(int x, int y) {
		return delegates.getCellContentChar(x, y);
	}

	@Override
	public void addCellContentItem(int x, int y, ItemService i) {
		delegates.addCellContentItem(x, y, i);
		
	}

	@Override
	public void addCellContentChar(int x, int y, CharacterService c) {
		delegates.addCellContentChar(x, y, c);
		
	}

	@Override
	public void removeCellContentItem(int x, int y, ItemService i) {
		delegates.removeCellContentItem(x, y, i);
	}
	@Override
	public void removeCellContentChar(int x, int y, CharacterService c) {
		delegates.removeCellContentChar(x, y, c);
		
	}


}
