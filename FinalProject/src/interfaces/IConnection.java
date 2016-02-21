package interfaces;

import classes.ClassClass;

public interface IConnection {
	public String getEdge();
	public String getConnection();
	public String getType();
	public void setIncludeJava(boolean x);
	public void setCc(ClassClass cc);
}
