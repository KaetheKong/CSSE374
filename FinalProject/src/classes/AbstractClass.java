package classes;

import interfaces.IConnection;

public class AbstractClass extends ClassClass implements IConnection{
	
	private String edge;
	private ClassClass cc;
	private String type;
	private boolean includeJava;
	
	public AbstractClass(ClassClass cc) {
		super(cc.getMethods(), cc.getFields(), cc.getSuperclassname(), cc.getInterfacesname(), cc.getAccess(), cc.getClassname(), cc.isInterface(), cc.isAbstract(), cc.getDpd());
		this.cc = cc;
		this.edge = "    edge [\n\t style = \"solid\"\n\t arrowhead = \"empty\"\n\t label = \"\"\n    ]\n";
		this.type = "abstract";
		this.includeJava = false;
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
		if (!cc.getSuperclassname().startsWith("java") || this.includeJava) {
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


	@Override
	public void setIncludeJava(boolean x) {
		this.includeJava = x;
	}
}
