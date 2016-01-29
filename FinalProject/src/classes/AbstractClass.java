package classes;

import interfaces.IConnection;

public class AbstractClass extends ClassClass implements IConnection{
	
	private String edge;
	private ClassClass cc;
	private String type;
	
	public AbstractClass(ClassClass cc) {
		super(cc.getMethods(), cc.getFields(), cc.getSuperclassname(), cc.getInterfacesname(), cc.getAccess(), cc.getClassname(), cc.isInterface(), cc.isAbstract());
		this.cc = cc;
		this.edge = "    edge [\n\t style = \"solid\"\n\t arrowhead = \"empty\"\n    ]\n";
		this.type = "abstract";
	}

	
	public String getType() {
		return type;
	}


	public String getEdge() {
		return this.edge;
	}

	public String getConnection() {
		String connect = "";
		if (cc.getSuperclassname() == null) return "";
		if (!cc.getSuperclassname().startsWith("java")) {
			String[] realname = cc.getSuperclassname().split("/");
			connect = connect + "    " + cc.getClassname() + "->" + realname[realname.length - 1] + "\n";
		}
		return connect;
	}


	public ClassClass getCc() {
		return cc;
	}


	public void setCc(ClassClass cc) {
		this.cc = cc;
	}
}
