package contracts;

import decorators.GuardDecorator;
import services.CharacterService;
import services.EngineService;
import services.EnvironmentService;
import services.GuardService;
import services.Move;
import services.PlayerService;

public class GuardContrtact extends GuardDecorator implements GuardService {

	public GuardContrtact(GuardService delegates) {
		super(delegates);
		// TODO Auto-generated constructor stub
	}
	
	/** 
	 * //AJOUT DE LIBERTE DE CASE ETC A VOIR
	 *pre : getEnvi().getCellNature(getWdt(),getHgt()) = HOL 
	 *post: getEnvi().getCellNature(getWdt()@pre-1, getHgt()@pre+1) in {MTL, PLT}
	 * 		implies getWdt() == getWdt()@pre and getHgt() == getHgt()@pre
	 * 
	 *post: characterAt(getWdt()@pre-1, getHgt()+1)
	 * 			implies getWdt() == getWdt()@pre && getHgt() == getHgt()@pre
	 * 
	 *post: getWdt()@pre != 0 && getEnvi().getCellNature(getWdt()@pre+1, getHgt()+1) not in {MLT, PLT}
	 *		&& not characterAt(getWdt()@pre-1, getHgt()+1) 
	 *			implies getWdt() == getWdt()@pre - 1 && getHgt() == getHgt()@pre + 1
	**/
	
	@Override
	public void climbRight() {
		super.climbRight();
	}

	@Override
	public void climbLeft() {
		// TODO Auto-generated method stub
		super.climbLeft();
	}

	@Override
	public void step() {
		super.step();
	}


}
