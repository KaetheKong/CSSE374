package interfaces;

import java.util.List;

public interface IMethod {
	public String getName();
	public String getAccess();
	public String getReturnType();
	public String[] getExceptions();
	public String getCode();
	public List<String> getParameters();
	public String getSignature();
	public List<String> parseSignature();
}
