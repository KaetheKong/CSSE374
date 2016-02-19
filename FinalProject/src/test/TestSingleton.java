package test;

import static org.junit.Assert.*;

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
import designPatterns.SingletonDetect;
import interfaces.IData;
import interfaces.IDesignPattern;

public class TestSingleton {

	@Test
	public void testRuntime() throws IOException {
		
		IData<IDesignPattern> dpd = new DesignPatternData(0, 0, 0);
		dpd.initialize(true, null);
		
		ClassReader cr = new ClassReader("java.lang.Runtime");
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
		
		boolean issingle = sdd.detectPattern(allms, fs);
		assertTrue(issingle);
	}
	
	@Test
	public void testCalendar() throws IOException {
		IData<IDesignPattern> dpd = new DesignPatternData(0, 0, 0);
		dpd.initialize(true, null);
		
		ClassReader cr = new ClassReader("java.util.Calendar");
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
		
		boolean issingle = sdd.detectPattern(allms, fs);
		assertFalse(issingle);
	}
	
	@Test
	public void testDesktop() throws IOException {
		
		IData<IDesignPattern> dpd = new DesignPatternData(0, 0, 0);
		dpd.initialize(true, null);
		
		ClassReader cr = new ClassReader("java.awt.Desktop");
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
		
		boolean issingle = sdd.detectPattern(allms, fs);
		assertTrue(issingle);
	}
	
	@Test
	public void testFilterInputStream() throws IOException {
		
		IData<IDesignPattern> dpd = new DesignPatternData(0, 0, 0);
		dpd.initialize(true, null);
		
		ClassReader cr = new ClassReader("java.io.FilterInputStream");
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
		
		boolean issingle = sdd.detectPattern(allms, fs);
		assertFalse(issingle);
	}
	
	@Test
	public void testChocolateBoiler() throws IOException {
		
		IData<IDesignPattern> dpd = new DesignPatternData(0, 0, 0);
		dpd.initialize(true, null);
		
		ClassReader cr = new ClassReader("TestFiles.ChocolateBoiler");
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
		
		boolean issingle = sdd.detectPattern(allms, fs);
		assertTrue(issingle);
	}

}
