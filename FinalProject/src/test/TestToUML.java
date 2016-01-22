package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import asm.ClassDecorationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import classes.AssociationClass;
import classes.ClassClass;
import classes.FieldClass;
import classes.MethodClass;
import classes.UsesClass;
import implementation.CodeASM;
import interfaces.IConnection;

public class TestToUML {

	@Test
	public void testCodeASM() {
		CodeASM ASMParser = new CodeASM("TestFiles.TestJavaCodeASMParsing");
		try {
			ASMParser.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ASMParser.getFields();
		ASMParser.getMethods();
		String classAccess = ASMParser.getClassAccess();
		String className = ASMParser.getClassName();
		String superclassNames = ASMParser.getSuperclassName();
		if (superclassNames.startsWith("java")) {
			superclassNames = null;
		}
		ASMParser.getClassInterfaces();
		boolean isInterface = ASMParser.isInterface();
		boolean isAbstract = ASMParser.isAbstract();

		assertEquals(classAccess, "public");
		assertEquals(className, "TestFiles/TestJavaCodeASMParsing");
		assertFalse(isInterface);
		assertFalse(isAbstract);
		assertEquals(superclassNames, null);
	}

	@Test
	public void testUsesClass() {
		CodeASM ASMParser = new CodeASM("TestFiles.TestJavaCodeASMParsing");
		try {
			ASMParser.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, FieldClass> fields = ASMParser.getFields();
		Map<String, MethodClass> methods = ASMParser.getMethods();
		ArrayList<String> Classnames = new ArrayList<String>();
		Classnames.add(ASMParser.getClassName());

		ArrayList<MethodClass> allmethods = new ArrayList<>();
		ArrayList<FieldClass> allfields = new ArrayList<>();

		for (String f : fields.keySet()) {
			allfields.add(fields.get(f));
		}

		for (String m : methods.keySet()) {
			allmethods.add(methods.get(m));
		}

		ClassClass newCC = new ClassClass(allmethods, allfields, ASMParser.getSuperclassName(),
				ASMParser.getClassInterfaces(), ASMParser.getClassAccess(), ASMParser.getClassName(),
				ASMParser.isInterface(), ASMParser.isAbstract());

		IConnection useTest = new UsesClass(newCC, Classnames);
		String edge = "    edge [\n\t style = \"dashed\"\n\t arrowhead = \"open\"\n    ]\n";
		String connections = "";
		assertEquals(useTest.getEdge(), edge);
		assertEquals(useTest.getConnection(), connections);
	}
	
	@Test
	public void testAssociationClass() {
		CodeASM ASMParser = new CodeASM("classes.ClassClass");
		try {
			ASMParser.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, FieldClass> fields = ASMParser.getFields();
		Map<String, MethodClass> methods = ASMParser.getMethods();
		ArrayList<String> Classnames = new ArrayList<String>();
		Classnames.add(ASMParser.getClassName());

		ArrayList<MethodClass> allmethods = new ArrayList<>();
		ArrayList<FieldClass> allfields = new ArrayList<>();

		for (String f : fields.keySet()) {
			allfields.add(fields.get(f));
		}

		for (String m : methods.keySet()) {
			allmethods.add(methods.get(m));
		}

		ClassClass newCC = new ClassClass(allmethods, allfields, ASMParser.getSuperclassName(),
				ASMParser.getClassInterfaces(), ASMParser.getClassAccess(), ASMParser.getClassName(),
				ASMParser.isInterface(), ASMParser.isAbstract());

		IConnection associationTest = new AssociationClass(newCC, Classnames);
		String edge = "    edge [\n\t style = \"solid\"\n\t arrowhead = \"open\"\n    ]\n";
		String connections = "";
		assertEquals(associationTest.getEdge(), edge);
		assertEquals(associationTest.getConnection(), connections);
	}

	@Test
	public void testMethodClass() throws IOException {
		ClassReader reader = new ClassReader("TestFiles.TestJavaCodeASMParsing");
		ClassVisitor visitor = new ClassDecorationVisitor(Opcodes.ASM5);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, visitor);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

		Map<String, MethodClass> methods = new HashMap<String, MethodClass>();
		methods = ((ClassMethodVisitor) methodVisitor).getMethodsInfoCollection();
		ArrayList<String> methodsName = new ArrayList<String>();
		methodsName.add("getA");
		methodsName.add("getB");
		methodsName.add("getC");
		methodsName.add("print");
		
		assertEquals(methodsName.size(), methods.keySet().size() - 1);
		for (String k : methods.keySet()) {
			if (!methods.get(k).getName().contains("init")) {
				assertTrue(methodsName.contains(methods.get(k).getName()));
				methodsName.remove(methods.get(k).getName());
			}
		}
	}
}
