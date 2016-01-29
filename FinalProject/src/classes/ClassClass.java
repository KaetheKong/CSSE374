package classes;

import java.util.ArrayList;
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
	private boolean isAdapter;
	private List<String> target;
	private List<String> adapteeClasses;

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
		this.isAdapter = false;
		this.target = new ArrayList<String>();
		adapteeClasses = new ArrayList<>();
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
		String singletonStr = "";
		if (this.isInterface) {
			interfaceAbsStr = "\\<\\<interface\\>\\>\\n";
		}
		if (this.isAbstract) {
			interfaceAbsStr = "\\<\\<abstract\\>\\>\\n";
		}
		if (this.isSingleton) {
			singletonStr = "\\<\\<singleton\\>\\>\\n";
		}
		retStr = retStr + interfaceAbsStr + this.classname + "\\n" + singletonStr + "|";
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

		retStr = retStr + "}\"" + "\n";

		if (this.isSingleton) {
			retStr += "\t style=\"solid\"\n";
			retStr += "\t color=\"blue\"\n";
		}

		retStr = retStr + "    ]";
		return start + retStr;
	}

	public String accessModifier(String access) {
		if (access.contains("private")) {
			return "-";
		} else if (access.contains("protected")) {
			return "#";
		} else if (access.contains("public")) {
			return "+";
		} else if (access.contains("none")) {
			return "?";
		} else {
			return " ";
		}
	}

	public boolean isSingleton() {
		checkSingleton();
		return isSingleton;
	}
	
	public boolean isInterface() {
		return isInterface;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public boolean isAdapter() {
		return isAdapter;
	}

	public void checkSingleton() {
		IDesignPattern sd = new SingletonDetect(this.classname);
		this.isSingleton = sd.detectPattern(this.methods, this.fields);
	}
	
	public void checkAdapter() {
		
	}

	public List<String> getTarget() {
		return target;
	}
	
	public void addTarget(String targetClassname) {
		this.target.add(targetClassname);
	}
	
	public List<String> getAdapteeClass() {
		return this.adapteeClasses;
	}

	public void addAdaptee(String adaptee) {
		this.adapteeClasses.add(adaptee);
	}
}
