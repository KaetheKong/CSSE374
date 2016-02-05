package toUMLimplement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParsingIdentifier {
	private String[] args;
	private boolean isSeq;
	private boolean includeJava;

	public ParsingIdentifier(String[] args) {
		this.args = args;
		this.isSeq = false;
		this.includeJava = false;
	}

	public List<String> getClassnames() throws ClassNotFoundException, IOException {
		List<String> clssname = new ArrayList<String>();
		if (this.args[0].equals("seq")) {
			clssname.add(this.args[1]);
			this.isSeq = true;
			if (this.args[this.args.length - 1].equals("true")) {
				this.includeJava = true;
			}
		} else if (this.args[0].equals("uml")) {
			if (this.args[this.args.length - 1].equals("true")) {
				this.includeJava = true;
			}
			if (this.args[this.args.length - 2].equals("true")) {
				for (int i = 1; i < this.args.length - 2; i++) {
					clssname.add(this.args[i]);
				}
			} else {
				for (int i = 1; i < this.args.length - 2; i++) {
					String arg = this.args[i];
					for (@SuppressWarnings("rawtypes")
					Class c : ClassFinder.getClasses(arg)) {
						clssname.add(c.getName());
					}
				}
			}
		}
		return clssname;
	}

	public boolean isIncludeJava() {
		return includeJava;
	}

	public boolean isSeq() {
		return isSeq;
	}

	public String getMethodName() {
		return this.args[2];
	}

	public String[] getParameters() {
		String comb = "";
		for (int i = 3; i < this.args.length - 1; i = i + 2) {
			if (i != this.args.length - 1) {
				if (this.args[i].contains("<")) {
					comb += this.args[i].substring(0, this.args[i].indexOf('<'));
				} else {
					comb += this.args[i];
				}
				comb += " ";
			} else
				comb += this.args[i];
		}
		return comb.split(" ");
	}
}
