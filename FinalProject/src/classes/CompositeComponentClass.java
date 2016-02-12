package classes;

public class CompositeComponentClass extends ClassClass{
	
	public ClassClass cc;
	public String name;

	public CompositeComponentClass(ClassClass cc) {
		super(cc.getMethods(), cc.getFields(), cc.getSuperclassname(), cc.getInterfacesname(), cc.getAccess(),
				cc.getClassname(), cc.isInterface(), cc.isAbstract(), cc.getDpd());
		this.cc = cc;
		this.name = "Composite";
		
	}
	
	@Override
	public String toUMLString() {
		String str = this.cc.toUMLString();
		String newString = "";
		
		int index = str.indexOf("|");
		int index2 = str.indexOf("}\"") + 2;
		
		if (this.cc.getPatternDetector().get(this.name)) {
			newString = str;
			for (ClassClass c : this.cc.getAllPatternClassClassInfo()) {
				newString = newString + c.toUMLString();
			}
			
			return newString;
		}
		
		for (int i = 0; i < str.length(); i++) {
			if (i == index) {
				newString += "\\<\\<Component\\>\\>";
			} else if (i == index2) {
				newString = newString + "\n" + "\t style=\"filled\"\n";
				newString = newString + "\t fillcolor=\"#ffff00\"\n";
			}
			newString += str.charAt(i);
		}
		
		return newString;
	}
	
	@Override
	public ClassClass getCc() {
		return this.cc;
	}
}
