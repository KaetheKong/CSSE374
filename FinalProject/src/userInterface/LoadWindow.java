package userInterface;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import Utilities.ConfigFileLoader;

public class LoadWindow extends JPanel implements ActionListener {

	private static final long serialVersionUID = 7392724284805849056L;

	private JButton search;
	private JFileChooser chooser;
	private String chooserTitle;
	private String selectedFile;
	private String selectedDirectory;
	private ConfigFileLoader loader;

	public LoadWindow() {
		search = new JButton("Load Config");
		search.addActionListener(this);
		this.add(search, BorderLayout.CENTER);
		this.loader = new ConfigFileLoader(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle(chooserTitle);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setAcceptAllFileFilterUsed(true);

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			this.selectedFile = this.chooser.getSelectedFile().toString();
			this.selectedDirectory = this.chooser.getCurrentDirectory().toString();
			this.loader.setFilePath(this.selectedFile);
			this.loader.load();
		} else {
			this.selectedFile = "No Selected File!";
			this.selectedDirectory = "";
		}
	}
	
	public String getSelectedFile() {
		return selectedFile;
	}

	public String getSelectedDirectory() {
		return selectedDirectory;
	}
	
	public Map<String, String> getFileInfo() {
		return this.loader.getFileInformation();
	}
}
