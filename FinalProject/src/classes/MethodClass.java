package classes;

import java.util.List;

import interfaces.IMethod;

public class MethodClass implements IMethod {
	private String name;
	private String access;
	private String returnType;
	private String[] exceptions;
	private String code;
	private List<String> parameters;

	public MethodClass(String name, String access, String returnType, String[] exceptions, String code, List<String> paramterTypes) {
		this.name = name;
		this.access = access;
		this.returnType = returnType;
		this.exceptions = exceptions;
		this.code = code;
		this.parameters = paramterTypes;
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

}
