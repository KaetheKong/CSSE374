package classes;

import java.util.List;

import designPatterns.SingletonDetect;
import interfaces.IClass;
import interfaces.IDesignPattern;

public class ClassClass implements IClass {
	private List<MethodClass> methods;
	private List<FieldClass> fields;
	private String superclassname;
	private String[] interfacesname;
	private String access;
	private String classname;
	private boolean isInterface;
	private boolean isAbstract;
	private boolean isSingleton;

	public boolean isInterface() {
		return isInterface;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public ClassClass(List<MethodClass> methods, List<FieldClass> fields, String superclassname,
			String[] interfacesname, String access, String classname, boolean isInterface, boolean isAbstract) {
		this.methods = methods;
		this.fields = fields;
		this.superclassname = superclassname;
		this.interfacesname = interfacesname;
		this.access = access;
		this.classname = classname;
		this.isInterface = isInterface;
		this.isAbstract = isAbstract;
		this.isSingleton = false;
	}

	public void modifyClassname() {
		String name = "";
		for (int i = classname.length() - 1; i > -1; i--) {
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
		this.checkSingleton();
		String start = "    " + this.classname + "[" + "\n" + "\t label" + " = ";
		String retStr = "\"{";
		String interfaceAbsStr = "";
		if (this.isInterface) {
			interfaceAbsStr = "interface\\n";
		}
		if (this.isAbstract) {
			interfaceAbsStr = "abstract\\n";
		}
		if (this.isSingleton) {
			interfaceAbsStr = "singleton\\n";
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
				if (!retStr.contains(method.getName() + "()" + " : " + method.getReturnType())) {
					retStr = retStr + acs + " " + method.getName() + "()" + " : " + method.getReturnType() + "\\l";
				}
			}
		}

		retStr = retStr + "}\"," + "\n";

		if (this.isSingleton) {
			retStr += "\t colour=blue\n";
		}

		retStr = retStr + "    ]";
		return start + retStr;
	}

	public String accessModifier(String access) {
		if (access.equals("private") || access.equals("private_static")) {
			return "-";
		} else if (access.equals("protected") || access.equals("protected_static")) {
			return "#";
		} else if (access.equals("public") || access.equals("public_static")) {
			return "+";
		} else {
			return " ";
		}
	}

	public boolean isSingleton() {
		return isSingleton;
	}

	public void setSingleton(boolean isSingleton) {
		this.isSingleton = isSingleton;
	}

	public void checkSingleton() {
		IDesignPattern sd = new SingletonDetect(this.classname);
		this.isSingleton = sd.detectPattern(this.methods, this.fields);
		System.out.println(this.isSingleton);
	}
}
