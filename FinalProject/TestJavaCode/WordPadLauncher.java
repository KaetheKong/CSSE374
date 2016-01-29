package problem;

import java.io.IOException;
import java.nio.file.Path;

public class WordPadLauncher extends AppLauncher{
	
	private String command;

	WordPadLauncher(Path dir) throws IOException {
		super(dir);
		this.command = "C:/Program Files/Windows NT/Accessories/wordpad.exe";
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
}