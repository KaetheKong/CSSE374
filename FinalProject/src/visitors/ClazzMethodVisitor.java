package visitors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import asm.ClassMethodVisitor;
import classes.MethodClass;

public class ClazzMethodVisitor extends MethodVisitor {

	private Map<String, ArrayList<String>> methodsPairToType;
	private String name;
	private Map<String, ArrayList<String>> methodCallInOrder;
	private ArrayList<String> owner;
	private MethodClass callfrom;
	private String access;
	private String[] exceptions;
	private ClassMethodVisitor cmv;

	public ClazzMethodVisitor(int code, MethodVisitor visitor, String name, Map<String, ArrayList<String>> mtotype,
			Map<String, ArrayList<String>> methodcall, ArrayList<String> owner, MethodClass callfrom, String access,
			String[] exceptions, ClassMethodVisitor cmv) {
		super(code, visitor);
		this.methodsPairToType = mtotype;
		this.name = name;
		this.methodCallInOrder = methodcall;
		this.owner = owner;
		this.callfrom = callfrom;
		this.access = access;
		this.exceptions = exceptions;
		this.cmv = cmv;
	}

	public MethodClass getCallfrom() {
		return callfrom;
	}

	@Override
	public void visitMethodInsn(int opcodes, String owner, String name, String desc, boolean itf) {
		super.visitMethodInsn(opcodes, owner, name, desc, itf);

		if (!this.owner.contains(owner))
			this.owner.add(owner);

		if (methodCallInOrder.containsKey(owner)) {
			ArrayList<String> temp = methodCallInOrder.get(owner);
			if (!temp.contains(name)) {
				temp.add(name);
			}
			methodCallInOrder.replace(owner, temp);
		} else {
			ArrayList<String> newMethodList = new ArrayList<String>();
			newMethodList.add(name);
			methodCallInOrder.put(owner, newMethodList);
		}

		String retType = Type.getReturnType(desc).getClassName();
		Type[] argType = Type.getArgumentTypes(desc);

		List<String> sTypes = new ArrayList<String>();

		for (Type t : argType) {
			sTypes.add(t.getClassName());
		}

		MethodClass subclass = new MethodClass(name, this.access, retType, this.exceptions, null, sTypes, null,
				this.callfrom, owner.replace('.', '/'));
		this.callfrom.addNeighbours(subclass);
		Map<String, MethodClass> temp = this.cmv.getMethodsInfoCollection();
		if (!temp.containsKey(name)) {
			temp.put(name, subclass);
		}
		this.cmv.setMethodsInfoCollection(temp);
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		super.visitTypeInsn(opcode, type);

		if (this.methodsPairToType.containsKey(this.name)) {
			ArrayList<String> temp = this.methodsPairToType.get(this.name);
			temp.add(type);
			this.methodsPairToType.replace(this.name, temp);
		} else {
			ArrayList<String> newMethod = new ArrayList<String>();
			newMethod.add(type);
			this.methodsPairToType.put(this.name, newMethod);
		}
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		super.visitFieldInsn(opcode, owner, name, desc);
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		super.visitVarInsn(opcode, var);
	}

	@Override
	public void visitCode() {
		super.visitCode();
	}

	public Map<String, ArrayList<String>> getMethodsPairToType() {
		return methodsPairToType;
	}

}
