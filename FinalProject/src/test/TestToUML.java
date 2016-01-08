package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import classes.FieldClass;
import classes.MethodClass;
import implementation.CodeASM;

public class TestToUML {

	@Test
	public void testCodeASM() {
		CodeASM ASMParser = new CodeASM("asm.TestJavaCodeASMParsing");
		try {
			ASMParser.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, FieldClass> fields = ASMParser.getFields();
		Map<String, MethodClass> methods = ASMParser.getMethods();
		String classAccess = ASMParser.getClassAccess();
		String className = ASMParser.getClassName();
		String superclassNames = ASMParser.getSuperclassName();
		if (superclassNames.startsWith("java")) {
			superclassNames = null;
		}
		String[] interfaceNames = ASMParser.getClassInterfaces();
		boolean isInterface = ASMParser.isInterface();
		boolean isAbstract = ASMParser.isAbstract();
		
		assertEquals(classAccess, "public");
		assertEquals(className, "asm/TestJavaCodeASMParsing");
		assertFalse(isInterface);
		assertFalse(isAbstract);
		assertEquals(superclassNames, null);
	}

}
