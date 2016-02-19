package userInterface;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class OutputPicture extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5324378012371215238L;
	
	private String outputDir;
	private String outputfilename;
	private BufferedImage img;
	private int imagewidth = 0;
	private int imageheight = 0;
	
	public OutputPicture(Map<String, String> fileInfo) {
		if (fileInfo != null) this.outputDir = fileInfo.get("output-directory");
		this.outputfilename = "tst.png";
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			this.img = ImageIO.read(new File(this.outputDir + "\\\\" + this.outputfilename));
			this.setPreferredSize(new Dimension(this.img.getWidth(), this.img.getHeight()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		g.drawImage(img, 0, 0, null);
				
		this.imageheight = this.img.getHeight();
		this.imagewidth = this.img.getWidth();
	}

	public int getImagewidth() {
		return imagewidth;
	}

	public int getImageheight() {
		return imageheight;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}
}
