package designPatterns;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import asm.ClassDecorationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import classes.ClassClass;
import classes.FieldClass;
import classes.MethodClass;
import interfaces.IDesignPattern;

public class AdapterDetect implements IDesignPattern {

	private String defaultereturnType;
	private List<String> defaultFields;
	private ClassClass cc;

	public AdapterDetect(ClassClass cc) {
		this.defaultereturnType = "";
		this.defaultFields = new ArrayList<String>();
		this.cc = cc;
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
				List<MethodClass> supermethods = ((ClassMethodVisitor) cmv).getAllMethodsInfo();
				allInterfacemethods.addAll(supermethods);
			}
			
			for (MethodClass mc : methods) {
				boolean contain = false;
				for (MethodClass sm : allInterfacemethods) {
					if (sm.getName().equals(mc.getName())
							&& sm.getParameters().size() == mc.getParameters().size()) {
						List<String> params = mc.getParameters();
						for (int i = 0; i < sm.getParameters().size(); i++) {
							for (int j = 0; j < params.size(); j++) {
								if (sm.getParameters().get(j).equals(params.get(j))) {
									params.remove(j);
								}
							}
						}
						if (params.isEmpty())
							contain = true;
					}
				}
				if (!contain)
					return false;
			}
			
			for (String ifc : interfaceclss) {
				this.cc.addTarget(ifc);
			}
			
			if (this.cc.isInterface()) {
				return true;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
}
