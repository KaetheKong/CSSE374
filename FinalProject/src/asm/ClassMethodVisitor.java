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

public class ClassMethodVisitor extends ClassVisitor {

	private Map<String, MethodClass> methodsInfoCollection;

	public ClassMethodVisitor(int arg0) {
		super(arg0);
		this.methodsInfoCollection = new HashMap<String, MethodClass>();
	}

	public ClassMethodVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
		this.methodsInfoCollection = new HashMap<String, MethodClass>();
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

		MethodClass newMethod = new MethodClass(name, accessString, returnType, exception, null, sTypes);
		this.methodsInfoCollection.put(name, newMethod);

		return toDecorates;
	}

	public Map<String, MethodClass> getMethodsInfoCollection() {
		return methodsInfoCollection;
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

}
