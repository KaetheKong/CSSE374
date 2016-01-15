package classes;

import java.util.ArrayList;
import java.util.List;

import interfaces.IConnection;

public class AssociationClass extends ClassClass implements IConnection {

	private String edge;
	private ClassClass cc;
	private List<FieldClass> fields;
	private List<String> classNames;
	private String type;

	public AssociationClass(ClassClass cc, List<String> classnames) {
		super(cc.getMethods(), cc.getFields(), cc.getSuperclassname(), cc.getInterfacesname(), cc.getAccess(),
				cc.getClassname(), cc.isInterface(), cc.isAbstract());
		this.cc = cc;
		this.edge = "    edge [\n\t style = \"solid\"\n\t arrowhead = \"open\"\n    ]\n";
		this.fields = cc.getFields();
		this.classNames = classnames;
		this.type = "association";
	}

	@Override
	public String getEdge() {
		return this.edge;
	}

	@Override
	public String getConnection() {
		String connect = "";
		for (FieldClass field : this.fields) {
			String fieldType = field.getFieldtype();
			List<String> sigs = field.parseSignature();
			List<String> parsedCname = new ArrayList<String>();
			for (String c : this.classNames) {
				String nc = c.replace('.', '/');
				String[] realc = nc.split("/");
				parsedCname.add(realc[realc.length - 1]);
			}
			if (!sigs.isEmpty()) {
				for (String s : sigs) {
					if (parsedCname.contains(s)) {
						connect = connect + "    " + cc.getClassname() + "->" + s + "\n";
					}
				}
			}

			if (!fieldType.startsWith("java") && this.classNames.contains(fieldType)) {
				fieldType = fieldType.replace('.', '/');
				String[] realname = fieldType.split("/");
				connect = connect + "    " + cc.getClassname() + "->" + realname[realname.length - 1] + "\n";
			}

		}
		return connect;
	}

	@Override
	public String getType() {
		return this.type;
	}

}
