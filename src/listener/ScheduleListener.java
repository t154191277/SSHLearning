package listener;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 比servlet启动早，用于启动web的后台程序
 * 1.写好class文件，2.配置
 * @author Administrator
 *
 */
public class ScheduleListener implements ServletContextListener {
	
	private Timer timer = null;

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		timer.cancel();
		sce.getServletContext().log(new Date() + "计时器被销毁！！");
		sce.getServletContext().log(new Date() + "计时器被销毁！！");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		timer = new Timer(true);
		sce.getServletContext().log(new Date() + "计时器已经启动...");
		sce.getServletContext().log(new Date() + "计时器已经启动...");
//		timer.schedule(new MyTask(),  0 , 2 * 60 * 1000);
		sce.getServletContext().log(new Date() + "计时器执行一次...");
		sce.getServletContext().log(new Date() + "计时器执行一次...");
	}
	
	static class MyTask extends TimerTask{
		
		private static boolean isRunning = false;

		@Override
		public void run() {
			if(!isRunning){
				isRunning = true;
				System.out.print(new Date() + " 任务开始");
				for(int i = 0; i < 100; i ++){
					System.out.println(new Date() + " 任务完成" + i +"/" + 100);
					try {
						Thread.sleep(80);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				isRunning = false;
				System.out.println(new Date() + " 所有任务完成！");
			}else{
				System.out.println(new Date() + " 任务退出");
			}
		}
		
	}

}
