package classes;

import interfaces.IConnection;

public class SingletonClass extends ClassClass implements IConnection {
	private String edge;
	private ClassClass cc;
	private String type;

	public SingletonClass(ClassClass cc) {
		super(cc.getMethods(), cc.getFields(), cc.getSuperclassname(), cc.getInterfacesname(), cc.getAccess(),
				cc.getClassname(), cc.isInterface(), cc.isAbstract());
		this.cc = cc;
		this.edge = "    edge [\n\t style = \"solid\"\n    ]\n";
		this.type = "singleton";
	}

	@Override
	public String getEdge() {
		return this.edge;
	}

	@Override
	public String getConnection() {
		String x = "";
		if (this.cc.isSingleton()) {
			if (x.contains(this.edge)) {
				x += this.edge;
			}
			x += "    " + cc.getClassname() + "->" + cc.getClassname();
		}
		return x;
	}

	@Override
	public String getType() {
		return this.type;
	}

}
