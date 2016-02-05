package Data;

import java.util.ArrayList;
import java.util.List;

import interfaces.IDesignPattern;

public class DesignPatternData {
	
	private List<IDesignPattern> allDesignPatterns;
	
	public DesignPatternData() {
		this.allDesignPatterns = new ArrayList<IDesignPattern>();
	}
	
	public void add(IDesignPattern idp) {
		this.allDesignPatterns.add(idp);
	}
	
	public void remove(IDesignPattern idp) {
		this.allDesignPatterns.remove(idp);
	}

	public List<IDesignPattern> getAllDesignPatterns() {
		return allDesignPatterns;
	}
	
	public IDesignPattern getIDP(String name) {
		for (IDesignPattern idp : this.allDesignPatterns) {
			if (idp.getName().equals(name)) {
				return idp;
			}
		}
		return null;
	}

}
