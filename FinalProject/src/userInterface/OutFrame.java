package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import toUMLimplement.UMLGenerator;

/*
 * JMenuBar referenced from 
 * http://www.java2s.com/Tutorial/Java/0240__Swing/CreatingJMenuBarComponents.htm
 * 
 */
public class OutFrame {
	
	private OutputPhase outputPhase;
	private Map<String, String> fileinfo;
	
	public OutFrame(UMLGenerator ugo, Map<String, String> fileInfo, UserInterfaceLoader uifl) {
		outputPhase = new OutputPhase(ugo, fileInfo, uifl);
		this.fileinfo = fileInfo;
	}
	
	public void init() {
		outputPhase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar menuBar = new JMenuBar();

	    JMenu fileMenu = new JMenu("File");
	    fileMenu.setMnemonic(KeyEvent.VK_F);
	    menuBar.add(fileMenu);

	    JMenuItem newMenuItem = new JMenuItem("Export", KeyEvent.VK_E);
	    fileMenu.add(newMenuItem);
	    
	    newMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Export Directory");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(true);
				String selectedDirectory = "";

				if (chooser.showOpenDialog(new JPanel()) == JFileChooser.APPROVE_OPTION) {
					selectedDirectory = chooser.getSelectedFile().toString();
					String source = fileinfo.get("output-directory");
					source =  source + "\\\\tst.png";
					String dest = selectedDirectory + "\\\\export.png";
					Path sourcept = Paths.get(source);
					Path destpt = Paths.get(dest);
					CopyOption[] options = new CopyOption[]{
							  StandardCopyOption.REPLACE_EXISTING,
							  StandardCopyOption.COPY_ATTRIBUTES
							};
					try {
						Files.copy(sourcept, destpt, options);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					selectedDirectory = "No Selected File!";
					JOptionPane.showMessageDialog(outputPhase, "No selected directory!! retry!!!!");
				}
				
				
			}
	    	
	    });

	    outputPhase.setJMenuBar(menuBar);
		outputPhase.run();
		outputPhase.setVisible(true);
	}
	
	public void setUgor(UMLGenerator ugor) {
		this.outputPhase.setUgor(ugor);
	}
}
