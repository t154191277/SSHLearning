package listener;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ��servlet�����磬��������web�ĺ�̨����
 * 1.д��class�ļ���2.����
 * @author Administrator
 *
 */
public class ScheduleListener implements ServletContextListener {
	
	private Timer timer = null;

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		timer.cancel();
		sce.getServletContext().log(new Date() + "��ʱ�������٣���");
		sce.getServletContext().log(new Date() + "��ʱ�������٣���");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		timer = new Timer(true);
		sce.getServletContext().log(new Date() + "��ʱ���Ѿ�����...");
		sce.getServletContext().log(new Date() + "��ʱ���Ѿ�����...");
//		timer.schedule(new MyTask(),  0 , 2 * 60 * 1000);
		sce.getServletContext().log(new Date() + "��ʱ��ִ��һ��...");
		sce.getServletContext().log(new Date() + "��ʱ��ִ��һ��...");
	}
	
	static class MyTask extends TimerTask{
		
		private static boolean isRunning = false;

		@Override
		public void run() {
			if(!isRunning){
				isRunning = true;
				System.out.print(new Date() + " ����ʼ");
				for(int i = 0; i < 100; i ++){
					System.out.println(new Date() + " �������" + i +"/" + 100);
					try {
						Thread.sleep(80);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				isRunning = false;
				System.out.println(new Date() + " ����������ɣ�");
			}else{
				System.out.println(new Date() + " �����˳�");
			}
		}
		
	}

}
