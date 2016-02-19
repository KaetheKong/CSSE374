package userInterface;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import toUMLimplement.UMLGenerator;

public class AnalyzeWindow extends JPanel implements ActionListener, PropertyChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1517103548631891142L;

	private JButton Analyze;
	private JProgressBar pb;
	private LoadWindow lw;
	private DotLauncher dl;
	private OutFrame op;
	private JTextArea taskOutput;
	private Task task;
	private int error = 0;

	private static final String MAP_FIELD_1 = "Input-Classes";
	// private static final String MAP_FIELD_2 = "Input-Folder";
	private static final String MAP_FIELD_3 = "Output-Directory";
	private static final String MAP_FIELD_4 = "Dot-Path";
	// private static final String MAP_FIELD_5 = "Phases";
	private static final String MAP_FIELD_6 = "Adapter-MethodDelegation";
	private static final String MAP_FIELD_7 = "Decorator-MethodDelegation";
	private static final String MAP_FIELD_8 = "Composite-MethodDelegation";
	// private static final String MAP_FIELD_8 = "Singleton-RequireGetInstance";

	public AnalyzeWindow(LoadWindow lw, UserInterfaceLoader uifl) {
		this.lw = lw;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.Analyze = new JButton("Analyze");
		this.Analyze.addActionListener(this);
		this.add(this.Analyze);
		this.Analyze.setAlignmentX(CENTER_ALIGNMENT);

		this.pb = new JProgressBar(0, 100);
		this.pb.setValue(0);
		this.pb.setStringPainted(true);
		this.add(this.pb);

		this.dl = new DotLauncher(null, null);
		this.op = new OutFrame(null, this.lw.getFileInfo(), uifl);
		
		taskOutput = new JTextArea(5, 20);
		taskOutput.setMargin(new Insets(5, 5, 5, 5));
		taskOutput.setEditable(false);
		this.add(taskOutput);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.error = 0;
		this.taskOutput.removeAll();
		this.pb.setValue(0);
		pb.setIndeterminate(false);
		this.Analyze.setEnabled(false);
		task = new Task();
		task.addPropertyChangeListener(this);
		task.execute();
	}
	
	/*
	 * Referenced from progress bar demo 2 from
	 * http://docs.oracle.com/javase/tutorial/uiswing/components/progress.html
	 */
	class Task extends SwingWorker<Void, Void> {
		/*
		 * Main task. Executed in background thread.
		 */
		@Override
		public Void doInBackground() {
			int progress = 0;
			setProgress(0);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException ignore) {
			}
			while (progress < 100) {
				if (lw.getSelectedDirectory() == null || lw.getSelectedDirectory().equals("")) {
					JOptionPane.showMessageDialog(AnalyzeWindow.this,
							"There is no configuration file loaded yet! Load Configuration file first, then analyze!",
							"Warning", JOptionPane.WARNING_MESSAGE);
					setProgress(100);
					progress = 100;
					error = 1;
				} else {
					Map<String, String> fileInfo = lw.getFileInfo();
					String args = "uml ";
					String classesToDrawUml = fileInfo.get(MAP_FIELD_1.toLowerCase());
					classesToDrawUml = classesToDrawUml.replace(",", " ");
					args += classesToDrawUml + " ";
					args += "true ";
					args += "true ";
					if (fileInfo.get(MAP_FIELD_6.toLowerCase()) != null) args += fileInfo.get(MAP_FIELD_6.toLowerCase()) + " ";
					else args += "-1";
					if (fileInfo.get(MAP_FIELD_7.toLowerCase()) != null) args += fileInfo.get(MAP_FIELD_7.toLowerCase()) + " ";
					else args += "-1";
					if (fileInfo.get(MAP_FIELD_8.toLowerCase()) != null) args += fileInfo.get(MAP_FIELD_8.toLowerCase()) + " ";
					else args += "-1";

					try {
						Thread.sleep(1000);
					} catch (InterruptedException ignore) {
					}

					progress += 10;
					pb.setValue(progress);
					taskOutput.append("Finished parsing the arguments\n");

					UMLGenerator ugor = new UMLGenerator(args.split("\\s"));
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ignore) {
					}
					
					progress += 10;
					pb.setValue(progress);
					taskOutput.append("Finished initializing the uml generator\n");
					
					try {
						ugor.run();

						try {
							Thread.sleep(1000);
						} catch (InterruptedException ignore) {
						}

						progress += 32;
						pb.setValue(progress);
						taskOutput.append("Finished examine the classes and running the uml\n");

						String dotpath = fileInfo.get(MAP_FIELD_4.toLowerCase());
						op.setUgor(ugor);
						
						try {
							Thread.sleep(1000);
						} catch (InterruptedException ignore) {
						}
						
						progress += 29;
						pb.setValue(progress);
						taskOutput.append("Finished setting up and initializing the panel\n");

						dl.setPath(dotpath);
						dl.setOutpath(fileInfo.get(MAP_FIELD_3.toLowerCase()));
						
						try {
							Thread.sleep(1000);
						} catch (InterruptedException ignore) {
						}
						
						dl.launch(true);
						progress += 21;
						pb.setValue(progress);
						taskOutput.append("Finished launching dot\n");
						
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			return null;
		}

		/*
		 * Executed in event dispatch thread
		 */
		public void done() {
			Analyze.setEnabled(true);
			if (error == 0) {
				taskOutput.append("Done!\n");
				op.init();
			} else {
				taskOutput.append("You need to load a configuration File first!\n");
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			this.pb.setIndeterminate(false);
			this.pb.setValue(progress);
			taskOutput.append(String.format("Completed %d%% of task.\n", progress));
		}

	}

}
