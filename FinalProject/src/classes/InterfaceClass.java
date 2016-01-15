package classes;

import interfaces.IConnection;

public class InterfaceClass extends ClassClass implements IConnection{
	
	private String edge;
	private ClassClass cc;
	private String type;
	
	public InterfaceClass(ClassClass cc) {
		super(cc.getMethods(), cc.getFields(), cc.getSuperclassname(), cc.getInterfacesname(), cc.getAccess(), cc.getClassname(), cc.isInterface(), cc.isAbstract());
		this.cc = cc;
		this.edge = "    edge [\n\t style = \"dashed\"\n\t arrowhead = \"empty\"\n    ]\n";
		this.type = "interface";
	}

	@Override
	public String getEdge() {
		return this.edge;
	}
	
	public String getConnection() {
		String connect = "";
		if (this.cc.getInterfacesname().length < 1) return "";
		for(String name : this.cc.getInterfacesname()){
			if(!name.startsWith("java")){
				String[] realname = name.split("/");
				connect = connect + "    " + this.cc.getClassname() + "->" + realname[realname.length-1] + "\n";
			}
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
	public String getType() {
		return this.type;
	}

}
