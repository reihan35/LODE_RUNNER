package decorators;

import java.util.ArrayList;

import contracts.ScreenContract;
import services.CharacterService;
import services.EnvironmentService;
import services.ItemService;

public class EnvironmentDecorator extends ScreenContract implements EnvironmentService{
	
	
	public EnvironmentDecorator(EnvironmentService delegates) {
		super(delegates);
	}

	EnvironmentService delegates;

	@Override
	public ArrayList<ItemService> getCellContentItem(int x, int y) {
		return delegates.getCellContentItem(x, y);
	}

	@Override
	public ArrayList<CharacterService> getCellContentChar(int x, int y) {
		return delegates.getCellContentChar(x, y);
	}

}
