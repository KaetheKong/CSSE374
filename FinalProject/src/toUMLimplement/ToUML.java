package toUMLimplement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		Map<String, Map<String, ArrayList<String>>> owner = new HashMap<String, Map<String, ArrayList<String>>>();
		Map<String, Map<String, ArrayList<String>>> methodCall = new HashMap<String, Map<String, ArrayList<String>>>();

		String UMLText = "digraph G {\n";
		String Classtext = "";

		ParsingIdentifier pi = new ParsingIdentifier(args);
		List<String> Classnames = pi.getClassnames();
		String methodname = "";
		String[] parameters = null;
		Map<String, MethodClass> methods = new HashMap<String, MethodClass>();
		List<MethodClass> allMethods = new ArrayList<MethodClass>();

		boolean isSeq = pi.isSeq();
		boolean includeJava = pi.isIncludeJava();

		if (isSeq) {
			methodname = pi.getMethodName();
			parameters = pi.getParameters();
		}

		for (String classname : Classnames) {
			CodeASM ASMParser = new CodeASM(classname);
			ASMParser.run(isSeq);
			Map<String, FieldClass> fields = ASMParser.getFields();
			methods = ASMParser.getMethods();
			allMethods = ASMParser.getAllMethodsinfo();

			Map<String, ArrayList<String>> mtoType = ASMParser.getMtotype();
			String className = ASMParser.getClassName();
			ArrayList<MethodClass> allmethods = new ArrayList<>();
			ArrayList<FieldClass> allfields = new ArrayList<>();

			owner.put(classname, ASMParser.getOwner());
			methodCall.put(classname, ASMParser.getMethodcalls());

			for (String name : mtoType.keySet()) {
				MethodClass temp = methods.get(name);
				if (!isSeq) {
					temp.setParameters(mtoType.get(name));
				}
				methods.replace(name, temp);
			}

			for (String f : fields.keySet()) {
				allfields.add(fields.get(f));
			}

			for (String m : methods.keySet()) {
				allmethods.add(methods.get(m));
			}

			if (isSeq) {
				for (MethodClass mc : allMethods) {
					String mcname = mc.getName();
					List<String> params = mc.getParameters();
					if (mcname.equals(methodname) && params.size() == parameters.length) {
						for (int i = 0; i < parameters.length; i++) {
							for (int j = 0; j < params.size(); j++) {
								if (parameters[i].equals(params.get(j))) {
									params.remove(j);
								}
							}
						}
						if (params.isEmpty())
							methods.put(mcname, mc);
					}
				}
			}

			ClassClass newCC = new ClassClass(allMethods, allfields, ASMParser.getSuperclassName(),
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

		if (!isSeq) {
			ComputeUMLConnection cuc = new ComputeUMLConnection(cd);
			cuc.findConnection();
			UMLText = UMLText + FIRST_SEVERAL_LINES + Classtext + cuc.getConnection() + "} \n";
			System.out.println(UMLText);
		} else {
			ComputeSeqDiagram csd = new ComputeSeqDiagram(methodname, methods, includeJava, 5);
			String t = csd.getText();
			// System.out.println(t);
			String filename = "seq_code.txt";
			BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter(filename));
				writer.write(t);
			} catch (IOException e) {
				System.err.println(e);
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						System.err.println(e);
					}
				}
			}
		}
	}
}
