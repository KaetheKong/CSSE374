package problem;

import java.io.IOException;
import java.nio.file.Path;

public class HtmlLauncher extends AppLauncher{
	
	private String command;
	
	HtmlLauncher(Path dir) throws IOException {
		super(dir);
		this.command = "explorer";
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
	

}
