package Utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigFileLoader {

	private String filePath;
	private Map<String, String> fileInformation;

	public ConfigFileLoader(String selectedFilePath) {
		this.filePath = selectedFilePath;
		this.fileInformation = new HashMap<String, String>();
	}

	public void load() {
		String line = null;
		try {
			FileReader fr = new FileReader(this.filePath);
			BufferedReader bufferedReader = new BufferedReader(fr);

			while ((line = bufferedReader.readLine()) != null) {
				if (line != null && line != "" && line != "\n" && line != "\r") {
					line = line.replaceFirst(":", "");
					String[] lineparts = line.split("\\s");
					if (lineparts.length == 2) {
						this.fileInformation.put(lineparts[0].toLowerCase(), lineparts[1]);
					} else if (lineparts.length == 1 && lineparts[0].length() > 1) {
						System.err.println("Insufficient information in the configuration!");
					} else if (lineparts.length > 2) {
						String x = "";
						for (int i = 1; i < lineparts.length; i++) {
							if (i < lineparts.length - 1) {
								x += lineparts[i] + " ";
							} else {
								x += lineparts[i];
							}
						}
						this.fileInformation.put(lineparts[0].toLowerCase(), x);
					}
				}
			}

			bufferedReader.close();
		} catch (IOException e) {
			System.out.println("Error in reading file");
		}
	}

	public Map<String, String> getFileInformation() {
		return fileInformation;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
