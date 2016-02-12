package asm;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import classes.FieldClass;

public class ClassFieldVisitor extends ClassVisitor {

	private Map<String, FieldClass> fieldInfoCollection;

	public ClassFieldVisitor(int arg0) {
		super(arg0);
		this.fieldInfoCollection = new HashMap<String, FieldClass>();
	}

	public ClassFieldVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
		this.fieldInfoCollection = new HashMap<String, FieldClass>();
	}

	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorats = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		String accessStr = checkAccessStr(access);
		FieldClass newField = new FieldClass(name, accessStr, type, value, signature);
		this.fieldInfoCollection.put(name, newField);
		
		return toDecorats;
	}

	public Map<String, FieldClass> getFieldInfoCollection() {
		return fieldInfoCollection;
	}

	public String checkAccessStr(int access) {
		String str = "";
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			str = "public";
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			str = "private";
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			str = "protected";
		} else if ((access & Opcodes.ACC_FINAL) != 0) {
			str = "private final";
		} else if ((access & Opcodes.NULL) != 0) {
			str = "private";
		} else {
			str = "none";
		}

		if ((access & Opcodes.ACC_STATIC) != 0) {
			str += "_static";
		}
		return str;
	}
}
