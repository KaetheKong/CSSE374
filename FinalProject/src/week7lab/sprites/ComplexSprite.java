package week7lab.sprites;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ComplexSprite extends AbstractSprite {

	private List<ISprite> shapecomps;

	public ComplexSprite(double x, double y, double width, double height) {
		super(x, y, width, height);
		this.shapecomps = new ArrayList<ISprite>();
	}

	public void add(ISprite sc) {
		if (!sc.getListShape().isEmpty()) {
			this.shapecomps.addAll(sc.getListShape());
		} else {
			this.shapecomps.add(sc);
		}
	}

	public void remove(ISprite sc) {
		this.shapecomps.remove(sc);
	}

	@Override
	public List<ISprite> getListShape() {
		return this.shapecomps;
	}

	@Override
	public void move(Dimension space) {
		Iterator<ISprite> it = this.shapecomps.iterator();
		while (it.hasNext()) {
			ISprite sc = it.next();
			sc.move(space);
		}
	}
}
