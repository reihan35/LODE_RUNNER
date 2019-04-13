package decorators;


import contracts.CharacterContract;
import services.CharacterService;
import services.EditableScreenService;
import services.EngineService;
import services.EnvironmentService;
import services.PlayerService;

public class PlayerDecorator extends CharacterContract implements PlayerService {
	
	PlayerService delegates;
	
	public PlayerDecorator(PlayerService delegates) {
		super(delegates);
		this.delegates = delegates;
	}
	
	@Override
	public EngineService getEngine() {
		return delegates.getEngine();
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		delegates.step();
		
	}

	@Override
	public void init(EngineService e, int w, int h) {
		delegates.init(e, w, h);
		
	}

}
