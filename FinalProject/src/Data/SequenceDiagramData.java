package Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import classes.FieldClass;
import classes.MethodClass;
import implementation.CodeASM;

public class SequenceDiagramData {
	private String classname;
	private String methodname;
	private List<Map<String, Map<String, ArrayList<String>>>> classnameTomethods;
	
	public SequenceDiagramData(String cn, String mdnm) {
		this.classname = cn;
		this.methodname = mdnm;
		this.classnameTomethods = new ArrayList<Map<String, Map<String, ArrayList<String>>>>();
	}
	
	public void digFiveLayers() throws IOException {
		InnerComputation IC = new InnerComputation();
		IC.digFiveFromCN(this.classname, this.methodname, 1);
	}
	
	private class InnerComputation {
		
		private List<Map<String, Map<String, ArrayList<String>>>> classnameTomethods;
		private List<Map<String, MethodClass>> allmethods;
		private List<Map<String, FieldClass>> allfields;
		private List<Map<String, Map<String, ArrayList<String>>>> allowners;
		private List<Map<String, Map<String, ArrayList<String>>>> allmethodCalls;
		
		public InnerComputation() {
			this.classnameTomethods = new ArrayList<Map<String, Map<String, ArrayList<String>>>>();
			this.allmethods = new ArrayList<Map<String, MethodClass>>();
			this.allfields = new ArrayList<Map<String, FieldClass>>();
			this.allowners = new ArrayList<Map<String, Map<String, ArrayList<String>>>>();
			this.allmethodCalls = new ArrayList<Map<String, Map<String, ArrayList<String>>>>();
		}
		
		public void digFiveFromCN(String classname, String methodname, int count) throws IOException {
			
		}
	}
}
