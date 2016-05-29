package thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import sun.rmi.runtime.Log;

//	��Java TCP/IP Socket�������̳߳ص�TCP��������
//	ʵ�ֲ���
//
//	1����һ�ͻ�һ�̷߳�����һ�����̳߳ط��������ȴ���һ��ServerSocketʵ����
//
//	2��Ȼ�󴴽�N���̣߳�ÿ���̷߳���ѭ�����ӣ�����ģ�ServerSocketʵ�����տͻ������ӡ�
//	������߳�ͬʱ����һ��ServerSocketʵ����accept��������ʱ��
//	���Ƕ��������ȴ���ֱ��һ���µ����ӳɹ�������Ȼ��ϵͳѡ��һ���̣߳�Ϊ������������ṩ���������߳�����������ȴ���
//
//	3���߳�����ɶ�һ���ͻ��˵ķ���󣬼����ȴ��������������󣬶�����ֹ�������һ���ͻ������ӱ�����ʱ��
//	û���߳���accept���������������������е��̶߳���Ϊ�������ӷ���
//	��ϵͳ���µ�����������һ�������У�ֱ����һ�ε���accept����������
public class ServerThread implements Runnable{


	private Socket client = null;
	
	public ServerThread(Socket client){
		this.client = client;
	}
	
	//����ͨ��ϸ�ڵľ�̬������������Ҫ�Ƿ����̳߳ط������ĵ���
	public static void execute(Socket client){
		BufferedReader buf = null ;
		PrintStream out = null;
		try {
			
			//��ȡSocket���������������ͻ��˷�������  
			out = new PrintStream(client.getOutputStream());
			//��ȡSocket�����������������մӿͻ��˷��͹���������
			buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
			boolean flag = true;
			while(flag){
				
				String str = buf.readLine();
				System.out.println(str);
				if(str == null || "".equals(str)){
					out.print("null:");
					flag = false;
				}else{
					//�����յ����ַ���ǰ�����echo�����͵���Ӧ�Ŀͻ���
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
