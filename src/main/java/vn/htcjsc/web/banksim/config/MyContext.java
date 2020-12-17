/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htcjsc.web.banksim.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.log4j.Logger;
import vn.htcjsc.web.banksim.common.DateProc;
import vn.htcjsc.web.banksim.common.Tool;
import vn.htcjsc.web.banksim.model.SysAccount;

/**
 *
 * @author Private
 */
@WebListener
public class MyContext implements ServletContextListener, HttpSessionListener {

    static Logger logger = Logger.getLogger(MyContext.class);
    private static final Map<String, HttpSession> SESSION_ONLINE = new HashMap<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("============>contextInitialized....");
//        Map<String, Integer> hasMap = new HashMap<>();
//        for (MyConfig.STATUS one : MyConfig.STATUS.values()) {
//            hasMap.put(one.toString(), one.val);
//        }
//        sce.getServletContext().setAttribute("status", hasMap);
//        sce.getServletContext().setAttribute("lstatus", MyConfig.getStatus());
//        sce.getServletContext().setAttribute("dProc", new DateProc());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("============>contextDestroyed....");
    }

    @Override
    public void sessionCreated(HttpSessionEvent evt) {
        HttpSession session = evt.getSession();
        if (session.isNew()) {
            Tool.out("Event New sessionCreated: " + evt.getSession().getId() + ":" + DateProc.createTimestamp());
        } else {
            Tool.out("Event Old sessionCreated ?? : " + evt.getSession().getId() + ":" + DateProc.createTimestamp());
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent evt) {
        HttpSession evtSession = evt.getSession();
        synchronized (SESSION_ONLINE) {
            Collection<HttpSession> listSession = SESSION_ONLINE.values();
            if (listSession != null && listSession.size() > 0) {
                for (HttpSession oneSession : listSession) {
                    if (oneSession.getId().equals(evtSession.getId())) {
                        SysAccount userlogin = (SysAccount) oneSession.getAttribute(MyConfig.USER_SESSION_NAME);
                        if (userlogin != null) {
                            SESSION_ONLINE.remove(userlogin.getUsername());
                            Tool.out("Ngon lay duoc User [" + userlogin.getUsername() + "] Theo Session [" + oneSession.getId() + "]:" + DateProc.createTimestamp());
                        } else {
                            Tool.out("Khong lay duoc User theo Session [" + oneSession.getId() + "]:" + DateProc.createTimestamp());
                        }
                        oneSession.invalidate();
                        break;
                    }
                }
            }
            SESSION_ONLINE.notify();
        }
    }

    public static boolean checkUserOneline(String user) {
        synchronized (SESSION_ONLINE) {
            if (!Tool.checkNull(user)) {
                HttpSession userSession = SESSION_ONLINE.get(user);
                return userSession != null;
            } else {
                return false;
            }
        }
    }

    // here is our own method to return the number of current sessions
    public static int getNumberOfSessions() {
        synchronized (SESSION_ONLINE) {
            return SESSION_ONLINE.size();
        }
    }

    public static void putSessionOnline(String user, HttpSession session) {
        synchronized (SESSION_ONLINE) {
            SESSION_ONLINE.put(user, session);
            SESSION_ONLINE.notify();
        }
    }

    public static void logOutSession(String user) {
        synchronized (SESSION_ONLINE) {
            try {
                HttpSession oneSession = SESSION_ONLINE.get(user);
                if (oneSession != null) {
                    // Dau Tien Phai Remove trong quan ly truoc da de deligate no tu huy
                    SESSION_ONLINE.remove(user);
                    SysAccount userlogin = (SysAccount) oneSession.getAttribute(MyConfig.USER_SESSION_NAME);
                    if (userlogin != null) {
                        SESSION_ONLINE.remove(userlogin.getUsername());
                        Tool.out("Tim thay 1 User can Logout and Remove From Cache [" + userlogin.getUsername() + "]");
                    }
                    oneSession.removeAttribute(MyConfig.USER_SESSION_NAME);
                    oneSession.invalidate();
                }
            } catch (Exception e) {
                logger.error(Tool.getLogMessage(e));
            }

            SESSION_ONLINE.notify();
        }
    }

    public static void removeSessionOnline(String user) {
        synchronized (SESSION_ONLINE) {
            SESSION_ONLINE.remove(user);
            SESSION_ONLINE.notify();
        }
    }

}
