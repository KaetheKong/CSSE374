package problem;

import java.io.IOException;
import java.nio.file.Path;

public class TextLauncher extends AppLauncher{
	private String command;
	
	TextLauncher(Path dir) throws IOException {
		super(dir);
		this.command = "Notepad";
	}
	
	@Override
	public String getCommand() {
		return command;
	}
}
