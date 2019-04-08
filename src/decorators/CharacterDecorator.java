package decorators;

import services.CharacterService;
import services.EnvironmentService;

public class CharacterDecorator implements CharacterService{
	
	CharacterService delegates;

	public CharacterDecorator(CharacterService delegates) {
		this.delegates = delegates;
	}

	public EnvironmentService getEnvi() {
		return delegates.getEnvi();
	}

	public int getHgt() {
		return delegates.getHgt();
	}

	public int getWdt() {
		return delegates.getWdt();
	}

	public boolean characterAt(int x, int y) {
		return delegates.characterAt(x, y);
	}

	public boolean isFreeCell(int x, int y) {
		return delegates.isFreeCell(x, y);
	}

	public void init(EnvironmentService s, int x, int y) {
		delegates.init(s, x, y);
	}

	public void goLeft() {
		delegates.goLeft();
	}

	public void goRight() {
		delegates.goRight();
	}

	public void goUp() {
		delegates.goUp();
	}

	public void goDown() {
		delegates.goDown();
	}

	public void stay() {
		delegates.stay();
	}

}
