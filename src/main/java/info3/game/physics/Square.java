package info3.game.physics;

public class Square {
	public Line[] lines = new Line[4];

	public Square(Line l1, Line l2, Line l3, Line l4) {
		this.lines[0] = l1;
		this.lines[1] = l2;
		this.lines[2] = l3;
		this.lines[3] = l4;
	}
}
