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
import classes.AdapteeClass;
import classes.ClassClass;
import classes.FieldClass;
import classes.MethodClass;
import classes.TargetClass;
import interfaces.IDesignPattern;

public class AdapterDetect implements IDesignPattern {

	private String defaultereturnType;
	private List<String> defaultFields;
	private ClassClass cc;
	private String colorSetUp;
	private String name;
	private List<String> aggregatedClasses = new ArrayList<>();
	private List<ClassClass> targetClasses = new ArrayList<>();
	private List<ClassClass> adapteeClasses = new ArrayList<>();
	private List<ClassClass> allinformation;
	private Parser p;
	private boolean includejava;
	private int depth;

	public AdapterDetect(ClassClass cc) {
		this.defaultereturnType = "";
		this.defaultFields = new ArrayList<String>();
		this.cc = cc;
		this.name = "Adapter";
		this.allinformation = new ArrayList<ClassClass>();
		this.colorSetUp = "\t style=\"filled\"\n" + "\t fillcolor=\"#a5000\"\n";
		this.p = new Parser(null);
		this.includejava = false;
		this.depth = 1;
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
		if (this.cc.isInterface()) {
			return false;
		}

		if (this.cc.getPatternDetector().get("Decorator") != null && this.cc.getPatternDetector().get("Decorator")) {
			return false;
		}

		String[] interfaceclss = this.cc.getInterfacesname();
		ClassReader cr;
		List<MethodClass> allInterfacemethods = new ArrayList<MethodClass>();
		int numberInit = 0;
		try {
			for (String ifc : interfaceclss) {
				if (!ifc.startsWith("java") || this.includejava) {
					cr = new ClassReader(ifc);
					ClassVisitor visitor = new ClassDecorationVisitor(Opcodes.ASM5);
					ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, visitor);
					ClassMethodVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
					cr.accept(cmv, ClassReader.EXPAND_FRAMES);

					ClassDecorationVisitor cdv = (ClassDecorationVisitor) visitor;

					List<MethodClass> supermethods = ((ClassMethodVisitor) cmv).getAllMethodsInfo();
					Map<String, FieldClass> fds = ((ClassFieldVisitor) fieldVisitor).getFieldInfoCollection();
					List<FieldClass> allfields = new ArrayList<FieldClass>();
					allInterfacemethods.addAll(supermethods);

					for (String f : fds.keySet()) {
						allfields.add(fds.get(f));
					}

					ClassClass supercc = new ClassClass(supermethods, allfields, cdv.getSuperName(),
							cdv.getInterfaces(), cdv.getAccess(), cdv.getName(), cdv.isInterface(), cdv.isAbstract(),
							this.cc.getDpd());

					TargetClass tc = new TargetClass(supercc);
					this.targetClasses.add(tc);
				}
			}

			if (this.cc.getSuperclassname() == null || (this.cc.getSuperclassname().startsWith("java") && !this.includejava)) {
				return false;
			}

			if ((this.cc.getSuperclassname() != null || this.cc.getSuperclassname().length() > 0)
					&& !this.cc.getSuperclassname().contains("Object")
					&& (!this.cc.getSuperclassname().startsWith("java") || this.includejava)
					&& !this.cc.getSuperclassname().startsWith("org")) {
				cr = new ClassReader(this.cc.getSuperclassname());
				ClassVisitor visitor = new ClassDecorationVisitor(Opcodes.ASM5);
				ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, visitor);
				ClassMethodVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
				cr.accept(cmv, ClassReader.EXPAND_FRAMES);

				ClassDecorationVisitor cdv = (ClassDecorationVisitor) visitor;

				List<MethodClass> supermethods = ((ClassMethodVisitor) cmv).getAllMethodsInfo();
				Map<String, FieldClass> fds = ((ClassFieldVisitor) fieldVisitor).getFieldInfoCollection();
				List<FieldClass> allfields = new ArrayList<FieldClass>();
				allInterfacemethods.addAll(supermethods);

				for (String f : fds.keySet()) {
					allfields.add(fds.get(f));
				}

				ClassClass supercc = new ClassClass(supermethods, allfields, cdv.getSuperName(), cdv.getInterfaces(),
						cdv.getAccess(), cdv.getName(), cdv.isInterface(), cdv.isAbstract(), this.cc.getDpd());

				TargetClass tc = new TargetClass(supercc);
				this.targetClasses.add(tc);
			}

			boolean contain = false;
			for (MethodClass mc : methods) {
				if (!mc.getName().contains("init")) {
					for (MethodClass sm : allInterfacemethods) {
						if (sm.getName().equals(mc.getName())
								&& sm.getParameters().size() == mc.getParameters().size()) {

							List<String> params = new ArrayList<String>();
							for (int i = 0; i < mc.getParameters().size(); i++) {
								params.add(mc.getParameters().get(i));
							}

							if (params.isEmpty() && sm.getParameters().isEmpty()) {
								contain = true;
								break;
							}

							for (int j = 0; j < params.size(); j++) {
								if (sm.getParameters().get(j).equals(params.get(j))) {
									params.remove(j);
									params.add(0, null);
								}
							}

							boolean rt = true;
							for (String s : params) {
								rt &= (s == null);
							}
							if (rt == true) {
								contain |= rt;
							}
						}
					}
				}
			}

			if (!contain)
				return false;

			for (MethodClass mc : methods) {
				if (mc.getName().equals("<init>")) {
					numberInit++;
					for (String paramType : mc.getParameters()) {
						for (FieldClass field : fields) {
							if (field.getFieldtype().equals(paramType)) {
								this.aggregatedClasses.add(paramType);
							}
						}
					}
				}
			}

			List<ClassClass> ancestorclass = this.getAncestorClasses(this.cc, 0);

			for (FieldClass field : fields) {
				boolean c = false;
				for (ClassClass ccc : ancestorclass) {
					this.p.setToParse(ccc.getClassname());
					String st1 = this.p.parse().toLowerCase();
					this.p.setToParse(field.getFieldtype());
					String st2 = this.p.parse().toLowerCase();
					if (st1.equals(st2)) {
						c |= true;
					}
				}
				if (!c)
					this.aggregatedClasses.add(field.getFieldtype());
			}

			if (this.aggregatedClasses.isEmpty())
				return false;

			for (String className : this.aggregatedClasses) {
				int count = 0;
				for (MethodClass mc : methods) {
					if (!mc.getName().contains("init")) {
						int prevcount = count;
						for (MethodClass method : mc.getNeighbours()) {
							this.p.setToParse(className);
							String st1 = this.p.parse().toLowerCase();
							this.p.setToParse(method.getClssnameCalledFrom());
							String st2 = this.p.parse().toLowerCase();

							if (st1.equals(st2)) {
								cr = new ClassReader(className);
								ClassVisitor visitor = new ClassDecorationVisitor(Opcodes.ASM5);
								ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, visitor);
								ClassMethodVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
								cr.accept(cmv, ClassReader.EXPAND_FRAMES);

								ClassDecorationVisitor cdv = (ClassDecorationVisitor) visitor;

								List<MethodClass> supermethods = ((ClassMethodVisitor) cmv).getAllMethodsInfo();
								Map<String, FieldClass> fds = ((ClassFieldVisitor) fieldVisitor)
										.getFieldInfoCollection();
								List<FieldClass> allfields = new ArrayList<FieldClass>();
								allInterfacemethods.addAll(supermethods);

								for (String f : fds.keySet()) {
									allfields.add(fds.get(f));
								}

								ClassClass supercc = new ClassClass(supermethods, allfields, cdv.getSuperName(),
										cdv.getInterfaces(), cdv.getAccess(), cdv.getName(), cdv.isInterface(),
										cdv.isAbstract(), this.cc.getDpd());
								if (!supercc.getClassname().startsWith("java") || this.includejava) {
									AdapteeClass aptcc = new AdapteeClass(supercc);
									this.adapteeClasses.add(aptcc);
									// this.cc.addAdaptee(className);
									count++;
									break;
								}
							}
						}
						if (count <= prevcount) {
							break;
						}
					}
				}
				if (count == methods.size() - numberInit) {
					break;
				}
			}
			if (this.adapteeClasses.isEmpty()) {
				return false;
			}

		} catch (IOException e) {
			// do nothing
		}
		this.allinformation.addAll(this.targetClasses);
		this.allinformation.addAll(this.adapteeClasses);
		return true;
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
			if (!interfaceclss[i].startsWith("java") || this.includejava) {
				allPossibleSupers.add(interfaceclss[i]);
			}
		}

		if (!clazzc.getSuperclassname().startsWith("java") || this.includejava) {
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

	public void setIncludejava(boolean includejava) {
		this.includejava = includejava;
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

}