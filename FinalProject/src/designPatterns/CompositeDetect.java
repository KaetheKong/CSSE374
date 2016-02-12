package designPatterns;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import Utilities.Parser;
import asm.ClassDecorationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import classes.ClassClass;
import classes.CompositeComponentClass;
import classes.FieldClass;
import classes.LeafClass;
import classes.MethodClass;
import interfaces.IDesignPattern;

public class CompositeDetect implements IDesignPattern {

	private String defaultereturnType;
	private List<String> defaultFields;
	private ClassClass cc;
	private List<ClassClass> allinformation;
	private List<ClassClass> allClassesInSystem;
	private boolean includeJava;
	private String name;
	private Parser p;
	private String colorSetUp;

	public CompositeDetect(ClassClass cc, List<ClassClass> allccs) {
		this.cc = cc;
		this.allinformation = new ArrayList<ClassClass>();
		this.includeJava = false;
		this.colorSetUp = "\t style=\"filled\"\n" + "\t fillcolor=\"#ffff00\"\n";
		this.p = new Parser(null);
		this.defaultereturnType = "";
		this.defaultFields = new ArrayList<String>();
		this.name = "Composite";
		this.allClassesInSystem = new ArrayList<ClassClass>();
		this.allClassesInSystem.addAll(allccs);
	}

	@Override
	public String getDefaultReturnType() {
		return this.defaultereturnType;
	}

	@Override
	public List<String> getDefaultFields() {
		return this.defaultFields;
	}

	@Override
	public boolean detectPattern(List<MethodClass> methods, List<FieldClass> fields) {
		List<String> superinfClasses = new ArrayList<String>();
		ClassReader cr;

		if (this.cc.getInterfacesname().length > 0) {
			String[] intfs = this.cc.getInterfacesname();
			for (int i = 0; i < this.cc.getInterfacesname().length; i++) {
				superinfClasses.add(intfs[i]);
			}
		}

		if (this.cc.getSuperclassname() == null)
			return false;
		if (this.cc.getSuperclassname() != null && this.cc.getSuperclassname().length() > 0) {
			superinfClasses.add(this.cc.getSuperclassname());
		}

		try {
			boolean hasCollection = false;
			for (int i = 0; i < fields.size(); i++) {
				FieldClass fc = fields.get(i);
				String ftype = "";
				if (!fc.getFieldtype().contains("int") && !fc.getFieldtype().contains("boolean")
						&& !fc.getFieldtype().contains("double") && !fc.getFieldtype().contains("float")
						&& !fc.getFieldtype().contains("char") && !fc.getFieldtype().contains("short")
						&& !fc.getFieldtype().contains("long") && !fc.getFieldtype().contains("byte")) {
					cr = new ClassReader(fc.getFieldtype().replace("[", "").replace("]", "").replace(".", "/"));
					ClassVisitor visitor = new ClassDecorationVisitor(Opcodes.ASM5);
					ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, visitor);
					ClassMethodVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
					cr.accept(cmv, ClassReader.EXPAND_FRAMES);

					String superclass = ((ClassDecorationVisitor) visitor).getSuperName();
					String[] interfaces = ((ClassDecorationVisitor) visitor).getInterfaces();

					if (superclass != null && superclass.length() > 0) {
						this.p.setToParse(superclass);
						ftype = this.p.parse();
					} else {
						ftype = "";
					}

					if (this.containCollection(interfaces) || ftype.toLowerCase().contains("collection")) {
						String sig = fc.getSignatures();
						int strtidx = sig.indexOf('<');
						int endidx = sig.indexOf('>');
						String subsig = sig.substring(strtidx + 2, endidx);
						subsig = subsig.replace(";", "");

						for (int j = 0; j < superinfClasses.size(); j++) {
							String supname = superinfClasses.get(j);
							this.p.setToParse(supname);
							String spname = this.p.parse();
							this.p.setToParse(subsig);
							String ssnm = this.p.parse();

							if (!supname.toLowerCase().startsWith("java") || this.includeJava) {
								if (spname.toLowerCase().equals(ssnm.toLowerCase())) {
									hasCollection |= true;

									cr = new ClassReader(supname);
									visitor = new ClassDecorationVisitor(Opcodes.ASM5);
									fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, visitor);
									cmv = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
									cr.accept(cmv, ClassReader.EXPAND_FRAMES);

									ClassDecorationVisitor cdv = (ClassDecorationVisitor) visitor;

									List<MethodClass> supermethods = ((ClassMethodVisitor) cmv).getAllMethodsInfo();
									Map<String, FieldClass> fds = ((ClassFieldVisitor) fieldVisitor)
											.getFieldInfoCollection();
									List<FieldClass> allfields = new ArrayList<FieldClass>();

									for (String f : fds.keySet()) {
										allfields.add(fds.get(f));
									}

									ClassClass supercc = new ClassClass(supermethods, allfields, cdv.getSuperName(),
											cdv.getInterfaces(), cdv.getAccess(), cdv.getName(), cdv.isInterface(),
											cdv.isAbstract(), this.cc.getDpd());

									CompositeComponentClass ccc = new CompositeComponentClass(supercc);
									this.allinformation.add(ccc);

									this.setLeafClasses(ccc);
									return true;
								}
							}
						}
					}
				}
			}

			if (!hasCollection) {
				return false;
			}
		} catch (IOException e) {

		}
		return false;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getColorSetUp() {
		return this.colorSetUp;
	}

	@Override
	public void setCc(ClassClass cc) {
		this.cc = cc;
	}

	@Override
	public List<ClassClass> getInformation() {
		return this.allinformation;
	}

	@Override
	public void setIncludejava(boolean includejava) {
		this.includeJava = includejava;
	}

	private void setLeafClasses(ClassClass ccc) {
		for (ClassClass x : this.allClassesInSystem) {
			if (x.getSuperclassname() != null && x.getSuperclassname().length() > 0) {
				this.p.setToParse(x.getSuperclassname());
				String str1 = this.p.parse();
				this.p.setToParse(ccc.getClassname());
				String str2 = this.p.parse();
				this.p.setToParse(this.cc.getClassname());
				String str3 = this.p.parse();
				this.p.setToParse(x.getClassname());
				String str4 = this.p.parse();

				if (!str3.toLowerCase().equals(str4.toLowerCase()) && str1.toLowerCase().equals(str2.toLowerCase())) {
					LeafClass lc = new LeafClass(x);
					this.allinformation.add(lc);
				}
			}
		}
	}

	private boolean containCollection(String[] interfaces) {
		for (int i = 0; i < interfaces.length; i++) {
			this.p.setToParse(interfaces[i]);
			String str = this.p.parse();
			if (str.toLowerCase().equals("collection")) {
				return true;
			}
		}
		return false;
	}
}
