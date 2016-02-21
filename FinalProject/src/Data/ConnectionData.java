package Data;

import java.util.ArrayList;
import java.util.List;

import classes.AbstractClass;
import classes.AdapterClass;
import classes.AssociationClass;
import classes.ClassClass;
import classes.CompositeClass;
import classes.DecoratorClass;
import classes.InterfaceClass;
import classes.SingletonClass;
import classes.UsesClass;
import interfaces.IConnection;
import interfaces.IData;

public class ConnectionData implements IData<IConnection>{
	
	private List<IConnection> connections;
	private List<String> classnames;
	
	public ConnectionData(List<String> classnames) {
		this.classnames = new ArrayList<String>();
		this.connections = new ArrayList<IConnection>();
		this.classnames.addAll(classnames);
	}

	@Override
	public List<IConnection> getData() {
		return this.connections;
	}

	@Override
	public void addData(IConnection data) {
		this.connections.add(data);		
	}

	@Override
	public void removeData(IConnection data) {
		this.connections.remove(data);		
	}

	@Override
	public void initialize(boolean includeJava, ClassClass cc) {
		if (!this.connections.isEmpty()) {
			for (IConnection ic : this.connections) {
				ic.setCc(cc);
			}
		}
		IConnection interfaceclss = new InterfaceClass(cc);
		IConnection absClass = new AbstractClass(cc);
		IConnection usesClass = new UsesClass(cc, this.classnames);
		IConnection associationClass = new AssociationClass(cc, this.classnames, includeJava);
		IConnection singletonClass = new SingletonClass(cc);
		IConnection decoratorClass = new DecoratorClass(cc);
		IConnection adapterClass = new AdapterClass(cc);
		IConnection compositeClass = new CompositeClass(cc);

		interfaceclss.setIncludeJava(includeJava);
		absClass.setIncludeJava(includeJava);
		usesClass.setIncludeJava(includeJava);
		associationClass.setIncludeJava(includeJava);
		singletonClass.setIncludeJava(includeJava);
		decoratorClass.setIncludeJava(includeJava);
		adapterClass.setIncludeJava(includeJava);
		compositeClass.setIncludeJava(includeJava);
		
		this.connections.add(interfaceclss);
		this.connections.add(absClass);
		this.connections.add(usesClass);
		this.connections.add(associationClass);
		this.connections.add(singletonClass);
		this.connections.add(decoratorClass);
		this.connections.add(adapterClass);
		this.connections.add(compositeClass);
	}

	@Override
	public IConnection getEbyName(String name) {
		for (int i = 0; i < this.connections.size(); i++) {
			if (this.connections.get(i).getType().toLowerCase().equals(name.toLowerCase())) {
				return this.connections.get(i);
			}
		}
		return null;
	}

	@Override
	public void setAllClassData(List<ClassClass> ccs) {
	}

}
