package interfaces;

import java.util.List;

import classes.ClassClass;

public interface IData<T> {
	public List<T> getData();
	public void addData(T data);
	public void removeData(T data);
	public void initialize(boolean includeJava, ClassClass cc);
	public T getEbyName(String name);
	public void setAllClassData(List<ClassClass> ccs);
}
