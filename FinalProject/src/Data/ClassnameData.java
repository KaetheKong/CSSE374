package Data;

import java.util.ArrayList;
import java.util.List;

import interfaces.IConnection;

public class ClassnameData {

	private List<String> classname;
	private List<IConnection> connections;

	public ClassnameData(List<String> classname) {
		this.setClassname(classname);
		this.connections = new ArrayList<IConnection>();
	}

	public void addConnections(IConnection connection) {
		this.connections.add(connection);
	}

	public List<String> getClassname() {
		return classname;
	}

	public void setClassname(List<String> classname) {
		this.classname = classname;
	}

	public List<IConnection> getConnections() {
		return connections;
	}
}
