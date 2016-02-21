package userInterface;

import java.awt.Color;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import Data.DesignPatternGenInfoData;
import interfaces.IDesignPattern;

public class UserInterfaceLoader {
	private DesignPatternGenInfoData dpfid;
	private JFrame frame;
	private AnalyzeWindow apanel;
	private LoadWindow panel;

	public UserInterfaceLoader() {
		this.dpfid = new DesignPatternGenInfoData();
		this.init();
		this.frame = new JFrame("Design Parser");
		this.panel = new LoadWindow();
		this.apanel = new AnalyzeWindow(this.panel, this);
	}

	public void init() {
		this.dpfid.initialize();
	}

	public void launcher() {

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 400);

		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.getContentPane().add(this.panel);
		frame.getContentPane().add(this.apanel);
		frame.setVisible(true);
	}

	public void addColorSet(String name, Color c) {
		this.dpfid.addColorMapping(name, c);
	}

	public void addDPchecker(String name, List<String> cls) {
		this.dpfid.addAspectInDpChecker(name, cls);
	}

	public void addDpName(String name) {
		this.dpfid.addDpAgg(name);
	}

	public DesignPatternGenInfoData getDpfid() {
		return dpfid;
	}
	
	public void reload() {
		this.frame.dispose();
		this.frame = new JFrame("Design Parser");
		this.launcher();
	}
	
	public void addDesignPatternData(IDesignPattern idp) {
		this.apanel.addDesignPatternData(idp);
	}
}
