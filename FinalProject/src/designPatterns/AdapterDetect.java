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
	private List<String> aggregatedClasses = new ArrayList<>();
	private List<String> adapteeClasses = new ArrayList<>();

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
				List<MethodClass> supermethods = ((ClassMethodVisitor) cmv).getAllMethodsInfo();
				allInterfacemethods.addAll(supermethods);
				this.cc.addTarget(ifc);
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
			
			for(MethodClass mc : methods){
				if(mc.getName().equals("<init>")){
					for(String paramType : mc.getParameters()){
						for(FieldClass field : fields){
							if(field.getFieldtype().equals(paramType)){
								this.aggregatedClasses.add(paramType);
							}
						}
					}
				}
			}
			
			for(String className : this.aggregatedClasses){
				int count = 0;
				for(MethodClass mc : methods){
					for(MethodClass method : mc.getNeighbours()){
						if(className.equals(method.getClssnameCalledFrom())){
							this.cc.addAdaptee(className);
							count++;
						}
					}
				}
				if(count==methods.size()){
					this.adapteeClasses.add(className);
				}
			}
			
			if(this.adapteeClasses.isEmpty()){
				return false;
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}
	
}
