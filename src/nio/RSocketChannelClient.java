package nio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class RSocketChannelClient {
	public static void main(String[] args) {
		client();
		
	}
	
	public static void client(){
		ByteBuffer buf = ByteBuffer.allocate(1024);
		SocketChannel sc = null;
		try {
			sc = SocketChannel.open();
			//非阻塞模式下
			sc.configureBlocking(false);
			sc.connect(new InetSocketAddress("localhost",80));
			System.out.println(sc.finishConnect());
			if(sc.finishConnect()){
				int i = 0;
				//sockerChannel.write()不能保证把数据全部写入。所以需要重复调用直到没有需要写入的数据为止。
				while(true){
					TimeUnit.SECONDS.sleep(1);
					String info = "i'm" + i++ + "-th information form cilent";
					buf.clear();
					buf.put(info.getBytes());
					buf.flip();
					while(buf.hasRemaining()){
						System.out.println(buf);
						sc.write(buf);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
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
	
	
}
