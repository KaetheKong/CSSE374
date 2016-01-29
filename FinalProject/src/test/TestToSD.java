package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import classes.MethodClass;
import implementation.CodeASM;
import toUMLimplement.ComputeSeqDiagram;

public class TestToSD {

	@Test
	public void testdep1() throws IOException {
		int d = 1;
		CodeASM ASMParser = new CodeASM("TestFiles.TestJavaCodeASMParsing");
		ASMParser.run(true);
		Map<String, MethodClass> methods = ASMParser.getMethods();
		List<MethodClass> allMethods = ASMParser.getAllMethodsinfo();
		String methodname = "getC";
		String[] parameters = { "int", "int" };

		for (MethodClass mc : allMethods) {
			String mcname = mc.getName();
			List<String> params = mc.getParameters();
			if (mcname.equals(methodname) && params.size() == parameters.length) {
				for (int i = 0; i < parameters.length; i++) {
					for (int j = 0; j < params.size(); j++) {
						if (parameters[i].equals(params.get(j))) {
							params.remove(j);
						}
					}
				}
				if (params.isEmpty())
					methods.put(mcname, mc);
			}
		}

		ComputeSeqDiagram csd = new ComputeSeqDiagram(methodname, methods, true, d);
		csd.getText();

		String[] clssnames = { "TestFiles/TestJavaCodeASMParsing", "java/util/List" };
		List<String> cns = csd.getClassnames();

		assertEquals(clssnames.length, cns.size());
		for (int i = 0; i < clssnames.length; i++) {
			assertEquals(clssnames[i], cns.get(i));
		}
	}
}
