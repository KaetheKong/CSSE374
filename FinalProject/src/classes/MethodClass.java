package classes;

import java.util.ArrayList;
import java.util.List;

import interfaces.IMethod;

public class MethodClass implements IMethod {
	private String name;
	private String access;
	private String returnType;
	private String[] exceptions;
	private String code;
	private List<String> parameters;
	private String signature;

	public MethodClass(String name, String access, String returnType, String[] exceptions, String code, List<String> paramterTypes, String signature) {
		this.name = name;
		this.access = access;
		this.returnType = returnType;
		this.exceptions = exceptions;
		this.code = code;
		this.parameters = paramterTypes;
		this.signature = signature;
	}
	
	public List<String> parseSignature() {
		List<String> signatures = new ArrayList<String>();
		if (signature != null) {
			String[] signatureSplits = signature.split(";");
			for (String sig : signatureSplits) {
				String[] ss = sig.split("/");
				for (String s : ss) {
					signatures.add(s);
				}
			}
		}
		return signatures;
	}
	
	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	@Override
	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	@Override
	public String[] getExceptions() {
		return exceptions;
	}

	public void setExceptions(String[] exceptions) {
		this.exceptions = exceptions;
	}

	@Override
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public List<String> getParameters() {
		return parameters;
	}

	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String getSignature() {
		return this.signature;
	}

}
