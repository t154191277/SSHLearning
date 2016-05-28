package nio;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;

public class FileChannel {
	public static void main(String[] args) {
		method1();
		method2();
	}
	
	public static void method2(){
		InputStream is = null;
		try {
			//中文乱码、
//			InputStreamReader isr = new InputStreamReader(new FileInputStream("src/io.txt"),"utf-8");
			is = new BufferedInputStream(new FileInputStream("src/io.txt"));
			byte[] buf = new byte[1024];
			int bytesRead = is.read(buf);
			while(bytesRead != -1){
				for(int i = 0; i < bytesRead; i++ ){
					System.out.print((char)buf[i]);
				}
				bytesRead = is.read(buf);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void method1(){
		RandomAccessFile aFile = null;
		//中文乱码
		Charset charset = Charset.forName("GBK");
		try {
			//给写权限的话可能会本没有这个文件，就会creat一个。导致catch 不到 fileNotFileException
			aFile = new RandomAccessFile("src/io.txt","r");
			java.nio.channels.FileChannel fc = aFile.getChannel();
			ByteBuffer buf = ByteBuffer.allocate(1024);
			
			int bytesRead = fc.read(buf);
			System.out.println(bytesRead);
			while(bytesRead != -1){
				buf.flip();
				while(buf.hasRemaining()){
					System.out.print(charset.decode(buf));
				}
				
				buf.compact();
				bytesRead = fc.read(buf);
			}
			System.out.println("");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(aFile != null){
				try {
					aFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
