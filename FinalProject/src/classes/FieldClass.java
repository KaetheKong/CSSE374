package classes;

import java.util.ArrayList;
import java.util.List;

import interfaces.IField;

public class FieldClass implements IField {
	private String name;
	private String access;
	private String fieldtype;
	private Object defaultValue;
	private String signatures;

	public FieldClass(String name, String access, String fieldtype, Object defaultValue, String signature) {
		this.name = name;
		this.access = access;
		this.fieldtype = fieldtype;
		this.defaultValue = defaultValue;
		this.signatures = signature;
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

	public String getSignatures() {
		return signatures;
	}

	public void setSignatures(String signatures) {
		this.signatures = signatures;
	}

	@Override
	public List<String> parseSignature() {
		List<String> sigs = new ArrayList<String>();
		if (this.signatures != null) {
			String[] signatureSplits = this.signatures.split(";");
			for (String sig : signatureSplits) {
				String[] ss = sig.split("/");
				for (String s : ss) {
					sigs.add(s);
				}
			}
		}
		return sigs;
	}

}
