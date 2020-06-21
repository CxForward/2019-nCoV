package cn.javaee.servletListener;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import cn.javaee.servletListener.impl.StatisticsTask;

public class StatisticsContextListener implements ServletContextListener {
	private Timer timer = null;
	Logger logger = Logger.getLogger(StatisticsContextListener.class);
	/**
	 * 这个方法在Web应用服务做好接受请求的时候被调用。
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		timer = new Timer(true);
		logger.info("定时器已启动");
		//event.getServletContext().log("定时器已启动");
		//timer.schedule(task, delay, period)　
		//delay为long,period为long：从现在起过delay毫秒以后，每隔period。毫秒执行一次。
		timer.schedule(new StatisticsTask(), 0,
				 60*60*1000);// 每隔1个小时
		//event.getServletContext().log("已经添加任务调度表");
		logger.info("已经添加任务调度表");
	}

	/**
	 * 这个方法在Web应用服务被移除，没有能力再接受请求的时候被调用。
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel();
		//event.getServletContext().log("定时器销毁");
		logger.info("定时器销毁");
	}
}
