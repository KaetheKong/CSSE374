package toUMLimplement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import classes.AbstractClass;
import classes.ClassClass;
import classes.FieldClass;
import classes.InterfaceClass;
import classes.MethodClass;
import implementation.CodeASM;

public class ToUML {
	public final static String FIRST_SEVERAL_LINES = "    fontname = \"Bitstream Vera Sans\"\n" + "    fontsize = 8\n"
			+ "    node [\n" + "\t fontname = \"Bitstream Vera Sans\"\n" + "\t fontsize = 8\n"
			+ "\t shape = \"record\"\n" + "    ]\n" + "    edge [\n" + "\t fontname = \"Bitstream Vera Sans\"\n"
			+ "\t fontsize = 8\n" + "    ]\n";

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Map<String, ClassClass> classes = new HashMap<String, ClassClass>();

		String UMLText = "digraph G {\n";
		String Classtext = "";
		
		ArrayList<String> Classnames = new ArrayList<String>();
		for(String arg: args){
			for(@SuppressWarnings("rawtypes") Class c: ClassFinder.getClasses(arg)){
				Classnames.add(c.getName());
			}
		}
		
		for (String classname : Classnames) {
			CodeASM ASMParser = new CodeASM(classname);
			ASMParser.run();
			Map<String, FieldClass> fields = ASMParser.getFields();
			Map<String, MethodClass> methods = ASMParser.getMethods();
			String classAccess = ASMParser.getClassAccess();
			String className = ASMParser.getClassName();
			String superclassNames = ASMParser.getSuperclassName();
			String[] interfaceNames = ASMParser.getClassInterfaces();
			boolean isInterface = ASMParser.isInterface();
			boolean isAbstract = ASMParser.isAbstract();

			ArrayList<MethodClass> allmethods = new ArrayList<>();
			ArrayList<FieldClass> allfields = new ArrayList<>();

			for (String f : fields.keySet()) {
				allfields.add(fields.get(f));
			}

			for (String m : methods.keySet()) {
				allmethods.add(methods.get(m));
			}

			if (interfaceNames.length >= 1) {
				ClassClass cc = new InterfaceClass(allmethods, allfields, superclassNames, interfaceNames, classAccess,
						className, isInterface, isAbstract);
				classes.put(className, cc);
			} else if (superclassNames != null) {
				ClassClass cc = new AbstractClass(allmethods, allfields, superclassNames, interfaceNames, classAccess,
						className, isInterface, isAbstract);
				classes.put(className, cc);
			} else {
				ClassClass newCC = new ClassClass(allmethods, allfields, superclassNames, interfaceNames, classAccess,
						className, isInterface, isAbstract);
				classes.put(className, newCC);
			}
		}

		for (String cc : classes.keySet()) {
			String t = classes.get(cc).toUMLString();
			Classtext = Classtext + t + "\n";
		}

		String interfaceText = "";
		String connection = "";

		for (ClassClass cc : classes.values()) {
			if (cc.getInterfacesname().length >= 1) {
				connection = ((InterfaceClass) cc).interfaceConnection(cc);

				if (!interfaceText.contains(((InterfaceClass) cc).getEdge())) {
					interfaceText = interfaceText + ((InterfaceClass) cc).getEdge() + "\n";
				}

				if (!interfaceText.contains(connection)) {
					interfaceText = interfaceText + connection;
				}
			}
		}
		interfaceText = interfaceText + "\n";

		String superEdge = "    edge [\n\t style = \"solid\"\n\t arrowhead = \"empty\"\n    ]\n";

		for (ClassClass cc : classes.values()) {
			if (!cc.getSuperclassname().startsWith("java")) {
				String[] realname = cc.getSuperclassname().split("/");
				superEdge = superEdge + "    " + cc.getClassname() + "->" + realname[realname.length - 1] + "\n";
			}
		}

		UMLText = UMLText + FIRST_SEVERAL_LINES + Classtext + interfaceText + superEdge + "} \n";
		System.out.println(UMLText);
	}
}
