package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import Data.DesignPatternData;
import asm.ClassDecorationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import classes.ClassClass;
import classes.FieldClass;
import classes.MethodClass;
import designPatterns.AdapterDetect;
import designPatterns.DecoratorDetect;
import designPatterns.SingletonDetect;
import interfaces.IData;
import interfaces.IDesignPattern;

public class TestDecoratorAdapter {

	@Test
	public void testDecryptionInputStream() throws IOException {
		IData<IDesignPattern> dpd = new DesignPatternData(2, 2, 0);
		dpd.initialize(true, null);
		
		ClassReader cr = new ClassReader("testfile2.DecryptionInputStream");
		ClassVisitor visitor = new ClassDecorationVisitor(Opcodes.ASM5);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, visitor);
		ClassMethodVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
		cr.accept(cmv, ClassReader.EXPAND_FRAMES);
		List<MethodClass> allms = ((ClassMethodVisitor) cmv).getAllMethodsInfo();
		Map<String, FieldClass> fields = ((ClassFieldVisitor) fieldVisitor).getFieldInfoCollection();

		List<FieldClass> fs = new ArrayList<FieldClass>();
		for (String f : fields.keySet()) {
			fs.add(fields.get(f));
		}
		
		ClassClass newCC = new ClassClass(allms, fs, ((ClassDecorationVisitor) visitor).getSuperName(),
				((ClassDecorationVisitor) visitor).getInterfaces(),
				((ClassDecorationVisitor) visitor).getAccess(), ((ClassDecorationVisitor) visitor).getName(),
				((ClassDecorationVisitor) visitor).isInterface(),
				((ClassDecorationVisitor) visitor).isAbstract(), dpd);
		
		SingletonDetect sdd = new SingletonDetect(newCC);
		DecoratorDetect ddd = new DecoratorDetect(newCC);
		AdapterDetect add = new AdapterDetect(newCC);
		sdd.setIncludejava(true);
		ddd.setIncludejava(true);
		add.setIncludejava(true);
		
		boolean issingle = sdd.detectPattern(allms, fs);
		boolean isdecorator = ddd.detectPattern(allms, fs);
		boolean isadapter = add.detectPattern(allms, fs);
		assertFalse(issingle);
		assertTrue(isdecorator);
		assertTrue(isadapter);
	}
	
	@Test
	public void testIteratorToEnumerationAdapter() throws IOException {
		IData<IDesignPattern> dpd = new DesignPatternData(2, 2, 0);
		dpd.initialize(true, null);
		
		ClassReader cr = new ClassReader("problem.client.IteratorToEnumerationAdapter");
		ClassVisitor visitor = new ClassDecorationVisitor(Opcodes.ASM5);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, visitor);
		ClassMethodVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
		cr.accept(cmv, ClassReader.EXPAND_FRAMES);
		List<MethodClass> allms = ((ClassMethodVisitor) cmv).getAllMethodsInfo();
		Map<String, FieldClass> fields = ((ClassFieldVisitor) fieldVisitor).getFieldInfoCollection();

		List<FieldClass> fs = new ArrayList<FieldClass>();
		for (String f : fields.keySet()) {
			fs.add(fields.get(f));
		}
		
		ClassClass newCC = new ClassClass(allms, fs, ((ClassDecorationVisitor) visitor).getSuperName(),
				((ClassDecorationVisitor) visitor).getInterfaces(),
				((ClassDecorationVisitor) visitor).getAccess(), ((ClassDecorationVisitor) visitor).getName(),
				((ClassDecorationVisitor) visitor).isInterface(),
				((ClassDecorationVisitor) visitor).isAbstract(), dpd);
		
		SingletonDetect sdd = new SingletonDetect(newCC);
		DecoratorDetect ddd = new DecoratorDetect(newCC);
		AdapterDetect add = new AdapterDetect(newCC);
		sdd.setIncludejava(true);
		ddd.setIncludejava(true);
		add.setIncludejava(true);
		
		boolean issingle = sdd.detectPattern(allms, fs);
		boolean isdecorator = ddd.detectPattern(allms, fs);
		boolean isadapter = add.detectPattern(allms, fs);
		assertFalse(issingle);
		assertFalse(isdecorator);
		assertTrue(isadapter);
	}
	
	@Test
	public void testInputStreamReader() throws IOException {
		IData<IDesignPattern> dpd = new DesignPatternData(2, 2, 0);
		dpd.initialize(true, null);
		
		ClassReader cr = new ClassReader("java.io.InputStreamReader");
		ClassVisitor visitor = new ClassDecorationVisitor(Opcodes.ASM5);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, visitor);
		ClassMethodVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
		cr.accept(cmv, ClassReader.EXPAND_FRAMES);
		List<MethodClass> allms = ((ClassMethodVisitor) cmv).getAllMethodsInfo();
		Map<String, FieldClass> fields = ((ClassFieldVisitor) fieldVisitor).getFieldInfoCollection();

		List<FieldClass> fs = new ArrayList<FieldClass>();
		for (String f : fields.keySet()) {
			fs.add(fields.get(f));
		}
		
		ClassClass newCC = new ClassClass(allms, fs, ((ClassDecorationVisitor) visitor).getSuperName(),
				((ClassDecorationVisitor) visitor).getInterfaces(),
				((ClassDecorationVisitor) visitor).getAccess(), ((ClassDecorationVisitor) visitor).getName(),
				((ClassDecorationVisitor) visitor).isInterface(),
				((ClassDecorationVisitor) visitor).isAbstract(), dpd);
		
		SingletonDetect sdd = new SingletonDetect(newCC);
		DecoratorDetect ddd = new DecoratorDetect(newCC);
		AdapterDetect add = new AdapterDetect(newCC);
		sdd.setIncludejava(true);
		ddd.setIncludejava(true);
		add.setIncludejava(true);
		
		boolean issingle = sdd.detectPattern(allms, fs);
		boolean isdecorator = ddd.detectPattern(allms, fs);
		boolean isAdapter = add.detectPattern(allms, fs);
		assertFalse(issingle);
		assertFalse(isdecorator);
		assertTrue(isAdapter);
	}
	
	@Test
	public void testOuputStreamWriter() throws IOException {
		IData<IDesignPattern> dpd = new DesignPatternData(2, 2, 0);
		dpd.initialize(true, null);
		
		ClassReader cr = new ClassReader("java.io.OutputStreamWriter");
		ClassVisitor visitor = new ClassDecorationVisitor(Opcodes.ASM5);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, visitor);
		ClassMethodVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
		cr.accept(cmv, ClassReader.EXPAND_FRAMES);
		List<MethodClass> allms = ((ClassMethodVisitor) cmv).getAllMethodsInfo();
		Map<String, FieldClass> fields = ((ClassFieldVisitor) fieldVisitor).getFieldInfoCollection();

		List<FieldClass> fs = new ArrayList<FieldClass>();
		for (String f : fields.keySet()) {
			fs.add(fields.get(f));
		}
		
		ClassClass newCC = new ClassClass(allms, fs, ((ClassDecorationVisitor) visitor).getSuperName(),
				((ClassDecorationVisitor) visitor).getInterfaces(),
				((ClassDecorationVisitor) visitor).getAccess(), ((ClassDecorationVisitor) visitor).getName(),
				((ClassDecorationVisitor) visitor).isInterface(),
				((ClassDecorationVisitor) visitor).isAbstract(), dpd);
		
		SingletonDetect sdd = new SingletonDetect(newCC);
		DecoratorDetect ddd = new DecoratorDetect(newCC);
		AdapterDetect add = new AdapterDetect(newCC);
		sdd.setIncludejava(true);
		ddd.setIncludejava(true);
		add.setIncludejava(true);
		
		boolean issingle = sdd.detectPattern(allms, fs);
		boolean isdecorator = ddd.detectPattern(allms, fs);
		boolean isAdapter = add.detectPattern(allms, fs);
		assertFalse(issingle);
		assertFalse(isdecorator);
		assertTrue(isAdapter);
	}
	
	@Test
	public void testMouseAdapter() throws IOException {
		IData<IDesignPattern> dpd = new DesignPatternData(2, 2, 0);
		dpd.initialize(true, null);
		
		ClassReader cr = new ClassReader("java.awt.event.MouseAdapter");
		ClassVisitor visitor = new ClassDecorationVisitor(Opcodes.ASM5);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, visitor);
		ClassMethodVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
		cr.accept(cmv, ClassReader.EXPAND_FRAMES);
		List<MethodClass> allms = ((ClassMethodVisitor) cmv).getAllMethodsInfo();
		Map<String, FieldClass> fields = ((ClassFieldVisitor) fieldVisitor).getFieldInfoCollection();

		List<FieldClass> fs = new ArrayList<FieldClass>();
		for (String f : fields.keySet()) {
			fs.add(fields.get(f));
		}
		
		ClassClass newCC = new ClassClass(allms, fs, ((ClassDecorationVisitor) visitor).getSuperName(),
				((ClassDecorationVisitor) visitor).getInterfaces(),
				((ClassDecorationVisitor) visitor).getAccess(), ((ClassDecorationVisitor) visitor).getName(),
				((ClassDecorationVisitor) visitor).isInterface(),
				((ClassDecorationVisitor) visitor).isAbstract(), dpd);
		
		SingletonDetect sdd = new SingletonDetect(newCC);
		DecoratorDetect ddd = new DecoratorDetect(newCC);
		AdapterDetect add = new AdapterDetect(newCC);
		sdd.setIncludejava(true);
		ddd.setIncludejava(true);
		add.setIncludejava(true);
		
		boolean issingle = sdd.detectPattern(allms, fs);
		boolean isdecorator = ddd.detectPattern(allms, fs);
		boolean isAdapter = add.detectPattern(allms, fs);
		assertFalse(issingle);
		assertFalse(isdecorator);
		assertFalse(isAdapter);
	}
	
	@Test
	public void testBufferedReader() throws IOException {
		IData<IDesignPattern> dpd = new DesignPatternData(2, 2, 0);
		dpd.initialize(true, null);
		
		ClassReader cr = new ClassReader("java.io.BufferedReader");
		ClassVisitor visitor = new ClassDecorationVisitor(Opcodes.ASM5);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, visitor);
		ClassMethodVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
		cr.accept(cmv, ClassReader.EXPAND_FRAMES);
		List<MethodClass> allms = ((ClassMethodVisitor) cmv).getAllMethodsInfo();
		Map<String, FieldClass> fields = ((ClassFieldVisitor) fieldVisitor).getFieldInfoCollection();

		List<FieldClass> fs = new ArrayList<FieldClass>();
		for (String f : fields.keySet()) {
			fs.add(fields.get(f));
		}
		
		ClassClass newCC = new ClassClass(allms, fs, ((ClassDecorationVisitor) visitor).getSuperName(),
				((ClassDecorationVisitor) visitor).getInterfaces(),
				((ClassDecorationVisitor) visitor).getAccess(), ((ClassDecorationVisitor) visitor).getName(),
				((ClassDecorationVisitor) visitor).isInterface(),
				((ClassDecorationVisitor) visitor).isAbstract(), dpd);
		
		SingletonDetect sdd = new SingletonDetect(newCC);
		DecoratorDetect ddd = new DecoratorDetect(newCC);
		AdapterDetect add = new AdapterDetect(newCC);
		sdd.setIncludejava(true);
		ddd.setIncludejava(true);
		add.setIncludejava(true);
		
//		boolean issingle = sdd.detectPattern(allms, fs);
		boolean isdecorator = ddd.detectPattern(allms, fs);
		boolean isAdapter = add.detectPattern(allms, fs);
//		assertFalse(issingle);
		assertFalse(isdecorator);
		assertFalse(isAdapter);
	}
}
