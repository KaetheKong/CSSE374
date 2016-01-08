package classes;

import java.util.List;

import interfaces.IClass;

public class ClassClass implements IClass {
	private List<MethodClass> methods;
	private List<FieldClass> fields;
	private String superclassname;
	private String[] interfacesname;
	private String access;
	private String classname;

	public ClassClass() {

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
}
