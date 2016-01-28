package asm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import classes.MethodClass;
import visitors.ClazzMethodVisitor;

public class ClassMethodVisitor extends ClassVisitor {

	private Map<String, MethodClass> methodsInfoCollection;
	private List<MethodClass> allMethodsInfo;
	private Map<String, ArrayList<String>> mtotype;
	private Map<String, ArrayList<String>> methodCall;
	private Map<String, ArrayList<String>> owner;
	private String classnameCalledFrom;
	private MethodClass mc;

	public ClassMethodVisitor(int arg0) {
		super(arg0);
		this.methodsInfoCollection = new HashMap<String, MethodClass>();
		this.allMethodsInfo = new ArrayList<MethodClass>();
		this.mtotype = new HashMap<String, ArrayList<String>>();
		this.methodCall = new HashMap<String, ArrayList<String>>();
		this.owner = new HashMap<String, ArrayList<String>>();
		this.classnameCalledFrom = null;
		this.mc = null;
	}

	public ClassMethodVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
		this.methodsInfoCollection = new HashMap<String, MethodClass>();
		this.allMethodsInfo = new ArrayList<MethodClass>();
		this.mtotype = new HashMap<String, ArrayList<String>>();
		this.methodCall = new HashMap<String, ArrayList<String>>();
		this.owner = new HashMap<String, ArrayList<String>>();
		this.classnameCalledFrom = null;
		this.mc = null;
	}

	public ClassMethodVisitor(int arg0, ClassVisitor arg1, String classname) {
		super(arg0, arg1);
		this.methodsInfoCollection = new HashMap<String, MethodClass>();
		this.allMethodsInfo = new ArrayList<MethodClass>();
		this.mtotype = new HashMap<String, ArrayList<String>>();
		this.methodCall = new HashMap<String, ArrayList<String>>();
		this.owner = new HashMap<String, ArrayList<String>>();
		this.classnameCalledFrom = classname;
		this.mc = null;
	}

	public ClassMethodVisitor(int arg0, ClassVisitor arg1, String classname, MethodClass mc) {
		super(arg0, arg1);
		this.methodsInfoCollection = new HashMap<String, MethodClass>();
		this.allMethodsInfo = new ArrayList<MethodClass>();
		this.mtotype = new HashMap<String, ArrayList<String>>();
		this.methodCall = new HashMap<String, ArrayList<String>>();
		this.owner = new HashMap<String, ArrayList<String>>();
		this.classnameCalledFrom = classname;
		this.mc = mc;
	}

	public Map<String, ArrayList<String>> getMethodCall() {
		return this.methodCall;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exception) {
		MethodVisitor toDecorates = super.visitMethod(access, name, desc, signature, exception);
		String accessString = checkAccessStr(access);
		String returnType = Type.getReturnType(desc).getClassName();
		Type[] argType = Type.getArgumentTypes(desc);
		List<String> sTypes = new ArrayList<String>();

		for (Type t : argType) {
			sTypes.add(t.getClassName());
		}

		if (!this.owner.containsKey(name)) {
			ArrayList<String> t = new ArrayList<String>();
			this.owner.put(name, t);
		}

		MethodClass newMethod = new MethodClass(name, accessString, returnType, exception, null, sTypes, signature,
				this.mc, this.classnameCalledFrom.replace('.', '/'));

		this.methodsInfoCollection.put(name, newMethod);
		this.allMethodsInfo.add(newMethod);

		MethodVisitor mv = new ClazzMethodVisitor(Opcodes.ASM5, toDecorates, name, this.mtotype, this.methodCall,
				this.owner.get(name), newMethod, checkAccessStr(access), exception, this);
		return mv;
	}

	public void setMethodsInfoCollection(Map<String, MethodClass> methodsInfoCollection) {
		this.methodsInfoCollection = methodsInfoCollection;
	}

	public Map<String, ArrayList<String>> getOwner() {
		return owner;
	}

	public Map<String, MethodClass> getMethodsInfoCollection() {
		return methodsInfoCollection;
	}

	public Map<String, ArrayList<String>> getMethodsToType() {
		return this.mtotype;
	}

	public String checkAccessStr(int access) {
		String str = "";
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			str = "public";
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			str = "private";
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			str = "protected";
		} else {
			str = "default";
		}

		return str;
	}

	public MethodClass getMethod() {
		MethodClass x = null;
		for (String k : this.methodsInfoCollection.keySet()) {
			x = this.methodsInfoCollection.get(k);
		}
		return x;
	}
	
	public List<MethodClass> getAllMethodsInfo() {
		return allMethodsInfo;
	}
}
