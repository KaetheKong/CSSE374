package TestFiles;

import java.io.IOException;
import java.io.InputStream;

import testfile2.SubstitutionCipher;

public class InputStrm extends InputStream{
	private InputStream in;
	public InputStrm(InputStream in){
		this.in = in;
	}

	@Override
	public int read() throws IOException {
		int ind;
		SubstitutionCipher sc = new SubstitutionCipher();
		if ((ind = this.in.read()) != -1){
			return sc.decrypt((char) ind); 
		}
		return -1;
	}

}
