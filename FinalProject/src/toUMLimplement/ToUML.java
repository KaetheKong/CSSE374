package toUMLimplement;

import java.io.IOException;
import java.util.ArrayList;
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

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Map<String, ClassClass> classes = new HashMap<String, ClassClass>();

		String UMLText = "digraph G {\n";
		String Classtext = "";
		ArrayList<String> Classnames = new ArrayList<String>();
		for(String arg: args){
			for(Class c: ClassFinder.getClasses(arg)){
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

			ClassClass newCC = new ClassClass(allmethods, allfields, superclassNames, interfaceNames, classAccess,
					className, isInterface, isAbstract);
			classes.put(className, newCC);
		}

		for (String cc : classes.keySet()) {
			String t = classes.get(cc).toUMLString();
			Classtext = Classtext + t +"\n";
		}
		
		String interfaceEdge = "edge [\n style = \"dashed\"\n arrowhead = \"empty\"\n]\n";
		
		for(ClassClass cc: classes.values()){
			for(String name : cc.getInterfacesname()){
				if(!name.startsWith("java")){
					String[] realname = name.split("/");
					interfaceEdge = interfaceEdge + cc.getClassname() + "->" + realname[realname.length-1] + "\n";
				}
			}
		}
		
		String superEdge = "edge [\n style = \"solid\"\n arrowhead = \"empty\"\n]\n";
		
		for(ClassClass cc: classes.values()){
			if(!cc.getSuperclassname().startsWith("java")){
				String[] realname = cc.getSuperclassname().split("/");
				superEdge = superEdge + cc.getClassname() + "->" + realname[realname.length-1] + "\n";
			}
		}
		
		UMLText = UMLText + FIRST_SEVERAL_LINES + Classtext + interfaceEdge + superEdge + "} \n";
		System.out.println(UMLText);
	}
}
