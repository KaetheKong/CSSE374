package toUMLimplement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Data.ClassnameData;
import implementation.CodeASM;

public class ComputeSeqDiagram {

	private Map<String, Map<String, ArrayList<String>>> owner;
	private Map<String, Map<String, ArrayList<String>>> methodCall;
	private String topText;
	private String text;
	private String methodname;

	public ComputeSeqDiagram(Map<String, Map<String, ArrayList<String>>> owner,
			Map<String, Map<String, ArrayList<String>>> methodCall, ClassnameData cd, String methodname) {
		this.owner = owner;
		this.methodCall = methodCall;
		this.topText = "";
		this.text = "";
		this.methodname = methodname;
	}

	public String getText() throws IOException {
		String classname = "";
		for (String c : owner.keySet()) {
			if (owner.get(c).containsKey(this.methodname)) {
				classname = c;
				String tmp = c.replace('.', '/');
				topText = topText + tmp + ":" + tmp + "[a]\n";
				text = text + tmp + ":" + tmp + "." + this.methodname + "()\n";
				break;
			}
		}
//		findAllMethodCallsFromMethod(classname);
		digFiveDepth(classname, this.methodname, this.owner, this.methodCall, 0);
		String t = topText + "\n" + text;
		return t;
	}

//	private void findAllMethodCallsFromMethod(String o, String method) {
//		String own = o;
//		ArrayList<String> ownersmethods = new ArrayList<String>();
//
//		if (this.owner.get(own).containsKey(own.replace('/', '.'))) {
//			ownersmethods = owners.get(own.replace('/', '.')).get(method);
//		}
//		
//		CodeASM ASMParser = new CodeASM(ow);
//		ASMParser.run();
//		
//		owns.put(ow, ASMParser.getOwner());
//		methods.put(ow, ASMParser.getMethodcalls());
//	}

	public void digFiveDepth(String o, String method, Map<String, Map<String, ArrayList<String>>> owners,Map<String, Map<String, ArrayList<String>>> mthdCall, int count) throws IOException {
		String own = o;
		ArrayList<String> ownersmethods = new ArrayList<String>();
		if (count == 5)
			return;
		if (owners.containsKey(own.replace('/', '.'))) {
			ownersmethods = owners.get(own.replace('/', '.')).get(method);
		}

		for (int i = 0; i < ownersmethods.size(); i++) {
			String ow = ownersmethods.get(i);
			String tempComb = "/" + ow + ":" + ow + "\n";
			String com = ow + ":" + ow ;
			String txt = o.replace('.', '/') + ":" + ow + ".new\n";
			if (!topText.contains(tempComb) && !topText.contains(com)) {
				topText = topText + tempComb;
				if (!text.contains(txt)) {
					text = text + txt;
				}
			}
			
			Map<String, Map<String, ArrayList<String>>> owns = new HashMap<String, Map<String, ArrayList<String>>>();
			Map<String, Map<String, ArrayList<String>>> methods = new HashMap<String, Map<String, ArrayList<String>>>();
			
			System.out.println(ow);
			CodeASM ASMParser = new CodeASM(ow);
			ASMParser.run();
			
			owns.put(ow.replace('/', '.'), ASMParser.getOwner());
			methods.put(ow.replace('/', '.'), ASMParser.getMethodcalls());
			
			String mc = ow;
			ArrayList<String> metds = mthdCall.get(own.replace('/', '.')).get(mc);
			mc.replace('/', '.');
			for (int j = 0; j < metds.size(); j++) {
				String m = metds.get(j);
				if (!m.contains("init")) {
					String tp = ow.replace('.', '/') + ":" + mc + "." + m + "()" + "\n";
					String str = ow.replace('.', '/') + ":" + mc + ".new\n";
					String str2 = mc + ":" + mc + "[a]\n";
					String str3 = mc + ":" + mc ;
					if (!text.contains(mc + ".new")) {
						text = text + str;
					}
					if (!topText.contains(str3)) {
						topText = topText + str2;
					}
					if (!text.contains(tp)) {
						text = text + tp;
					}
					digFiveDepth(mc, m, owns, methods, count + 1);
				} else {
					String tp = ow.replace('.', '/') + ":" + mc + "[a]" + "\n";
					if (!topText.contains(tp)) {
						topText = topText + tp;
					}
				}
			}
		}
	}
}
