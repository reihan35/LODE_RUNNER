package test;
import javax.swing.text.EditorKit;

import org.junit.Test;

import components.Character;
import components.EditableScreen;
import components.Environment;
import components.Item;
import contracts.CharacterContract;
import contracts.EditableScreenContract;
import contracts.EnvironmentContract;
import contracts.InvariantError;
import contracts.PreconditionError;
import services.Cell;
import services.CharacterService;
import services.EnvironmentService;
import services.ItemService;
import services.ItemType;
import util.SetUtil;



public class EnvironementTest {
	
	//preconditions 
	@Test(expected = PreconditionError.class)
	public void init_precondition1() {
		EnvironmentService e = new Environment();
		EnvironmentContract econtrat = new EnvironmentContract(e);
		EditableScreen s = new EditableScreen();
		s.init(4, 5);
		econtrat.init(s);
	}
	
	@Test
	public void getCellContentChar_precondition1() {
		EnvironmentService e = new Environment();
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(3,5);
		EditableScreenContract scontrat = new EditableScreenContract(s);
		EnvironmentContract econtrat = new EnvironmentContract(e);
		econtrat.init(scontrat);
		econtrat.getCellContentChar(1, 2);
	}
	
	
	@Test(expected = PreconditionError.class)
	public void getCellContentChar_precondition2() {
		EnvironmentService e = new Environment();
		EnvironmentContract econtrat = new EnvironmentContract(e);
		EditableScreen s = new EditableScreen();
		s.init(4, 5);
		econtrat.init(s);
		econtrat.getCellContentChar(6, 5);
	}
	
	//Transition 

	@Test
	public void addCellContentItem_transition1() {
		EnvironmentService e = new Environment();
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(3,5);
		EnvironmentContract econtrat = new EnvironmentContract(e);
		econtrat.init(s);
		ItemService i = new Item();
		i.init(0, ItemType.TREASURE, 2, 2);
		System.out.println(econtrat.getCellNature(1, 1));
		econtrat.addCellContentItem(2, 2, i);
	}
	
	@Test(expected = InvariantError.class)
	public void addCellContentItem_transition2() {
		EnvironmentService e = new Environment();
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(3,5);
		EnvironmentContract econtrat = new EnvironmentContract(e);
		s.setNature(2, 2, Cell.LAD);
		econtrat.init(s);
		ItemService i = new Item();
		i.init(0, ItemType.TREASURE, 2, 2);
		System.out.println(econtrat.getCellNature(2, 2));
		econtrat.addCellContentItem(2, 2, i);
	}
	
	@Test(expected = PreconditionError.class)
	public void removeCellContentItem_transition1() {
		EnvironmentService e = new Environment();
		EnvironmentContract econtrat = new EnvironmentContract(e);
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(3,5);
		s.init(4, 5);
		econtrat.init(s);
		ItemService i = new Item();
		i.init(0, ItemType.TREASURE, 2, 2);
		econtrat.removeCellContentItem(3, 3,i );
	}
	
	@Test(expected = PreconditionError.class)
	public void removeCellContentChar_transition() {
		EnvironmentService e = new Environment();
		EnvironmentContract econtrat = new EnvironmentContract(e);
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(3,5);
		s.init(4, 5);
		econtrat.init(s);
		CharacterService c = new Character();
		CharacterContract ccontrat = new CharacterContract(c);
		ccontrat.init(econtrat, 2, 2);
		econtrat.removeCellContentChar(3, 3,ccontrat);
	}
	
	//Paires de transitions
	@Test
	public void addItem_addItem() {
		EnvironmentService e = new Environment();
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(3,5);
		EnvironmentContract econtrat = new EnvironmentContract(e);
		s.setNature(2, 2, Cell.LAD);
		econtrat.init(s);
		ItemService i = new Item();
		i.init(0, ItemType.TREASURE, 1, 2);
		ItemService i2 = new Item();
		i2.init(0, ItemType.TREASURE, 1, 2);
		econtrat.addCellContentItem(1, 2, i);
		econtrat.addCellContentItem(1, 2, i2);
	}
	
	@Test
	public void addItem_removeItem() {
		EnvironmentService e = new Environment();
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(3,5);
		EnvironmentContract econtrat = new EnvironmentContract(e);
		s.setNature(2, 2, Cell.LAD);
		econtrat.init(s);
		ItemService i = new Item();
		i.init(0, ItemType.TREASURE, 1, 2);
		econtrat.addCellContentItem(1, 2, i);
		econtrat.removeCellContentItem(1, 2, i);
	}
	
	@Test
	public void addChar_removeChar() {
		EnvironmentService e = new Environment();
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(3,5);
		EnvironmentContract econtrat = new EnvironmentContract(e);
		s.setNature(2, 2, Cell.LAD);
		econtrat.init(s);
		CharacterService c = new Character();
		CharacterContract ci = new CharacterContract(c);
		ci.init(econtrat,1, 2);
		econtrat.addCellContentChar(1, 2, ci);
		econtrat.removeCellContentChar(1, 2, ci);
	}
	
	//Scenario
	@Test
	public void Scenario() {
		EnvironmentService e = new Environment();
		EditableScreenContract s = SetUtil.MakeEdiatableScreen(3,5);
		EnvironmentContract econtrat = new EnvironmentContract(e);
		s.setNature(2, 2, Cell.LAD);
		econtrat.init(s);
		CharacterService c = new Character();
		CharacterContract ci = new CharacterContract(c);
		ci.init(econtrat,1, 2);
		econtrat.addCellContentChar(1, 2, ci);
		econtrat.removeCellContentChar(1, 2, ci);
		ItemService i = new Item();
		i.init(0, ItemType.TREASURE, 1, 2);
		econtrat.addCellContentItem(1, 2, i);
		econtrat.removeCellContentItem(1, 2, i);
	}
	
	

}
