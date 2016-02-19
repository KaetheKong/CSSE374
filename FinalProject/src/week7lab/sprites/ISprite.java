package week7lab.sprites;

import java.awt.Dimension;
import java.awt.Shape;
import java.util.List;

public interface ISprite {
	public void move(Dimension space);
	public Shape getShape();
	public List<ISprite> getListShape();
}
