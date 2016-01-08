package asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class ClassDecorationVisitor extends ClassVisitor {
	private int version;
	private int access;
	private String accessStr;
	private String name;
	private String signature;
	private String superName;
	private String[] interfaces;
	private boolean isInterface;
	private boolean isAbstract;
	
	public ClassDecorationVisitor(int arg0) {
		super(arg0);
	}
	
	public ClassDecorationVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){
		super.visit(version, access, name, signature, superName, interfaces);
		this.version = version;
		this.access = access;
		this.name = name;
		this.signature = signature;
		this.superName = superName;
		this.interfaces = interfaces;
		checkAccessCode();
	}

	public void checkAccessCode() {
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			this.accessStr = "public";
		}else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			this.accessStr = "private";
		}else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			this.accessStr = "protected";
		}else {
			this.accessStr = "default";
		}
		
		if ((access & Opcodes.ACC_INTERFACE) != 0) {
			this.isInterface = true;
			return;
		}
		
		if ((access & Opcodes.ACC_ABSTRACT) != 0) {
			this.isAbstract = true;
			return;
		}
	}
	
	public boolean isInterface() {
		return isInterface;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public int getVersion() {
		return version;
	}

	public String getAccess() {
		return accessStr;
	}

	public String getName() {
		return name;
	}

	public String getSignature() {
		return signature;
	}

	public String getSuperName() {
		return superName;
	}

	public String[] getInterfaces() {
		return interfaces;
	}
}
