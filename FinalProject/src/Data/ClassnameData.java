package Data;

import java.util.ArrayList;
import java.util.List;

import classes.ClassClass;
import interfaces.IData;

public class ClassnameData implements IData<String>{

	private List<String> classname;

	public ClassnameData(List<String> classname) {
		this.classname = new ArrayList<>();
	}

	@Override
	public List<String> getData() {
		return this.classname;
	}

	@Override
	public void addData(String data) {
		this.classname.add(data);		
	}

	@Override
	public void removeData(String data) {
		this.classname.remove(data);
	}

	@Override
	public void initialize(boolean includeJava, ClassClass cc) {
		// do nothing
	}

	@Override
	public String getEbyName(String name) {
		return name;
	}

	@Override
	public void setAllClassData(List<ClassClass> ccs) {
	}
}
