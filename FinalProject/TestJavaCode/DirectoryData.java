package problem;

import java.util.ArrayList;
import java.util.List;

public class DirectoryData implements Subject{
	private String directory;
	private List<String> files;
	private List<Observer<DirectoryData>> observers;
	
	public DirectoryData(String directory){
		this.directory = directory;
		this.files = new ArrayList<String>();
		this.observers = new ArrayList<Observer<DirectoryData>>();
	}

	@Override
	public void registerObserver(Observer o) {
		this.observers.add(o);
		
	}

	@Override
	public void removeObserver(Observer o) {
		this.observers.remove(o);
		
	}
	
	public List<String> getFiles(){
		return this.files;
	}
	
	public void addfiles(String filename){
		files.add(filename);
		dataChanged();
	}
	
	@Override
	public void notifyObservers() {
		for(Observer o : observers){
			o.update(this);
		}
	}
	
	public void dataChanged(){
		notifyObservers();
	}
}
