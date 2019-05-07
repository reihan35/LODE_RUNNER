package test;
import org.junit.Test;

import components.Character;
import components.EditableScreen;
import components.Environment;
import components.Player;
import components.Screen;
import contracts.CharacterContract;
import contracts.EditableScreenContract;
import contracts.EnvironmentContract;
import contracts.PlayerContract;
import contracts.PostconditionError;
import contracts.PreconditionError;
import services.Cell;
import services.CharacterService;
import services.EnvironmentService;
import services.PlayerService;
import util.SetUtil;

public class CharacterTest {
	
	@Test(expected = PreconditionError.class)
	public void init_precondition1() {
		EnvironmentService e = new Environment();
		EditableScreenContract scontrat = SetUtil.MakeEdiatableScreen(3,5);
		EnvironmentContract econtrat = new EnvironmentContract(e);
		econtrat.init(scontrat);
		CharacterService c = new Character();
		CharacterContract ci = new CharacterContract(c);
		ci.init(econtrat, 3, 4);
	}
	
	@Test
	public void init_precondition2() {
		EnvironmentService e = new Environment();
		EditableScreenContract scontrat = SetUtil.MakeEdiatableScreen(3,5);
		EnvironmentContract econtrat = new EnvironmentContract(e);
		econtrat.init(scontrat);
		CharacterService c = new Character();
		CharacterContract ci = new CharacterContract(c);
		ci.init(econtrat, 2, 3);
		assert ci.getHgt() == 3 && ci.getWdt() == 2;
	}
	
	
	@Test(expected = PreconditionError.class)
	public void goDown_precondition3() {
		EnvironmentService e = new Environment();
		EditableScreenContract scontrat = SetUtil.MakeEdiatableScreen(3,3);
		EnvironmentContract econtrat = new EnvironmentContract(e);
		econtrat.init(scontrat);
		CharacterService c = new Character();
		CharacterContract ci = new CharacterContract(c);
		ci.init(econtrat, 2, 0);
		ci.goDown();
	}
	
	
	
	// Paires de transitions
	@Test
	public void go_Down2() {
		EnvironmentService e = new Environment();
		EditableScreenContract scontrat = SetUtil.MakeEdiatableScreen(10,10);
		EnvironmentContract econtrat = new EnvironmentContract(e);
		econtrat.init(scontrat);
		CharacterService c = new Character();
		CharacterContract ci = new CharacterContract(c);
		ci.init(econtrat, 5, 5);
		ci.goDown();
		ci.goDown();
		assert  ci.getHgt() == 3 & ci.getWdt() == 5;

	}
	
	public void go_Down_go_Up() {
		EnvironmentService e = new Environment();
		EditableScreenContract scontrat = SetUtil.MakeEdiatableScreen(10,10);
		EnvironmentContract econtrat = new EnvironmentContract(e);
		econtrat.init(scontrat);
		CharacterService c = new Character();
		CharacterContract ci = new CharacterContract(c);
		ci.init(econtrat, 2, 2);
		ci.goDown();
		ci.goUp();
		assert  ci.getHgt() == 2 & ci.getWdt() == 2;


	}
	
	//Scenario
	
	@Test
	public void scenario() {
		EnvironmentService e = new Environment();
		EditableScreenContract scontrat = SetUtil.MakeEdiatableScreen(10,10);
		EnvironmentContract econtrat = new EnvironmentContract(e);
		econtrat.init(scontrat);
		CharacterService c = new Character();
		CharacterContract ci = new CharacterContract(c);
		ci.init(econtrat, 5, 5);
		ci.goLeft();
		ci.stay();
		ci.goRight();
		assert  ci.getHgt() == 5 & ci.getWdt() == 5;

	}
	
}
