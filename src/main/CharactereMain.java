package main;

import components.Character;
import components.EditableScreen;
import contracts.CharacterContract;
import contracts.EditableScreenContract;
import services.CharacterService;
import services.EditableScreenService;

public class CharactereMain {

	public static void main(String[] args) {
		CharacterService c = new Character();
		CharacterService ccontrat = new CharacterContract(c);

	}

}
