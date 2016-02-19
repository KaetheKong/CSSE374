package userInterface;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import toUMLimplement.UMLGenerator;

public class OutputPhase extends JFrame {

	/**
		 * 
		 */
	private static final long serialVersionUID = -2695333464234067555L;

	private OutputControlPane oc;
	private OutputPicture op;
	private JScrollPane jsp;
	private JScrollPane tsp;
	private Map<String, String> fileInfo;

	public OutputPhase(UMLGenerator ugo, Map<String, String> fileInfo, UserInterfaceLoader uifl) {
		this.setLayout(null);
		this.setSize(new Dimension(1030, 950));
		this.op = new OutputPicture(fileInfo);
		this.oc = new OutputControlPane(ugo, fileInfo, this, uifl);
		this.fileInfo = fileInfo;
	}

	public void run() {
		
		this.oc.setButtons();
		
		this.tsp = new JScrollPane();
		this.tsp.setViewportView(this.oc);
		this.tsp.setAutoscrolls(true);
		
		this.tsp.setLocation(0, 10);
		this.tsp.setBounds(0, 10, 230, 900);
		this.oc.setPreferredSize(new Dimension(200, 900));

		this.op.setOutputDir(this.fileInfo.get("output-directory"));
				
		this.jsp = new JScrollPane();
		this.jsp.setViewportView(this.op);
		this.jsp.setAutoscrolls(true);

		this.jsp.setLocation(231, 10);
		this.jsp.setBounds(231, 10, 770, 900);
		
		this.add(this.tsp);
		this.add(this.jsp);
	}

	public void setUgor(UMLGenerator ugor) {
		this.oc.setUgo(ugor);
	}

	public void setFileInfo(Map<String, String> fileInfo) {
		this.fileInfo = fileInfo;
		this.oc.setFileInfo(fileInfo);
	}
	
	@Override
	public void repaint() {
		super.repaint();
		this.op.repaint();		
		
		this.add(tsp);
		this.add(jsp);		
	}
}
