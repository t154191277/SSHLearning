package nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RScatterAndGatter {
	
//	FileChannel��transferFrom()�������Խ����ݴ�Դͨ�����䵽FileChannel�С�
//	transferTo()���������ݴ�FileChannel���䵽������channel�С�
	
	public static void main(String[] args) {
	}
	
//	��ɢ��scatter����Channel�ж�ȡ��ָ�ڶ�����ʱ����ȡ������д����buffer�С�
//	��ˣ�Channel����Channel�ж�ȡ�����ݡ���ɢ��scatter���������Buffer�С�
//	�ۼ���gather��д��Channel��ָ��д����ʱ�����buffer������д��ͬһ��Channel��
//	��ˣ�Channel �����Buffer�е����ݡ��ۼ���gather�������͵�Channel��
//	scatter / gather����������Ҫ����������ݷֿ�����ĳ��ϣ����紫��һ������Ϣͷ����Ϣ����ɵ���Ϣ��
//	����ܻὫ��Ϣ�����Ϣͷ��ɢ����ͬ��buffer�У���������Է���Ĵ�����Ϣͷ����Ϣ�塣
	public static void gatter(){
		ByteBuffer header = ByteBuffer.allocate(10);
		ByteBuffer body = ByteBuffer.allocate(10);
		
		byte[] b1 = {'0','1'};
		byte[] b2 = {'2','3'};
		
		header.put(b1);
		body.put(b2);
		
		ByteBuffer[] buffs = {header,body};
		FileOutputStream fos = null;
		FileChannel fc = null;
		try {
			fos = new FileOutputStream("src/io.txt");
			fc = fos.getChannel();
			fc.write(buffs);
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
			}if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

}
