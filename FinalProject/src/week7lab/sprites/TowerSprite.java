package week7lab.sprites;

public class TowerSprite extends ComplexSprite{

	public TowerSprite(double x, double y, double width, double height) {
		super(x, y, width, height);
		ISprite rec1 = new RectangleSprite(x, y, width, height);
		ISprite rec2 = new RectangleSprite(x + 5, y - height + 5, width - 10, height - 5);
		ISprite rec3 = new RectangleSprite(x + 7, y - 2 * height + 12, width - 14, height - 7);
		this.add(rec1);
		this.add(rec2);
		this.add(rec3);
	}

}
