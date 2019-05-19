package listener;


import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import api.LastTime;

public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		// TODO 自动生成的方法存根
		System.out.println("SessionListenerCreated:"+event.getSession().getAttribute("user"));
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// TODO 自动生成的方法存根
		System.out.println("SessionListenerDestroyed:"+event.getSession().getAttribute("user"));
		if("".equals(event.getSession().getAttribute("user"))||event.getSession().getAttribute("user")==null){
			return;
		}
		else {
			HttpSession session = event.getSession();
			LastTime lastTime = new LastTime(session);
			lastTime.update();
		}

	}

}
