package visitors;

import java.util.ArrayList;
import java.util.Map;

import org.objectweb.asm.MethodVisitor;

public class ClazzMethodVisitor extends MethodVisitor {

	private Map<String, ArrayList<String>> methodsPairToType;
	private String name;
	private Map<String, ArrayList<String>> methodCallInOrder;
	private ArrayList<String> owner;

	public ClazzMethodVisitor(int code, MethodVisitor visitor, String name, Map<String, ArrayList<String>> mtotype,
			Map<String, ArrayList<String>> methodcall, ArrayList<String> owner) {
		super(code, visitor);
		this.methodsPairToType = mtotype;
		this.name = name;
		this.methodCallInOrder = methodcall;
		this.owner = owner;
	}

	@Override
	public void visitMethodInsn(int opcodes, String owner, String name, String desc, boolean itf) {
		super.visitMethodInsn(opcodes, owner, name, desc, itf);
		
		this.owner.add(owner);
		
		if (methodCallInOrder.containsKey(owner)) {
			ArrayList<String> temp = methodCallInOrder.get(owner);
			temp.add(name);
			methodCallInOrder.replace(owner, temp);
		} else {
			ArrayList<String> newMethodList = new ArrayList<String>();
			newMethodList.add(name);
			methodCallInOrder.put(owner, newMethodList);
		}

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

	public Map<String, ArrayList<String>> getMethodsPairToType() {
		return methodsPairToType;
	}

}
