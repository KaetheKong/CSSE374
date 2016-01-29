package interfaces;

import java.util.List;

import classes.FieldClass;
import classes.MethodClass;

public interface IDesignPattern {
	public String getDefaultReturnType();
	public List<String> getDefaultFields();
	public boolean detectPattern(List<MethodClass> methods, List<FieldClass> fields);
}
