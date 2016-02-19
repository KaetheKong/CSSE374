package interfaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public interface Handler extends ActionListener {
	@Override
	public void actionPerformed(ActionEvent e);
}
