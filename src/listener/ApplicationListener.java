/**
 * 
 */
package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import util.CommUtil;

/**
 * @author Œ­³¿³¿
 *
 */
public class ApplicationListener implements ServletContextListener {

	/*
	 * £¨·Ç Javadoc£©
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	/*
	 * £¨·Ç Javadoc£©
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext application = event.getServletContext();
		CommUtil commUtil = CommUtil.newInstance();
		application.setAttribute("test", commUtil.getDictMap());
		application.setAttribute("groupMap", commUtil.getGroupMap());
		application.setAttribute("roleMap", commUtil.getRoleMap());
		application.setAttribute("clsMap", commUtil.getClassMap());
	}

}
