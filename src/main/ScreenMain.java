package main;

import components.Screen;
import contracts.ScreenContract;
import services.ScreenService;

public class ScreenMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ScreenService s = new Screen();
		ScreenService scontrat = new ScreenContract(s);
		scontrat.init(1, 2);
		int h = scontrat.getHeight();
		int w = scontrat.getWidth();
		scontrat.fill(0,1 );
		System.out.println(scontrat.getCellNature(0,1));
		System.out.println(scontrat.getCellNature(0,0));
	}

}
