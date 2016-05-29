package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

//Java NIO�е�DatagramChannel��һ�����շ�UDP����ͨ����
//��ΪUDP�������ӵ�����Э�飬���Բ���������ͨ��������ȡ��д�롣�����ͺͽ��յ������ݰ���
public class RDatagramChannel {
	
	public static void main(String[] args) {
		reveive();
		send();
		
	}
	
	public static void  reveive(){
	    DatagramChannel channel = null;
	    try{
	        channel = DatagramChannel.open();
	        channel.socket().bind(new InetSocketAddress(8080));
	        ByteBuffer buf = ByteBuffer.allocate(1024);
	        buf.clear();
	        channel.receive(buf);

	        buf.flip();
	        while(buf.hasRemaining()){
	            System.out.print((char)buf.get());
	        }
	        System.out.println();

	    }catch(IOException e){
	        e.printStackTrace();
	    }finally{
	        try{
	            if(channel!=null){
	                channel.close();
	            }
	        }catch(IOException e){
	            e.printStackTrace();
	        }
	    }
	}

	public static void send(){
	    DatagramChannel channel = null;
	    try{
	        channel = DatagramChannel.open();
	        String info = "I'm the Sender!";
	        ByteBuffer buf = ByteBuffer.allocate(1024);
	        buf.clear();
	        buf.put(info.getBytes());
	        buf.flip();

	        int bytesSent = channel.send(buf, new InetSocketAddress("localhost",8080));
	        System.out.println(bytesSent);
	    }catch(IOException e){
	        e.printStackTrace();
	    }finally{
	        try{
	            if(channel!=null){
	                channel.close();
	            }
	        }catch(IOException e){
	            e.printStackTrace();
	        }
	    }
	}
}
