package toUMLimplement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Data.ClassnameData;
import Data.ConnectionData;
import Data.DesignPatternData;
import Utilities.Parser;
import classes.ClassClass;
import classes.FieldClass;
import classes.MethodClass;
import implementation.CodeASM;
import interfaces.IConnection;
import interfaces.IData;
import interfaces.IDesignPattern;

public class UMLGenerator {
	private IData<IDesignPattern> dpd;
	private IData<IConnection> cnctd;
	private IData<String> cd;
	private String[] args;
	private Map<String, ClassClass> classes;
	private Map<String, ClassClass> additionalclasses;
	private String FIRST_SEVERAL_LINES;
	private String UMLText;
	private Parser p;

	public UMLGenerator(String[] args) {
		this.args = args;
		this.cd = new ClassnameData(new ArrayList<>());
		this.cnctd = new ConnectionData(new ArrayList<>());
		this.dpd = new DesignPatternData(-1, -1, -1);
		this.FIRST_SEVERAL_LINES = "    fontname = \"Bitstream Vera Sans\"\n" + "    rankdir = BT\n"
				+ "    fontsize = 8\n" + "    node [\n" + "\t fontname = \"Bitstream Vera Sans\"\n"
				+ "\t fontsize = 8\n" + "\t shape = \"record\"\n" + "    ]\n" + "    edge [\n"
				+ "\t fontname = \"Bitstream Vera Sans\"\n" + "\t fontsize = 8\n" + "    ]\n";
		this.p = new Parser(null);
		this.classes = new HashMap<String, ClassClass>();
		this.additionalclasses = new HashMap<String, ClassClass>();
		this.UMLText = "digraph G {\n";
	}

	public void run() throws IOException, ClassNotFoundException {
		
		Map<String, Map<String, ArrayList<String>>> owner = new HashMap<String, Map<String, ArrayList<String>>>();
		Map<String, Map<String, ArrayList<String>>> methodCall = new HashMap<String, Map<String, ArrayList<String>>>();
		
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
					temp.setAlltypes(mtoType.get(name));
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
					ASMParser.isInterface(), ASMParser.isAbstract(), dpd);
			if (!classes.containsKey(className)) {
				classes.put(className, newCC);
			}
		}

		List<ClassClass> ccs = new ArrayList<ClassClass>();

		for (String cc : classes.keySet()) {
			ccs.add(classes.get(cc));
		}
		
		this.dpd = new DesignPatternData(pi.getAdpcount(), pi.getDccount(), pi.getCmcount());
		this.dpd.setAllClassData(ccs);
		this.dpd.initialize(includeJava, null);

		for (String cc : classes.keySet()) {
			classes.get(cc).setDpd(this.dpd);
			String t = classes.get(cc).toUMLString();
			String comptxt = "";
			if (classes.get(cc).getAllPatternClassClassInfo() != null) {
				List<ClassClass> patternclassinfor = classes.get(cc).getAllPatternClassClassInfo();
				for (ClassClass pci : patternclassinfor) {
					comptxt += pci.toUMLString();
					additionalclasses.put(pci.getClassname(), pci.getCc());
				}
			}
			if (!t.equals("") && t != null && !Classtext.contains(t.substring(0, t.indexOf('[')))) {
				Classtext = Classtext + t;
			}
			if (!comptxt.equals("") && !Classtext.contains(comptxt)) {
				Classtext = Classtext + comptxt + "\n";
			}
		}

		this.dpd.setAllClassData(ccs);
		this.dpd.initialize(includeJava, null);

		for (String cn : Classnames) {
			this.cd.addData(cn);
		}
		this.cnctd = new ConnectionData(Classnames);
		for (ClassClass cc : classes.values()) {
			if (!this.checkcontains(cc, additionalclasses)) {
				cc.setDpd(this.dpd);
				cnctd.initialize(includeJava, cc);
			}
		}

		for (ClassClass cc : additionalclasses.values()) {
			cc.setDpd(dpd);
			cnctd.initialize(includeJava, cc);
		}

		if (!isSeq) {
			ComputeUMLConnection cuc = new ComputeUMLConnection(cd, cnctd);
			cuc.findConnection();
			UMLText = UMLText + FIRST_SEVERAL_LINES + Classtext + cuc.getConnection() + "} \n";
			UMLText = UMLText.replaceAll("\\$", "");
			System.out.println(UMLText);

			String filename = "uml_code.gv";
			BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter(filename));
				writer.write(UMLText);
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
		} else {
			ComputeSeqDiagram csd = new ComputeSeqDiagram(methodname, methods, includeJava, 5);
			String t = csd.getText();

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

	public String getUMLText() {
		return UMLText;
	}

	public void addDesignPattern(IDesignPattern idp) {
		this.dpd.addData(idp);
	}

	public void addConnection(IConnection ic) {
		this.cnctd.addData(ic);
	}

	public void addClassname(String cnm) {
		this.cd.addData(cnm);
	}

	public void removeDesignPattern(IDesignPattern idp) {
		this.dpd.removeData(idp);
	}

	public void removeConnection(IConnection ic) {
		this.cnctd.removeData(ic);
	}

	public void removeClassname(String cnm) {
		this.cd.removeData(cnm);
	}
	
	public Map<String, ClassClass> getClasses() {
		return classes;
	}

	public Map<String, ClassClass> getAdditionalclasses() {
		return additionalclasses;
	}

	private boolean checkcontains(ClassClass cc, Map<String, ClassClass> addClass) {
		for (ClassClass clzz : addClass.values()) {
			this.p.setToParse(clzz.getClassname());
			String x1 = this.p.parse();
			this.p.setToParse(cc.getClassname());
			String x2 = this.p.parse();

			if (x1.toLowerCase().equals(x2.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
}
