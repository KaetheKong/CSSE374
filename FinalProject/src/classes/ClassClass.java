package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Data.DesignPatternData;
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
	private DesignPatternData dpd;
	private Map<String, Boolean> patternDetector;
	private List<ClassClass> allinfo;

	public ClassClass(List<MethodClass> methods, List<FieldClass> fields, String superclassname,
			String[] interfacesname, String access, String classname, boolean isInterface, boolean isAbstract,
			DesignPatternData dpd) {
		this.methods = methods;
		this.fields = fields;
		this.superclassname = superclassname;
		this.interfacesname = interfacesname;
		this.access = access;
		this.classname = classname;
		this.isInterface = isInterface;
		this.isAbstract = isAbstract;
		this.dpd = dpd;
		this.patternDetector = new HashMap<String, Boolean>();
		this.allinfo = new ArrayList<ClassClass>();
	}

	public DesignPatternData getDpd() {
		return dpd;
	}

	public boolean isDecorator() {
		return this.patternDetector.get("decorator");
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
		this.check();
		String start = "    " + this.classname + "[" + "\n" + "\t label" + " = ";
		String retStr = "\"{";
		String interfaceAbsStr = "";
		if (this.isInterface) {
			interfaceAbsStr = "\\<\\<interface\\>\\>\\n";
		}

		if (this.isAbstract) {
			interfaceAbsStr = "\\<\\<abstract\\>\\>\\n";
		}
		retStr = retStr + interfaceAbsStr + this.classname + "\\n";

		for (String k : this.patternDetector.keySet()) {
			if (this.patternDetector.get(k)) {
				retStr += "\\<\\<" + this.dpd.getIDP(k).getName() + "\\>\\>";
			}
		}

		retStr = retStr + "|";
		for (FieldClass field : this.fields) {
			String acs = accessModifier(field.getAccess());
			retStr = retStr + acs + " " + field.getName() + " : " + field.getFieldtype() + "\\l";
		}
		retStr = retStr + "|";

		for (MethodClass method : this.methods) {
			if (!method.getName().contains("init")) {
				String acs = accessModifier(method.getAccess());
				List<String> parameters = method.getParameters();
				String totl = "";
				for (int j = 0; j < parameters.size(); j++) {
					if (j == parameters.size() - 1) {
						totl = totl + parameters.get(j);
					} else {
						totl = totl + parameters.get(j) + ", ";
					}
				}
				if (!retStr.contains(method.getName() + "(" + totl + ")" + " : " + method.getReturnType())) {
					retStr = retStr + acs + " " + method.getName() + "(" + totl + ")" + " : " + method.getReturnType()
							+ "\\l";
				}
			}
		}

		retStr = retStr + "}\"" + "\n";

		for (String k : this.patternDetector.keySet()) {
			if (this.patternDetector.get(k)) {
				retStr += this.dpd.getIDP(k).getColorSetUp();
			}
		}
		retStr = retStr + "    ]\n";
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

	public boolean isInterface() {
		return isInterface;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void check() {
		List<IDesignPattern> alldps = this.dpd.getAllDesignPatterns();
		for (IDesignPattern idp : alldps) {
			idp.setCc(this);
			boolean x = idp.detectPattern(this.methods, this.fields);
			if (idp.getInformation() != null && !idp.getInformation().isEmpty() && x) {
				for (ClassClass cc : idp.getInformation()) {
					if (cc != null && !this.containinfo(cc)) {
						this.allinfo.add(cc);
					}
				}
			}
			this.patternDetector.put(idp.getName(), x);
		}
	}
	
	private boolean containinfo(ClassClass c) {
		for (ClassClass xcc : this.allinfo) {
			if (xcc.getClassname().equals(c.getClassname())) {
				return true;
			}
		}
		return false;
	}

	public List<ClassClass> getAllPatternClassClassInfo() {
		return this.allinfo;
	}

	public Map<String, Boolean> getPatternDetector() {
		return patternDetector;
	}

	public ClassClass getCc() {
		return this;
	}
}