package problem;

import java.util.List;

public class CurrentFilesDisplay implements Observer<DirectoryData>{

	@Override
	public void update(DirectoryData data) {
		List<String> filenames = data.getFiles();
		System.out.println("File got added is: " + filenames.get(filenames.size()-1));
	}

}
