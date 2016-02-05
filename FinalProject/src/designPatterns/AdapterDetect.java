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
	private List<String> adapteeClasses = new ArrayList<>();
	private List<ClassClass> allinformation = new ArrayList<ClassClass>();
	private Parser p;

	public AdapterDetect(ClassClass cc) {
		this.defaultereturnType = "";
		this.defaultFields = new ArrayList<String>();
		this.cc = cc;
		this.name = "Adapter";
		this.allinformation = new ArrayList<ClassClass>();
		this.colorSetUp = "\t style=\"filled\"\n" + "\t fillcolor=\"#a5000\"\n";
		this.p = new Parser(null);
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
		String[] interfaceclss = this.cc.getInterfacesname();
		ClassReader cr;
		List<MethodClass> allInterfacemethods = new ArrayList<MethodClass>();
		try {
			for (String ifc : interfaceclss) {
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

				ClassClass supercc = new ClassClass(supermethods, allfields, cdv.getSuperName(), cdv.getInterfaces(),
						cdv.getAccess(), cdv.getName(), cdv.isInterface(), cdv.isAbstract(), this.cc.getDpd());

				TargetClass tc = new TargetClass(supercc);
				this.allinformation.add(tc);
			}

			for (MethodClass mc : methods) {
				boolean contain = false;
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

							contain = rt;
						}
					}
					if (!contain)
						return false;
				}
			}

			for (MethodClass mc : methods) {
				if (mc.getName().equals("<init>")) {
					for (String paramType : mc.getParameters()) {
						for (FieldClass field : fields) {
							if (field.getFieldtype().equals(paramType)) {
								this.aggregatedClasses.add(paramType);
							}
						}
					}
				}
			}

			for (String className : this.aggregatedClasses) {
				int count = 0;
				for (MethodClass mc : methods) {
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
							Map<String, FieldClass> fds = ((ClassFieldVisitor) fieldVisitor).getFieldInfoCollection();
							List<FieldClass> allfields = new ArrayList<FieldClass>();
							allInterfacemethods.addAll(supermethods);

							for (String f : fds.keySet()) {
								allfields.add(fds.get(f));
							}

							ClassClass supercc = new ClassClass(supermethods, allfields, cdv.getSuperName(),
									cdv.getInterfaces(), cdv.getAccess(), cdv.getName(), cdv.isInterface(),
									cdv.isAbstract(), this.cc.getDpd());

							AdapteeClass aptcc = new AdapteeClass(supercc);
							this.allinformation.add(aptcc);
							// this.cc.addAdaptee(className);
							count++;
						}
					}
				}
				if (count == methods.size() - 1) {
					this.adapteeClasses.add(className);
				}
			}

			if (this.adapteeClasses.isEmpty()) {
				return false;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
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