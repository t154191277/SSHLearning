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

public class RFileChannel {
	public static void main(String[] args) {
		method1();
		method2();
	}
	
	public static void method2(){
		InputStream is = null;
		try {
			//�������롢
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
	
	
	//Bufferһ����ѭ���漸�����裺
	//����ռ䣨ByteBuffer buf = ByteBuffer.allocate(1024); ����һ��allocateDirector�����ٳ�����
	//д�����ݵ�Buffer(int bytesRead = fileChannel.read(buf);)
	//����filp()������ buf.flip();��
	//��Buffer�ж�ȡ���ݣ�System.out.print((char)buf.get());��
	//����clear()��������compact()����
	public static void method1(){
		RandomAccessFile aFile = null;
		//��������
		Charset charset = Charset.forName("GBK");
		try {
			//��дȨ�޵Ļ����ܻ᱾û������ļ����ͻ�creatһ��������catch ���� fileNotFileException
			aFile = new RandomAccessFile("src/io.txt","r");
			FileChannel fc = aFile.getChannel();
			ByteBuffer buf = ByteBuffer.allocate(1024);
			
			int bytesRead = fc.read(buf);
			System.out.println(bytesRead);
			while(bytesRead != -1){
				//��bufд��channel
				//position���0������limit���֮ǰ��position��ֵ,(�������ݵ�����)
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
