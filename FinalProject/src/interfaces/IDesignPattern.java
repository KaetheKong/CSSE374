package interfaces;

import java.util.List;

import classes.ClassClass;
import classes.FieldClass;
import classes.MethodClass;

public interface IDesignPattern {
	public String getDefaultReturnType();
	public List<String> getDefaultFields();
	public boolean detectPattern(List<MethodClass> methods, List<FieldClass> fields);
	public String getName();
	public String getColorSetUp();
	public void setCc(ClassClass cc);
	public List<ClassClass> getInformation();
}
