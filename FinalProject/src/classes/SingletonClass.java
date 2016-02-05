package classes;

import java.util.Map;

import interfaces.IConnection;

public class SingletonClass extends ClassClass implements IConnection {
	private String edge;
	private ClassClass cc;
	private String type;
	@SuppressWarnings("unused")
	private boolean includeJava;

	public SingletonClass(ClassClass cc) {
		super(cc.getMethods(), cc.getFields(), cc.getSuperclassname(), cc.getInterfacesname(), cc.getAccess(),
				cc.getClassname(), cc.isInterface(), cc.isAbstract(), cc.getDpd());
		this.cc = cc;
		this.edge = "    edge [\n\t style = \"solid\"\n    ]\n";
		this.type = "Singleton";
		this.includeJava = false;
	}

	@Override
	public String getEdge() {
		return this.edge;
	}

	@Override
	public String getConnection() {
		String x = "";
		Map<String, Boolean> patternBooleans = this.cc.getPatternDetector();
		for (String k : patternBooleans.keySet()) {
			if (k.toLowerCase().equals(this.type.toLowerCase())) {
				if (patternBooleans.get(k)) {
					x = x + "    " + cc.getClassname() + "->" + cc.getClassname() + "\n";
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

}
