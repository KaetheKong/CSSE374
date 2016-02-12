package toUMLimplement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.IConnection;
import interfaces.IData;

public class ComputeUMLConnection {
	private String connection;
	private IData<IConnection> cnctd;

	public ComputeUMLConnection(IData<String> cd, IData<IConnection> cnctd) {
		this.cnctd = cnctd;
		this.connection = "";
	}

	public void findConnection() {
		List<IConnection> connects = this.cnctd.getData();
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
				if (cnct != null && cnct.length() >= 3) {
					if (!differentStrings[count].contains(c.getEdge().replace("\\s", ""))) {
						differentStrings[count] = differentStrings[count] + c.getEdge() + "\n";
					}
					if (!differentStrings[count].contains(cnct.replace("\\s", ""))) {
						differentStrings[count] = differentStrings[count] + cnct;
					}
				}
			}
			count++;
		}

		for (int i = 0; i < differentStrings.length; i++) {
			if (!this.connection.contains(differentStrings[i])) {
				this.connection = this.connection + differentStrings[i] + "\n";
			}
		}
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}
}
