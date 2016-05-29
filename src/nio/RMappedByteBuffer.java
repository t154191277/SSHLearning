package nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class RMappedByteBuffer {
	public static void main(String[] args) {
		readFileByByteBuffer();
		System.out.println("============");
		readFileByMappedByteBuffer();
	}
	
	public static void readFileByByteBuffer(){
		RandomAccessFile file = null;
		FileChannel fc = null;
		Charset charset = Charset.forName("GBK");
		try {
			file = new RandomAccessFile("src/bigfile.txt", "rw");
			fc = file.getChannel();
			long time1 = System.currentTimeMillis();
			ByteBuffer buf = ByteBuffer.allocate((int) file.length());
			int bytesRead = fc.read(buf);
			long time2 = System.currentTimeMillis();
			System.out.println("time1:"+(time2 - time1));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fc!=null){
				try {
					fc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}if(file!=null){
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
//	MappedByteBuffer��NIO������ļ��ڴ�ӳ�䷽������д���ܼ��ߡ�
//	JAVA������ļ���һ����BufferedReader,BufferedInputStream����������IO�࣬��������ļ�����Ļ�������ķ�ʽ�ǲ���MappedByteBuffer��
	public static void readFileByMappedByteBuffer(){
		RandomAccessFile file = null;
		FileChannel fc = null;
		try {
			file = new RandomAccessFile("src/bigfile.txt", "rw");
			fc = file.getChannel();
			long time1 = System.currentTimeMillis();
			MappedByteBuffer buf = fc.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			long time2 = System.currentTimeMillis();
			System.out.println("time2:"+(time2 - time1));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fc!=null){
				try {
					fc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}if(file!=null){
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
