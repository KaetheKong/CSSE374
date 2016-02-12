package Data;

import java.util.ArrayList;
import java.util.List;

import classes.ClassClass;
import designPatterns.AdapterDetect;
import designPatterns.CompositeDetect;
import designPatterns.DecoratorDetect;
import designPatterns.SingletonDetect;
import interfaces.IData;
import interfaces.IDesignPattern;

public class DesignPatternData implements IData<IDesignPattern>{
	
	private List<IDesignPattern> allDesignPatterns;
	private List<ClassClass> allClasses;
	
	public DesignPatternData() {
		this.allDesignPatterns = new ArrayList<IDesignPattern>();
		this.allClasses = new ArrayList<ClassClass>();
	}

	@Override
	public List<IDesignPattern> getData() {
		return this.allDesignPatterns;
	}

	@Override
	public void addData(IDesignPattern data) {
		this.allDesignPatterns.add(data);
		
	}

	@Override
	public void removeData(IDesignPattern data) {
		this.allDesignPatterns.remove(data);		
	}

	@Override
	public void initialize(boolean includeJava, ClassClass cc) {
		IDesignPattern sd = new SingletonDetect(null);
		IDesignPattern dd = new DecoratorDetect(null);
		IDesignPattern ad = new AdapterDetect(null);
		IDesignPattern compd = new CompositeDetect(null, this.allClasses);
		
		sd.setIncludejava(includeJava);
		dd.setIncludejava(includeJava);
		ad.setIncludejava(includeJava);
		compd.setIncludejava(includeJava);
		
		this.allDesignPatterns.add(sd);
		this.allDesignPatterns.add(dd);
		this.allDesignPatterns.add(ad);
		this.allDesignPatterns.add(compd);
		
	}

	@Override
	public IDesignPattern getEbyName(String name) {
		for (IDesignPattern idp : this.allDesignPatterns) {
			if (idp.getName().equals(name)) {
				return idp;
			}
		}
		return null;
	}

	@Override
	public void setAllClassData(List<ClassClass> ccs) {
		this.allClasses = ccs;
	}

}
