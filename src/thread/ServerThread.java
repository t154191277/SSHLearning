package thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import sun.rmi.runtime.Log;

//	【Java TCP/IP Socket】基于线程池的TCP服务器、
//	实现步骤
//
//	1、与一客户一线程服务器一样，线程池服务器首先创建一个ServerSocket实例。
//
//	2、然后创建N个线程，每个线程反复循环，从（共享的）ServerSocket实例接收客户端连接。
//	当多个线程同时调用一个ServerSocket实例的accept（）方法时，
//	它们都将阻塞等待，直到一个新的连接成功建立，然后系统选择一个线程，为建立起的连接提供服务，其他线程则继续阻塞等待。
//
//	3、线程在完成对一个客户端的服务后，继续等待其他的连接请求，而不终止。如果在一个客户端连接被创建时，
//	没有线程在accept（）方法上阻塞（即所有的线程都在为其他连接服务）
//	，系统则将新的连接排列在一个队列中，直到下一次调用accept（）方法。
public class ServerThread implements Runnable{


	private Socket client = null;
	
	public ServerThread(Socket client){
		this.client = client;
	}
	
	//处理通信细节的静态方法，这里主要是方便线程池服务器的调用
	public static void execute(Socket client){
		BufferedReader buf = null ;
		PrintStream out = null;
		try {
			
			//获取Socket的输出流，用来向客户端发送数据  
			out = new PrintStream(client.getOutputStream());
			//获取Socket的输入流，用来接收从客户端发送过来的数据
			buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
			boolean flag = true;
			while(flag){
				
				String str = buf.readLine();
				System.out.println(str);
				if(str == null || "".equals(str)){
					out.print("null:");
					flag = false;
				}else{
					//将接收到的字符串前面加上echo，发送到对应的客户端
					out.print("echo:"+ str);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(buf!=null){
				try {
					buf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out != null){
				out.close();
			}
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		execute(client);
		
	}
}
