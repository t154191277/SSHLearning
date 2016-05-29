package thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ����ʵ�ֻ����̳߳صķ�����
 */
public class ThreadPool {
	
	private static final int THREADPOOL_SIZE = 2;
	
	public static void main(String[] args) throws IOException{
		//�������20006�˿ڼ����ͻ��������TCP���� 
		final ServerSocket server = new ServerSocket(20006);
		
		//���̳߳���һ��ֻ��THREADPOOLSIZE���̣߳�
        //�����THREADPOOLSIZE���߳���accept()�����������ȴ���������
		for(int i = 0 ; i < THREADPOOL_SIZE ; i++){
			 //�����ڲ��࣬��ǰ�߳�Ϊ�����̣߳���û��Ϊ�κοͻ��������ṩ����
			Thread thread = new Thread(){
				public void run() {
					//�߳�Ϊĳ�����ṩ������ѭ���ȴ���������������
					while(true){
						try {
							Socket client = server.accept();
							System.out.println("��ͻ������ӳɹ���");
							 //һ�����ӳɹ������ڸ��߳�����ͻ���ͨ��
							ServerThread.execute(client);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
			};
			//�Ƚ����е��߳̿���
			thread.start();
		}
	}
}
