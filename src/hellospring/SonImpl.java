package hellospring;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SonImpl {
	
	private ParentImpl parentImpl;
	
	public void write(String msg) throws IOException{
		parentImpl.getFos().write(msg.getBytes());
	}
	
	public void init() throws IOException
	{
		parentImpl.getFos().write("³õÊ¼»¯".getBytes());
	}
	
	public void destory() throws IOException{
		parentImpl.getFos().write("Ïú»Ù".getBytes());
	}

	public void setParentImpl(ParentImpl parentImpl) {
		this.parentImpl = parentImpl;
	}
}
