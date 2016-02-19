package toUMLimplement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParsingIdentifier {
	private String[] args;
	private boolean isSeq;
	private boolean includeJava;
	private int adpcount;
	private int dccount;
	private int cmcount;

	public ParsingIdentifier(String[] args) {
		this.args = args;
		this.isSeq = false;
		this.includeJava = false;
		this.adpcount = -1;
		this.dccount = -1;
		this.cmcount = -1;
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
			this.cmcount = Integer.parseInt(this.args[this.args.length - 1]);
			this.dccount = Integer.parseInt(this.args[this.args.length - 2]);
			this.adpcount = Integer.parseInt(this.args[this.args.length - 3]);
			
			if (this.args[this.args.length - 4].equals("true")) {
				this.includeJava = true;
			}
			if (this.args[this.args.length - 5].equals("true")) {
				for (int i = 1; i < this.args.length - 5; i++) {
					clssname.add(this.args[i]);
				}
			} else {
				for (int i = 1; i < this.args.length - 5; i++) {
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

	public int getAdpcount() {
		return adpcount;
	}

	public int getDccount() {
		return dccount;
	}

	public int getCmcount() {
		return cmcount;
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
