package util;

import services.Cell;

public class SetUtil {

	public static boolean isIn(Cell obj, Cell[] my_set) {
		for(Cell e: my_set) {
			if(e == obj) {
				return true;
			}
		}
		return false;
	}
}
