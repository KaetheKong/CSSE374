package interfaces;

import java.util.List;

public interface IField {
	public String getName();
	public String getAccess();
	public String getFieldtype();
	public Object getDefaultValue();
	public String getSignatures();
	public List<String> parseSignature();
}
