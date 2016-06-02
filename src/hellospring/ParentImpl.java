package hellospring;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ParentImpl {
	private FileOutputStream fos;
	private File file;
	
	public void init(){
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void destory(){
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public FileOutputStream getFos() {
		return fos;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	
}
