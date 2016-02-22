package designPatterns;

import java.util.List;

import Utilities.Parser;
import classes.ClassClass;
import classes.FieldClass;
import classes.MethodClass;
import interfaces.IDesignPattern;

public class HashMapDetect implements IDesignPattern {

	private String name;
	private String colorSetUp;
	private Parser p;
	public HashMapDetect(ClassClass cc) {
		this.name = "HashMap";
		this.colorSetUp = "\t style=\"filled\"\n" + "\t fillcolor=\"#00ffff\"\n";
		this.p = new Parser(null);
	}

	@Override
	public String getDefaultReturnType() {
		return null;
	}

	@Override
	public List<String> getDefaultFields() {
		return null;
	}

	@Override
	public boolean detectPattern(List<MethodClass> methods, List<FieldClass> fields) {
		for (MethodClass mc : methods) {
			List<MethodClass> neighbours = mc.getNeighbours();
			for (MethodClass x : neighbours) {
				this.p.setToParse(x.getClssnameCalledFrom());
				String st = this.p.parse();
				if (x.getName().equals("<init>") && st.toLowerCase().equals(this.name.toLowerCase())) {
					return true;
				}
			}

		}
		return false;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getColorSetUp() {
		return this.colorSetUp;
	}

	@Override
	public void setCc(ClassClass cc) {
	}

	@Override
	public List<ClassClass> getInformation() {
		return null;
	}

	@Override
	public void setIncludejava(boolean includejava) {
	}

	@Override
	public void setDepth(int d) {
		// do nothing
	}

}
