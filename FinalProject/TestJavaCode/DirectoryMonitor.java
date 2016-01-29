package problem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DirectoryMonitor implements Observer<DirectoryData>{

	@Override
	public void update(DirectoryData data) {
		List<String> filenames = data.getFiles();
		// print the content of the newly added file reversely
		try {
			for (String lines : Files.readAllLines(Paths.get(filenames.get(filenames.size()-1)))){
				for (int i = lines.length()-1; i >= 0; i--){
					System.out.print(lines.charAt(i));
				}
				System.out.println();
			}
		} catch (IOException e) {
			System.out.println("Document does not exist anymore!");
		}
	}

}