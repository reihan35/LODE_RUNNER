package main;

import components.EditableScreen;
import components.Environment;
import contracts.EditableScreenContract;
import contracts.EnvironmentContract;
import services.Cell;
import services.EditableScreenService;
import services.EnvironmentService;
import services.ItemService;

public class EnvironmentMain {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EnvironmentService e = new Environment();
		EnvironmentService econtrat = new EnvironmentContract(e);
		econtrat.init(2, 2);
		System.out.println(econtrat.getCellContentItem(0, 1));

	}

}
