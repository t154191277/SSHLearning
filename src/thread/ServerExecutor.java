package thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//�ڴ����̳߳�ʱ���̳߳صĴ�С�Ǹ�����Ҫ�Ŀ������أ�����������߳�̫�ࣨ�����߳�̫�ࣩ��
//������ĵ��ܶ�ϵͳ��Դ������������߳�̫�٣��ͻ��˻����п��ܵȺܳ�ʱ����ܻ�÷���
//��ˣ��̳߳صĴ�С��Ҫ���ݸ���������е�������ʹ�ͻ������ӵ�ʱ����̣�������������һ�����ȵĹ��ߣ�
//������ϵͳ��������ʱ��չ�̳߳صĴ�С�����ڴ�����ֵ�������ؼ���ʱ�����̳߳صĴ�С��
//һ�ֽ���ķ�������ʹ��Java�е�Executor�ӿڡ�
//
//Executor�ӿڴ�����һ������ĳ�ֲ�����ִ��Runnableʵ���Ķ������п��ܰ������ŶӺ͵��ȵ�ϸ�ڣ�
//�����ѡ��Ҫִ�е�����Executor�ӿ�ֻ������һ��������
public class ServerExecutor {
	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(20006);
			Socket client = null;
			//ͨ������Executors��ľ�̬����������һ��ExecutorServiceʵ��
	        //ExecutorService�ӿ���Executor�ӿڵ��ӽӿ�
			Executor service = Executors.newCachedThreadPool();
			boolean f = true;
			while(f){
				client = server.accept();
				System.out.println("��ͻ������ӳɹ���");
				//����execute()����ʱ�������Ҫ���ᴴ��һ���µ��߳����������񣬵������Ȼ᳢��ʹ�����е��̣߳�
	            //���һ���߳̿���60�����ϣ������Ƴ��̳߳أ�
	            //���⣬��������Executor���ڲ��Ŷӣ����������������Ŷ�
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
