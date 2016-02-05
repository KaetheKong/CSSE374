package classes;

import java.util.ArrayList;
import java.util.List;

import interfaces.IConnection;

public class UsesClass extends ClassClass implements IConnection {

	private ClassClass cc;
	private String edge;
	private List<MethodClass> methods;
	private List<String> classnames;
	private String type;
	private boolean includeJava;

	public UsesClass(ClassClass cc, List<String> classnames) {
		super(cc.getMethods(), cc.getFields(), cc.getSuperclassname(), cc.getInterfacesname(), cc.getAccess(),
				cc.getClassname(), cc.isInterface(), cc.isAbstract(), cc.getDpd());
		this.cc = cc;
		this.edge = "    edge [\n\t style = \"dashed\"\n\t arrowhead = \"open\"\n\t label = \"\"\n    ]\n";
		this.methods = cc.getMethods();
		this.classnames = classnames;
		this.type = "uses";
		this.includeJava = false;
	}

	@Override
	public String getEdge() {
		return this.edge;
	}

	@Override
	public String getConnection() {
		String connect = "";
		for (MethodClass method : this.methods) {
			List<String> sigs = method.parseSignature();
			List<String> parsedCname = new ArrayList<String>();
			for (String c : this.classnames) {
				String nc = c.replace('.', '/');
				String[] realc = nc.split("/");
				parsedCname.add(realc[realc.length - 1]);
			}
			if (!sigs.isEmpty()) {
				for (String s : sigs) {
					if (parsedCname.contains(s)) {
						if (!connect.contains(cc.getClassname() + "->" + s)) {
							connect = connect + "    " + cc.getClassname() + "->" + s + "\n";
						}
					}
				}
			}
			List<String> paramTypes = method.getAlltypes();
			for (String paramType : paramTypes) {
				paramType = paramType.replace('/', '.');
				if ((!paramType.startsWith("java") && this.classnames.contains(paramType)) || this.includeJava) {
					paramType = paramType.replace('.', '/');
					String[] realname = paramType.split("/");
					if (!connect.contains(cc.getClassname() + "->" + realname[realname.length - 1])) {
						connect = connect + "    " + cc.getClassname() + "->" + realname[realname.length - 1] + "\n";
					}
				}
			}
		}
		return connect;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public void setIncludeJava(boolean x) {
		this.includeJava = x;
	}

}
