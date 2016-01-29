package toUMLimplement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import asm.ClassDecorationVisitor;
import asm.ClassFieldVisitor;
import asm.ClassMethodVisitor;
import classes.MethodClass;

public class ComputeSeqDiagram {

	private String topText;
	private String text;
	private String methodname;
	private Map<String, MethodClass> methods;
	private List<String> classnames;
	private boolean includeJava;
	private int depth;

	public ComputeSeqDiagram(String methodname, Map<String, MethodClass> methods, boolean includeJava, int depth) {
		this.topText = "";
		this.text = "";
		this.methodname = methodname;
		this.methods = methods;
		this.includeJava = includeJava;
		this.depth = depth;
		this.classnames = new ArrayList<String>();
	}

	public String getText() throws IOException {
		digFiveDepth(this.methodname, 0, this.methods);
		String ret = topText + "\n" + text;
		return ret;
	}

	public void digFiveDepth(String method, int count, Map<String, MethodClass> methods) throws IOException {
		if (count >= this.depth) {
			return;
		}

		if (methods.containsKey(method)) {
			MethodClass x = methods.get(method);
			if ((!x.getClssnameCalledFrom().startsWith("java") && !x.getClssnameCalledFrom().startsWith("org"))
					|| this.includeJava) {
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
				if (!classnames.contains(x.getClssnameCalledFrom())) {
					this.classnames.add(x.getClssnameCalledFrom());
				}
			}

			for (int i = 0; i < x.getNeighbours().size(); i++) {
				MethodClass t = x.getNeighbours().get(i);

				if ((!t.getClssnameCalledFrom().startsWith("java") && !t.getClssnameCalledFrom().startsWith("org"))
						|| this.includeJava) {

					if (!topText.contains(t.getClssnameCalledFrom() + ":" + t.getClssnameCalledFrom())) {
						this.topText += t.getClssnameCalledFrom() + ":" + t.getClssnameCalledFrom() + "[a]\n";
					}

					if (!this.text.contains(
							x.getClssnameCalledFrom() + ":" + t.getClssnameCalledFrom() + "." + t.getName())) {
						String temp = "";
						if (!t.getReturnType().equals("void")) {
							if (!x.getParameters().isEmpty()) {
								temp = x.getClssnameCalledFrom() + ":" + t.getReturnType() + "="
										+ t.getClssnameCalledFrom() + "." + t.getName() + "()\n";
							} else {
								temp = x.getClssnameCalledFrom() + ":" + t.getReturnType() + "="
										+ t.getClssnameCalledFrom() + "." + t.getName() + "(" + t.getParameters()
												.toString().substring(1, t.getParameters().toString().length() - 1)
										+ ")\n";
							}
						} else {
							if (!t.getParameters().isEmpty()) {
								temp = x.getClssnameCalledFrom() + ":" + t.getClssnameCalledFrom() + "." + t.getName()
										+ "()\n";
							} else {
								temp = x.getClssnameCalledFrom() + ":"
										+ t.getClssnameCalledFrom() + "." + t.getName() + "(" + t.getParameters()
												.toString().substring(1, t.getParameters().toString().length() - 1)
										+ ")\n";
							}
						}
						this.text += temp;
					}

					if (!t.getClssnameCalledFrom().startsWith("[L")) {
						ClassReader cr = new ClassReader(t.getClssnameCalledFrom());
						ClassVisitor visitor = new ClassDecorationVisitor(Opcodes.ASM5);
						ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, visitor);
						ClassMethodVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor,
								t.getClssnameCalledFrom(), x);
						cr.accept(cmv, ClassReader.EXPAND_FRAMES);
						Map<String, MethodClass> mds = ((ClassMethodVisitor) cmv).getMethodsInfoCollection();
						List<MethodClass> allms = ((ClassMethodVisitor) cmv).getAllMethodsInfo();
						MethodClass mctemp = this.findCorrectMethodClass(t, allms);
						if (mctemp != null) {
							mds.put(mctemp.getName(), mctemp);
						}
						if (!classnames.contains(t.getClssnameCalledFrom())) {
							this.classnames.add(t.getClssnameCalledFrom());
						}
						digFiveDepth(t.getName(), count + 1, mds);
					}
				}
			}
		}
	}

	public List<String> getClassnames() {
		return classnames;
	}

	public MethodClass findCorrectMethodClass(MethodClass toMatch, List<MethodClass> allmethod) {
		for (MethodClass mc : allmethod) {
			String mcname = mc.getName();
			List<String> params = mc.getParameters();
			if (mcname.equals(toMatch.getName()) && params.size() == toMatch.getParameters().size()) {
				for (int i = 0; i < toMatch.getParameters().size(); i++) {
					for (int j = 0; j < params.size(); j++) {
						if (toMatch.getParameters().get(j).equals(params.get(j))) {
							params.remove(j);
						}
					}
				}
				if (params.isEmpty())
					return mc;
			}
		}

		return null;
	}

}
