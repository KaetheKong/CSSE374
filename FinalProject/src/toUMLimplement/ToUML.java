package toUMLimplement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import classes.ClassClass;
import classes.FieldClass;
import classes.MethodClass;
import implementation.CodeASM;

public class ToUML {
	public final static String FIRST_SEVERAL_LINES = 
			"    fontname = \"Bitstream Vera Sans\"\n" + 
			"    fontsize = 8\n" + 
			"    node [\n" +
			"\t fontname = \"Bitstream Vera Sans\"\n" +
			"\t fontsize = 8\n" + 
			"\t shape = \"record\"\n" + 
			"    ]\n" + 
			"    edge [\n" +
			"\t fontname = \"Bitstream Vera Sans\"\n" +
			"\t fontsize = 8\n" + 
			"    ]\n";

	public static void main(String[] args) throws IOException {
		Map<String, ClassClass> classes = new HashMap<String, ClassClass>();

		String UMLText = "digraph G {\n";
		String ClassText = "";

		for (String classname : args) {
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

			ClassClass newCC = new ClassClass(allmethods, allfields, superclassNames, interfaceNames, classAccess,
					className, isInterface, isAbstract);
			classes.put(className, newCC);
		}

		for (String cc : classes.keySet()) {
			String t = classes.get(cc).toUMLString();
			System.out.println(Arrays.toString(classes.get(cc).getInterfacesname()));
			ClassText = ClassText + t + "\n";
		}
		
		UMLText = UMLText + FIRST_SEVERAL_LINES + ClassText + "}\n";
		System.out.println(UMLText);
	}
}
