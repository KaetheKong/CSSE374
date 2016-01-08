package classes;

import interfaces.IField;

public class FieldClass implements IField {
	private String name;
	private String access;
	private String fieldtype;
	private Object defaultValue;

	public FieldClass(String name, String access, String fieldtype, Object defaultValue) {
		this.name = name;
		this.access = access;
		this.fieldtype = fieldtype;
		this.defaultValue = defaultValue;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	@Override
	public String getFieldtype() {
		return fieldtype;
	}

	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}

	@Override
	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
