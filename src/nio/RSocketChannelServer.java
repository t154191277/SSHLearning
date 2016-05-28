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
				//非阻塞模式下，read()可能在没有读取到任何数据的情况下就返回了，所以得判断Int返回值，知道具体读取了多少。
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
		//附着对象。能方便的识别某个给定的通道
		sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(BUF_SIZE));
	}
	
	static void handleRead(SelectionKey key) throws IOException{
		SocketChannel sc = (SocketChannel)key.channel();
		//接收对象。
		ByteBuffer buf = (ByteBuffer)key.attachment();
		long bytesRead =sc.read(buf);
		while(bytesRead != 0){
			buf.flip();
			while(buf.hasRemaining()){
				System.out.print((char)buf.get());
			}
			System.out.println();
			//数据没有写完的放到开头下次继续写。
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
			//通过selector监听channel对什么类型的事件感兴趣。
			scc.register(selector, SelectionKey.OP_ACCEPT);
			
			while(true){
				
//				select()阻塞到至少有一个通道在你注册的事件上就绪了。
//				select(long timeout)和select()一样，除了最长会阻塞timeout毫秒(参数)。
//				selectNow()不会阻塞，不管什么通道就绪都立刻返回（译者注：此方法执行非阻塞的选择操作。如果自从前一次选择操作后，没有通道变成可选择的，则此方法直接返回零。）
//				select()方法返回的int值表示有多少通道已经就绪。亦即，自上次调用select()方法后有多少通道变成就绪状态。
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
