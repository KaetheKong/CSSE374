package Data;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DesignPatternGenInfoData{
	
	private Map<String, Color> colorDPMapping;
	private Map<String, List<String>> dpchecker;
	private List<String> designPatternAgg;
	
	public DesignPatternGenInfoData() {
		this.colorDPMapping = new HashMap<String, Color>();
		this.dpchecker = new HashMap<String, List<String>>();
		this.designPatternAgg = new ArrayList<String>();
	}
	
	public void addColorMapping(String name, Color c) {
		this.colorDPMapping.put(name.toLowerCase(), c);
	}
	
	public void addDpchecker(String name, List<String> checklist) {
		this.dpchecker.put(name.toLowerCase(), checklist);
	}
	
	public void addAspectInDpChecker(String name, List<String> checklist) {
		if (this.dpchecker.containsKey(name.toLowerCase())) {
			this.dpchecker.get(name.toLowerCase()).addAll(checklist);
		} else {
			this.addDpchecker(name.toLowerCase(), checklist);
		}
	}
	
	public void addDpAgg(String name) {
		if (!this.designPatternAgg.contains(name.toLowerCase())) this.designPatternAgg.add(name.toLowerCase());
	}

	public Map<String, Color> getColorDPMapping() {
		return colorDPMapping;
	}

	public Map<String, List<String>> getDpchecker() {
		return dpchecker;
	}

	public List<String> getDesignPatternAgg() {
		return designPatternAgg;
	}
	
	public Color getColorbyName(String name) {
		if (this.colorDPMapping.get(name.toLowerCase()) != null) {
			return this.colorDPMapping.get(name.toLowerCase());
		} else {
			return Color.BLACK;
		}
	}
	
	public List<String> getAspectsbyName(String name) {
		if (this.dpchecker.get(name.toLowerCase()) != null) {
			return this.dpchecker.get(name.toLowerCase());
		} else {
			return new ArrayList<String>();
		}
	}
	
	public void initialize() {
		this.addColorMapping("singleton", Color.BLUE);
		this.addColorMapping("decorator", Color.GREEN);
		this.addColorMapping("adapter", Color.RED);
		this.addColorMapping("composite", Color.YELLOW);
		
		List<String> singletoncls = new ArrayList<String>();
		List<String> decoratorcls = new ArrayList<String>();
		List<String> adaptercls = new ArrayList<String>();
		List<String> compositecls = new ArrayList<String>();
		
		singletoncls.add("Singleton");
		decoratorcls.add("Decorator");
		decoratorcls.add("Component");
		adaptercls.add("Adaptee");
		adaptercls.add("Target");
		adaptercls.add("Adapter");
		compositecls.add("Component");
		compositecls.add("Composite");
		compositecls.add("Leaf");
		
		this.addAspectInDpChecker("singleton", singletoncls);
		this.addAspectInDpChecker("decorator", decoratorcls);
		this.addAspectInDpChecker("adapter", adaptercls);
		this.addAspectInDpChecker("composite", compositecls);
		
		this.addDpAgg("singleton");
		this.addDpAgg("decorator");
		this.addDpAgg("adapter");
		this.addDpAgg("composite");
	}
}
