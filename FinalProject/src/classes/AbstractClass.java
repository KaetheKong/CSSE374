package classes;

import java.util.ArrayList;

import interfaces.IAbstractClass;

public class AbstractClass extends ClassClass implements IAbstractClass{
	
	private String edge;

	public AbstractClass(ArrayList<MethodClass> methods, ArrayList<FieldClass> fields, String superclassname,
			String[] interfacesname, String access, String classname, boolean isInterface, boolean isAbstract) {
		super(methods, fields, superclassname, interfacesname, access, classname, isInterface, isAbstract);
		this.edge = "    edge [\n\t style = \"solid\"\n\t arrowhead = \"empty\"\n    ]\n";
	}

	@Override
	public String getEdge() {
		return this.edge;
	}

	public String superConnection(ClassClass cc) {
		String connect = "";
		if (!cc.getSuperclassname().startsWith("java")) {
			String[] realname = cc.getSuperclassname().split("/");
			connect = connect + "    " + cc.getClassname() + "->" + realname[realname.length - 1] + "\n";
		}
		return connect;
	}
}
