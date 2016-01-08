package classes;

import java.util.ArrayList;
import java.util.List;

import interfaces.IClass;

public class ClassClass implements IClass {
	private List<MethodClass> methods;
	private List<FieldClass> fields;
	private String superclassname;
	private String[] interfacesname;
	private String access;
	private String classname;
	private boolean isInterface;
	private boolean isAbstract;

	public ClassClass(ArrayList<MethodClass> methods, ArrayList<FieldClass> fields, String superclassname,
			String[] interfacesname, String access, String classname, boolean isInterface, boolean isAbstract) {
		this.methods = methods;
		this.fields = fields;
		this.superclassname = superclassname;
		this.interfacesname = interfacesname;
		this.access = access;
		this.classname = classname;
		this.isInterface = isInterface;
		this.isAbstract = isAbstract;
	}
	
	public void modifyClassname() {
		String name = "";
		for (int i = classname.length()-1; i > -1; i--) {
			if (classname.charAt(i) == '/') {
				break;
			}
			name = classname.charAt(i) + name;
		}
		
		this.classname = name;
	}
	
	@Override
	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	@Override
	public List<MethodClass> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodClass> methods) {
		this.methods = methods;
	}

	@Override
	public List<FieldClass> getFields() {
		return fields;
	}

	public void setFields(List<FieldClass> fields) {
		this.fields = fields;
	}

	@Override
	public String getSuperclassname() {
		return superclassname;
	}

	public void setSuperclassname(String superclassname) {
		this.superclassname = superclassname;
	}

	@Override
	public String[] getInterfacesname() {
		return interfacesname;
	}

	public void setInterfacesname(String[] interfacesname) {
		this.interfacesname = interfacesname;
	}

	@Override
	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String toUMLString() {
		this.modifyClassname();
		String start = "    " + this.classname + "[" + "\n" + "\t label" + " = ";
		String retStr = "\"{";
		String interfaceAbsStr = "";
		if (this.isInterface) {
			interfaceAbsStr = "interface\\n";
		}
		if (this.isAbstract) {
			interfaceAbsStr = "abstract\\n";
		}
		retStr = retStr + interfaceAbsStr + this.classname + "|";
		for (FieldClass field : this.fields) {
			String acs = accessModifier(field.getAccess());
			retStr = retStr + acs + " " + field.getName() + " : " + field.getFieldtype() + "\\l";
		}
		retStr = retStr + "|";

		for (MethodClass method : this.methods) {
			if (!method.getName().contains("init")) {
				String acs = accessModifier(method.getAccess());
				retStr = retStr + acs + " " + method.getName() + "()" + " : " + method.getReturnType() + "\\l";
			}
		}
		retStr = retStr + "}\"" + "\n" + "    ]";
		return start + retStr;
	}

	public String accessModifier(String access) {
		if (access.equals("private")) {
			return "-";
		} else if (access.equals("protected")) {
			return "#";
		} else if (access.equals("public")) {
			return "+";
		} else {
			return " ";
		}
	}
}
