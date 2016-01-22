package toUMLimplement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Data.ClassnameData;

public class ComputeSeqDiagram {

	private Map<String, Map<String, ArrayList<String>>> owner;
	private Map<String, Map<String, ArrayList<String>>> methodCall;
	private ClassnameData cd;
	private String topText;
	private String text;

	public ComputeSeqDiagram(Map<String, Map<String, ArrayList<String>>> owner,
			Map<String, Map<String, ArrayList<String>>> methodCall, ClassnameData cd) {
		this.owner = owner;
		this.methodCall = methodCall;
		this.topText = "";
		this.text = "";
		this.cd = cd;
	}

	public String getText() {
		ArrayList<String> own = new ArrayList<String>();
		String classname = "";
		for (String c : owner.keySet()) {
			if (owner.get(c).containsKey("main")) {
				classname = c;
				own = owner.get(c).get("main");
				break;
			}
		}
		List<String> classnm = cd.getClassname();
		for (String o : own) {
			String temp = o.replace('/', '.');
			if (classnm.contains(temp)) {
				String tempComb = o + ":" + o + "[a]\n";
				if (!topText.contains(tempComb)) {
					topText = topText + o + ":" + o + "[a]\n";
				}
				for (String mc : methodCall.get(classname).keySet()) {
					ArrayList<String> metds = methodCall.get(classname).get(mc);
					String s = mc.replace('/', '.');
					if (classnm.contains(s)) {
						for (String m : metds) {
							if (!m.contains("init")) {
								String tp = o + ":" + mc + "." + m + "()" + "\n";
								if (!text.contains(tp)) {
									text = text + tp;
								}
							}
						}
					}
				}
			}
		}

		String t = topText + "\n" + text;
		return t;
	}
}
