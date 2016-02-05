package classes;

public class AdapteeClass extends ClassClass{
	
	private ClassClass cc;
	private String name;

	public AdapteeClass(ClassClass cc) {
		super(cc.getMethods(), cc.getFields(), cc.getSuperclassname(), cc.getInterfacesname(), cc.getAccess(),
				cc.getClassname(), cc.isInterface(), cc.isAbstract(), cc.getDpd());
		this.cc = cc;
		this.name = "Adapter";
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
				newString += "\\<\\<Adaptee\\>\\>";
			} else if (i == index2) {
				newString = newString + "\n" + "\t style=\"filled\"\n";
				newString = newString + "\t fillcolor=\"#a5000\"\n";
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
