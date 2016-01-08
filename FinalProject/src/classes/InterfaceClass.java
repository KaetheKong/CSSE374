package classes;

import java.util.ArrayList;

import interfaces.IInterface;

public class InterfaceClass extends ClassClass implements IInterface{
	
	private String edge;

	public InterfaceClass(ArrayList<MethodClass> methods, ArrayList<FieldClass> fields, String superclassname,
			String[] interfacesname, String access, String classname, boolean isInterface, boolean isAbstract) {
		super(methods, fields, superclassname, interfacesname, access, classname, isInterface, isAbstract);
		this.edge = "    edge [\n\t style = \"dashed\"\n\t arrowhead = \"empty\"\n    ]\n";
	}

	@Override
	public String getEdge() {
		return this.edge;
	}
	
	public String interfaceConnection(ClassClass cc) {
		String connect = "";
		for(String name : cc.getInterfacesname()){
			if(!name.startsWith("java")){
				String[] realname = name.split("/");
				connect = connect + "    " + cc.getClassname() + "->" + realname[realname.length-1] + "\n";
			}
		}
		return connect;
	}

}
