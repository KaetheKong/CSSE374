package classes;

import java.util.List;

import Utilities.Parser;
import interfaces.IConnection;

public class DecoratorClass extends ClassClass implements IConnection {

	private ClassClass cc;
	private String edge;
	private String type;
	private Parser p;
	@SuppressWarnings("unused")
	private boolean includeJava;

	public DecoratorClass(ClassClass cc) {
		super(cc.getMethods(), cc.getFields(), cc.getSuperclassname(), cc.getInterfacesname(), cc.getAccess(),
				cc.getClassname(), cc.isInterface(), cc.isAbstract(), cc.getDpd());
		this.cc = cc;
		this.edge = "    edge [\n\t style = \"solid\"\n    \t label = \"\\<\\<decorate\\>\\>\"\n    ]\n";
		this.type = "Decorator";
		this.p = new Parser(null);
		this.includeJava = false;
	}

	@Override
	public String getEdge() {
		return this.edge;
	}

	@Override
	public String getConnection() {
		String x = "";
		if (this.cc.getPatternDetector() != null && !this.cc.getPatternDetector().isEmpty()) {
			if (this.cc.getPatternDetector().get(this.type)) {
				List<ClassClass> spclassname = this.cc.getAllPatternClassClassInfo();
				for (ClassClass c : spclassname) {
					if (c.getClass().toString().contains("ComponentClass")) {
						this.p.setToParse(c.getClassname());
						String str = this.p.parse();
						x = x + "    " + this.cc.getClassname() + "->" + str + "\n";
					}
				}
			}
		}
		return x;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public void setIncludeJava(boolean x) {
		this.includeJava = x;
	}

	@Override
	public void setCc(ClassClass cc) {
		this.cc = cc;
	}

}
