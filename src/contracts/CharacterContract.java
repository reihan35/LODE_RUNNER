package contracts;

import decorators.CharacterDecorator;
import services.Cell;
import services.CharacterService;
import services.EnvironmentService;
import util.SetUtil;

public class CharacterContract extends CharacterDecorator implements CharacterService {
	
	public CharacterContract(CharacterService delegates) {
		super(delegates);
	}


	/**
	 * predicates
	 *
	 */
	
	/**
	 * predicate definition:
	 * exists Character c in getEnvi().getCellContent(x, y)
	 */
	public boolean characterAt(int x, int y) {
		if(getEnvi().getCellContentChar(x, y).size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * getEnvi().getCellNature(x, y) in {EMP, LAD, HDR, HOL}
	 */
	public boolean isFreeCell(int x, int y) {
		Cell currCell = getEnvi().getCellNature(x, y);
		Cell[] free = {Cell.LAD, Cell.EMP, Cell.HDR, Cell.HOL};
		if(SetUtil.isIn(currCell, free)) {
			return true;
		}
		return false;
	}
	
	/**
	 * invariants
	 * inv: isFreeCell(getWdt(), getHgt())
	 *  */
	public void checkInvariants() {
		if(!isFreeCell(getWdt(), getHgt())) {
			throw new InvariantError("Le personnage n'est pas sur une case libre");
		}
	}
	
	
	
	/**
	 * constructors
	 */
	
	/**
	 * pre: getEnvi().getCellNature(x, y) = EMP
	 * post: getEnvi() == s
	 * post: getHgt() == y
	 * post: getWdt() == x
	 */
	public void init(EnvironmentService s, int x, int y) {
		if(!(getEnvi().getCellNature(x, y) == Cell.EMP)) {
			throw new PreconditionError("Le personnage n'a pas spawn sur une case vide");
		}
		super.init(s, x, y);
		if(getEnvi() != s || getHgt() != y || getWdt() != x){
			throw new PostconditionError("mauvaise initialisation de l'environnement pour le personnage");
		}
	}
	

	
	/**
	 * operators
	 */
	
	/**
	 * post: getHgt() == getHgt()@pre
	 * 
	 * post: if (getWdt()@pre != 0 && isFreeCell(getWdt()@pre - 1, getHgt())
	 * 		&& (getEnvi().getCellNature(getWdt()@pre, getHgt()) in {LAD, HDR}
	 * 			|| getEnvi().getCellNature(getWdt()@pre, getHgt() - 1) in {PLT, MTL, LAD}
	 * 			|| characterAt(getWdt()@pre, getHgt() - 1))
	 *		&& not characterAt(getWdt()@pre - 1, getHgt()))
	 *			then getWdt() == getWdt()@pre - 1 
	 *			else getWdt() == getWdt()@pre
	 */			
	public void goLeft() {
		EnvironmentService envi_atPre = getEnvi();
		int getHgt_atPre = getHgt();
		int getWdt_atPre = getWdt();
		
		checkInvariants();
		super.goLeft();
		checkInvariants();
		if(!(envi_atPre == getEnvi())) {
			throw new InvariantError("La valeur d'une constante a changé");
		}
		
		if(getHgt() != getHgt_atPre) {
			throw new PostconditionError("La position y du personnage a changé pour une deplacement vers la gauche");
		}
		
		Cell downCell = getEnvi().getCellNature(getWdt_atPre, getHgt() - 1);
		Cell currCell = getEnvi().getCellNature(getWdt_atPre, getHgt());
		Cell[] rail = {Cell.LAD, Cell.HDR};
		Cell[] standable = {Cell.LAD, Cell.PLT, Cell.MTL};
		if(getWdt_atPre != 0
				&& isFreeCell(getWdt_atPre - 1, getHgt())
				&& (SetUtil.isIn(currCell, rail)
					|| SetUtil.isIn(downCell, standable)
					|| characterAt(getWdt_atPre, getHgt() - 1))
				&& !characterAt(getWdt_atPre - 1, getHgt())) {
			if(getWdt_atPre != getWdt() - 1) {
				throw new PostconditionError("Le personnage ne s'est pas deplace a gauche alors qu'il aurait du");
			}
			else {
				if(getWdt_atPre != getWdt()) {
					throw new PostconditionError("Le personnage s'est deplace alors qu'il aurait du rester en position");
				}
			}
		}
		
		
	}
	
	
	/**
	 * post: getHgt() == getHgt()@pre
	 * 
	 * post: if getWdt()@pre != getEnvi().getWidth() - 1 && isFreeCell(getWdt()@pre + 1, getHgt())
	 * 		&& (getEnvi().getCellNature(getWdt()@pre, getHgt()) in {LAD, HDR}
	 * 			|| getEnvi().getCellNature(getWdt()@pre, getHgt() - 1) in {PLT, MTL, LAD}
	 * 			|| characterAt(getWdt()@pre, getHgt() - 1))
	 *		&& not characterAt(getWdt()@pre + 1, getHgt())
	 *			then getWdt() == getWdt()@pre + 1 
	 *			else getWdt() == getWdt()@pre
	 *			
	 */
	public void goRight() {
		EnvironmentService envi_atPre = getEnvi();
		int getHgt_atPre = getHgt();
		int getWdt_atPre = getWdt();
		
		checkInvariants();
		super.goRight();
		checkInvariants();
		if(!(envi_atPre == getEnvi())) {
			throw new InvariantError("La valeur d'une constante a changé");
		}
		
		if(getHgt() != getHgt_atPre) {
			throw new PostconditionError("La position y du personnage a changé pour une deplacement vers la droite");
		}
		
		Cell downCell = getEnvi().getCellNature(getWdt_atPre, getHgt() - 1);
		Cell currCell = getEnvi().getCellNature(getWdt_atPre, getHgt());
		Cell[] rail = {Cell.LAD, Cell.HDR};
		Cell[] standable = {Cell.LAD, Cell.PLT, Cell.MTL};
		if(getWdt_atPre != getEnvi().getWidth() - 1
				&& isFreeCell(getWdt_atPre + 1, getHgt())
				&& (SetUtil.isIn(currCell, rail)
					|| SetUtil.isIn(downCell, standable)
					|| characterAt(getWdt_atPre, getHgt() - 1))
				&& !characterAt(getWdt_atPre - 1, getHgt())) {
			if(getWdt_atPre != getWdt() + 1) {
				throw new PostconditionError("Le personnage ne s'est pas deplace a droite alors qu'il aurait du");
			}
			else {
				if(getWdt_atPre != getWdt()) {
					throw new PostconditionError("Le personnage s'est deplace alors qu'il aurait du rester en position");
				}
			}
		}
	}
	
	/**
	 * post: getWdt() == getWdt()@pre
	 * 
	 * post: if not getHgt()@pre == getEnvi().getHeight()-1
	 * 			&& getEnvi().getCellNature(getWdt(), getHgt()@pre) == LAD
	 * 			&& isFreeCell(getWdt(), getHgt()@pre + 1)
	 * 			&& not characterAt(getWdt(), getHgt()@pre + 1)
	 * 				then getHgt() == getHgt()@pre + 1
	 * 				else getHgt() == getHgt()@pre + 1
	 * 
	 */
	public void goUp() {
		EnvironmentService envi_atPre = getEnvi();
		int getHgt_atPre = getHgt();
		int getWdt_atPre = getWdt();
		
		checkInvariants();
		super.goUp();
		checkInvariants();
		if(!(envi_atPre == getEnvi())) {
			throw new InvariantError("La valeur d'une constante a changé");
		}
		
		if(getWdt() != getWdt_atPre) {
			throw new PostconditionError("La position x du personnage a changé pour une deplacement vers le haut");
		}
		
		if(getHgt_atPre != getEnvi().getHeight()
			&& getEnvi().getCellNature(getWdt(), getHgt_atPre) == Cell.LAD
			&& isFreeCell(getWdt(), getHgt_atPre + 1)
			&& !(characterAt(getWdt(), getHgt_atPre + 1))){
				if(getHgt_atPre != getHgt() + 1) {
					throw new PostconditionError("Le personnage ne s'est pas deplace vers le haut alors qu'il aurait du");
				}
				else {
					if(getHgt_atPre != getHgt()) {
						throw new PostconditionError("Le personnage s'est deplace alors qu'il aurait du rester en position");
					}
				}				
		}
	}
	

	/**
	 * post: getWdt() == getWdt()@pre
	 * 
	 * 
	 * post: if not getHgt()@pre == 0
	 * 			&& isFreeCell(getWdt(), getHgt()@pre - 1)
	 * 			&& not characterAt(getWdt()@pre, getHgt()@pre - 1)
	 * 				then getHgt() == getHgt()@pre - 1
	 * 				else getHgt() == getHgt()@pre
	 * 
	 */
	public void goDown() {
		EnvironmentService envi_atPre = getEnvi();
		int getHgt_atPre = getHgt();
		int getWdt_atPre = getWdt();
		
		checkInvariants();
		super.goDown();
		checkInvariants();
		if(!(envi_atPre == getEnvi())) {
			throw new InvariantError("La valeur d'une constante a changé");
		}
		
		if(getWdt() != getWdt_atPre) {
			throw new PostconditionError("La position x du personnage a changé pour une deplacement vers le haut");
		}
		
		if(getHgt_atPre != 0
			&& isFreeCell(getWdt(), getHgt_atPre - 1)
			&& !(characterAt(getWdt(), getHgt_atPre - 1))){
				if(getHgt_atPre != getHgt() - 1) {
					throw new PostconditionError("Le personnage ne s'est pas deplace vers le bas alors qu'il aurait du");
				}
				else {
					if(getHgt_atPre != getHgt()) {
						throw new PostconditionError("Le personnage s'est deplace alors qu'il aurait du rester en position");
					}
				}				
		}
		
	}
	
	/**
	 * 	getHgt() == getHgt()@pre && getWdt() == getWdt()@pre
	 * 
	 **/
	public void stay() {
		EnvironmentService envi_atPre = getEnvi();
		int getHgt_atPre = getHgt();
		int getWdt_atPre = getWdt();
		
		checkInvariants();
		super.stay();
		checkInvariants();
		if(!(envi_atPre == getEnvi())) {
			throw new InvariantError("La valeur d'une constante a changé");
		}
		
		if(getWdt() != getWdt_atPre || getHgt() != getHgt_atPre) {
			throw new PostconditionError("Le personnage a changé de position");
		}
	}
}
