package implementation;

import java.io.IOException;
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
	private String classAccess;
	private String className;
	private String superclassName;
	private String[] classInterfaces;
	private String filename;
	private boolean isInterface;
	private boolean isAbstract;

	public CodeASM(String filename) {
		this.filename = filename;
	}

	public void run() throws IOException {
		ClassReader reader = new ClassReader(this.filename);
		ClassVisitor visitor = new ClassDecorationVisitor(Opcodes.ASM5);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, visitor);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

		fields = ((ClassFieldVisitor) fieldVisitor).getFieldInfoCollection();
		methods = ((ClassMethodVisitor) methodVisitor).getMethodsInfoCollection();
		classAccess = ((ClassDecorationVisitor)visitor).getAccess();
		className = ((ClassDecorationVisitor)visitor).getName();
		superclassName = ((ClassDecorationVisitor)visitor).getSuperName();
		classInterfaces = ((ClassDecorationVisitor)visitor).getInterfaces();
		isInterface = ((ClassDecorationVisitor)visitor).isInterface();
		isAbstract = ((ClassDecorationVisitor)visitor).isAbstract();
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
}
