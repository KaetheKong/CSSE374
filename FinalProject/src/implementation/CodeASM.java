package implementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import asm.ClassDecorationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import classes.FieldClass;
import classes.MethodClass;

public class CodeASM {
	private Map<String, FieldClass> fields;
	private Map<String, MethodClass> methods;
	private Map<String, ArrayList<String>> mtotype;
	private Map<String, ArrayList<String>> methodcalls;
	private Map<String, ArrayList<String>> owner;
	private String classAccess;
	private String className;
	private String superclassName;
	private String[] classInterfaces;
	private String filename;
	private boolean isInterface;
	private boolean isAbstract;
	private List<MethodClass> allMethodsinfo;

	public CodeASM(String filename) {
		this.filename = filename;
	}

	public void run(boolean isSeq) throws IOException {
		ClassReader reader = new ClassReader(this.filename);
		ClassVisitor visitor = new ClassDecorationVisitor(Opcodes.ASM5);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, visitor);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
		if (isSeq) {
			methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, this.filename);
		}
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

		fields = ((ClassFieldVisitor) fieldVisitor).getFieldInfoCollection();
		mtotype = ((ClassMethodVisitor) methodVisitor).getMethodsToType();
		methods = ((ClassMethodVisitor) methodVisitor).getMethodsInfoCollection();
		methodcalls = ((ClassMethodVisitor) methodVisitor).getMethodCall();
		setAllMethodsinfo(((ClassMethodVisitor) methodVisitor).getAllMethodsInfo());
		owner = ((ClassMethodVisitor) methodVisitor).getOwner();
		classAccess = ((ClassDecorationVisitor)visitor).getAccess();
		className = ((ClassDecorationVisitor)visitor).getName();
		superclassName = ((ClassDecorationVisitor)visitor).getSuperName();
		classInterfaces = ((ClassDecorationVisitor)visitor).getInterfaces();
		isInterface = ((ClassDecorationVisitor)visitor).isInterface();
		isAbstract = ((ClassDecorationVisitor)visitor).isAbstract();
	}

	public Map<String, ArrayList<String>> getOwner() {
		return owner;
	}

	public Map<String, ArrayList<String>> getMethodcalls() {
		return methodcalls;
	}

	public boolean isInterface() {
		return isInterface;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public String getClassAccess() {
		return classAccess;
	}

	public String getClassName() {
		return className;
	}

	public String getSuperclassName() {
		return superclassName;
	}

	public String[] getClassInterfaces() {
		return classInterfaces;
	}

	public Map<String, FieldClass> getFields() {
		return fields;
	}

	public Map<String, MethodClass> getMethods() {
		return methods;
	}
	
	public Map<String, ArrayList<String>> getMtotype() {
		return mtotype;
	}

	public void setMtotype(Map<String, ArrayList<String>> mtotype) {
		this.mtotype = mtotype;
	}

	public List<MethodClass> getAllMethodsinfo() {
		return allMethodsinfo;
	}

	public void setAllMethodsinfo(List<MethodClass> allMethodsinfo) {
		this.allMethodsinfo = allMethodsinfo;
	}
}
