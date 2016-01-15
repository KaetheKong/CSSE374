package toUMLimplement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Data.ClassnameData;
import classes.AbstractClass;
import classes.AssociationClass;
import classes.ClassClass;
import classes.FieldClass;
import classes.InterfaceClass;
import classes.MethodClass;
import classes.UsesClass;
import implementation.CodeASM;
import interfaces.IConnection;

public class ToUML {
	public final static String FIRST_SEVERAL_LINES = "    fontname = \"Bitstream Vera Sans\"\n" + "    rankdir = BT\n"
			+ "    fontsize = 8\n" + "    node [\n" + "\t fontname = \"Bitstream Vera Sans\"\n" + "\t fontsize = 8\n"
			+ "\t shape = \"record\"\n" + "    ]\n" + "    edge [\n" + "\t fontname = \"Bitstream Vera Sans\"\n"
			+ "\t fontsize = 8\n" + "    ]\n";

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Map<String, ClassClass> classes = new HashMap<String, ClassClass>();

		String UMLText = "digraph G {\n";
		String Classtext = "";

		ArrayList<String> Classnames = new ArrayList<String>();
		for (String arg : args) {
			for (@SuppressWarnings("rawtypes")
			Class c : ClassFinder.getClasses(arg)) {
				Classnames.add(c.getName());
			}
		}

		for (String classname : Classnames) {
			CodeASM ASMParser = new CodeASM(classname);
			ASMParser.run();
			Map<String, FieldClass> fields = ASMParser.getFields();
			Map<String, MethodClass> methods = ASMParser.getMethods();
			String className = ASMParser.getClassName();

			ArrayList<MethodClass> allmethods = new ArrayList<>();
			ArrayList<FieldClass> allfields = new ArrayList<>();

			for (String f : fields.keySet()) {
				allfields.add(fields.get(f));
			}

			for (String m : methods.keySet()) {
				allmethods.add(methods.get(m));
			}

			ClassClass newCC = new ClassClass(allmethods, allfields, ASMParser.getSuperclassName(),
					ASMParser.getClassInterfaces(), ASMParser.getClassAccess(), ASMParser.getClassName(),
					ASMParser.isInterface(), ASMParser.isAbstract());
			classes.put(className, newCC);
		}

		for (String cc : classes.keySet()) {
			String t = classes.get(cc).toUMLString();
			Classtext = Classtext + t + "\n";
		}

		ClassnameData cd = new ClassnameData(Classnames);
		for (ClassClass cc : classes.values()) {

			IConnection interfaceclss = new InterfaceClass(cc);
			IConnection absClass = new AbstractClass(cc);
			IConnection usesClass = new UsesClass(cc, Classnames);
			IConnection associationClass = new AssociationClass(cc, Classnames);

			cd.addConnections(interfaceclss);
			cd.addConnections(absClass);
			cd.addConnections(usesClass);
			cd.addConnections(associationClass);
		}

		ComputeUMLConnection cuc = new ComputeUMLConnection(cd);
		cuc.findConnection();
		UMLText = UMLText + FIRST_SEVERAL_LINES + Classtext + cuc.getConnection() + "} \n";
		System.out.println(UMLText);
	}
}
