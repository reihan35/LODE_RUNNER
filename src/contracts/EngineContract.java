package contracts;

import java.util.ArrayList;

import decorators.EngineDecorator;
import services.Coordinates;
import services.EditableScreenService;
import services.EngineService;

public class EngineContract extends EngineDecorator implements EngineService{
	
	
	public void init(EditableScreenService screen,Coordinates playerCoord,ArrayList<Coordinates> guardsCoord,
			ArrayList<Coordinates> treasuresCoord) {
		
		super.init(screen, playerCoord, guardsCoord, treasuresCoord);
	
	}
	
	public void step() {
		
		super.step();
	}
	
	
}
