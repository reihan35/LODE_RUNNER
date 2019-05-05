package test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.Screen;
import contracts.PreconditionError;
import contracts.ScreenContract;
import services.ScreenService;

public class ScreenTest {
	
	
	//Les preconditions
	@Test
	public void init_precondition() {
		try {
			ScreenService s = new Screen();
			ScreenContract scontrat = new ScreenContract(s);
			scontrat.init(1,2);
		}catch (Error e) {
			assertFalse(e instanceof PreconditionError);
		}
	}
	
	@Test(expected = PreconditionError.class)
	public void init_precondition2() {
			ScreenService s = new Screen();
			ScreenContract scontrat = new ScreenContract(s);
			scontrat.init(-1,2);
	}
	
	//Les transitions
	
	@Test(expected = PreconditionError.class)
	public void dig_transition1() {
		ScreenService s = new Screen();
		ScreenContract scontrat = new ScreenContract(s);
		scontrat.init(10,10);
		scontrat.dig(11, 2);
	}
	
	@Test
	public void dig_transition2() {
		ScreenService s = new Screen();
		ScreenContract scontrat = new ScreenContract(s);
		scontrat.init(10,10);
		scontrat.dig(9, 2);
	}
	@Test(expected = PreconditionError.class)
	public void fill_transition1() {
		ScreenService s = new Screen();
		ScreenContract scontrat = new ScreenContract(s);
		scontrat.dig(11, 2);
	}
	
	
	@Test
	public void fill_transition2() {
		ScreenService s = new Screen();
		ScreenContract scontrat = new ScreenContract(s);
		scontrat.init(10,10);
		scontrat.fill(9, 2);
	}
	
	//Paire des transitions
	
	public void dig_fill() {
		ScreenService s = new Screen();
		ScreenContract scontrat = new ScreenContract(s);
		scontrat.init(10,10);
		scontrat.dig(9, 2);
		scontrat.fill(9, 2);
	}
	
	public void fill_dig() {
		ScreenService s = new Screen();
		ScreenContract scontrat = new ScreenContract(s);
		scontrat.init(10,10);
		scontrat.fill(9, 2);
		scontrat.dig(9, 2);
	}
	
	@Test(expected = PreconditionError.class)	
	public void dig_dig() {
		ScreenService s = new Screen();
		ScreenContract scontrat = new ScreenContract(s);
		scontrat.init(10,10);
		scontrat.dig(9, 2);
		scontrat.dig(9, 2);
	}
	
	@Test(expected = PreconditionError.class)
	public void fill_fill() {
		ScreenService s = new Screen();
		ScreenContract scontrat = new ScreenContract(s);
		scontrat.init(10,10);
		scontrat.fill(9, 2);
		scontrat.fill(9, 2);
	}
	
	//Scenario
	
	@Test
	public void fill_dig_fill() {
		ScreenService s = new Screen();
		ScreenContract scontrat = new ScreenContract(s);
		scontrat.init(10,10);
		scontrat.fill(9, 2);
		scontrat.dig(9, 2);
		scontrat.fill(9, 2);
	}
	
}
