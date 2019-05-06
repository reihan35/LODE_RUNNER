package services;

public class Door {
	Coordinates in, out;

	public Door(Coordinates in, Coordinates out) {
		super();
		this.in = in;
		this.out = out;
	}
	
	public boolean isOnIn(Coordinates p) {
		return p.getX() == in.getX() && p.getY() == in.getY();
	}
	
	public boolean isOnOut(Coordinates p) {
		return p.getX() == out.getX() && p.getY() == out.getY();
	}

	public Coordinates getIn() {
		return in;
	}

	public Coordinates getOut() {
		return out;
	}
	
}
