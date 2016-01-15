package toUMLimplement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Data.ClassnameData;
import interfaces.IConnection;

public class ComputeUMLConnection {
	private String connection;
	private ClassnameData cd;
	
	public ComputeUMLConnection(ClassnameData cd) {
		this.cd = cd;
		this.connection = "";
	}
	
	public void findConnection() {
		List<IConnection> connects = cd.getConnections();
		Map<String, ArrayList<IConnection>> typemap = new HashMap<String, ArrayList<IConnection>>();
		for (IConnection connect : connects) {
			if (!typemap.keySet().contains(connect.getType())) {
				ArrayList<IConnection> temp = new ArrayList<IConnection>();
				temp.add(connect);
				typemap.put(connect.getType(), temp);
			} else {
				typemap.get(connect.getType()).add(connect);
			}
		}
		
		String[] differentStrings = new String[typemap.keySet().size()];
		int count = 0;
		
		for (String k : typemap.keySet()) {
			differentStrings[count] = "";
			for (IConnection c : typemap.get(k)) {
				String cnct = c.getConnection();
				if (!differentStrings[count].contains(c.getEdge())) {
					differentStrings[count] = differentStrings[count] + c.getEdge() + "\n";
				}
				if (!differentStrings[count].contains(cnct)) {
					differentStrings[count] = differentStrings[count] + cnct;
				}
			}
			count++;
		}
		
		for (int i = 0; i < differentStrings.length; i++) {
			this.connection = this.connection + differentStrings[i] + "\n";
		}
//		System.out.println(connection);
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}
}
