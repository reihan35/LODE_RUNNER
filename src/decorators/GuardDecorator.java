package decorators;

import contracts.CharacterContract;
import contracts.GuardContrtact;
import services.CharacterService;
import services.EngineService;
import services.GuardService;
import services.ItemService;
import services.Move;
import services.PlayerService;

public class GuardDecorator extends CharacterContract implements GuardService{
	
	GuardService delegates;

	public GuardDecorator(GuardService delegates) {
		super(delegates);
		this.delegates = delegates;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return delegates.getId();
	}

	@Override
	public Move getBehaviour() {
		// TODO Auto-generated method stub
		return delegates.getBehaviour();
	}

	@Override
	public CharacterService getTarget() {
		// TODO Auto-generated method stub
		return delegates.getTarget();
	}

	@Override
	public int getTimeInHole() {
		// TODO Auto-generated method stub
		return delegates.getTimeInHole();
	}

	@Override
	public void climbRight() {
		GuardContrtact delegats;
		// TODO Auto-generated method stub
		delegates.climbRight();
	}

	@Override
	public void climbLeft() {
		// TODO Auto-generated method stub
		delegates.climbLeft();
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		delegates.step();
	}

	@Override
	public void init(EngineService e, int w, int h, PlayerService p) {
		// TODO Auto-generated method stub
		delegates.init(e, w, h, p);
	}

	@Override
	public void Reinitialize() {
		// TODO Auto-generated method stub
		delegates.Reinitialize();
	}

	@Override
	public PlayerService getPlayer() {
		// TODO Auto-generated method stub
		return delegates.getPlayer();
	}

	@Override
	public EngineService getEngine() {
		// TODO Auto-generated method stub
		return delegates.getEngine();
	}


	@Override
	public void setTreasure(ItemService i) {
		// TODO Auto-generated method stub
		delegates.setTreasure(i);
	}

	@Override
	public boolean willMove() {
		return delegates.willMove();
	}

	@Override
	public void dropTreasure() {
		delegates.dropTreasure();
	}

	@Override
	public boolean has_treasure() {
		// TODO Auto-generated method stub
		return delegates.has_treasure();
	}

	@Override
	public ItemService get_treasure() {
		// TODO Auto-generated method stub
		return delegates.get_treasure();
	}

	@Override
	public void die() {
		delegates.die();
		
	}

	@Override
	public int getFirst_x() {
		// TODO Auto-generated method stub
		return delegates.getFirst_x();
	}

	@Override
	public int getFirst_y() {
		// TODO Auto-generated method stub
		return delegates.getFirst_y();
	}

	public void grabTreasure() {
		delegates.grabTreasure();
	}

	@Override
	public int getNbStayInHole() {
		return delegates.getNbStayInHole();
	}

	@Override
	public int getNbStay() {
		return delegates.getNbStay();
	}

	@Override
	public int getTimeStayed() {
		return delegates.getTimeStayed();
	}

	
}
