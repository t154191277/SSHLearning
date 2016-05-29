package thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//在创建线程池时，线程池的大小是个很重要的考虑因素，如果创建的线程太多（空闲线程太多），
//则会消耗掉很多系统资源，如果创建的线程太少，客户端还是有可能等很长时间才能获得服务。
//因此，线程池的大小需要根据负载情况进行调整，以使客户端连接的时间最短，理想的情况是有一个调度的工具，
//可以在系统负载增加时扩展线程池的大小（低于大上限值），负载减轻时缩减线程池的大小。
//一种解决的方案便是使用Java中的Executor接口。
//
//Executor接口代表了一个根据某种策略来执行Runnable实例的对象，其中可能包括了排队和调度等细节，
//或如何选择要执行的任务。Executor接口只定义了一个方法：
public class ServerExecutor {
	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(20006);
			Socket client = null;
			//通过调用Executors类的静态方法，创建一个ExecutorService实例
	        //ExecutorService接口是Executor接口的子接口
			Executor service = Executors.newCachedThreadPool();
			boolean f = true;
			while(f){
				client = server.accept();
				System.out.println("与客户端连接成功！");
				//调用execute()方法时，如果必要，会创建一个新的线程来处理任务，但它首先会尝试使用已有的线程，
	            //如果一个线程空闲60秒以上，则将其移除线程池；
	            //另外，任务是在Executor的内部排队，而不是在网络中排队
				service.execute(new ServerThread(client));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
