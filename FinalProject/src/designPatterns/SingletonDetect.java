package designPatterns;

import java.util.ArrayList;
import java.util.List;

import classes.ClassClass;
import classes.FieldClass;
import classes.MethodClass;
import interfaces.IDesignPattern;

public class SingletonDetect implements IDesignPattern {

	private String defaultreturnType;
	private List<String> defaultFields;
	private String name;
	private String colorSetUp;
	private ClassClass cc;
	public SingletonDetect(ClassClass cc) {
		this.defaultFields = new ArrayList<String>();
		this.name = "Singleton";
		this.colorSetUp = "\t style=\"solid\"\n" + "\t color=\"blue\"\n";
		this.cc = cc;
		this.setCc(cc);
	}

	public void setCc(ClassClass cc) {
		if (cc != null) {
			String somename = cc.getClassname().replace(".", "/");
			String[] real = somename.split("/");
			this.defaultreturnType = real[real.length - 1];
			this.defaultFields.add(real[real.length - 1]);
		}
		this.cc = cc;
	}

	public ClassClass getCc() {
		return cc;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getColorSetUp() {
		return colorSetUp;
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
				boolean hasSelfinit = false;
				for (int i = 0; i < mc.getNeighbours().size(); i++) {
					MethodClass mtp = mc.getNeighbours().get(i);
					String clssname = mtp.getClssnameCalledFrom().replace(".", "/");
					String[] realname = clssname.split("/");
					if (realname[realname.length - 1].equals(this.defaultreturnType)
							&& mtp.getName().contains("init")) {
						hasSelfinit = true;
						break;
					}
				}
				if (hasSelfinit) {
					getinstncemds = mc;
				}
				break;
			}
		}

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

		if ((reqinstancefields.size() < 1 || reqinstancefields.size() != this.defaultFields.size())
				&& getinstncemds == null)
			return false;

		for (FieldClass eachfc : reqinstancefields) {
			if (!eachfc.getAccess().contains("_static")) {
				return false;
			}
		}

		return true;
	}

	@Override
	public List<ClassClass> getInformation() {
		return null;
	}

	@Override
	public void setIncludejava(boolean includejava) {		
	}

}
