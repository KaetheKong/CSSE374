package interfaces;

import java.util.List;

import classes.FieldClass;
import classes.MethodClass;

public interface IClass {
	public String getClassname();
	public List<MethodClass> getMethods();
	public List<FieldClass> getFields();
	public String getSuperclassname();
	public String[] getInterfacesname();
	public String getAccess();	
}
