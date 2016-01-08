package asm;

import java.util.ArrayList;
import java.util.List;

public class TestJavaCodeASMParsing {
	protected int a;
	public String b;
	private List<String[]> c;
	
	public TestJavaCodeASMParsing() {
		this.a = 1;
		this.b = "hdfja";
		this.c = new ArrayList<>();
	}
	
	public int getA(){
		return this.a;
	}

	public String getB() {
		return b;
	}

	public List<String[]> getC() {
		String[] t = {"1", "2", "3"};
		this.c.add(t);
		return c;
	}
	
	
}
