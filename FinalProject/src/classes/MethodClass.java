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
	private MethodClass callfrom;
	private String clssnameCalledFrom;
	private List<MethodClass> neighbours;

	public MethodClass(String name, String access, String returnType, String[] exceptions, String code,
			List<String> paramterTypes, String signature, MethodClass callfrom, String classname) {
		this.name = name;
		this.access = access;
		this.returnType = returnType;
		this.exceptions = exceptions;
		this.code = code;
		this.parameters = paramterTypes;
		this.signature = signature;
		this.callfrom = callfrom;
		this.neighbours = new ArrayList<MethodClass>();
		this.clssnameCalledFrom = classname;
	}

	public String getClssnameCalledFrom() {
		return clssnameCalledFrom;
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
	
	public void addNeighbours(MethodClass x){
		this.neighbours.add(x);
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

	public MethodClass getCallfrom() {
		return callfrom;
	}

	public void setCallfrom(MethodClass callfrom) {
		this.callfrom = callfrom;
	}

	public List<MethodClass> getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(List<MethodClass> neighbours) {
		this.neighbours = neighbours;
	}

}
