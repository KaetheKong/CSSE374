package week7lab.sprites;

public class CircularTower extends ComplexSprite{
	public CircularTower(double x, double y, double width, double height) {
		super(x, y, width, height);
		ISprite c1 = new CircleSprite(x, y, width, height);
		ISprite c2 = new CircleSprite(x + 5, y - height + 5, width - 10, height - 5);
		ISprite c3 = new CircleSprite(x + 7, y - 2 * height + 12, width - 14, height - 7);
		this.add(c1);
		this.add(c2);
		this.add(c3);
	}

}
