package userInterface;

import java.io.File;
import java.io.IOException;

public class DotLauncher {
	private String path;
	private String outpath;

	public DotLauncher(String p, String op) {
		this.path = p;
		this.outpath = op;
	}

	public void launch(boolean init) throws IOException {

		this.outpath = this.outpath + "\\\\tst.png";

		String[] cmd = this.buildcmd(init);
		ProcessBuilder proc = new ProcessBuilder(cmd);

		proc.redirectOutput(new File("tst.txt"));
		proc.redirectError(new File("error.txt"));
		Process pcs = proc.start();

		try {
			pcs.waitFor();
		} catch (InterruptedException e) {
			System.out.println("Interrupt caught!!!");
		}
	}

	public String[] buildcmd(boolean init) {
		if (init) {
			String[] cmd = { this.path, "-Tpng", "-o", this.outpath, "initialsetup.gv" };
			return cmd;
		} else {
			String[] cmd = { path, "-Tpng", "-o", this.outpath, "uml_code.gv" };
			return cmd;
		}
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setOutpath(String outpath) {
		this.outpath = outpath;
	}
}
