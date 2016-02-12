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
import classes.ComponentClass;
import classes.FieldClass;
import classes.MethodClass;
import interfaces.IDesignPattern;

public class DecoratorDetect implements IDesignPattern {

	private List<String> defaultFields;
	private ClassClass cc;
	private String name;
	private String colorSetUp;
	private ClassClass component;
	private boolean includejava;
	private Parser p;

	public DecoratorDetect(ClassClass cc) {
		this.defaultFields = new ArrayList<String>();
		this.cc = cc;
		this.name = "Decorator";
		this.colorSetUp = "\t style=\"filled\"\n" + "\t fillcolor=\"green\"\n";
		this.component = null;
		this.p = new Parser(null);
		this.includejava = false;
	}

	@Override
	public String getDefaultReturnType() {
		return null;
	}

	@Override
	public List<String> getDefaultFields() {
		return null;
	}

	@Override
	public boolean detectPattern(List<MethodClass> methods, List<FieldClass> fields) {
		List<MethodClass> allInterfacemethods = new ArrayList<MethodClass>();
		List<FieldClass> fdscomp = new ArrayList<FieldClass>();

		for (FieldClass fc : fields) {
			fdscomp.add(fc);
		}

		this.defaultFields.add(this.cc.getClassname());

		if (this.cc.getClassname().startsWith("java") && this.cc.getClassname().contains("Object")) {
			return false;
		}

		if (this.cc.getSuperclassname() != null) {

			if (!this.cc.getSuperclassname().startsWith("java") && !this.cc.getSuperclassname().contains("Object")) {
				String spcn = this.cc.getSuperclassname();
				String[] real = spcn.split("/");
				String str = spcn;
				if (real.length > 0) {
					str = real[real.length - 1];
				}
				this.defaultFields.add(str);
			}
		} else {
			return false;
		}

		for (MethodClass mc : methods) {
			if (mc.getName().contains("init")) {
				List<String> parameters = mc.getParameters();
				for (String s : parameters) {
					FieldClass tempfc = new FieldClass("", "private", s, null, null);
					fdscomp.add(tempfc);
				}

				List<MethodClass> neighbours = mc.getNeighbours();
				for (MethodClass n : neighbours) {
					FieldClass tempfc = new FieldClass("", "private", n.getClssnameCalledFrom(), null, null);
					fdscomp.add(tempfc);
				}
				break;
			}
		}

		String spclzz = this.cc.getSuperclassname();
		List<ClassVisitor> cvs = this.setVisitors(spclzz);
		ClassMethodVisitor cmv = (ClassMethodVisitor) cvs.get(2);
		ClassFieldVisitor cfv = (ClassFieldVisitor) cvs.get(1);
		ClassDecorationVisitor cdv = (ClassDecorationVisitor) cvs.get(0);

		List<MethodClass> supermethods = cmv.getAllMethodsInfo();
		Map<String, FieldClass> fds = cfv.getFieldInfoCollection();
		List<FieldClass> allfields = new ArrayList<FieldClass>();
		allInterfacemethods.addAll(supermethods);

		for (String f : fds.keySet()) {
			allfields.add(fds.get(f));
		}

		ClassClass supercc = new ClassClass(supermethods, allfields, cdv.getSuperName(), cdv.getInterfaces(),
				cdv.getAccess(), cdv.getName(), cdv.isInterface(), cdv.isAbstract(), this.cc.getDpd());
		
		Map<String, Boolean> superpinfo = supercc.getPatternDetector();

		if (superpinfo != null && !superpinfo.isEmpty()) {
			if (superpinfo.get(this.name)) {
				this.component = new ComponentClass(supercc);
				return true;
			}
		}

		FieldClass x = this.checkCommonAncestor(fdscomp);
		if (x != null) {
			boolean checkMeths = this.checkMethods(methods, x);
			if (checkMeths) {
				this.component = new ComponentClass(supercc);
				return true;
			}
		}
		return false;
	}

	private boolean checkMethods(List<MethodClass> methods, FieldClass field) {
		List<MethodClass> allInterfacemethods = new ArrayList<MethodClass>();
		String[] interfaceclss = this.cc.getInterfacesname();
		String supercc = this.cc.getSuperclassname();
		boolean result = true;

		if (supercc.toLowerCase().startsWith("java") && supercc.toLowerCase().contains("object")) {
			return true;
		}

		List<String> allPossibleSupers = new ArrayList<String>();

		for (int i = 0; i < interfaceclss.length; i++) {
			allPossibleSupers.add(interfaceclss[i]);
		}

		allPossibleSupers.add(supercc);

		for (String ifc : allPossibleSupers) {
			List<ClassVisitor> cvs1 = this.setVisitors(ifc);
			ClassMethodVisitor cmv1 = (ClassMethodVisitor) cvs1.get(2);
			ClassFieldVisitor cfv1 = (ClassFieldVisitor) cvs1.get(1);

			List<MethodClass> supermethods1 = cmv1.getAllMethodsInfo();
			Map<String, FieldClass> fds1 = cfv1.getFieldInfoCollection();
			List<FieldClass> allfields1 = new ArrayList<FieldClass>();

			for (String f : fds1.keySet()) {
				allfields1.add(fds1.get(f));
			}

			allInterfacemethods.addAll(supermethods1);
		}

		for (MethodClass mc : methods) {
			boolean contain = false;
			if (!mc.getName().contains("init")) {
				for (MethodClass sm : allInterfacemethods) {
					if (sm.getName().equals(mc.getName()) && sm.getParameters().size() == mc.getParameters().size()) {
						List<String> params = new ArrayList<String>();
						for (int i = 0; i < mc.getParameters().size(); i++) {
							params.add(mc.getParameters().get(i));
						}
						if (params.isEmpty() && sm.getParameters().isEmpty()) {
							contain = true;
							break;
						}

						if (params.size() == sm.getParameters().size()) {
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

							contain = rt;
						}

					}
					if (contain)
						break;
				}

				if (!contain) {
					result = false;
				}

				if (contain) {
					List<MethodClass> neighbors = mc.getNeighbours();
					for (MethodClass n : neighbors) {
						String classnamecallfrom = n.getClssnameCalledFrom();

						List<ClassVisitor> cvs = this.setVisitors(classnamecallfrom);
						ClassMethodVisitor cmv = (ClassMethodVisitor) cvs.get(2);
						ClassFieldVisitor cfv = (ClassFieldVisitor) cvs.get(1);
						ClassDecorationVisitor cdv = (ClassDecorationVisitor) cvs.get(0);

						List<MethodClass> supermethods = cmv.getAllMethodsInfo();
						Map<String, FieldClass> fds = cfv.getFieldInfoCollection();
						List<FieldClass> allfields = new ArrayList<FieldClass>();

						for (String f : fds.keySet()) {
							allfields.add(fds.get(f));
						}

						ClassClass scc = new ClassClass(supermethods, allfields, cdv.getSuperName(),
								cdv.getInterfaces(), cdv.getAccess(), cdv.getName(), cdv.isInterface(),
								cdv.isAbstract(), this.cc.getDpd());

						this.p.setToParse(classnamecallfrom);
						String st1 = this.p.parse();
						this.p.setToParse(field.getFieldtype());
						String st2 = this.p.parse();

						if (n.getName().equals(mc.getName()) && st2.toLowerCase().equals(st1.toLowerCase())
								&& n.getParameters().size() == mc.getParameters().size()) {
							List<String> params = new ArrayList<String>();
							for (int i = 0; i < mc.getParameters().size(); i++) {
								params.add(mc.getParameters().get(i));
							}
							if (params.isEmpty() && n.getParameters().isEmpty()) {
								contain = true;
								break;
							}

							if (params.size() == n.getParameters().size()) {
								for (int j = 0; j < params.size(); j++) {
									if (n.getParameters().get(j).equals(params.get(j))) {
										params.remove(j);
										params.add(0, null);
									}
								}

								boolean rt = true;
								for (String s : params) {
									rt &= (s == null);
								}

								contain = rt;
							}
						}

						List<FieldClass> sccfc = scc.getFields();
						for (int k = 0; k < sccfc.size(); k++) {
							this.p.setToParse(sccfc.get(k).getFieldtype());
							String st3 = this.p.parse();
							if (n.getName().equals(mc.getName()) && st2.toLowerCase().equals(st3.toLowerCase())
									&& n.getParameters().size() == mc.getParameters().size()) {
								List<String> params = new ArrayList<String>();
								for (int i = 0; i < mc.getParameters().size(); i++) {
									params.add(mc.getParameters().get(i));
								}
								if (params.isEmpty() && n.getParameters().isEmpty()) {
									contain = true;
									break;
								}

								if (params.size() == n.getParameters().size()) {
									for (int j = 0; j < params.size(); j++) {
										if (n.getParameters().get(j).equals(params.get(j))) {
											params.remove(j);
											params.add(0, null);
										}
									}
									boolean rt = true;
									for (String s : params) {
										rt &= (s == null);
									}
									contain = rt;
								}
							}
						}
						if (contain)
							break;
					}
				}
				if (!result)
					break;
			}
		}

		return result;
	}

	private FieldClass checkCommonAncestor(List<FieldClass> fields) {

		FieldClass result = null;

		for (FieldClass fc : fields) {
			String ftype = fc.getFieldtype();
			this.p.setToParse(ftype);
			String s1 = this.p.parse();

			this.p.setToParse(this.cc.getClassname());
			String s2 = this.p.parse();

			if (s1.equals(s2))
				return fc;

			List<ClassClass> cs = this.getAncestorClasses(this.cc, 0);

			if (cs.isEmpty())
				return null;

			List<ClassVisitor> cvs = this.setVisitors(ftype.replace(".", "/"));

			if (!cvs.isEmpty()) {
				ClassMethodVisitor cmv = (ClassMethodVisitor) cvs.get(2);
				ClassFieldVisitor cfv = (ClassFieldVisitor) cvs.get(1);
				ClassDecorationVisitor cdv = (ClassDecorationVisitor) cvs.get(0);

				List<MethodClass> supermethods = cmv.getAllMethodsInfo();
				Map<String, FieldClass> fds = cfv.getFieldInfoCollection();
				List<FieldClass> allfields = new ArrayList<FieldClass>();

				for (String f : fds.keySet()) {
					allfields.add(fds.get(f));
				}

				ClassClass fieldcc = new ClassClass(supermethods, allfields, cdv.getSuperName(), cdv.getInterfaces(),
						cdv.getAccess(), cdv.getName(), cdv.isInterface(), cdv.isAbstract(), this.cc.getDpd());

				List<ClassClass> fcs = this.getAncestorClasses(fieldcc, 0);
				fcs.add(fieldcc);

				for (ClassClass c : cs) {
					for (ClassClass fci : fcs) {
						this.p.setToParse(c.getClassname());
						String str1 = this.p.parse();
						this.p.setToParse(fci.getClassname());
						String str2 = this.p.parse();

						if (str1.equals(str2)) {
							result = fc;
							break;
						}
					}
					if (result != null) {
						break;
					}
				}

				if (result != null) {
					break;
				}
			}
		}
		return result;
	}

	private List<ClassClass> getAncestorClasses(ClassClass clazzc, int count) {
		List<ClassClass> ancestorClass = new ArrayList<ClassClass>();
		if ((clazzc.getClassname().toLowerCase().startsWith("java")
				&& clazzc.getClassname().toLowerCase().contains("object")) || count > 2) {
			return ancestorClass;
		}

		if ((clazzc.getSuperclassname().toLowerCase().startsWith("java")
				&& clazzc.getSuperclassname().toLowerCase().contains("object")) || count > 2) {
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
		List<ClassClass> x = new ArrayList<ClassClass>();
		x.add(this.component);
		return x;
	}

	@Override
	public void setIncludejava(boolean includejava) {
		this.includejava = includejava;
	}

}
