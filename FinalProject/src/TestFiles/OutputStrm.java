package TestFiles;

import java.io.IOException;
import java.io.OutputStream;

import testfile2.SubstitutionCipher;

public class OutputStrm extends OutputStream{
	private OutputStream out;
	public OutputStrm(OutputStream out){
		this.out = out;
	}

	@Override
	public void write(int arg0) throws IOException {
		SubstitutionCipher sc = new SubstitutionCipher();
		if (arg0 != -1){
			this.out.write(sc.encrypt((char)arg0));
			return;
		}
		this.out.write(arg0);
	}
	
}
