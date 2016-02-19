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
	private int depth;

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
		this.depth = 3;
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

			List<ClassClass> ancClasses = this.getAncestorClasses(x, 0);

			for (ClassClass t : ancClasses) {
				if (t.getClassname() != null && t.getClassname().length() > 0) {
					this.p.setToParse(t.getClassname());
					String str1 = this.p.parse();
					this.p.setToParse(ccc.getClassname());
					String str2 = this.p.parse();
					this.p.setToParse(this.cc.getClassname());
					String str3 = this.p.parse();
					this.p.setToParse(x.getClassname());
					String str4 = this.p.parse();

					if (!str3.toLowerCase().equals(str4.toLowerCase())
							&& str1.toLowerCase().equals(str2.toLowerCase())) {
						LeafClass lc = new LeafClass(x);
						this.allinformation.add(lc);
					}

					if (str1.toLowerCase().equals(str3.toLowerCase())) {
						LeafClass lc = new LeafClass(x);
						this.allinformation.add(lc);
					}
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

	private List<ClassClass> getAncestorClasses(ClassClass clazzc, int count) {
		List<ClassClass> ancestorClass = new ArrayList<ClassClass>();
		if ((clazzc.getClassname().toLowerCase().startsWith("java")
				&& clazzc.getClassname().toLowerCase().contains("object")) || count > this.depth) {
			return ancestorClass;
		}

		if ((clazzc.getSuperclassname().toLowerCase().startsWith("java")
				&& clazzc.getSuperclassname().toLowerCase().contains("object")) || count > this.depth) {
			return ancestorClass;
		}

		String[] interfaceclss = clazzc.getInterfacesname();
		List<String> allPossibleSupers = new ArrayList<String>();

		for (int i = 0; i < interfaceclss.length; i++) {
			if (!interfaceclss[i].startsWith("java") || this.includeJava) {
				allPossibleSupers.add(interfaceclss[i]);
			}
		}

		if (!clazzc.getSuperclassname().startsWith("java") || this.includeJava) {
			allPossibleSupers.add(clazzc.getSuperclassname());
		}

		for (String ifc : allPossibleSupers) {
			List<ClassVisitor> cvs = this.setVisitors(ifc);
			ClassMethodVisitor cmv = (ClassMethodVisitor) cvs.get(2);
			ClassFieldVisitor cfv = (ClassFieldVisitor) cvs.get(1);
			ClassDecorationVisitor cdv = (ClassDecorationVisitor) cvs.get(0);

			List<MethodClass> supermethods = cmv.getAllMethodsInfo();
			Map<String, FieldClass> fds = cfv.getFieldInfoCollection();
			List<FieldClass> allfields = new ArrayList<FieldClass>();

			for (String f : fds.keySet()) {
				allfields.add(fds.get(f));
			}

			ClassClass interfacecc = new ClassClass(supermethods, allfields, cdv.getSuperName(), cdv.getInterfaces(),
					cdv.getAccess(), cdv.getName(), cdv.isInterface(), cdv.isAbstract(), this.cc.getDpd());
			ancestorClass.add(interfacecc);

			if (cdv.getInterfaces().length > 0 || cdv.getSuperName().length() > 0 || cdv.getSuperName() != null) {
				List<ClassClass> subcc = getAncestorClasses(interfacecc, count + 1);
				for (ClassClass c : subcc) {
					this.addwithoutduplication(ancestorClass, c);
				}
			}
		}
		return ancestorClass;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	private List<ClassVisitor> setVisitors(String name) {
		ClassReader cr;
		List<ClassVisitor> x = new ArrayList<ClassVisitor>();
		try {
			cr = new ClassReader(name);
			ClassVisitor visitor = new ClassDecorationVisitor(Opcodes.ASM5);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, visitor);
			ClassMethodVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
			cr.accept(cmv, ClassReader.EXPAND_FRAMES);
			x.add(visitor);
			x.add(fieldVisitor);
			x.add(cmv);
		} catch (IOException e) {
			// System.out.println("Class not found!");
		}

		return x;

	}

	private void addwithoutduplication(List<ClassClass> ccs, ClassClass c) {
		this.p.setToParse(c.getClassname());
		String cname = this.p.parse();

		for (int i = 0; i < ccs.size(); i++) {
			this.p.setToParse(ccs.get(i).getClassname());
			String ccsnm = this.p.parse();
			if (cname.equals(ccsnm))
				return;
		}

		ccs.add(c);
	}
}
