package toUMLimplement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import Data.ClassnameData;
import asm.ClassDecorationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import classes.MethodClass;

public class ComputeSeqDiagram {

	private Map<String, Map<String, ArrayList<String>>> owner;
	private Map<String, Map<String, ArrayList<String>>> methodCall;
	private String topText;
	private String text;
	private String methodname;
	private Map<String, MethodClass> methods;

	public ComputeSeqDiagram(Map<String, Map<String, ArrayList<String>>> owner,
			Map<String, Map<String, ArrayList<String>>> methodCall, ClassnameData cd, String methodname,
			Map<String, MethodClass> methods, String[] parameters) {
		this.owner = owner;
		this.methodCall = methodCall;
		this.topText = "";
		this.text = "";
		this.methodname = methodname;
		this.methods = methods;
	}

	public String getText() throws IOException {
		digFiveDepth(this.methodname, this.owner, this.methodCall, 0, this.methods);
		String ret = topText + "\n" + text;
		return ret;
	}

	public void digFiveDepth(String method, Map<String, Map<String, ArrayList<String>>> owners,
			Map<String, Map<String, ArrayList<String>>> mthdCall, int count, Map<String, MethodClass> methods)
					throws IOException {
		if (count >= 5) {
			return;
		}

		if (methods.containsKey(method)) {
			MethodClass x = methods.get(method);
//			if (!x.getClssnameCalledFrom().startsWith("java")) {
				if (!topText.contains(x.getClssnameCalledFrom() + ":" + x.getClssnameCalledFrom())) {
					this.topText += x.getClssnameCalledFrom() + ":" + x.getClssnameCalledFrom() + "[a]\n";
				}

				if (!this.text
						.contains(x.getClssnameCalledFrom() + ":" + x.getClssnameCalledFrom() + "." + x.getName())) {
					String temp = "";
					if (!x.getReturnType().equals("void")) {
						if (x.getParameters().isEmpty()) {
							temp = x.getClssnameCalledFrom() + ":" + x.getReturnType() + "=" + x.getClssnameCalledFrom()
									+ "." + x.getName() + "()\n";
						} else {
							temp = x.getClssnameCalledFrom() + ":" + x.getReturnType() + "=" + x.getClssnameCalledFrom()
									+ "." + x.getName() + "(" + x.getParameters().toString().substring(1,
											x.getParameters().toString().length() - 1)
									+ ")\n";
						}
					} else {
						if (x.getParameters().isEmpty()) {
							temp = x.getClssnameCalledFrom() + ":" + x.getClssnameCalledFrom() + "." + x.getName()
									+ "()\n";
						} else {
							temp = x.getClssnameCalledFrom() + ":" + x.getClssnameCalledFrom() + "." + x.getName() + "("
									+ x.getParameters().toString().substring(1,
											x.getParameters().toString().length() - 1)
									+ ")\n";
						}
					}
					this.text += temp;
				}
//			}

			for (int i = 0; i < x.getNeighbours().size(); i++) {
				MethodClass t = x.getNeighbours().get(i);

				if (!topText.contains(t.getClssnameCalledFrom() + ":" + t.getClssnameCalledFrom())) {
					this.topText += t.getClssnameCalledFrom() + ":" + t.getClssnameCalledFrom() + "[a]\n";
				}

				if (!this.text
						.contains(x.getClssnameCalledFrom() + ":" + t.getClssnameCalledFrom() + "." + t.getName())) {
					String temp = "";
					if (!t.getReturnType().equals("void")) {
						if (!x.getParameters().isEmpty()) {
							temp = x.getClssnameCalledFrom() + ":" + t.getReturnType() + "=" + t.getClssnameCalledFrom()
									+ "." + t.getName() + "()\n";
						} else {
							temp = x.getClssnameCalledFrom() + ":" + t.getReturnType() + "=" + t.getClssnameCalledFrom()
									+ "." + t.getName() + "(" + t.getParameters().toString().substring(1,
											t.getParameters().toString().length() - 1)
									+ ")\n";
						}
					} else {
						if (!t.getParameters().isEmpty()) {
							temp = x.getClssnameCalledFrom() + ":" + t.getClssnameCalledFrom() + "." + t.getName()
									+ "()\n";
						} else {
							temp = x.getClssnameCalledFrom() + ":" + t.getClssnameCalledFrom() + "." + t.getName() + "("
									+ t.getParameters().toString().substring(1,
											t.getParameters().toString().length() - 1)
									+ ")\n";
						}
					}
					this.text += temp;
				}

				if (!t.getClssnameCalledFrom().startsWith("[L") /*&& !t.getClssnameCalledFrom().startsWith("java")*/) {
					ClassReader cr = new ClassReader(t.getClssnameCalledFrom());
					ClassVisitor visitor = new ClassDecorationVisitor(Opcodes.ASM5);
					ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, visitor);
					ClassMethodVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor,
							t.getClssnameCalledFrom(), x);
					cr.accept(cmv, ClassReader.EXPAND_FRAMES);
					Map<String, MethodClass> mds = ((ClassMethodVisitor) cmv).getMethodsInfoCollection();

					digFiveDepth(t.getName(), owners, mthdCall, count + 1, mds);
				}
			}
		}
	}

	public MethodClass selectCorrectMethod(Map<String, MethodClass> methods) {
		MethodClass test = methods.get(this.methodname);
		String sigs = test.getSignature();
		List<String> pms = parseSignaturesForParams(sigs);
		test.setParameters(pms);
		methods.put(test.getName(), test);

		return test;
	}

	public List<String> parseSignaturesForParams(String sig) {
		List<String> ret = new ArrayList<String>();
		String tsig = sig.substring(sig.indexOf('(') + 1, sig.indexOf(')'));
		String[] allsigs = tsig.split(";");

		for (int i = 0; i < allsigs.length; i++) {
			if (allsigs[i].startsWith("L")) {
				allsigs[i] = allsigs[i].substring(1);
			}
			ret.add(allsigs[i]);
		}

		return ret;
	}

}
