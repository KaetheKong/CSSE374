package designPatterns;

import java.util.ArrayList;
import java.util.List;

import classes.FieldClass;
import classes.MethodClass;
import interfaces.IDesignPattern;

public class SingletonDetect implements IDesignPattern {

	private String defaultreturnType;
	private List<String> defaultFields;

	public SingletonDetect(String defaultfield) {
		this.defaultreturnType = defaultfield;
		this.defaultFields = new ArrayList<String>();
		this.defaultFields.add(defaultfield);
	}

	@Override
	public String getDefaultReturnType() {
		return this.defaultreturnType;
	}

	@Override
	public List<String> getDefaultFields() {
		return this.defaultFields;
	}

	@Override
	public boolean detectPattern(List<MethodClass> methods, List<FieldClass> fields) {
		MethodClass getinstncemds = null;
		for (MethodClass mc : methods) {
			String str = mc.getReturnType();
			str = str.replace(".", "/");
			if (str.split("/").length >= 1) {
				String[] x = str.split("/");
				str = x[x.length - 1];
			}
			
			if (mc.getAccess().equals("public_static") && this.defaultreturnType.equals(str)) {
				getinstncemds = mc;
				break;
			}
		}
		if (getinstncemds == null)
			return false;

		List<FieldClass> reqinstancefields = new ArrayList<FieldClass>();

		for (FieldClass fc : fields) {
			String laststr = fc.getFieldtype();
			laststr = laststr.replace(".", "/");
			if (laststr.split("/").length > 0) {
				String[] ftype = laststr.split("/");
				laststr = ftype[ftype.length - 1];
			}
			if (this.defaultFields.contains(laststr)) {
				reqinstancefields.add(fc);
				break;
			}
		}

		if (reqinstancefields.size() < 1 || reqinstancefields.size() != this.defaultFields.size())
			return false;

		for (FieldClass eachfc : reqinstancefields) {
			if (!eachfc.getAccess().contains("_static")) {
				return false;
			}
		}

		return true;
	}

}
