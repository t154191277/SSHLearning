package nio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import com.sun.org.apache.bcel.internal.generic.Select;

public class RSocketChannelServer {
	
	private static final int BUF_SIZE = 1024;
	private static final int PORT = 80;
	private static final int TIMEOUT = 3000;
	public static void main(String[] args) {
		selector();
	}
	
	public static void server(){
		ServerSocket sc = null;
		InputStream is = null;
		try {
			sc = new ServerSocket(80);
			int recvMsgSize = 0;
			byte[] recvBuf = new byte[1024];
			while(true){
				Socket cIntSocket = sc.accept();
				SocketAddress clientAddress = cIntSocket.getRemoteSocketAddress();
				System.out.println("Handling client at "+clientAddress);
				is = cIntSocket.getInputStream();
				//������ģʽ�£�read()������û�ж�ȡ���κ����ݵ�����¾ͷ����ˣ����Ե��ж�Int����ֵ��֪�������ȡ�˶��١�
				while((recvMsgSize = is.read(recvBuf)) != -1){
					byte[] temp = new byte[recvMsgSize];
					System.arraycopy(recvBuf, 0, temp, 0, recvMsgSize);
					System.out.println(new String(temp));
				}
				System.out.println(recvMsgSize);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(sc!=null){
				try {
					sc.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void NIOServer(){

	}
	
	static void handleAccept(SelectionKey key) throws IOException{
		ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
		SocketChannel sc = ssc.accept();
		sc.configureBlocking(false);
		//���Ŷ����ܷ����ʶ��ĳ��������ͨ��
		sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(BUF_SIZE));
	}
	
	static void handleRead(SelectionKey key) throws IOException{
		SocketChannel sc = (SocketChannel)key.channel();
		//���ն���
		ByteBuffer buf = (ByteBuffer)key.attachment();
		long bytesRead =sc.read(buf);
		while(bytesRead != 0){
			buf.flip();
			while(buf.hasRemaining()){
				System.out.print((char)buf.get());
			}
			System.out.println();
			//����û��д��ķŵ���ͷ�´μ���д��
			buf.clear();
			bytesRead = sc.read(buf);
		}
		if(bytesRead == -1){
			sc.close();
		}
	}
	
	static void handleWrite(SelectionKey key) throws IOException{
		ByteBuffer buf = (ByteBuffer)key.attachment();
		buf.flip();
		SocketChannel sc = (SocketChannel)key.channel();
		while(buf.hasRemaining()){
			sc.write(buf);
		}
		buf.compact();
	}
	
	public static void selector(){
		Selector selector = null;
		ServerSocketChannel scc = null;
		try {
			selector = Selector.open();
			scc = ServerSocketChannel.open();
			scc.socket().bind(new InetSocketAddress(PORT));
			scc.configureBlocking(false);
			//ͨ��selector����channel��ʲô���͵��¼�����Ȥ��
			scc.register(selector, SelectionKey.OP_ACCEPT);
			
			while(true){
				
//				select()������������һ��ͨ������ע����¼��Ͼ����ˡ�
//				select(long timeout)��select()һ���������������timeout����(����)��
//				selectNow()��������������ʲôͨ�����������̷��أ�����ע���˷���ִ�з�������ѡ�����������Դ�ǰһ��ѡ�������û��ͨ����ɿ�ѡ��ģ���˷���ֱ�ӷ����㡣��
//				select()�������ص�intֵ��ʾ�ж���ͨ���Ѿ��������༴�����ϴε���select()�������ж���ͨ����ɾ���״̬��
				if(selector.select(TIMEOUT) == 0){
					System.out.println("==");
					continue;
				}
				Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
				while(iter.hasNext()){
					SelectionKey key = iter.next();
					if(key.isAcceptable()){
						handleAccept(key);
					}
					
					if(key.isReadable()){
						handleRead(key);
					}
					
					if(key.isWritable()){
						handleWrite(key);
					}
					
					if(key.isConnectable()){
						System.out.println("isConnectable");
					}
					
					iter.remove();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(scc!=null){
				try {
					scc.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(selector!=null){
				try {
					selector.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
