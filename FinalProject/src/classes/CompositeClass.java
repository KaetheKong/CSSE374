package classes;

import java.util.List;

import Utilities.Parser;
import interfaces.IConnection;

public class CompositeClass extends ClassClass implements IConnection {
	
	private ClassClass cc;
	private String edge;
	private String type;
	private Parser p;
	@SuppressWarnings("unused")
	private boolean includeJava;
	
	public CompositeClass(ClassClass cc) {
		super(cc.getMethods(), cc.getFields(), cc.getSuperclassname(), cc.getInterfacesname(), cc.getAccess(),
				cc.getClassname(), cc.isInterface(), cc.isAbstract(), cc.getDpd());
		this.edge = "    edge [\n\t style = \"solid\"\n\t arrowhead = \"empty\"\n\t label = \"\"\n    ]\n";;
		this.type = "Composite";
		this.p = new Parser(null);
		this.includeJava = false;
		this.cc = cc;
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
					if (c.getClass().toString().contains("CompositeComponentClass")) {
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
