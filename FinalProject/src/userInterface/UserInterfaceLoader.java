package userInterface;

import java.awt.Color;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import Data.DesignPatternGenInfoData;

public class UserInterfaceLoader {
	private DesignPatternGenInfoData dpfid;
	private JFrame frame;

	public UserInterfaceLoader() {
		this.dpfid = new DesignPatternGenInfoData();
		this.init();
		this.frame = new JFrame("Design Parser");
	}

	public void init() {
		this.dpfid.initialize();
	}

	public void launcher() {
		LoadWindow panel = new LoadWindow();
		AnalyzeWindow apanel = new AnalyzeWindow(panel, this);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 400);

		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.getContentPane().add(panel);
		frame.getContentPane().add(apanel);
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
}
