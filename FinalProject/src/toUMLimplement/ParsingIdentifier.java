package toUMLimplement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParsingIdentifier {
	private String[] args;
	private boolean isSeq;
	
	public ParsingIdentifier(String[] args) {
		this.args = args;
		this.isSeq = false;
	}
	
	public List<String> getClassnames() throws ClassNotFoundException, IOException {
		List<String> clssname = new ArrayList<String>();
		if (this.args[0].equals("seq")) {
			clssname.add(this.args[1]);
			this.isSeq = true;
		} else if (this.args[0].equals("uml")) {
			for (int i = 1; i < this.args.length; i++) {
				String arg = this.args[i];
				for (@SuppressWarnings("rawtypes")
				Class c : ClassFinder.getClasses(arg)) {
					clssname.add(c.getName());
				}
			}
		}
		return clssname;
	}

	public boolean isSeq() {
		return isSeq;
	}
	
	public String getMethodName() {
		return this.args[2];
	}
	
	public String[] getParameters() {
		return this.args[3].split(",");
	}
}
