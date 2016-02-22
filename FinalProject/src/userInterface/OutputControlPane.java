package userInterface;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import Data.DesignPatternGenInfoData;
import classes.ClassClass;
import interfaces.IData;
import interfaces.IDesignPattern;
import toUMLimplement.UMLGenerator;

public class OutputControlPane extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2526169158117758457L;

	private static final String MAP_FIELD_3 = "Output-Directory";
	private static final String MAP_FIELD_4 = "Dot-Path";
	private static final String MAP_FIELD_6 = "Adapter-MethodDelegation";
	private static final String MAP_FIELD_7 = "Decorator-MethodDelegation";
	private static final String MAP_FIELD_8 = "Composite-MethodDelegation";
	private static final String MAP_FIELD_9 = "include-java";
	private static final String MAP_FIELD_10 = "use-classes";

	private UMLGenerator ugo;
	private Map<JCheckBox, List<JCheckBox>> mappingButtons;
	private Map<JCheckBox, List<ClassClass>> clzz;
	private Map<JCheckBox, List<String>> classnames;
	private Map<String, String> fileInfo;
	private DotLauncher dl;
	private OutputPhase ops;
	private UserInterfaceLoader oifl;
	private DesignPatternGenInfoData dpgid;
	private JButton reload;

	public OutputControlPane(UMLGenerator ug, Map<String, String> fileInfo, OutputPhase ops, UserInterfaceLoader uifl) {
		this.ugo = ug;
		this.fileInfo = fileInfo;
		this.mappingButtons = new HashMap<JCheckBox, List<JCheckBox>>();
		this.clzz = new HashMap<JCheckBox, List<ClassClass>>();
		this.classnames = new HashMap<JCheckBox, List<String>>();
		this.setLayout(null);
		this.setBorder(new LineBorder(Color.BLACK, 1, true));
		this.dl = new DotLauncher(null, null);
		this.ops = ops;
		this.dpgid = uifl.getDpfid();
		this.reload = new JButton("Reload");
		this.add(this.reload);
		this.reload.setLocation(50, 800);
		this.reload.setBounds(50, 800, 100, 20);
		this.oifl = uifl;
	}

	public void setFileInfo(Map<String, String> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public void setButtons() {
		reload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ops.dispose();
				oifl.reload();
			}
		});
		String pss = this.fileInfo.get("phases");
		String[] phases = pss.split(", ");
		int x = 5;
		int y = 10;
		for (String s : phases) {
			for (String s1 : this.dpgid.getDesignPatternAgg()) {
				if (s.toLowerCase().contains(s1.toLowerCase())) {
					JCheckBox rb1 = new JCheckBox(s1);
					this.mappingButtons.put(rb1, new ArrayList<JCheckBox>());
					rb1.setForeground(this.dpgid.getColorbyName(s1));
					rb1.addActionListener(this);
					rb1.setBounds(x, y, 190, 20);
					this.add(rb1);
					rb1.setLocation(x, y);
					y += 20;

					int count = this.setSubButtons(s.split("-")[0], rb1.getLocation().x, rb1.getLocation().y);
					y += 20 * count;
					break;
				}
			}
		}
	}

	public int setSubButtons(String type, int lx, int ly) {
		Map<String, ClassClass> classes = ugo.getClasses();
		Map<String, ClassClass> addclasses = ugo.getAdditionalclasses();
		int c = this.setupGroupOfButtons(type, classes, lx, ly);
		int n = this.setupGroupOfButtons(type, addclasses, lx, ly + 20 * c);
		return n + c;
	}

	public int setupGroupOfButtons(String type, Map<String, ClassClass> classes, int lx, int ly) {

		List<String> dpmapping = this.dpgid.getAspectsbyName(type);

		int llx = lx;
		int lly = ly;
		llx += 10;
		int count = 0;

		for (String name : classes.keySet()) {
			Map<String, Boolean> x = classes.get(name).getPatternDetector();
			if (x.get(type)) {
				lly += 20;
				JCheckBox rb = new JCheckBox(classes.get(name).getClassname());
				rb.setLocation(llx, lly);
				rb.setForeground(this.dpgid.getColorbyName(type));
				rb.addActionListener(this);
				this.add(rb);
				rb.setBounds(llx, lly, 180, 20);
				this.putCorrButton(rb, type);
				if (this.clzz.get(rb) == null) {
					List<ClassClass> cses = new ArrayList<ClassClass>();
					List<String> csname = new ArrayList<String>();
					cses.add(classes.get(name));
					csname.add(name);
					this.clzz.put(rb, cses);
					this.classnames.put(rb, csname);
				} else {
					this.clzz.get(rb).add(classes.get(name));
					this.classnames.get(rb).add(name);
				}
				count++;
			} else {
				String umltext = this.ugo.getUMLText();
				int ind = umltext.indexOf(classes.get(name).getClassname().replaceAll("\\$", "") + "[");
				if (ind > 0) {
					umltext = umltext.substring(ind);
				} else {
					umltext = umltext.substring(umltext.indexOf(classes.get(name).getClassname()));
				}
				umltext = umltext.substring(umltext.indexOf("\"{"), umltext.indexOf("|"));
				for (String z : dpmapping) {
					if (umltext.toLowerCase().contains("\\<\\<" + z.toLowerCase() + "\\>\\>")) {
						lly += 20;
						JCheckBox rb = new JCheckBox(classes.get(name).getClassname());
						rb.setLocation(llx, lly);
						rb.setForeground(this.dpgid.getColorbyName(type));
						rb.addActionListener(this);
						this.add(rb);
						rb.setBounds(llx, lly, 180, 20);
						this.putCorrButton(rb, type);
						if (this.clzz.get(rb) == null) {
							List<ClassClass> cses = new ArrayList<ClassClass>();
							List<String> csname = new ArrayList<String>();
							cses.add(classes.get(name));
							csname.add(name);
							this.clzz.put(rb, cses);
							this.classnames.put(rb, csname);
						} else {
							this.clzz.get(rb).add(classes.get(name));
							this.classnames.get(rb).add(name);
						}
						count++;
						break;
					}
				}
			}
		}

		return count;
	}

	public void setUgo(UMLGenerator ugo) {
		this.ugo = ugo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<ClassClass> clsses = new ArrayList<ClassClass>();
		String args = "uml ";

		for (JCheckBox x : this.mappingButtons.keySet()) {
			if (x.isSelected()) {
				List<JCheckBox> subs = this.mappingButtons.get(x);
				for (JCheckBox y : subs) {
					y.setSelected(true);
				}
			}
		}
		for (JCheckBox x : this.mappingButtons.keySet()) {
			List<JCheckBox> subs = this.mappingButtons.get(x);
			for (JCheckBox y : subs) {
				if (y.isSelected()) {
					clsses.addAll(this.clzz.get(y));
					for (String name : this.classnames.get(y)) {
						args += name + " ";
					}
					System.out.println(args);
				}
			}
		}

		args += "true ";
		if (fileInfo.get(MAP_FIELD_10.toLowerCase()) != null)
			args += fileInfo.get(MAP_FIELD_9.toLowerCase()) + " ";
		else
			args += "true ";
		if (fileInfo.get(MAP_FIELD_6.toLowerCase()) != null)
			args += fileInfo.get(MAP_FIELD_6.toLowerCase()) + " ";
		else
			args += "-1";
		if (fileInfo.get(MAP_FIELD_7.toLowerCase()) != null)
			args += fileInfo.get(MAP_FIELD_7.toLowerCase()) + " ";
		else
			args += "-1";
		if (fileInfo.get(MAP_FIELD_8.toLowerCase()) != null)
			args += fileInfo.get(MAP_FIELD_8.toLowerCase()) + " ";
		else
			args += "-1";

		UMLGenerator ugor = new UMLGenerator(args.split("\\s"));
		IData<IDesignPattern> dpds = this.ugo.getDpd();
		List<IDesignPattern> alldata = dpds.getData();
		for (int i = 0; i < alldata.size(); i++) {
			ugor.addDesignPattern(alldata.get(i));
		}

		try {
			ugor.run();
			String dotpath = fileInfo.get(MAP_FIELD_4.toLowerCase());
			this.dl.setOutpath(fileInfo.get(MAP_FIELD_3.toLowerCase()));
			this.dl.setPath(dotpath);
			this.dl.launch(false);
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}

		this.ops.repaint();
	}

	public void putCorrButton(JCheckBox cbx, String type) {
		for (JCheckBox cb : this.mappingButtons.keySet()) {
			if (cb.getText().toLowerCase().equals(type.toLowerCase())) {
				this.mappingButtons.get(cb).add(cbx);
				break;
			}
		}
	}

	public boolean checkBoxes() {
		boolean rslt = false;
		for (JCheckBox x : this.mappingButtons.keySet()) {
			rslt = x.isSelected() || rslt;
			List<JCheckBox> lscb = this.mappingButtons.get(x);
			for (JCheckBox cb : lscb) {
				rslt = cb.isSelected() || rslt;
			}
		}
		return rslt;
	}
}
